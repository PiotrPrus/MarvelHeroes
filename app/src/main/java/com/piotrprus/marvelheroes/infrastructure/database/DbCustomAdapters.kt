package com.piotrprus.marvelheroes.infrastructure.database

import com.piotrprus.marvelheroes.data.model.ThumbnailItem
import com.squareup.sqldelight.ColumnAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object DbCustomAdapters {
    val listOfThumbnailAdapter = object : ColumnAdapter<List<ThumbnailItem>, String> {
        override fun decode(databaseValue: String): List<ThumbnailItem> =
            if (databaseValue.isEmpty()) {
                listOf()
            } else {
                Json.decodeFromString(databaseValue)
            }

        override fun encode(value: List<ThumbnailItem>): String =
            Json.encodeToString(value)
    }
}