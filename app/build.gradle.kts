import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    kotlin("kapt")
}

android {
    compileSdk = Versions.compileSdk
    buildToolsVersion = Versions.buildToolsVersion

    gradleLocalProperties(rootDir).getProperty("publicAccess.token")?.let { publicAccessToken ->
        buildTypes {
            getByName("debug") { buildConfigField("String", "publicAccessToken", publicAccessToken) }
            getByName("release") { buildConfigField("String", "publicAccessToken", publicAccessToken) }
        }
    }

    val releaseKeyStoreLocation: String? = gradleLocalProperties(rootDir).getProperty("keyStore.release.location")
    val releaseKeyStorePassword: String? = gradleLocalProperties(rootDir).getProperty("keyStore.release.storePassword")
    val releaseKeyAlias: String? = gradleLocalProperties(rootDir).getProperty("keyStore.release.keyAlias")
    val releaseKeyPassword: String? = gradleLocalProperties(rootDir).getProperty("keyStore.release.keyPassword")
    val releaseKeyStoreExist: Boolean = releaseKeyStoreLocation?.let { File(it).run { exists() && isFile } } ?: false
    val isReleaseSignatureAvailable: Boolean = releaseKeyStoreExist && releaseKeyStorePassword != null && releaseKeyAlias != null && releaseKeyPassword != null

    signingConfigs {
        getByName("debug") {
            keyAlias = "appKey"
            keyPassword = "appKey"
            storeFile = file("../keystore/appKeyNbaDebug.jks")
            storePassword = "appKey"
        }
        if (isReleaseSignatureAvailable) {
            create("release") {
                keyAlias = "release"
                keyPassword = "my release key password"
                storeFile = file("/home/miles/keystore.jks")
                storePassword = "my keystore password"
            }
        }
    }

    defaultConfig {
        applicationId = "com.hongwei.android_nba_assist"
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = Versions.versionCode
        versionName = Versions.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
                arg("room.incremental", true)
                arg("room.expandProjection", true)
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            if (isReleaseSignatureAvailable) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
        debug {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    
    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        resources.excludes.add("**/attach_hotspot_windows.dll")
        resources.excludes.add("**/attach_hotspot_windows.dll")
        resources.excludes.add("META-INF/licenses/**")
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }
}

dependencies {
    implementation(platform(kotlin("bom", Versions.kotlin)))
    implementation(kotlin("stdlib-jdk8"))

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}")
    implementation("androidx.core:core-ktx:1.7.0")

    // Android
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:${Versions.hilt}")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    kapt("com.google.dagger:hilt-android-compiler:${Versions.hilt}")
    annotationProcessor("com.google.dagger:hilt-compiler:${Versions.hilt}")

    // Network
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.9.0"))
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.moshi:moshi:1.13.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.12.0")

    // Jetpack Navigation
    implementation("androidx.navigation:navigation-ui-ktx:${Versions.navigation}")
    implementation("androidx.navigation:navigation-compose:2.5.0-alpha02")
    // Jetpack Compose
    implementation("androidx.compose.ui:ui:${Versions.compose}")
    implementation("androidx.compose.ui:ui-tooling-preview:${Versions.compose}")
    debugImplementation("androidx.compose.ui:ui-tooling:${Versions.compose}")
    implementation("androidx.compose.foundation:foundation:${Versions.compose}")
    implementation("androidx.compose.material:material:${Versions.compose}")
    implementation("androidx.compose.material:material-icons-core:${Versions.compose}")
    implementation("androidx.compose.material:material-icons-extended:${Versions.compose}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.0-alpha02")
    implementation("androidx.compose.runtime:runtime:${Versions.compose}")
    implementation("androidx.compose.runtime:runtime-livedata:${Versions.compose}")
    implementation("androidx.compose.runtime:runtime-rxjava2:${Versions.compose}")
    // Jetpack Lifecycle
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    // Jetpack Room
    implementation("androidx.room:room-runtime:${Versions.room}")
    implementation("com.google.code.gson:gson:2.8.8")
    implementation("androidx.room:room-ktx:${Versions.room}")
    kapt("androidx.room:room-compiler:${Versions.room}")

    // Jetpack Compose accompanist
    implementation("com.google.accompanist:accompanist-pager:${Versions.composeAccompanist}")
    implementation("com.google.accompanist:accompanist-pager-indicators:${Versions.composeAccompanist}")
    implementation("com.google.accompanist:accompanist-systemuicontroller:${Versions.composeAccompanist}")
    implementation("com.google.accompanist:accompanist-swiperefresh:${Versions.composeAccompanist}")
    implementation("com.google.accompanist:accompanist-navigation-animation:${Versions.composeAccompanist}")
    implementation("com.google.accompanist:accompanist-placeholder-material:${Versions.composeAccompanist}")

    // Animations and image loading
    implementation("io.coil-kt:coil-compose:${Versions.coilCompose}")
    implementation("com.airbnb.android:lottie:${Versions.lottie}")
    implementation("com.airbnb.android:lottie-compose:${Versions.lottieCompose}")

    // System utilities
    implementation("androidx.multidex:multidex:${Versions.multidex}")

    // Unit testing
    testImplementation("junit:junit:4.13.2")
    // Instrument testing
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
}

kapt {
    javacOptions {
        // These options are normally set automatically via the Hilt Gradle plugin, but we
        // set them manually to workaround a bug in the Kotlin 1.5.20
        option("-Adagger.fastInit=ENABLED")
        option("-Adagger.hilt.android.internal.disableAndroidSuperclassValidation=true")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
