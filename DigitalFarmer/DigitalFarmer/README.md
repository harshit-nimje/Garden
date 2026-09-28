# Digital Farmer

Offline-first Android app for plant care: digital plant library, automated
watering schedules, ambient light (lux) meter, plant diagnosis, and a
Duolingo-style seed/rank gamification loop for garden tasks.

## Status
MVP scaffold. Fully offline (Room DB + WorkManager). No network calls yet —
species presets, diagnosis rules, and scan buttons are stubbed for local
logic now and are designed to be swapped for on-device ML / online sync later.

## Stack
- Kotlin + Jetpack Compose (Material 3, expressive shapes/type/color)
- Room (local plant + profile storage)
- WorkManager (periodic watering-due notifications)
- Android sensor API (TYPE_LIGHT) for the lux meter

## Features implemented
- Plant library with watering countdown per plant
- Add plant: species presets with water/sun/lux/fertilizer data baked in
  ("scan and add" button is a hook point for a future CameraX + on-device
  identification model)
- Lux meter: reads the phone's ambient light sensor, flags whether it's a
  good spot for each saved plant
- Diagnose: symptom picker with offline advice (hook point for photo-based
  diagnosis model)
- Marketplace/Rank: seeds earned per completed care task, rank tiers
  Seedling → Sprout → Budding Gardener → Green Thumb → Master Grower →
  Botanist Legend

## Build
1. Open this folder in Android Studio (Koala+ recommended).
2. Let Gradle sync (needs the Android SDK; not run in this environment).
3. Build > Build APK(s), or `./gradlew assembleDebug`.
4. Output APK: `app/build/outputs/apk/debug/app-debug.apk`.

## Roadmap (next phases)
- CameraX capture wired into "scan and add" / "scan and diagnose"
- On-device or cloud plant ID + disease detection model
- Online sync, real marketplace (redeem seeds for real seed packets/discounts)
- Push notifications, multi-device sync
