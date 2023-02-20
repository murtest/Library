package ru.murtest.library

import android.icu.text.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.murtest.library.databinding.FragmentBookListBinding
import ru.murtest.library.databinding.ListItemBookBinding
import java.util.Date

class BookListAdapter(private val books: List<Book>) :
    RecyclerView.Adapter<BookListAdapter.BookHolder>() {

    class BookHolder(
        private val binding: ListItemBookBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.bookTitle.text = book.title
            val dateFormat= DateFormat.getPatternInstance("EEEE, MMMM d, YYYY")
            binding.bookDateRange.text = dateFormat.format(book.dateReadStart)
                .plus(if (book.isFinished) " - ".plus(dateFormat.format(book.dateReadEnd)) else "")

            binding.root.setOnClickListener {
                Toast.makeText(
                    binding.root.context,
                    "${book.title} clicked!",
                    Toast.LENGTH_SHORT
                ).show()
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
        holder.bind(book)
    }

    override fun getItemCount(): Int = books.size
}

