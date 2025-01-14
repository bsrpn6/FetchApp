# Fetch Rewards Interview Project

This repository was created as part of an interview process and serves as a sandbox environment for
experimenting with various Android development concepts and features. While it is structured to
demonstrate best practices and modern techniques, it is also a space for exploring ideas and
implementing experimental features.

---

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
- [Future Enhancements](#future-enhancements)
- [Acknowledgments](#acknowledgments)

---

## Project Overview

The app retrieves and displays data from a remote API, utilizing offline support for enhanced user
experience. It features a collapsible list UI, pull-to-refresh functionality, and navigation with a
custom splash screen.

---

## Features

### 1. **Collapsible List UI**

- Items are grouped by `listId` and displayed in collapsible sections.
- Only one section can be expanded at a time for better readability.

### 2. **Pull-to-Refresh**

- Refresh data from the API with swipe-down gestures.
- Includes a loading indicator for feedback.

### 3. **Offline Support**

- Uses Room Database to cache API data for offline viewing.
- Automatically syncs data when the app reconnects to the network.

### 4. **Navigation**

- Implements a `NavigationDrawer` for seamless navigation between screens.
- Screens include:
    - Home (Main Content)
    - About
    - Settings

### 5. **Custom Splash Screen**

- Displays a unique splash screen with a custom app logo on startup.

### 6. **Error Handling**

- Graceful error handling with fallback mechanisms for network or data issues.

---

## Technologies Used

- **Programming Language:** Kotlin
- **Architecture:**
    - MVVM (Model-View-ViewModel)
    - Hilt for Dependency Injection
- **UI Framework:** Jetpack Compose
    - **Dynamic Theming**: Fully supports light and dark themes using Material Design 3 and
      MaterialTheme for consistent UI.
    - **Dark Mode State Management:**
        - DataStore for persisting dark mode preferences.
        - Reactive state handling with Flow and collectAsState to adapt to changes.
- **Data Storage:**
    - Retrofit for API calls
    - Room Database for offline caching
- **Other Libraries:**
    - Moshi for JSON parsing
    - Material3 for modern UI components

---

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/bsrpn6/FetchApp.git
   ```
2. Open the project in Android Studio.
3. Sync the Gradle files.
4. Run the app on an emulator or physical device.

---

## Future Enhancements

### 1. **Unit and Integration Tests**

- Add coverage for ViewModel and Repository logic.
- Use MockK and JUnit for efficient test development.

### 2. **Animations**

- Smooth transitions for expanding and collapsing lists.
- Add subtle feedback animations for user interactions.

### 3. **Error Screens**

- Introduce user-friendly error screens for network or data issues.
- Include retry mechanisms.

### 4. **Localization**

- Support multiple languages to make the app globally accessible.

### 5. **Data Filtering and Sorting**

- Add options for users to filter and sort the displayed data.

### 6. **Expandable Header Customization**

- Allow users to customize expanded header styles or behavior.

### 7. **Optimized Offline Mode**

- Use WorkManager to periodically sync data in the background.

### 8. **Detailed Analytics**

- Add event tracking and performance analytics.

---

## Acknowledgments

Special thanks to **Fetch Rewards** for providing the challenge and inspiration for this project!