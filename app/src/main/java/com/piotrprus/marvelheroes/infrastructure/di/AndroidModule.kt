package com.piotrprus.marvelheroes.infrastructure.di

import com.piotrprus.marvelheroes.db.HeroesDb
import com.piotrprus.marvelheroes.feature.detail.DetailViewModel
import com.piotrprus.marvelheroes.feature.favourite.FavouriteViewModel
import com.piotrprus.marvelheroes.feature.home.HomeViewModel
import com.piotrprus.marvelheroes.infrastructure.database.DbCustomAdapters
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import compiotrprusmarvelheroesdb.Favourite
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { params -> DetailViewModel(params.get(), get(), get()) }
    single<SqlDriver> { AndroidSqliteDriver(HeroesDb.Schema, get(), "Heroes.db") }
    single {
        HeroesDb(
            get(),
            favouriteAdapter = Favourite.Adapter(
                comicsAdapter = DbCustomAdapters.listOfThumbnailAdapter,
                eventsAdapter = DbCustomAdapters.listOfThumbnailAdapter
            )
        )
    }
    viewModel { FavouriteViewModel(get()) }
}