# MultiCalculator

## Overview
This is a Kotlin Multiplatform project targeting Android and iOS, implementing a simple calculator.

## Project Structure
- `/composeApp`: Shared code for Compose Multiplatform applications.
  - `commonMain`: Code common for all platforms.
  - `androidMain`, `iosMain`: Platform-specific code.
- `/iosApp`: iOS application entry point and SwiftUI code.
- `/shared`: Code shared between all targets.

## Features
- Basic arithmetic operations: addition, subtraction, multiplication, division.
- Cross-platform compatibility.

## Setup Instructions
### Prerequisites
- Kotlin 1.5+
- Android Studio
- Xcode for iOS development

### Building and Running
#### Android
1. Open the project in Android Studio.
2. Build and run the application on an Android emulator or device.

#### iOS
1. Open the `iosApp` project in Xcode.
2. Build and run the application on an iPhone simulator or device.

## Contributors
- [Selin180796](https://github.com/Selin180796)
- [Selinchristian](https://github.com/Selinchristian)

## License
[MIT](LICENSE)
