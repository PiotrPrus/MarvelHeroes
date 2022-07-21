plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlinx-serialization")
    id("com.squareup.sqldelight")
}

android {
    namespace = "com.piotrprus.marvelheroes"
    compileSdk = Versions.Android.compileSdk

    defaultConfig {
        applicationId = "com.piotrprus.marvelheroes"
        minSdk = Versions.Android.minSdk
        targetSdk = Versions.Android.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.AndroidX.compose
    }
}

dependencies {
    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.Google.material)
    implementation(Deps.AndroidX.Compose.ui)
    implementation(Deps.AndroidX.Compose.material)
    implementation(Deps.AndroidX.Compose.materialIcons)
    implementation(Deps.AndroidX.Compose.materialIconsExtended)
    implementation(Deps.AndroidX.lifecycleRuntime)
    implementation(Deps.AndroidX.activityCompose)
    debugImplementation(Deps.AndroidX.Compose.tooling)

    implementation(Deps.coil)

    implementation(Deps.Accompanist.placeholder)
    implementation(Deps.Accompanist.navigationMaterial)
    implementation(Deps.Accompanist.navigationAnimation)

    implementation(Deps.AndroidX.navigationCompose)

    implementation(Deps.KotlinX.Coroutines.core)
    implementation(Deps.KotlinX.Coroutines.android)

    //KTOR
    implementation(Deps.Ktor.android)
    implementation(Deps.Ktor.core)
    implementation(Deps.Ktor.json)
    implementation(Deps.Ktor.logging)
    implementation(Deps.Ktor.serialization)

    implementation(Deps.KotlinX.Serialization.core)
    implementation(Deps.KotlinX.Serialization.json)

    //KOIN - DI
    implementation(Deps.Koin.core)
    implementation(Deps.Koin.android)
    implementation(Deps.Koin.compose)

    implementation(Deps.timber)

    testImplementation(Deps.junit)
    androidTestImplementation(Deps.AndroidX.Test.junit)
    androidTestImplementation(Deps.AndroidX.Compose.test)
}

sqldelight {
    database("HeroesDb") {
        packageName = "com.piotrprus.marvelheroes.db"
    }
}