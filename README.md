# KSafe: Password Manager 🔒


KSafe is a secure, open-source password management app for Android devices. It offers features for securely storing, generating, and analyzing passwords with a focus on security.

## Key Features

- **Secure Password Storage**: All passwords are stored with end-to-end encryption for enhanced security.

- **Password Generation**: Create strong and unique passwords with ease using our password generation feature.

- **Password Analysis**: Evaluate the strength of your existing passwords with our password analysis tool.

## Download
[<img src="https://github.com/VitorCezila/ksafe/assets/70729616/4ca707e3-b175-4081-bd11-6454431aa1c0">](https://play.google.com/store/apps/details?id=com.cezila.ksafe)
---

## Tech stack & Open-source libraries
- Minimum SDK level 27
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous.
- Android Keystore to store keys
- AES256 key to encrypt passwords.
- Jetpack
  - Lifecycle: Observe Android lifecycles and handle UI states upon the lifecycle changes.
  - ViewModel: Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
  - DataBinding: Binds UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
  - Room: Constructs Database by providing an abstraction layer over SQLite to allow fluent database access.
  - [Hilt](https://dagger.dev/hilt/): for dependency injection.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Repository Pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit): Construct the REST APIs and paging network data.
- [ksp](https://github.com/google/ksp): Kotlin Symbol Processing API.
