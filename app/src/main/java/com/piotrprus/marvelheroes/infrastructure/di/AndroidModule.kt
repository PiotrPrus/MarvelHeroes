package com.piotrprus.marvelheroes.infrastructure.di

import com.piotrprus.marvelheroes.feature.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {
    viewModel { HomeViewModel(get()) }
}