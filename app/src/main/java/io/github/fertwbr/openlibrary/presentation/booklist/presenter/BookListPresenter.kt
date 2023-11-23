package io.github.fertwbr.openlibrary.presentation.booklist.presenter

import io.github.fertwbr.openlibrary.data.OpenLibraryClient
import io.github.fertwbr.openlibrary.presentation.booklist.BookListContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookListPresenter(view: BookListContract.View) : BookListContract.Presenter {

    private var view: BookListContract.View
    private val openLibraryClient = OpenLibraryClient()
    private lateinit var currentQuery: String
    private var currentPage: Int = 1
    private val searchPageLimit = 10

    init {
        this.view = view
    }

    override fun search(query: String) {
        currentQuery = query
        currentPage = 1
        view.onLoadingFirstPage()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = openLibraryClient.searchBooks(query, currentPage, searchPageLimit)
                withContext(Dispatchers.Main) {
                    view.onSuccessFirstPage(result.books)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view.onError(e.message)
                }
            }
        }
    }

    override fun getMoreBooks() {
        currentPage++
        view.onLoadingNextPage()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result =
                    openLibraryClient.searchBooks(currentQuery, currentPage, searchPageLimit)
                withContext(Dispatchers.Main) {
                    view.onSuccessNextPage(result.books)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view.onError(e.message)
                }
            }
        }
    }
}