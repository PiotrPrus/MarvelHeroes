package com.piotrprus.marvelheroes.infrastructure.di

import com.piotrprus.marvelheroes.BuildConfig
import com.piotrprus.marvelheroes.infrastructure.api.MarvelApi
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import timber.log.Timber
import java.math.BigInteger
import java.security.MessageDigest

val networkModule = module {
    single { createJson() }
    single<Logger> {
        val tag = "HttpClient"
        val logger = object : Logger {
            override fun log(message: String) {
                Timber.tag(tag).i(message)
            }
        }
        MessageLengthLimitingLogger(delegate = logger)
    }
    single { createHttpClient(get(), get()) }
    single { MarvelApi(get()) }
}

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true; useAlternativeNames = false }

fun createHttpClient(logger: Logger, json: Json) =
    HttpClient(Android) {
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = "gateway.marvel.com"
                val timestamp = System.currentTimeMillis().toString()
                parameters.append("ts", timestamp)
                parameters.append("apikey", BuildConfig.MARVEL_API_KEY)
                parameters.append("hash", generateMD5(timestamp))
            }
        }
        install(ContentNegotiation) { json(json) }
        install(Logging) {
            this.logger = logger
            level = LogLevel.INFO
        }
    }

private fun generateMD5(timestamp: String): String {
    val text =
        "$timestamp${BuildConfig.MARVEL_PRIVATE_KEY}${BuildConfig.MARVEL_API_KEY}"
    val digest = MessageDigest.getInstance("MD5")
    digest.update(text.toByteArray())
    val messageDigest = digest.digest()
    val bigInt = BigInteger(1, messageDigest)
    var hashText = bigInt.toString(16)
    while (hashText.length < 32) {
        hashText = "0$hashText"
    }
    return hashText
}