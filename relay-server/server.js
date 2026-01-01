
const express = require('express');
const http = require('http');
const WebSocket = require('ws');
const cors = require('cors');
const Database = require('better-sqlite3');
const { v4: uuidv4 } = require('uuid');
const path = require('path');


const app = express();
const server = http.createServer(app);
const wss = new WebSocket.Server({ server });

app.use(cors());
app.use(express.json());


app.use((req, res, next) => {
  console.log(`➡️  ${req.method} ${req.url}`);
  if (req.method !== 'GET') {
    console.log('   📦 Body:', req.body);
  }
  next();
});

let db;
try {
  db = new Database('relay.db');
  db.pragma('journal_mode = WAL');
} catch (err) {
  console.error('❌ Failed to open SQLite DB:', err);
  process.exit(1);
}


try {
  db.exec(`
    CREATE TABLE IF NOT EXISTS guardians (
      guardian_id TEXT PRIMARY KEY,
      name TEXT NOT NULL,
      phone TEXT NOT NULL UNIQUE,
      token TEXT,
      created_at DATETIME DEFAULT CURRENT_TIMESTAMP
    );

    CREATE TABLE IF NOT EXISTS pairings (
      id TEXT PRIMARY KEY,
      guardian_id TEXT NOT NULL,
      elder_id TEXT NOT NULL,
      paired_at DATETIME DEFAULT CURRENT_TIMESTAMP
    );

    CREATE TABLE IF NOT EXISTS pairing_codes (
      code TEXT PRIMARY KEY,
      elder_id TEXT NOT NULL,
      expires_at DATETIME NOT NULL,
      created_at DATETIME DEFAULT CURRENT_TIMESTAMP
    );

    CREATE INDEX IF NOT EXISTS idx_guardian_id ON pairings(guardian_id);
    CREATE INDEX IF NOT EXISTS idx_elder_id ON pairings(elder_id);
  `);
  console.log('✅ Database initialized (including guardians table)');
} catch (err) {
  console.error('❌ Failed to init tables:', err);
  process.exit(1);
}


const connections = new Map();

// ============================================================================
// utility
// ============================================================================

function generatePairingCode() {
  return Math.floor(100000 + Math.random() * 900000).toString(); // 6-digit code
}

function isCodeExpired(expiresAt) {
  return new Date(expiresAt) < new Date();
}

function cleanExpiredCodes() {
  try {
    
    const nowIso = new Date().toISOString();
    const stmt = db.prepare('DELETE FROM pairing_codes WHERE expires_at < ?');
    const result = stmt.run(nowIso);
    if (result.changes > 0) {
      console.log(` Cleaned ${result.changes} expired pairing codes`);
    }
  } catch (err) {
    console.error(' Failed to clean expired codes:', err);
  }
}
fdfdfd


setInterval(cleanExpiredCodes, 5 * 60 * 1000);

// ============================================================================
// api endpoints
// ============================================================================

// Health check
app.get('/health', (req, res) => {
  res.json({ 
    status: 'ok', 
    connections: connections.size,
    timestamp: new Date().toISOString()
  });
});


app.post('/api/guardian/register', (req, res, next) => {
  try {
    const { name, phone } = req.body || {};

    if (!name || !phone) {
      console.warn('⚠️  /api/guardian/register missing name or phone:', req.body);
      return res.status(400).json({ 
        success: false, 
        error: 'Name and phone are required' 
      });
    }

    const phoneNorm = String(phone).trim();

    const lookupStmt = db.prepare('SELECT guardian_id, token, name, phone FROM guardians WHERE phone = ?');
    const existing = lookupStmt.get(phoneNorm);

    if (existing) {
      console.log(`ℹ️  Guardian re-registered (existing) for phone ${phoneNorm}: ${existing.guardian_id}`);
      return res.json({
        success: true,
        data: {
          guardianId: existing.guardian_id,
          token: existing.token,
          name: existing.name,
          phone: existing.phone,
          reused: true
        }
      });
    }


    const guardianId = `guardian_${uuidv4()}`;
    const token = `token_${uuidv4()}`; 

    const insertStmt = db.prepare('INSERT INTO guardians (guardian_id, name, phone, token) VALUES (?, ?, ?, ?)');
    insertStmt.run(guardianId, name, phoneNorm, token);

    console.log(`✅ Guardian registered: ${guardianId} (${name}, ${phoneNorm})`);

    res.json({
      success: true,
      data: {
        guardianId,
        token,
        name,
        phone: phoneNorm
      }
    });
  } catch (err) {
    console.error('❌ Error in /api/guardian/register:', err);
    next(err);
  }
});


