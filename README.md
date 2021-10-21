# android-nba-assistant

[![Build Status](https://hongwei-test1.top:8444/buildStatus/icon?job=android-nba-assist)](https://hongwei-test1.top:8444/job/android-nba-assist/)

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

