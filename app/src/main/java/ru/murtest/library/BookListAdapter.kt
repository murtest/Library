package ru.murtest.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ru.murtest.library.databinding.FragmentBookListBinding
import ru.murtest.library.databinding.ListItemBookBinding
import ru.murtest.library.databinding.ListItemBookFavoriteBinding

class BookListAdapter(private val books: List<Book>) :
    RecyclerView.Adapter<BookListAdapter.ListRecyclerViewHolder>() {

    sealed class ListRecyclerViewHolder(binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        class BookHolder(private val binding: ListItemBookBinding) :
            ListRecyclerViewHolder(binding) {
            fun bind(book: Book) {
                binding.bookTitle.text = book.title
                binding.bookDateRange.text = book.dateReadStart.toString()
                    .plus(if (book.isFinished) " - ".plus(book.dateReadEnd.toString()) else "")

                binding.root.setOnClickListener {
                    Toast.makeText(
                        binding.root.context,
                        "${book.title} clicked!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        class BookFavoriteHolder(private val binding: ListItemBookFavoriteBinding) :
            ListRecyclerViewHolder(binding) {
            fun bind(book: Book) {
                binding.bookTitle.text = book.title
                binding.bookDateRange.text = book.dateReadStart.toString()
                    .plus(if (book.isFinished) " - ".plus(book.dateReadEnd.toString()) else "")

                binding.root.setOnClickListener {
                    Toast.makeText(
                        binding.root.context,
                        "${book.title} clicked!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.list_item_book -> ListRecyclerViewHolder.BookHolder(
                ListItemBookBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
            R.layout.list_item_book_favorite -> ListRecyclerViewHolder.BookFavoriteHolder(
                ListItemBookFavoriteBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Unsupported layout")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val book = books[position]

        return if (book.isFavorite) R.layout.list_item_book_favorite else R.layout.list_item_book
    }

    override fun onBindViewHolder(holder: ListRecyclerViewHolder, position: Int) {
        val book = books[position]
        when (holder) {
            is ListRecyclerViewHolder.BookFavoriteHolder -> holder.bind(book)
            is ListRecyclerViewHolder.BookHolder -> holder.bind(book)
        }
    }

    override fun getItemCount(): Int = books.size
}

