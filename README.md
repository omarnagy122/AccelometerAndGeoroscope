# SensorReadingApp 📱

A native Android application built with Java that reads and displays live data from two hardware sensors — the **Accelerometer** and the **Gyroscope** — in real time.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Screenshots](#screenshots)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [How It Works](#how-it-works)
- [Sensor Details](#sensor-details)
- [Getting Started](#getting-started)
- [Requirements](#requirements)
- [Academic Context](#academic-context)

---

## Overview

**SensorReadingApp** is a lightweight native Android app that demonstrates real-time sensor data reading using the Android `SensorManager` API. The app listens to the device's built-in accelerometer and gyroscope chips and displays their readings live on screen, updating continuously as the phone moves.

The app was built as part of a university assignment on mobile sensing and inertial measurement systems.

---

## Features

### Accelerometer Section
- ✅ Live X, Y, Z axis readings in **m/s²**
- ✅ Real-time magnitude calculation using **√(X² + Y² + Z²)**
- ✅ Automatic motion status detection:
  - ⬛ **STILL** — phone is not moving
  - 🚶 **WALKING** — gentle rhythmic movement
  - 🏃 **RUNNING** — strong continuous movement
  - 📳 **SHAKING** — very strong rapid movement

### Gyroscope Section
- ✅ Live rotation X, Y, Z axis readings in **rad/s**
- ✅ Real-time rotation magnitude calculation
- ✅ Automatic rotation status detection:
  - 🔲 **NOT rotating** — phone is stationary
  - 🔄 **SLOW rotation** — gentle turning
  - 🔄 **FAST rotation** — quick flick or turn
  - 💫 **SPINNING rapidly!** — very fast spin

### General
- ✅ No permissions required — sensors are freely accessible
- ✅ Battery optimized — sensors stop when app goes to background
- ✅ Works on any Android device with API 26+
- ✅ Clean, minimal UI with color-coded axes

---

## Tech Stack

| Technology | Details |
|---|---|
| Language | Java |
| Platform | Native Android |
| Minimum SDK | API 26 (Android 8.0 Oreo) |
| Target SDK | API 34 (Android 14) |
| IDE | Android Studio |
| Sensor API | `android.hardware.SensorManager` |
| UI | XML Layouts with ScrollView + LinearLayout |

---

## Project Structure

```
SensorReadingApp/
├── app/
│   ├── manifests/
│   │   └── AndroidManifest.xml        ← No special permissions needed
│   ├── java/
│   │   └── com.student.sensorreadingapp/
│   │       └── MainActivity.java      ← All sensor logic lives here
│   └── res/
│       └── layout/
│           └── activity_main.xml      ← UI layout with 2 sensor cards
└── build.gradle
```

---

## How It Works

### Architecture
The app follows a simple event-driven architecture using Android's built-in sensor callback system:

```
Hardware Chip
      ↓
SensorManager (Android system service)
      ↓
onSensorChanged() callback (called automatically)
      ↓
Data processed + UI updated
```

### Sensor Lifecycle
```
App opens     → onCreate()  → sensors retrieved from SensorManager
App visible   → onResume()  → listeners registered → data starts flowing
App background → onPause()  → listeners unregistered → battery saved
App closed    → onDestroy() → everything cleaned up
```

### Motion Detection Logic

**Accelerometer status** is based on subtracting Earth's gravity (9.8 m/s²) from the total magnitude:

```java
float gravityRemoved = Math.abs(magnitude - SensorManager.GRAVITY_EARTH);

if      (gravityRemoved < 0.3f) → STILL
else if (gravityRemoved < 3.0f) → WALKING
else if (gravityRemoved < 6.0f) → RUNNING
else                            → SHAKING
```

**Gyroscope status** is based on total rotation magnitude in rad/s:

```java
if      (magnitude < 0.5f) → NOT rotating
else if (magnitude < 2.0f) → SLOW rotation
else if (magnitude < 5.0f) → FAST rotation
else                       → SPINNING rapidly!
```

---

## Sensor Details

### Accelerometer
| Property | Details |
|---|---|
| Type | `Sensor.TYPE_ACCELEROMETER` |
| Unit | m/s² (metres per second squared) |
| X axis | Left / Right tilt |
| Y axis | Forward / Backward tilt |
| Z axis | Up / Down (≈ 9.8 when flat due to gravity) |
| Resting magnitude | ≈ 9.8 m/s² (Earth's gravity) |
| Typical phone type | Capacitive MEMS |

### Gyroscope
| Property | Details |
|---|---|
| Type | `Sensor.TYPE_GYROSCOPE` |
| Unit | rad/s (radians per second) |
| X axis | Rotation: nodding forward/backward |
| Y axis | Rotation: shaking left/right |
| Z axis | Rotation: spinning flat on table |
| Resting magnitude | ≈ 0.0 rad/s |

---

## Getting Started

### 1. Clone or download the project
```bash
git clone https://github.com/yourusername/SensorReadingApp.git
```

### 2. Open in Android Studio
- Open **Android Studio**
- Click **"Open"** and select the project folder
- Wait for Gradle to sync

### 3. Run on a device
> ⚠️ **A real physical device is strongly recommended** — emulators have no real sensor chips so readings will be fake or empty.

**Option A — Real device (recommended):**
1. Enable **Developer Options** on your phone (tap Build Number 7 times)
2. Enable **USB Debugging**
3. Connect via USB
4. Select your device in Android Studio
5. Click ▶ **Run**

**Option B — Emulator:**
1. Create a virtual device in Device Manager
2. Use the emulator's **Extended Controls → Virtual sensors** to simulate movement

---

## Requirements

- Android Studio **Hedgehog** or newer
- Android device running **API 26** (Android 8.0) or higher
- USB cable for physical device testing
- No internet connection required
- No special permissions required

---

## Academic Context

This application was developed as part of **Assignment 2** for a Mobile Computing course.

### Assignment Requirements Met

| Requirement | Implementation |
|---|---|
| Read accelerometer sensor | ✅ `Sensor.TYPE_ACCELEROMETER` via SensorManager |
| Read gyroscope sensor | ✅ `Sensor.TYPE_GYROSCOPE` via SensorManager |
| Display live X, Y, Z values | ✅ Updated in real time via `onSensorChanged()` |
| Detect motion type | ✅ STILL / WALKING / RUNNING / SHAKING |
| Detect rotation type | ✅ NOT rotating / SLOW / FAST / SPINNING |
| Native Android (Java) | ✅ Pure Java, no Flutter or third-party sensor libraries |

### Key Concepts Demonstrated
- `SensorManager` — Android system service for hardware sensor access
- `SensorEventListener` — interface/callback pattern for receiving sensor data
- `onSensorChanged()` — event-driven data processing
- `onResume()` / `onPause()` — Android activity lifecycle management
- Magnitude formula — `√(X² + Y² + Z²)` for combining 3-axis data
- Threshold-based classification — converting raw numbers into meaningful states

---

## Author

**Student Name** — Mobile Computing Course
University: _______________
Date: April 2026

---

> Built with ❤️ using Native Android Java
