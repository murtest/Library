package ru.murtest.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.murtest.library.databinding.FragmentBookListBinding
import ru.murtest.library.databinding.ListItemBookBinding
import java.util.*

class BookListAdapter(
    private val books: List<Book>,
    private val onBookClicked: (bookId: UUID) -> Unit) :
    RecyclerView.Adapter<BookListAdapter.BookHolder>() {

    class BookHolder(
        private val binding: ListItemBookBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book, onBookClicked: (bookId: UUID) -> Unit) {
            binding.bookTitle.text = book.title
            binding.bookAuthor.text = book.author
            binding.bookDateRange.text = book.dateReadStart.toString()
                .plus(if (book.isFinished) " - ".plus(book.dateReadEnd.toString()) else "")

            binding.root.setOnClickListener {
                onBookClicked(book.id)
            }

            binding.bookFinished.visibility = if (book.isFinished) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBookBinding.inflate(inflater, parent, false)
        return BookHolder(binding)
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        val book = books[position]
        holder.bind(book, onBookClicked)
    }

    override fun getItemCount(): Int = books.size
}