app.post('/api/elder/generate-code', (req, res, next) => {
  try {
    const { elderId } = req.body || {};

    if (!elderId) {
      console.warn('⚠️  /api/elder/generate-code missing elderId:', req.body);
      return res.status(400).json({ 
        success: false, 
        error: 'Elder ID is required' 
      });
    }

    const code = generatePairingCode();
    const expiresAt = new Date(Date.now() + 10 * 60 * 1000); // 10 minutes

    const stmt = db.prepare(
      'INSERT INTO pairing_codes (code, elder_id, expires_at) VALUES (?, ?, ?)'
    );

    stmt.run(code, elderId, expiresAt.toISOString());
    
    console.log(`🔑 Pairing code generated: ${code} for elder ${elderId}`);
    
    res.json({
      success: true,
      data: {
        code,
        expiresAt: expiresAt.toISOString()
      }
    });
  } catch (error) {
    console.error('❌ Error generating pairing code:', error);
    next(error);
  }
});


app.post('/api/pair', (req, res, next) => {
  try {
    const { guardianId, pairingCode } = req.body || {};

    if (!guardianId || !pairingCode) {
      console.warn('⚠️  /api/pair missing guardianId or pairingCode:', req.body);
      return res.status(400).json({ 
        success: false, 
        error: 'Guardian ID and pairing code are required' 
      });
    }

    const stmt = db.prepare('SELECT * FROM pairing_codes WHERE code = ?');
    const codeRecord = stmt.get(pairingCode);

    if (!codeRecord) {
      console.warn(`⚠️  Invalid pairing code: ${pairingCode}`);
      return res.status(404).json({ 
        success: false, 
        error: 'Invalid pairing code' 
      });
    }

    if (isCodeExpired(codeRecord.expires_at)) {
      console.warn(`⚠️  Expired pairing code: ${pairingCode}`);
      return res.status(400).json({ 
        success: false, 
        error: 'Pairing code has expired' 
      });
    }

    const elderId = codeRecord.elder_id;

    const checkStmt = db.prepare(
      'SELECT * FROM pairings WHERE guardian_id = ? AND elder_id = ?'
    );
    const existing = checkStmt.get(guardianId, elderId);

    if (existing) {
      console.log(`ℹ️  Guardian ${guardianId} already paired with elder ${elderId}`);
      const deleteStmt1 = db.prepare('DELETE FROM pairing_codes WHERE code = ?');
      deleteStmt1.run(pairingCode);

      return res.json({
        success: true,
        data: {
          elderId,
          message: 'Already paired'
        }
      });
    }

    const pairingId = uuidv4();
    const insertStmt = db.prepare(
      'INSERT INTO pairings (id, guardian_id, elder_id) VALUES (?, ?, ?)'
    );
    
    insertStmt.run(pairingId, guardianId, elderId);
    
    const deleteStmt = db.prepare('DELETE FROM pairing_codes WHERE code = ?');
    deleteStmt.run(pairingCode);
    
    console.log(`✅ Paired: Guardian ${guardianId} ↔ Elder ${elderId}`);
    
    res.json({
      success: true,
      data: {
        elderId,
        pairedAt: new Date().toISOString()
      }
    });
  } catch (error) {
    console.error('❌ Error creating pairing:', error);
    next(error);
  }
});


app.get('/api/guardian/:guardianId/elders', (req, res, next) => {
  try {
    const { guardianId } = req.params;

    const stmt = db.prepare(
      'SELECT elder_id, paired_at FROM pairings WHERE guardian_id = ?'
    );
    const pairings = stmt.all(guardianId);

    const elders = pairings.map(p => ({
      elderId: p.elder_id,
      pairedAt: p.paired_at,
      isOnline: connections.has(p.elder_id)
    }));

    res.json({
      success: true,
      data: elders
    });
  } catch (err) {
    console.error('❌ Error fetching guardian elders:', err);
    next(err);
  }
});

// ============================================================================
// websocket connection handling
// ============================================================================

wss.on('connection', (ws, req) => {
  let deviceId;
  let deviceType;

  try {
    
    const url = new URL(req.url, 'http://localhost');
    deviceId = url.searchParams.get('deviceId');
    deviceType = url.searchParams.get('type'); 
  } catch (err) {
    console.error('❌ Invalid WS URL:', req.url, err);
    ws.close(1008, 'Invalid URL');
    return;
  }

  if (!deviceId || !deviceType) {
    console.log('❌ Connection rejected: missing deviceId or type');
    ws.close(1008, 'Missing deviceId or type');
    return;
  }

  if (!['guardian', 'elder'].includes(deviceType)) {
    console.log('❌ Connection rejected: invalid device type');
    ws.close(1008, 'Invalid device type');
    return;
  }

  
  connections.set(deviceId, {
    ws,
    type: deviceType,
    connectedAt: new Date()
  });

  console.log(`✅ ${deviceType} connected: ${deviceId} (Total connections: ${connections.size})`);

  
  safeSend(ws, {
    type: 'CONNECTION_ACK',
    deviceId,
    timestamp: new Date().toISOString()
  });

  
  ws.on('message', (data) => {
    try {
      const message = JSON.parse(data.toString());
      handleMessage(deviceId, deviceType, message);
    } catch (error) {
      console.error('❌ Error parsing message:', error);
      safeSend(ws, {
        type: 'ERROR',
        error: 'Invalid message format',
        timestamp: new Date().toISOString()
      });
    }
  });

  
  ws.on('close', (code, reason) => {
    connections.delete(deviceId);
    console.log(`🔌 ${deviceType} disconnected: ${deviceId} (code=${code}, reason=${reason}) (Total connections: ${connections.size})`);
  });

  // Handle errors
  ws.on('error', (error) => {
    console.error(`❌ WebSocket error for ${deviceId}:`, error);
  });
});

