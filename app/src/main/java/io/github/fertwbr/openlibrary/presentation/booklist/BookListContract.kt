package io.github.fertwbr.openlibrary.presentation.booklist

import io.github.fertwbr.openlibrary.model.Book

interface BookListContract {
    interface View {
        fun onSuccessFirstPage(result: List<Book>)
        fun onSuccessNextPage(result: List<Book>)
        fun onLoadingFirstPage()
        fun onLoadingNextPage()
        fun onError(errorMessage: String?)
    }

    interface Presenter {
        fun search(query: String)
        fun getMoreBooks()
    }
}