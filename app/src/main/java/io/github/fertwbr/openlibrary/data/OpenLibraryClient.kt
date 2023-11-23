package io.github.fertwbr.openlibrary.data

import io.github.fertwbr.openlibrary.model.OpenLibraryModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class OpenLibraryClient {

    private var httpClient: HttpClient = HttpClient(Android) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun searchBooks(query: String, page: Int, limit: Int): OpenLibraryModel {
        return httpClient.get(
            "https://openlibrary.org/search.json?q=$query" +
                    "&fields=title,author_name,first_publish_year,edition_key" +
                    "&page=$page" +
                    "&limit=$limit"
        ).body()
    }
}