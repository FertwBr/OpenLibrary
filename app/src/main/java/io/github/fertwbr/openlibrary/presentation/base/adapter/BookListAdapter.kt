package io.github.fertwbr.openlibrary.presentation.base.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.fertwbr.openlibrary.databinding.BookItemViewBinding
import io.github.fertwbr.openlibrary.databinding.LoaderItemViewBinding
import io.github.fertwbr.openlibrary.model.Book
import io.github.fertwbr.openlibrary.presentation.base.viewholder.BookViewHolder
import io.github.fertwbr.openlibrary.presentation.base.viewholder.LoaderViewHolder

class BookListAdapter(
    private var hasLoader: Boolean = false,
    // Argument used at BookListActivity, its an error from IDE Lint
    @Suppress("unused") private var onBookItemClick: ((Book) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: MutableList<Book> = mutableListOf()
        // Needed to reset all data
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int =
        if (position < data.size && data.size > 0) ItemType.BOOK.ordinal else ItemType.LOADER.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ItemType.BOOK.ordinal -> {
                val binding = BookItemViewBinding.inflate(layoutInflater, parent, false)
                BookViewHolder(binding)
            }

            ItemType.LOADER.ordinal -> {
                val binding = LoaderItemViewBinding.inflate(layoutInflater, parent, false)
                LoaderViewHolder(binding)
            }

            else -> throw IllegalArgumentException("ViewHolder desconhecido para o tipo de item: $viewType")
        }
    }

    override fun getItemCount(): Int = data.size + if (hasLoader) 1 else 0

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (holder is LoaderViewHolder
            && payloads.isNotEmpty()
            && payloads.first() is Boolean
        ) {
            holder.showLoader(payloads.first() as Boolean)
        } else if (holder is BookViewHolder) {
            holder.bind(data[position])
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BookViewHolder) {
            holder.bind(data[position])
        }
    }

    fun addAllData(newData: MutableList<Book>) {
        val lastSize = data.size
        data.addAll(newData)
        notifyItemRangeChanged(lastSize, data.size)
    }

    fun showLoader() {
        notifyItemChanged(itemCount, true)
    }

    fun hideLoader() {
        notifyItemChanged(itemCount, false)
    }

    private enum class ItemType {
        BOOK, LOADER
    }
}