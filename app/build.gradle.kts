import java.util.Properties


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    kotlin("plugin.serialization") version "1.9.10"
//    id("com.google.devtools.ksp")
}

android {
    namespace = "com.mikeapp.sportsmate"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mikeapp.sportsmate"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val localProperties = Properties()
        val localPropertiesFile = File(project.rootDir, "local.properties")
        if (localPropertiesFile.exists()) {
            localProperties.load(localPropertiesFile.inputStream())  // Load properties into Properties object
        }
        val staticApiToken = localProperties.getProperty("staticApi.token", "ci")
        buildConfigField("String", "STATIC_API_TOKEN", "\"$staticApiToken\"")
//        buildTypes {
//            getByName("debug") { buildConfigField("String", "staticApiToken", staticApiToken) }
//            getByName("release") { buildConfigField("String", "staticApiToken", staticApiToken) }
//        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.symbol.processing.api)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.material)
    implementation(libs.lottie)
    implementation(libs.lottie.compose)
    implementation(libs.coil.compose)
    implementation(libs.ui)
    implementation(libs.material3)
    implementation(libs.androidx.foundation)
//    implementation(libs.androidx.material.pullrefresh)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.runtime.livedata)
    implementation(platform(libs.androidx.compose.bom.v20231001))
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.moshi)
    kapt(libs.moshi.kotlin.codegen)
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.moshi)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom.v20231001))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // network
    implementation(libs.retrofit.v290)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.gson)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.okhttp3.okhttp)

    // di
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
//    implementation(libs.androidx.hilt.lifecycle.viewmodel)

    // For testing (optional)
    testImplementation(libs.hilt.android.testing)
    kaptTest(libs.hilt.compiler)
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.compiler)
}