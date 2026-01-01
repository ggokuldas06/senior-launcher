

export const API_BASE_URL = 'http://172.20.10.2;';
export const WS_BASE_URL = 'ws://172.20.10.2;';


export const API_ENDPOINTS = {
  REGISTER: '/api/guardian/register',
  PAIR: '/api/pair',
  GET_ELDERS: '/api/guardian', 
};


export const WS_RECONNECT_INTERVAL = 3000; 
export const WS_MAX_RECONNECT_ATTEMPTS = 10;
export const WS_REQUEST_TIMEOUT = 30000; 

export const STORAGE_KEYS = {
  GUARDIAN_TOKEN: 'guardian_token',
  GUARDIAN_ID: 'guardian_id',
  GUARDIAN_INFO: 'guardian_info',
  ELDERS_CACHE: 'elders_cache',
};

// Alert Severity
export const ALERT_SEVERITY = {
  SOS: 'critical',
  FALL: 'critical',
  MISSED_MED: 'warning',
  INACTIVITY: 'warning',
  LOW_BATTERY: 'info',
} as const;

// Colors (for consistent theming)
export const COLORS = {
  primary: '#6200ee',
  secondary: '#03dac6',
  error: '#b00020',
  warning: '#ff9800',
  success: '#4caf50',
  background: '#f5f5f5',
  surface: '#ffffff',
  text: '#000000',
  textSecondary: '#666666',
  border: '#e0e0e0',
  
  // Alert colors
  alertCritical: '#d32f2f',
  alertWarning: '#f57c00',
  alertInfo: '#1976d2',
};

// Status Colors
export const STATUS_COLORS = {
  online: '#4caf50',
  offline: '#9e9e9e',
  warning: '#ff9800',
  error: '#f44336',
};