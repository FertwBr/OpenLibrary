package io.github.fertwbr.openlibrary.presentation.booklist.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.fertwbr.openlibrary.databinding.ActivityBookListBinding
import io.github.fertwbr.openlibrary.model.Book
import io.github.fertwbr.openlibrary.presentation.base.adapter.BookListAdapter
import io.github.fertwbr.openlibrary.presentation.booklist.BookListContract
import io.github.fertwbr.openlibrary.presentation.booklist.presenter.BookListPresenter

class BookListActivity : AppCompatActivity(), BookListContract.View {

    private lateinit var binding: ActivityBookListBinding
    private lateinit var presenter: BookListContract.Presenter
    private lateinit var bookListAdapter: BookListAdapter
    private val scrollThreshold = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = BookListPresenter(this)

        setupSearchButton()
        setupRecyclerView()
    }

    private fun setupSearchButton() {
        with(binding) {
            binding.searchButton.setOnClickListener {
                booksRecyclerView.scrollToPosition(0)
                presenter.search(searchField.text.toString())
            }
        }
    }

    private fun setupRecyclerView() {
        bookListAdapter = BookListAdapter(true) { book: Book ->
            Log.d("KELVAO", "$book")
        }

        val linearLayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.booksRecyclerView.apply {
            adapter = bookListAdapter
            layoutManager = linearLayoutManager
            addOnScrollListener(object :
                OpenLibraryScrollListener(linearLayoutManager, scrollThreshold) {
                override fun onScrolledToThreshold() {
                    presenter.getMoreBooks()
                }
            })
        }
    }

    override fun onSuccessFirstPage(result: List<Book>) {
        bookListAdapter.hideLoader()
        bookListAdapter.data = result.toMutableList()
    }

    override fun onSuccessNextPage(result: List<Book>) {
        bookListAdapter.hideLoader()
        bookListAdapter.addAllData(result.toMutableList())
    }

    override fun onLoadingFirstPage() {
        bookListAdapter.showLoader()
    }

    override fun onLoadingNextPage() {
        bookListAdapter.showLoader()
    }

    override fun onError(errorMessage: String?) {
        Log.e(this.localClassName, errorMessage ?: "erro")
    }
}