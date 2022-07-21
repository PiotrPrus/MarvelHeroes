package com.piotrprus.marvelheroes.infrastructure.di

import com.piotrprus.marvelheroes.BuildConfig
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
}

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true; useAlternativeNames = false }

fun createHttpClient(logger: Logger, json: Json) =
    HttpClient(Android) {
        defaultRequest {
            url {
                protocol = URLProtocol.HTTP
                host = "gateway.marvel.com"
                parameters.append("apikey", BuildConfig.MARVEL_API_KEY)
                parameters.append("hash", generateMD5())
            }
        }
        install(ContentNegotiation) { json(json) }
        install(Logging) {
            this.logger = logger
            level = LogLevel.INFO
        }
    }

private fun generateMD5(): String {
    val text =
        "${System.currentTimeMillis()}${BuildConfig.MARVEL_PRIVATE_KEY}${BuildConfig.MARVEL_API_KEY}"
    val crypt = MessageDigest.getInstance("MD5")
    crypt.update(text.toByteArray())
    return BigInteger(1, crypt.digest()).toString(16)
}