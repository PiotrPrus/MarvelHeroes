package com.piotrprus.marvelheroes

import com.piotrprus.marvelheroes.data.model.CharacterItem
import com.piotrprus.marvelheroes.data.model.FavouriteItem
import com.piotrprus.marvelheroes.db.HeroesDb
import com.piotrprus.marvelheroes.infrastructure.database.DbCustomAdapters
import com.piotrprus.marvelheroes.infrastructure.database.FavouriteDataStore
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import compiotrprusmarvelheroesdb.Favourite
import org.koin.dsl.module
import java.io.*

object TestUtils {
    @Throws(IOException::class)
    fun readFile(fileName: String): String {
        var inputStream: InputStream? = null
        try {
            inputStream =
                javaClass.classLoader?.getResourceAsStream(fileName)
            val builder = StringBuilder()
            val reader = BufferedReader(InputStreamReader(inputStream))

            var str: String? = reader.readLine()
            while (str != null) {
                builder.append(str)
                str = reader.readLine()
            }
            return builder.toString()
        } finally {
            inputStream?.close()
        }
    }

    private fun createInMemorySqlDriver() =
        JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
            HeroesDb.Schema.create(this)
        }

    val testModule = module {
        single<SqlDriver> { createInMemorySqlDriver() }
        single {
            HeroesDb(
                get(),
                favouriteAdapter = Favourite.Adapter(
                    comicsAdapter = DbCustomAdapters.listOfThumbnailAdapter,
                    eventsAdapter = DbCustomAdapters.listOfThumbnailAdapter
                )
            )
        }
        single<FavouriteDataStore> { FavouriteDataStore.Impl(get()) }
    }

    object Fake {
        val IronMan = FavouriteItem(
            info = CharacterItem(
                id = 1009368,
                name = "Iron Man",
                imageUrl = "https://i.annihil.us/u/prod/marvel/i/mg/9/c0/527bb7b37ff55.jpg",
                description = "Wounded, captured and forced to build a weapon by his enemies, billionaire industrialist Tony Stark instead created an advanced suit of armor to save his life and escape captivity. Now with a new outlook on life, Tony uses his money and intelligence to make the world a safer, better place as Iron Man."
            ), comics = listOf(), events = listOf()
        )
        val fiveHeroes = listOf(
            FavouriteItem(
                info = CharacterItem(
                    id = 100,
                    name = "Iron Man",
                    imageUrl = "https://test.jpg",
                    description = ""
                ), comics = listOf(), events = listOf()
            ),
            FavouriteItem(
                info = CharacterItem(
                    id = 101,
                    name = "Hulk",
                    imageUrl = "https://test.jpg",
                    description = ""
                ), comics = listOf(), events = listOf()
            ),
            FavouriteItem(
                info = CharacterItem(
                    id = 102,
                    name = "Captain America",
                    imageUrl = "https://test.jpg",
                    description = ""
                ), comics = listOf(), events = listOf()
            ),
            FavouriteItem(
                info = CharacterItem(
                    id = 103,
                    name = "Spider Man",
                    imageUrl = "https://test.jpg",
                    description = ""
                ), comics = listOf(), events = listOf()
            ),
            FavouriteItem(
                info = CharacterItem(
                    id = 104,
                    name = "Venom",
                    imageUrl = "https://test.jpg",
                    description = ""
                ), comics = listOf(), events = listOf()
            )
        )
    }
}