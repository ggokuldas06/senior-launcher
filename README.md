# SeniorCare OS

SeniorCare OS is a simplified smartphone operating layer and connected care platform designed specifically for elderly users. It enables safer, more independent living while keeping families informed and engaged.

The system consists of:
- **Elder App** (Launcher + Safety and Health Layer)
- **Guardian / Family App**
- **Real-time Relay Server**
- **NLP Assistant Backend**

The primary focus areas are safety, medication adherence, routine support, health awareness, and secure family supervision.

---

## Table of Contents

- [Product Overview](#product-overview)
- [Core Objectives](#core-objectives)
- [Features](#features)
- [System Architecture](#system-architecture)
- [UI/UX Principles](#uiux-principles)
- [Alert Triggers](#alert-triggers)
- [Privacy & Security](#privacy--security)
- [Technical Architecture](#technical-architecture)
- [Installation & Setup](#installation--setup)
- [Roadmap](#roadmap)
- [License](#license)

---

## Product Overview

SeniorCare OS is designed to replace the complex Android experience with a controlled, simplified launcher and integrated care tools.

**It focuses on:**
- Reducing emergency risks
- Supporting daily health and medication routines
- Connecting families and caregivers
- Providing voice-based assistance
- Keeping the experience predictable and low-stress

**This project combines:**
- Android launcher (Kotlin)
- Guardian app (React Native / Expo)
- Node.js relay server
- NLP assistant server

---

## Core Objectives

| Goal | How It Is Achieved |
|------|-------------------|
| Reduce emergency risk | SOS, location sharing, fall detection (planned) |
| Improve medicine adherence | Smart reminders, family-controlled schedules |
| Monitor health daily | Simple daily check-ins (planned) |
| Support financial awareness | Pension and bill reminders (planned) |
| Enable family involvement | Real-time alerts and remote configuration |
| Make smartphones simpler | Large UI, minimal actions, voice assistance |

---

## Features

### Elder Launcher (Kotlin)

**Current capabilities:**
- Simplified home interface
- Large, readable tiles
- Voice navigation using hybrid NLP system
- Receives medications from guardian app
- Local reminders and logs
- SOS button functionality (partial)

**Planned enhancements:**
- Daily health check-in prompts
- Fall detection alerts
- Inactivity monitoring
- Health tips and doctor reminders

### Guardian / Family App

Built with **React Native / Expo**.

**Current capabilities:**
- Guardian registration and login
- Secure pairing with elder device using pairing code
- Persistent WebSocket connection
- Add and manage medication schedules
- View connected elders
- Track online connection state
- Send messages and reminders

### Relay Server

Node.js and WebSocket-based communication hub.

**Responsibilities:**
- Real-time communication
- OTP-style pairing system
- WebSocket routing between devices
- Guardian ↔ Elder permissions enforcement
- SQLite storage for pairings and accounts
- Expiring pairing codes
- Handling device connection status

> **Note:** This server ensures that devices never directly connect to each other. All traffic is validated and routed centrally.

### NLP Assistant

The launcher uses a **hybrid NLP approach**:

**Fast Intent Matching:**
- TF-IDF + cosine similarity
- Selects most relevant candidate intents
- Runs under 10 ms

**LLM Refinement:**
- Uses Groq-hosted LLaMA model
- Extracts entities such as time, medications, contacts
- Produces predictable JSON-based responses

**Example output:**
```json
{
  "intent": "SET_ALARM",
  "entities": { "time": "07:00" },
  "reply": "Alarm set for 7:00 AM"
}
```

This ensures a balance between accuracy, performance, and safety.

---

## System Architecture
```
Elder Launcher  <——>  Relay Server  <——>  Guardian App
                         |
                         |
                     NLP Assistant
```

**Backend responsibilities:**
- Relaying real-time messages
- Permission validation
- Data logging and persistence
- Monitoring device connectivity

---

## UI/UX Principles

**Design priorities include:**
- Large buttons (minimum ~120px)
- High contrast themes
- A maximum of 4–5 core functions visible
- Voice assistance for reading and confirmation
- Support for local languages
- Minimal screens and reduced decision-making

**Suggested primary home tiles:**

| Section | Purpose |
|---------|---------|
| Health | Daily check-ins and suggestions |
| Medicines | Reminders and logs |
| Location | Live sharing with contacts |
| SOS | Instant emergency help |
| Family | Quick access to contacts |

---

## Alert Triggers

| Trigger | System Action |
|---------|--------------|
| SOS press | Notify family, optionally share live location |
| Fall detected (planned) | Notify family automatically |
| Missed medication (planned) | Notify family member |
| Low battery (planned) | Send notification to family |
| Inactivity (planned) | Prompt health check |
| Unconfirmed pension (planned) | Alert family after 24 hours |

---

## Privacy & Security

**Security principles guiding the system:**
- Elder controls what data is shared
- No financial transactions or payments inside the app
- Family cannot view personal financial balances
- Sensitive data should be encrypted during transmission
- Phone numbers stored using hashed identifiers
- Minimum data storage necessary for functionality

> **Primary design intention:** Protect seniors from exploitation or unwanted tracking.

---

## Technical Architecture

### Technologies Used

**Launcher (Elder):**
- Kotlin
- Android Jetpack components
- Local storage and reminders
- WebSocket client

**Guardian App:**
- React Native + Expo
- Secure local storage
- WebSocket communication
- REST API integration

**Relay Server:**
- Node.js
- Express
- WebSockets
- SQLite (better-sqlite3)

**NLP Backend:**
- Python
- FastAPI
- TF-IDF + cosine similarity
- Groq LLaMA API

---

## Installation & Setup

### Relay Server
```bash
cd relay-server
npm install
npm start
```

**Server runs at:**
- HTTP: `http://localhost:3000`
- WebSocket: `ws://localhost:3000`

### Guardian App
```bash
cd guardian-app
npm install
npx expo start
```

> Configure backend URLs before running.

### Elder Launcher

1. Open the launcher module in Android Studio
2. Build and run on an Android device
3. Set it as the default launcher when prompted

---

## Roadmap

### Phase 1 (MVP – currently implemented)
- ✅ Launcher UI
- ✅ SOS and location sender (partial)
- ✅ Medication reminders
- ✅ Family-controlled medication scheduling
- ✅ Real-time pairing and communication

### Phase 2
- ⏳ Fall detection
- ⏳ Daily health check-ins
- ⏳ Missed medication alerts
- ⏳ Battery alerts
- ⏳ Alert logs for family

### Phase 3
- ⏳ Pension reminder system
- ⏳ Inactivity alerts
- ⏳ Health tips and visit reminders

---

## License

[Add your license information here]

---

**Built with ❤️ for safer, independent senior living**
