# Caffesist Desktop

Caffesist Desktop is the desktop companion to the Caffesist café application. It is intended to provide a larger-screen interface for the main café workflow and history views, using Firebase and Cloud Firestore to connect to project data.

The application is built with Kotlin and Compose Multiplatform.

## Firebase credentials

The app reads a Firebase service-account JSON file from the path in the `FIREBASE_CREDENTIALS` environment variable. Keep this file outside the repository and never commit service-account credentials.