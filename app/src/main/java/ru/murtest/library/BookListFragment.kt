package ru.murtest.library

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.murtest.library.databinding.FragmentBookDetailBinding
import ru.murtest.library.databinding.FragmentBookListBinding
import java.util.*

class BookListFragment : Fragment() {

    private var _binding: FragmentBookListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val bookListViewModel: BookListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookListBinding.inflate(inflater, container, false)

        binding.bookRecyclerView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                bookListViewModel.books.collect { books ->
                    binding.bookRecyclerView.adapter = BookListAdapter(books) { bookId ->
                        findNavController().navigate(
                            BookListFragmentDirections.showBookDetail(bookId)
                        )
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_book_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_book -> {
                showNewBook()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showNewBook() {
        viewLifecycleOwner.lifecycleScope.launch {
            val newBook = Book(
                id = UUID.randomUUID(),
                title = "",
                author = "",
                dateReadStart = Date(),
                dateReadEnd = Date(),
                isFinished = false
            )
            bookListViewModel.addBook(newBook)
            findNavController().navigate(
                BookListFragmentDirections.showBookDetail(newBook.id)
            )
        }
    }
}