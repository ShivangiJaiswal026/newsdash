# NewsDash 📰

A modern Android news application built with Jetpack Compose that fetches and displays news articles from the GNews API. The app features bookmarking functionality, offline storage, and a clean Material Design 3 UI.

## Features ✨

- **📰 News Feed**: Browse today's headlines with article images, titles, and descriptions
- **🔖 Bookmarking**: Save articles for later reading
- **💾 Offline Storage**: Articles are cached locally using Room database
- **🔄 Smart Refresh**: Bookmark status is preserved when refreshing articles
- **📱 Edge-to-Edge UI**: Modern edge-to-edge design with proper system bar insets
- **🌐 Article Details**: View full articles in an embedded WebView
- **🎨 Material Design 3**: Beautiful UI with Material 3 components and theming
- **⚡ Reactive UI**: Built with Kotlin Flow and StateFlow for reactive data handling

## Tech Stack 🛠️

### Core Technologies
- **Kotlin** - Programming language
- **Jetpack Compose** - Modern declarative UI toolkit
- **Material Design 3** - UI components and theming
- **Kotlin Coroutines & Flow** - Asynchronous programming

### Architecture & Dependency Injection
- **MVVM Architecture** - Model-View-ViewModel pattern
- **Hilt** - Dependency injection framework
- **Repository Pattern** - Data layer abstraction

### Networking & Data
- **Retrofit** - HTTP client for API calls
- **Moshi** - JSON serialization/deserialization
- **OkHttp** - HTTP client
- **Room Database** - Local data persistence
- **Coil** - Image loading library

### Testing
- **JUnit** - Unit testing framework
- **MockK** - Mocking library for Kotlin
- **Turbine** - Testing library for Kotlin Flow
- **Kotlin Coroutines Test** - Testing coroutines


## Setup Instructions 🚀

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- JDK 11 or higher
- Android SDK with API level 24+ (Android 7.0+)
- Gradle 8.13.1 or compatible version

### Installation

1. **Clone the repository**

   git clone <repository-url>
   cd NewsDash
2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to the NewsDash directory

3. **Sync and Build**
    - Android Studio will automatically sync Gradle dependencies
    - Wait for the sync to complete
    - Build the project: `Build > Make Project` or `Ctrl+F9` (Windows/Linux) / `Cmd+F9` (Mac)

4. **Run the App**
    - Connect an Android device or start an emulator (API 24+)
    - Click the Run button or press `Shift+F10` (Windows/Linux) / `Ctrl+R` (Mac)

## Architecture 🏗️

The app follows the **MVVM (Model-View-ViewModel)** architecture pattern:

- **Model**: Data layer with Repository, Room Database, and Remote API
- **View**: Jetpack Compose UI screens
- **ViewModel**: Manages UI-related data and business logic

### Data Flow
1. ViewModel observes data from Repository via Flow
2. Repository fetches data from API and stores in Room database
3. UI reacts to state changes through StateFlow
4. User actions trigger ViewModel methods
5. ViewModel updates Repository, which persists changes

### Dependency Injection
Hilt is used for dependency injection:
- `NetworkModule`: Provides Retrofit, OkHttp, and Moshi instances
- `DatabaseModule`: Provides Room database instance
- ViewModels are automatically injected with `@HiltViewModel`

## API Configuration 🌐

The app uses the **GNews API** to fetch news articles:
- Base URL: `https://gnews.io/api/v4/`
- Endpoint: `top-headlines`
- Default parameters:
    - Category: `general`
    - Language: `en`
    - Country: `in`
    - Max articles: `10`

## Testing 🧪

The project includes comprehensive unit tests using:
- **JUnit** for test framework
- **MockK** for mocking dependencies
- **Turbine** for testing Kotlin Flow
- **Kotlin Coroutines Test** for testing coroutines

### Running Tests
# Run all unit tests
./gradlew testDebugUnitTest

# Run specific test class
./gradlew testDebugUnitTest --tests "com.newsdash.model.NewsFeedViewModelTest"## Key Features Implementation

### Bookmarking System
- Articles can be bookmarked/unbookmarked
- Bookmark status persists across app restarts
- Bookmark status is preserved when refreshing articles
- Dedicated bookmarks screen to view saved articles

### Offline Support
- Articles are cached in Room database
- App works offline with cached articles
- Bookmark status is maintained locally

### Edge-to-Edge Design
- Modern edge-to-edge UI implementation
- Proper handling of system bars (status bar, navigation bar)
- WindowInsets for safe content placement

## UI Components

### Screens
- **UserFeedScreen**: Displays news articles in a scrollable list
- **UserBookmarksScreen**: Shows all bookmarked articles
- **ItemDetailScreen**: Full article view in WebView

### Components
- **NewsTile**: Reusable card component for displaying articles
- **Loader**: Centered loading indicator
- **Zero States**: Empty state messages for no articles/bookmarks

## Permissions 📋

The app requires the following permission:
- `INTERNET` - To fetch news articles from the API

## Build Variants

- **Debug**: Development build with debugging enabled
- **Release**: Production build (minification currently disabled)

## Version Information

- **Version Code**: 1
- **Version Name**: 1.0
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 36 (Android 14)
- **Compile SDK**: 36

## Dependencies Versions

- Kotlin: 2.0.21
- Compose BOM: 2024.09.00
- Hilt: 2.52
- Retrofit: 2.11.0
- Room: 2.6.1
- Coil: 2.6.0
- Moshi: 1.15.1

## Acknowledgments 🙏

- [GNews API](https://gnews.io/) for providing news data
- Jetpack Compose team for the amazing UI toolkit
- All open-source libraries used in this project

---

