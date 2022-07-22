object Versions {
    const val kotlin = "1.7.0"

    object Android {
        const val gradle = "7.1.3"
        const val minSdk = 23
        const val compileSdk = 32
        const val targetSdk = compileSdk
    }

    const val buildKonfig = "0.11.0"
    const val junit = "4.13.2"
    const val koin = "3.1.2"
    const val ktor = "2.0.1"
    const val playServicesLocation = "18.0.0"
    const val settings = "0.9"
    const val sqlDelight = "1.5.2"
    const val stately = "1.1.7"
    const val turbine = "0.7.0"
    const val mokoResources = "0.19.0"
    const val kermit = "1.1.3"

    object KotlinX {
        const val coroutines = "1.6.0"
        const val dateTime = "0.2.1"
        const val serialization = "1.2.2"
    }

    object AndroidX {
        const val activityCompose = "1.4.0"
        const val compose = "1.2.0-beta02"
        const val coreKtx = "1.7.0"
        const val dataStore = "1.0.0"
        const val lifecycle = "2.4.0-alpha02"
        const val navigationCompose = "2.5.0-rc02"
        const val workManager = "2.7.1"

        object Test {
            const val core = "1.4.0"
            const val junit = "1.1.3"
        }
    }
}

object Deps {
    const val junit = "junit:junit:${Versions.junit}"
    const val playServicesLocation = "com.google.android.gms:play-services-location:${Versions.playServicesLocation}"
    const val turbine = "app.cash.turbine:turbine:${Versions.turbine}"
    const val coil = "io.coil-kt:coil-compose:2.1.0"
    const val timber = "com.jakewharton.timber:timber:4.7.1"
    const val jodaTime = "joda-time:joda-time:2.10.10"
    const val mokoParcelize = "dev.icerock.moko:parcelize:0.8.0"

    object MapLibre {
        const val android = "org.maplibre.gl:android-sdk:9.5.2"
    }

    object AndroidX {
        const val activityCompose = "androidx.activity:activity-compose:${Versions.AndroidX.activityCompose}"
        const val coreKtx = "androidx.core:core-ktx:${Versions.AndroidX.coreKtx}"
        const val dataStore = "androidx.datastore:datastore-preferences:${Versions.AndroidX.dataStore}"
        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.AndroidX.lifecycle}"
        const val navigationCompose = "androidx.navigation:navigation-compose:${Versions.AndroidX.navigationCompose}"
        const val workManager = "androidx.work:work-runtime-ktx:${Versions.AndroidX.workManager}"

        object Room {
            private const val version = "2.4.2"
            const val runtime = "androidx.room:room-runtime:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            const val ktx = "androidx.room:room-ktx:$version"
        }

        object Compose {
            const val material = "androidx.compose.material:material:${Versions.AndroidX.compose}"
            const val materialIcons = "androidx.compose.material:material-icons-core:${Versions.AndroidX.compose}"
            const val materialIconsExtended =
                "androidx.compose.material:material-icons-extended:${Versions.AndroidX.compose}"
            const val test = "androidx.compose.ui:ui-test-junit4:${Versions.AndroidX.compose}"
            const val tooling = "androidx.compose.ui:ui-tooling:${Versions.AndroidX.compose}"
            const val ui = "androidx.compose.ui:ui:${Versions.AndroidX.compose}"
            const val constraintLayout =
                "androidx.constraintlayout:constraintlayout-compose:1.0.0-alpha07"
            const val paging = "androidx.paging:paging-compose:1.0.0-alpha15"
        }

        object Test {
            const val core = "androidx.test:core:${Versions.AndroidX.Test.core}"
            const val junit = "androidx.test.ext:junit:${Versions.AndroidX.Test.junit}"
        }

        object Glance {
            private const val version = "1.0.0-alpha03"

