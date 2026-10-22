# Caffesist Desktop

Caffesist Desktop is a Kotlin desktop application built with Compose Multiplatform. It opens a main screen and a history screen, and connects to Firebase/Cloud Firestore.

## Tech stack

- Kotlin/JVM
- Compose Multiplatform for Desktop
- Navigation Compose
- Firebase Admin SDK and Cloud Firestore

## Build and run

Use a JDK supported by the project’s Gradle wrapper:

```sh
./gradlew run
```

On Windows, use:

```bat
gradlew.bat run
```

## Firebase credentials

The application loads an Admin SDK credential file named `adminSdk.json` from its runtime resources. Configure credentials for your own Firebase project before running the app. Keep service account credentials private and out of version control.