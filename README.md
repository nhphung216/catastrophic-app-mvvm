
# CATastrophic App

CATastrophic is an Android application that fetches cat images from The Cat API and displays them in a grid layout. The app supports offline viewing and allows users to zoom in on images. It uses Kotlin, MVVM, Clean Architecture, Koin for dependency injection, Room for local storage, and adheres to Material Design principles.
## Features

- Display cat images in a 3-column grid layout.
- Supports endless scrolling to load more images.
- Smooth animations and transitions.
- Zoom feature for images.
- Offline mode using Room for caching data.
- MVVM, Clean Architecture with separation of concerns.
- Unit and UI tests for reliable codebase.


## Installation
### Prerequisites
- Android Studio Flamingo or later
- JDK 11 or later
- Internet connection

### Setup
#### 1. Download and unzip the file project
#### 2. Open the project in Android Studio
- Launch Android Studio.
- Select Open an existing Android Studio project.
- Navigate to the CatApp directory and open it.
#### 3. Sync and build the project:
- Android Studio will prompt you to sync the Gradle files. Click on - Sync Now.
- Build the project using Build > Make Project.
#### 4. Run the app:
- Connect an Android device or start an emulator.
- Click on the Run button.
    
## API Key Setup

The app uses The Cat API to fetch images. You need to obtain an API key:

##### 1. Sign up at The Cat API to get your free API key.
https://thecatapi.com/signup
##### 2. Add your API key to the gradle.properties file:
```bash
  THE_CAT_API_KEY="your_api_key_here"
```
##### 3. The key will be automatically injected into your project.


## Testing

### Unit Tests
Running Unit Tests:

```bash
./gradlew test
```

### UI Tests
Running UI Tests

```bash
./gradlew connectedAndroidTest
```



## Libraries and Tools
**Kotlin:** Programming language.\
**Android Jetpack:** Includes LiveData, ViewModel, Room.\
**Koin:** Dependency injection framework.\
**Retrofit:** Networking library for API calls.\
**Room:** Database library for local storage.\
**Glide:** Image loading library.\
**Espresso:** UI testing framework.\
**Mockito:** Mocking framework for unit tests.\
**JUnit:** Testing framework.


## License

[MIT](https://choosealicense.com/licenses/mit/)

