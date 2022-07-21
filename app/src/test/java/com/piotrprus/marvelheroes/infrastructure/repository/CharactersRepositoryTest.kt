@file:OptIn(ExperimentalCoroutinesApi::class)

package com.piotrprus.marvelheroes.infrastructure.repository

import com.piotrprus.marvelheroes.infrastructure.api.MarvelApi
import com.piotrprus.marvelheroes.infrastructure.api.createMarvelApiMockClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CharactersRepositoryTest {

    private val marvelApi = MarvelApi(createMarvelApiMockClient())
    private val repository = CharactersRepository.Impl(marvelApi)

    @Test
    fun `Check that 8th character is Abyss`() = runTest {
        val char = repository.fetchHeroes(0)[7]
        assertEquals("Abyss", char.name)
        assertTrue { char.imageUrl.takeLast(4) == ".jpg" }
        assertTrue { char.imageUrl.take(4) == "http" }
    }

}