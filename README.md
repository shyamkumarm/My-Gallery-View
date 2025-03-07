# 📸 My-Gallery-View

A Simple Android Gallery App built using **Koin**, **Coil**, **Jetpack Compose**, **Clean Architecture + MVVM**, **Coroutines**, and **Navigation Compose**.

## 🚀 Features
- Display images from local storage
- Image lazy loading using Coil
- Clean Architecture with MVVM Pattern
- Dependency Injection with Koin
- Navigation using Navigation Compose
- Asynchronous operations using Coroutines
- Jetpack Compose UI with Material Design

## 🛠 Tech Stack
- **Kotlin** - Programming language
- **Jetpack Compose** - Modern UI toolkit
- **Coil** - Image loading library
- **Navigation Compose** - For in-app navigation
- **Koin** - Dependency Injection
- **Coroutines & Flow** - For background operations
- **Room Database** - Local storage (if applicable)
- **Clean Architecture + MVVM** - Architecture pattern

## 📂 Project Structure
```
app/
 ├── data/               # Data layer (MediaRepositories, Media data sources)
 ├── di/                 # Dependency Injection (Koin Modules)
 ├── domain/             # Domain layer (Use Cases, data classes, IMedia interfaces)
 ├── presentation/       # Presentation layer (Screens, Activities,ViewModels,Navigation)
 ├── utils/              # Utility classes (List transformations, Coil Image Caches)
 └── MyApplication.kt     # Entry point of the app
```



## 🏗️ Architecture Overview
- **Data Layer** - Handles data sources (Room, APIs, etc.)
- **Domain Layer** - Business logic with Use Cases
- **Presentation Layer** - Jetpack Compose UI + ViewModels

### 🏗 Functional Requirements:
- Displaying a grid or list of all images from the device's storage.
- Showing the album view with name of the folder present on the device
- There should be a folder like “All Images" and “All Videos".
- All the pictures taken with the local camera should be shown under the “Camera” folder.
- Show the name of the album and number of images or videos present in each album view.

### 🏗 Out of Scope:
- Cloud storage integration (e.g., Google Photos, Dropbox).
- Image editing features.
- Zoom and pan within the full-screen view
- Video playback.
- Advanced search functionality.
- Advanced image filtering.
- Sharing features.

## 📝 License
This project is licensed under the MIT License.

---
Made with ❤️ using Jetpack Compose

