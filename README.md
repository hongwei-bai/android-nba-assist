# Hami-sports-assistant.android

[![CircleCI](https://img.shields.io/circleci/build/github/hongwei-bai/android-nba-assist?logo=CircleCI&token=24%3Ae1%3A67%3Afb%3Ab1%3A8b%3Aaa%3A74%3Af3%3Ac1%3Af6%3A5a%3Ace%3Ac6%3A06%3A9a)](https://app.circleci.com/pipelines/github/hongwei-bai/android-nba-assist?branch=main)
[![Unit Tests](https://img.shields.io/badge/unit%20tests-covered-83B81A?style=for-the-bedge&logo=Stitcher)](https://github.com/hongwei-bai/android-nba-assist/tree/main/app/src/test/java/com/hami/sports_assist/util)
[![Instrumented Tests](https://img.shields.io/badge/instrumented%20tests-not%20applicable-EE0000?style=for-the-bedge&logo=Stitcher)](./README.md)
[![UI Tests](https://img.shields.io/badge/ui%20tests-not%20applicable-EE0000?style=for-the-bedge&logo=Stitcher)](./README.md)
[![GitHub repo size](https://img.shields.io/github/repo-size/hongwei-bai/android-nba-assist?logo=GitHub)](./README.md)
[![GitHub last commit](https://img.shields.io/github/last-commit/hongwei-bai/android-nba-assist?logo=Github)](./README.md)
[![GitHub commit activity](https://img.shields.io/github/commit-activity/m/hongwei-bai/android-nba-assist?logo=Github)](./README.md)
[![GitHub Repo stars](https://img.shields.io/github/stars/hongwei-bai/android-nba-assist?logo=Github&style=plastic)](./README.md)
[![GitHub forks](https://img.shields.io/github/forks/hongwei-bai/android-nba-assist?logo=GitHub&style=plastic)](./README.md)
[![YouTube Video Views](https://img.shields.io/youtube/views/l-4lD2POrqw?style=social)](https://www.youtube.com/watch?v=l-4lD2POrqw)

<a href='https://play.google.com/store/apps/details?id=com.hongwei.android_nba_assist'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' height="45px"/></a>

[![GitHub top language](https://img.shields.io/github/languages/top/hongwei-bai/android-nba-assist?logo=Kotlin)](./README.md)
[![Coroutines](https://img.shields.io/badge/kotlin%20coroutines-in%20use-83B81A?style=for-the-bedge&logo=Kotlin)](./README.md)
[![Jetpack Compose](https://img.shields.io/badge/jetpack%20compose-in%20use-83B81A?style=for-the-bedge&logo=Jetpack%20Compose)](./README.md)
[![Dynamic Theming](https://img.shields.io/badge/dynamic%20theming-in%20use-83B81A?style=for-the-bedge&logo=Jetpack%20Compose)](./README.md)
[![Navigation](https://img.shields.io/badge/compose%20navigation-in%20use-83B81A?style=for-the-bedge&logo=Jetpack%20Compose)](./README.md)
[![Navigation Animation](https://img.shields.io/badge/navigation%20animation-in%20use-83B81A?style=for-the-bedge&logo=Jetpack%20Compose)](./README.md)
[![Compose Accompanist](https://img.shields.io/badge/compose%20accompanist-in%20use-83B81A?style=for-the-bedge&logo=Jetpack%20Compose)](./README.md)
[![Accompanist Placeholder](https://img.shields.io/badge/accompanist%20placeholder-in%20use-83B81A?style=for-the-bedge&logo=Jetpack%20Compose)](./README.md)
[![Room](https://img.shields.io/badge/jetpack%20room-in%20use-83B81A?style=for-the-bedge&logo=Jetpack%20Compose)](./README.md)
[![Hilt](https://img.shields.io/badge/hilt-in%20use-83B81A?style=for-the-bedge&logo=Google)](./README.md)
[![OkHttp Retrofit](https://img.shields.io/badge/okhttp%2Bretrofit-in%20use-83B81A?style=for-the-bedge&logo=Square)](./README.md)
[![MVVM](https://img.shields.io/badge/architecture-mvvm-83B81A?style=for-the-bedge&logo=Laravel)](./README.md)
[![Design Patterns](https://img.shields.io/badge/design%20patterns-in%20use-83B81A?style=for-the-bedge&logo=Laravel)](./README.md)
[![SOLID](https://img.shields.io/badge/solid%20principles-in%20use-83B81A?style=for-the-bedge&logo=Laravel)](./README.md)

This is an Android App demo which is going to apply all sorts of state-of-the-art technologies, including those are still in Alpha & Beta tests.

#### Interest points in terms of Modern Android Development(MAD) skills:

- [x] Android Jetpack: Compose
- [x] Jetpack Compose dynamic theming
- [x] Android Jetpack: Navigation
- [x] Android Jetpack: Navigation Animation
- [x] Android Jetpack Compose accompanist: Material placeholder
- [x] Android Jetpack: Room
- [x] Hilt
- [x] OkHttp + Retrofit
- [x] Kotlin Coroutines

#### The architecture of the App would use MVVM, with layers:

- View
- View Model
- Data(Repository/Data Source)

#### How data flows to ui?

In this project, I am experimenting a new way which separating data flows and control flows.

In data layer, repository creates a kotlin channels, all control flows are sent via this channel. Events include load success, error, data is update to date, or data is old etc.

All other data sources flow to Room database. Room acts as the single source of truth.

Any changes made to Room database would trigger a Kotlin flows emission to ViewModel and then to ui layer.

This project choose not using resource wrapper, and instead, it uses NULL to tell ui the data is not ready.

Benefits are:

1. [x] Easily organised single source of truth hierarchy.

1. [x] It decouple control flows from data. Therefore, technically any type of control signals could be sent freely. A wide range of status can be conveyed not only limited to loading, success, error etc. For those information not that important, we could auto-dismiss them with a delay.

1. [x] The ui looks really **reactive** and **smooth**!

Downsides:

1. [ ] It doesn't looks very **structured**. Further it creates some confusions inevitably.

1. [ ] Since control flows are independent, you may need extra effort for align what is going on especially when data is not available.

This structure is still under experiments and I will update my thoughts continuously.

Please leave your comments thoughts in the issues tab.

Thanks for following the project!

#### Screenshots:

Team picker:

<img src="https://raw.githubusercontent.com/hongwei-bai/android-nba-assist/main/screenshots/Screenshot_picker.png" width="240" height="480" />

GSW theme:

<img src="https://raw.githubusercontent.com/hongwei-bai/android-nba-assist/main/screenshots/Screenshot_gs_splash.png" width="240" height="480" /> <img src="https://raw.githubusercontent.com/hongwei-bai/android-nba-assist/main/screenshots/Screenshot_gs_schedule.png" width="240" height="480" /> <img src="https://raw.githubusercontent.com/hongwei-bai/android-nba-assist/main/screenshots/Screenshot_gs_news.png" width="240" height="480" />

<img src="https://raw.githubusercontent.com/hongwei-bai/android-nba-assist/main/screenshots/Screenshot_gs_stand.png" width="240" height="480" /> <img src="https://raw.githubusercontent.com/hongwei-bai/android-nba-assist/main/screenshots/Screenshot_gs_playin.png" width="240" height="480" /> <img src="https://raw.githubusercontent.com/hongwei-bai/android-nba-assist/main/screenshots/Screenshot_gs_settings.png" width="240" height="480" />

Lakers theme:

<img src="https://raw.githubusercontent.com/hongwei-bai/android-nba-assist/main/screenshots/Screenshot_lakers_splash.png" width="240" height="480" /> <img src="https://raw.githubusercontent.com/hongwei-bai/android-nba-assist/main/screenshots/Screenshot_lakers_schedule.png" width="240" height="480" /> <img src="https://raw.githubusercontent.com/hongwei-bai/android-nba-assist/main/screenshots/Screenshot_lakers_news.png" width="240" height="480" />

<img src="https://raw.githubusercontent.com/hongwei-bai/android-nba-assist/main/screenshots/Screenshot_lakers_stand.png" width="240" height="480" /> <img src="https://raw.githubusercontent.com/hongwei-bai/android-nba-assist/main/screenshots/Screenshot_lakers_playoff.png" width="240" height="480" /> <img src="https://raw.githubusercontent.com/hongwei-bai/android-nba-assist/main/screenshots/Screenshot_lakers_settings.png" width="240" height="480" />

Nets theme:

<img src="https://raw.githubusercontent.com/hongwei-bai/android-nba-assist/main/screenshots/Screenshot_net_splash.png" width="240" height="480" /> <img src="https://raw.githubusercontent.com/hongwei-bai/android-nba-assist/main/screenshots/Screenshot_net_schedule.png" width="240" height="480" /> <img src="https://raw.githubusercontent.com/hongwei-bai/android-nba-assist/main/screenshots/Screenshot_net_news.png" width="240" height="480" />

<img src="https://raw.githubusercontent.com/hongwei-bai/android-nba-assist/main/screenshots/Screenshot_net_stand.png" width="240" height="480" /> <img src="https://raw.githubusercontent.com/hongwei-bai/android-nba-assist/main/screenshots/Screenshot_net_playoff.png" width="240" height="480" /> <img src="https://raw.githubusercontent.com/hongwei-bai/android-nba-assist/main/screenshots/Screenshot_net_settings.png" width="240" height="480" />

#### Related projects

backend project repository:
[hongwei-bai/application-service-sports](https://github.com/hongwei-bai/application-service-sports)

Authentication service(backend) repository:
[hongwei-bai/application-service-authentication](https://github.com/hongwei-bai/application-service-authentication)

