@file:OptIn(ExperimentalCoroutinesApi::class)

package com.piotrprus.marvelheroes.infrastructure.database

import com.piotrprus.marvelheroes.TestUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class FavouriteDataStoreTest: KoinTest {

    private val favouriteStore by inject<FavouriteDataStore>()

    @BeforeTest
    fun before() {
        startKoin { modules(TestUtils.testModule) }
    }

    @Test
    fun `Add item to database and check it exists`() = runTest {
        val hero = TestUtils.Fake.IronMan
        favouriteStore.addItem(hero)
        val itemFromDb = favouriteStore.getItem(hero.info.id)
        assertEquals(hero.info.name, itemFromDb.info.name)
    }

    @Test
    fun `Add 5 items to DB and assert the size of DB is 5`() = runTest {
        val heroes = TestUtils.Fake.fiveHeroes
        heroes.forEach {
            favouriteStore.addItem(it)
        }
        val favSize = favouriteStore.getAll().first().size
        assertEquals(heroes.size, favSize)
    }

    @AfterTest
    fun after() {
        stopKoin()
    }
}