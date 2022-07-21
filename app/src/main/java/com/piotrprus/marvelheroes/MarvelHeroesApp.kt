package com.piotrprus.marvelheroes

import android.app.Application
import com.piotrprus.marvelheroes.infrastructure.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class MarvelHeroesApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MarvelHeroesApp)
            modules(networkModule)
        }
    }
}