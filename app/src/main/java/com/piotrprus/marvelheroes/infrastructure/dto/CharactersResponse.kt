package com.piotrprus.marvelheroes.infrastructure.dto

@kotlinx.serialization.Serializable
data class CharactersResponse(
    val code: Int,
    val status: String,
    val data: CharactersResponseData
)

@kotlinx.serialization.Serializable
data class CharactersResponseData(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<CharacterDTO>
)
