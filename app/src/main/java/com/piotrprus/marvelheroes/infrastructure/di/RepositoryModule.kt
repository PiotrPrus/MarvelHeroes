package com.piotrprus.marvelheroes.infrastructure.di

import com.piotrprus.marvelheroes.infrastructure.repository.CharactersRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<CharactersRepository> { CharactersRepository.Impl(get()) }
}