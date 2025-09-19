# WARP.md

This file provides guidance to WARP (warp.dev) when working with code in this repository.

## Project Overview

This is an Android application (`com.example.palmtest`) built with Kotlin and Jetpack Compose. The project demonstrates a chat functionality with both modern Compose UI (MainActivity) and legacy Fragment-based UI (ChatFragment) architectures. The codebase includes comprehensive testing for Android lifecycle management, particularly focusing on fixing Fragment lifecycle observer issues.

## Common Development Commands

### Build and Run
```bash
# Build the project
./gradlew build

# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Install debug build on connected device
./gradlew installDebug

# Clean build artifacts
./gradlew clean
```

### Testing
```bash
# Run all tests
./gradlew test

# Run unit tests only
./gradlew testDebugUnitTest

# Run instrumented tests on connected device
./gradlew connectedAndroidTest

# Run specific test class
./gradlew test --tests "com.example.palmtest.viewmodel.ChatViewModelTest"

# Run specific instrumented test
./gradlew connectedAndroidTest --tests "com.example.palmtest.ui.ChatFragmentEspressoTest"

# Generate test coverage report
./gradlew testDebugUnitTestCoverage
```

### Code Quality
```bash
# Lint check
./gradlew lint

# Generate lint report
./gradlew lintDebug
```

## Architecture Overview

The project demonstrates a dual-architecture approach:

### Modern Architecture (MainActivity)
- **Jetpack Compose**: UI built with declarative UI framework
- **Material 3**: Following Material Design 3 guidelines
- **Edge-to-Edge**: Modern Android UI with edge-to-edge display support

### Legacy Architecture (ChatFragment)
- **Fragment-based UI**: Traditional Android Fragment pattern
- **MVVM Pattern**: ChatViewModel manages state using LiveData
- **RecyclerView**: Message list display using MessagesAdapter
- **Data Layer**: Simple Message data class with timestamp tracking

### Key Components

**ViewModels**:
- `ChatViewModel`: Manages chat messages using LiveData, demonstrates proper state management

**UI Components**:
- `ChatFragment`: Fixed fragment implementation using `viewLifecycleOwner` instead of `requireActivity()` for proper lifecycle management
- `MessagesAdapter`: RecyclerView adapter for displaying messages

**Data Models**:
- `Message`: Simple data class with id, text, and timestamp

## Critical Implementation Details

### Fragment Lifecycle Fix
The ChatFragment demonstrates a critical Android lifecycle pattern fix:
- **Problem**: Using `requireActivity()` as lifecycle owner causes crashes when fragment view is destroyed/recreated
- **Solution**: Using `viewLifecycleOwner` ensures observers are properly tied to the fragment's view lifecycle
- **Location**: `ChatFragment.onViewCreated()` line 19

### Testing Strategy
The project emphasizes comprehensive testing:
- **Unit Tests**: `ChatViewModelTest` uses Mockito and `InstantTaskExecutorRule` for LiveData testing
- **Fragment Testing**: `ChatFragmentLifecycleTest` and `ChatFragmentEspressoTest` test lifecycle scenarios
- **Lifecycle Testing**: Specific tests for fragment recreation scenarios that previously caused crashes

## Dependencies and Versions

### Key Android Components
- **Compile SDK**: 36 (Android 14+)
- **Min SDK**: 28 (Android 9)
- **Target SDK**: 36
- **Kotlin**: 2.0.21
- **Compose BOM**: 2024.09.00

### Testing Dependencies
- **JUnit**: 4.13.2 for unit testing
- **Mockito**: 4.5.1 with Kotlin support
- **AndroidX Test**: Fragment testing, Espresso for UI testing
- **InstantTaskExecutorRule**: For LiveData testing synchronization

### Project Structure Notes
- Modern Compose code lives in `MainActivity.kt`
- Legacy Fragment code organized under `legacy/` package
- Test files mirror the main source structure
- Uses Gradle version catalogs (`libs.versions.toml`) for dependency management

## Development Context

This appears to be a trial task focused on Android lifecycle management and testing patterns. The codebase specifically demonstrates:
1. Proper Fragment lifecycle observer patterns
2. Comprehensive testing of Android component lifecycles  
3. Migration patterns from Fragment-based to Compose-based UI
4. Best practices for LiveData observation in Fragments