            const val glance = "androidx.glance:glance:$version"
            const val appWidget = "androidx.glance:glance-appwidget:$version"
        }
    }

    object Accompanist {
        private const val version = "0.24.12-rc"
        const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:$version"
        const val placeholder = "com.google.accompanist:accompanist-placeholder-material:$version"
        const val pager = "com.google.accompanist:accompanist-pager:$version"
        const val pagerIndicators = "com.google.accompanist:accompanist-pager-indicators:$version"
        const val permissions = "com.google.accompanist:accompanist-permissions:$version"
        const val navigationMaterial = "com.google.accompanist:accompanist-navigation-material:$version"
        const val navigationAnimation = "com.google.accompanist:accompanist-navigation-animation:$version"
        const val systemcController = "com.google.accompanist:accompanist-systemuicontroller:$version"
    }

    object Google {
        const val playServicesLocation = "com.google.android.gms:play-services-location:19.0.1"
        const val gson = "com.google.code.gson:gson:2.9.0"
        const val licenses = "com.google.android.gms:play-services-oss-licenses:17.0.0"
        const val material = "com.google.android.material:material:1.6.1"
    }

    object GoogleMaps {
        const val compose = "com.google.maps.android:maps-compose:2.2.1"
        const val mapsServices = "com.google.android.gms:play-services-maps:18.0.2"
        const val mapsKtx = "com.google.maps.android:maps-ktx:3.4.0"
    }

    object Firebase {
        const val bom = "com.google.firebase:firebase-bom:29.0.3"
        const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
        const val analytics = "com.google.firebase:firebase-analytics-ktx"
        const val inAppMess = "com.google.firebase:firebase-inappmessaging-display-ktx"
        const val remoteConfig = "com.google.firebase:firebase-config-ktx"
        const val crashlyticsGradle = "com.google.firebase:firebase-crashlytics-gradle:2.7.1"
        const val dynamicLinks = "com.google.firebase:firebase-dynamic-links-ktx"
        const val perf = "com.google.firebase:firebase-perf-ktx"
        const val messaging = "com.google.firebase:firebase-messaging-ktx"
    }

    object KotlinX {
        const val dateTime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.KotlinX.dateTime}"

        object Coroutines {
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KotlinX.coroutines}"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KotlinX.coroutines}"
            const val playServices =
                "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.KotlinX.coroutines}"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.KotlinX.coroutines}"
        }

        object Serialization {
            const val core = "org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.KotlinX.serialization}"
            const val json = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KotlinX.serialization}"
        }
    }

    object Koin {
        const val core = "io.insert-koin:koin-core:${Versions.koin}"
        const val test = "io.insert-koin:koin-test:${Versions.koin}"
        const val android = "io.insert-koin:koin-android:${Versions.koin}"
        const val compose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
        const val workManager = "io.insert-koin:koin-androidx-workmanager:${Versions.koin}"
    }

    object Ktor {
        const val android = "io.ktor:ktor-client-android:${Versions.ktor}"
        const val core = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val ios = "io.ktor:ktor-client-ios:${Versions.ktor}"
        const val json = "io.ktor:ktor-client-json:${Versions.ktor}"
        const val logging = "io.ktor:ktor-client-logging:${Versions.ktor}"
        const val mock = "io.ktor:ktor-client-mock:${Versions.ktor}"
        const val serialization = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
        const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
    }

    object SqlDelight {
        const val android = "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}"
        const val coroutines = "com.squareup.sqldelight:coroutines-extensions:${Versions.sqlDelight}"
        const val jvm = "com.squareup.sqldelight:sqlite-driver:${Versions.sqlDelight}"
        const val native = "com.squareup.sqldelight:native-driver:${Versions.sqlDelight}"
    }

    object Settings {
        const val core = "com.russhwolf:multiplatform-settings:${Versions.settings}"
        const val coroutines = "com.russhwolf:multiplatform-settings-coroutines:${Versions.settings}"
        const val dataStore = "com.russhwolf:multiplatform-settings-datastore:${Versions.settings}"
        const val serialization = "com.russhwolf:multiplatform-settings-serialization:${Versions.settings}"
        const val test = "com.russhwolf:multiplatform-settings-test:${Versions.settings}"
    }

    object Resources {
        const val core = "dev.icerock.moko:resources:${Versions.mokoResources}"
        const val compose = "dev.icerock.moko:resources-compose:${Versions.mokoResources}"
    }

    object Kermit {
        const val core = "co.touchlab:kermit:${Versions.kermit}"
        const val crashlytics = "co.touchlab:kermit-crashlytics:${Versions.kermit}"
        const val iosStub = "co.touchlab:kermit-crashlytics-test:${Versions.kermit}"
    }

    object Stately {
        const val core = "co.touchlab:stately-common:${Versions.stately}"
    }
}
