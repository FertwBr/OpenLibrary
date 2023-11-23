package io.github.fertwbr.openlibrary.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenLibraryModel(
    @SerialName("docs") val books: List<Book>
)

@Serializable
data class Book(
    @SerialName("edition_key") private val _key: List<String>,
    val title: String,
    @SerialName("first_publish_year") val publishYear: Int? = null,
    @SerialName("author_name") val authors: List<String> = listOf(),

) {
    private val imageApiUrl = "https://covers.openlibrary.org/b/olid/"
    private val key
        get() = _key.first().substringAfterLast("/")

    fun getCoverUrl(size: Size) = "$imageApiUrl$key-${size.sizeTag}.jpg?default=false"

    enum class Size(val sizeTag: String) {
        SMALL("S"), MEDIUM("M"), LARGE("L")
    }
}