package io.github.fertwbr.openlibrary.presentation.base.viewholder

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.RecyclerView
import coil.load
import io.github.fertwbr.openlibrary.R
import io.github.fertwbr.openlibrary.databinding.BookItemViewBinding
import io.github.fertwbr.openlibrary.model.Book
import io.github.fertwbr.openlibrary.model.Book.Size.MEDIUM

class BookViewHolder(
    private val itemViewBinding: BookItemViewBinding,
    private var onBookItemClick: ((Book) -> Unit)? = null
) : RecyclerView.ViewHolder(itemViewBinding.root) {

    fun bind(book: Book) {
        with(itemViewBinding) {
            bookTitle.text = book.title
            bookAuthor.text = book.authors.toString()
            book.publishYear?.let {
                bookPublishYear.visibility = VISIBLE
                bookPublishYear.text = "$it"
            }

            bookCover.load(book.getCoverUrl(MEDIUM)) {
                crossfade(true)
                error(R.drawable.broken_image)
                listener(
                    onStart = {
                        bookCoverProgressBar.visibility = VISIBLE
                    },
                    onError = { _, _ ->
                        bookCoverProgressBar.visibility = GONE
                    },
                    onSuccess = { _, _ ->
                        bookCoverProgressBar.visibility = GONE
                    }
                )
            }

            root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onBookItemClick?.invoke(book)
                }
            }

        }
    }

}