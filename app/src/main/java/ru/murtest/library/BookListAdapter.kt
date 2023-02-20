package ru.murtest.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.murtest.library.databinding.FragmentBookListBinding
import ru.murtest.library.databinding.ListItemBookBinding

class BookListAdapter(private val books: List<Book>) :
    RecyclerView.Adapter<BookListAdapter.BookHolder>() {

    class BookHolder(
        private val binding: ListItemBookBinding
    ) : RecyclerView.ViewHolder(binding.root) {
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

