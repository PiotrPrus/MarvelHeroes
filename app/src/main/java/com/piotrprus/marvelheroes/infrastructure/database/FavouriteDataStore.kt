package com.piotrprus.marvelheroes.infrastructure.database

import com.piotrprus.marvelheroes.data.model.CharacterItem
import com.piotrprus.marvelheroes.data.model.FavouriteItem
import com.piotrprus.marvelheroes.db.HeroesDb
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

interface FavouriteDataStore {
    suspend fun addItem(item: FavouriteItem)
    suspend fun deleteItem(heroId: Int)
    fun getAll(): Flow<FavouriteItem>
    suspend fun getItem(heroId: Int): FavouriteItem
    fun isStored(heroId: Int): Flow<Boolean>

    class Impl(private val database: HeroesDb) : FavouriteDataStore {
        override suspend fun addItem(item: FavouriteItem) {
            database.favouriteQueries.insertItem(
                hero_id = item.info.id,
                description = item.info.description,
                title = item.info.name,
                image_url = item.info.imageUrl,
                comics = item.comics,
                events = item.events
            )
        }

        override suspend fun deleteItem(heroId: Int) {
            database.favouriteQueries.deleteFavourite(heroId)
        }

        override fun getAll(): Flow<FavouriteItem> =
            database.favouriteQueries.selectAll(mapper = { hero_id, description, title, image_url, comics, events ->
                FavouriteItem(
                    CharacterItem(
                        id = hero_id,
                        name = title,
                        imageUrl = image_url,
                        description = description
                    ), comics = comics, events = events
                )
            }).executeAsList().asFlow()

        override suspend fun getItem(heroId: Int): FavouriteItem =
            database.favouriteQueries.selectFavourite(
                hero_id = heroId,
                mapper = { hero_id, description, title, image_url, comics, events ->
                    FavouriteItem(
                        CharacterItem(
                            id = hero_id,
                            name = title,
                            imageUrl = image_url,
                            description = description
                        ), comics = comics, events = events
                    )
                })
                .executeAsOne()

        override fun isStored(heroId: Int): Flow<Boolean> =
            database.favouriteQueries.isStoredById(heroId).asFlow().mapToOne()

    }
}