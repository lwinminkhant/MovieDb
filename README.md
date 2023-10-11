![MovieDb](https://socialify.git.ci/lwinminkhant/MovieDb/image?font=Raleway&logo=https%3A%2F%2F3.bp.blogspot.com%2F-VVp3WvJvl84%2FX0Vu6EjYqDI%2FAAAAAAAAPjU%2FZOMKiUlgfg8ok8DY8Hc-ocOvGdB0z86AgCLcBGAsYHQ%2Fs1600%2Fjetpack%252Bcompose%252Bicon_RGB.png&name=1&pattern=Circuit%20Board&theme=Dark)
<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0">
     <img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/>
  </a>

  <a href="https://github.com/lwinminkhant">
     <img alt="Static Badge" src="https://img.shields.io/badge/Github-lwinminkhant-gold?style=flat-square&logo=github">
  </a>
</p>


## Features

- Enable users to search for movies by title.
- Display comprehensive information about each movie.
- Allow users to mark movies as favorites for easy access.
- Support Light / Dark mode.
- Support Language English / Burmese

## Technical Dependencies
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- Jetpack
    - Composable: 
    - ViewModel: Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
    - Room: Constructs Database by providing an abstraction layer over SQLite to allow fluent database access.
    - Navigation Component: Constructs Database by providing an abstraction layer over SQLite to allow fluent database access.
    - [Hilt](https://dagger.dev/hilt/): for dependency injection.
- Architecture
    - MVVM Architecture (View - ViewModel - Model)
    - Repository Pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit): Construct the REST APIs and paging network data.
- [ksp](https://github.com/google/ksp): Kotlin Symbol Processing API.
- [Coil](https://github.com/coil-kt/coil): Loading images from network.


## Architecture
**MovieDb** is based on the MVVM architecture, which follows the [Google's official architecture guidance](https://developer.android.com/topic/architecture).

The overall architecture of **MovieDb** is composed of two layers; the UI layer and the data layer. Each layer has dedicated components and they have each different responsibilities, as defined below:

**MovieDb** was built with [Guide to app architecture](https://developer.android.com/topic/architecture), so it would be a great sample to show how the architecture works in real-world projects.


### Architecture

- Each layer follows [unidirectional event/data flow](https://developer.android.com/topic/architecture/ui-layer#udf); the UI layer emits user events to the data layer, and the data layer exposes data as a stream to other layers.
- The data layer is designed to work independently from other layers and must be pure, which means it doesn't have any dependencies on the other layers.

With this loosely coupled architecture, you can increase the reusability of components and scalability of your app.

### UI Layer

The UI layer consists of UI elements to configure screens that could interact with users and [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) that holds app states and restores data when configuration changes.

### Data Layer

The data Layer consists of repositories, which include business logic, such as querying data from the local database and requesting remote data from the network. It is implemented as an offline-first source of business logic and follows the [single source of truth](https://en.wikipedia.org/wiki/Single_source_of_truth) principle.<br>

**MovieDb** is an offline-first app is an app that is able to perform all, or a critical subset of its core functionality without access to the internet.
So users don't need to be up-to-date on the network resources every time and it will decrease users' data consumption. For further information, you can check out [Build an offline-first app](https://developer.android.com/topic/architecture/data-layer/offline-first).

## License

**MovieDb** is distributed under the terms of the Apache License (Version 2.0). See the
[license](LICENSE) for more information.