// ============================================================================
// message routing
// ============================================================================

function handleMessage(fromId, fromType, message) {
  const { type, to, requestId, payload } = message || {};

  console.log(`📨 Message from ${fromType} ${fromId}: ${type} → ${to}`);

  if (!to) {
    console.warn('⚠️  Message missing "to" field:', message);
    return sendError(fromId, requestId, 'Missing "to" field');
  }

  // pairing verification
  try {
    if (fromType === 'guardian') {
      const stmt = db.prepare(
        'SELECT * FROM pairings WHERE guardian_id = ? AND elder_id = ?'
      );
      const pairing = stmt.get(fromId, to);
      
      if (!pairing) {
        console.log('❌ Not paired');
        return sendError(fromId, requestId, 'Not paired with this elder');
      }
    } else if (fromType === 'elder') {
      const stmt = db.prepare(
        'SELECT * FROM pairings WHERE elder_id = ? AND guardian_id = ?'
      );
      const pairing = stmt.get(fromId, to);
      
      if (!pairing) {
        console.log('❌ Not paired');
        return sendError(fromId, requestId, 'Not paired with this guardian');
      }
    }
  } catch (err) {
    console.error('❌ DB error during pairing check:', err);
    return sendError(fromId, requestId, 'Internal server error');
  }


  const recipient = connections.get(to);
  
  if (!recipient) {
    console.log('❌ Recipient offline');
    return sendError(fromId, requestId, 'Recipient is offline');
  }


  const forwardedMessage = {
    ...message,
    from: fromId,
    forwardedAt: new Date().toISOString()
  };

  safeSend(recipient.ws, forwardedMessage);
  console.log(`✅ Message forwarded to ${to}`);
}

function safeSend(ws, obj) {
  try {
    if (ws.readyState === WebSocket.OPEN) {
      ws.send(JSON.stringify(obj));
    }
  } catch (err) {
    console.error('❌ Failed to send WS message:', err);
  }
}

function sendError(deviceId, requestId, errorMessage) {
  const connection = connections.get(deviceId);
  if (connection) {
    safeSend(connection.ws, {
      type: 'ERROR',
      requestId,
      error: errorMessage,
      timestamp: new Date().toISOString()
    });
  } else {
    console.warn(`⚠️  Tried to send error to offline device ${deviceId}:`, errorMessage);
  }
}

// ============================================================================
// error handling
// ============================================================================

// 404 handler
app.use((req, res) => {
  console.warn(`⚠️  404 Not Found: ${req.method} ${req.url}`);
  res.status(404).json({
    success: false,
    error: 'Not Found'
  });
});

// Central error handler
app.use((err, req, res, next) => {
  console.error('💥 Unhandled server error:', err);
  res.status(500).json({
    success: false,
    error: 'Internal server error'
  });
});

// ============================================================================
// START SERVER
// ============================================================================

const PORT = process.env.PORT || 3000;

server.listen(PORT, '0.0.0.0', () => {
  console.log('');
  console.log('═══════════════════════════════════════════════════════════');
  console.log('  🏥 SeniorCare Relay Server');
  console.log('═══════════════════════════════════════════════════════════');
  console.log(`  📡 HTTP Server: http://localhost:${PORT}`);
  console.log(`  🔌 WebSocket Server: ws://localhost:${PORT}`);
  console.log('');
  console.log('  Endpoints:');
  console.log(`    GET  /health`);
  console.log(`    POST /api/guardian/register`);
  console.log(`    POST /api/elder/generate-code`);
  console.log(`    POST /api/pair`);
  console.log(`    GET  /api/guardian/:guardianId/elders`);
  console.log('');
  console.log('  WebSocket: ws://<host>:3000?deviceId=XXX&type=guardian|elder');
  console.log('═══════════════════════════════════════════════════════════');
  console.log('');
});


process.on('SIGINT', () => {
  console.log('\n🛑 Shutting down...');
  
  connections.forEach((conn, deviceId) => {
    try {
      conn.ws.close(1001, 'Server shutting down');
    } catch (err) {
      console.error(`❌ Error closing WS for ${deviceId}:`, err);
    }
  });
  
  try {
    db.close();
  } catch (err) {
    console.error('❌ Error closing DB:', err);
  }
  
  server.close(() => {
    console.log('✅ Server closed');
    process.exit(0);
  });
});
