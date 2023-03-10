package ru.murtest.library

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.murtest.library.databinding.FragmentBookDetailBinding
import java.util.*

class BookDetailFragment : Fragment() {

    private var _binding: FragmentBookDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val args: BookDetailFragmentArgs by navArgs()

    private val bookDetailViewModel: BookDetailViewModel by viewModels {
        BookDetailViewModelFactory(args.bookId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentBookDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            bookTitle.doOnTextChanged { text, _, _, _ ->
                bookDetailViewModel.updateBook { oldBook ->
                    oldBook.copy(title = text.toString())
                }
            }

            bookDateReadStart.apply {
                isEnabled = false
            }

            bookDateReadEnd.apply {
                isEnabled = false
            }

            bookFinished.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) {
                    bookDetailViewModel.updateBook { oldBook ->
                        oldBook.copy(
                            isFinished = false
                        )
                    }
                    bookDateReadEnd.text = ""
                } else {
                    bookDetailViewModel.updateBook { oldBook ->
                        oldBook.copy(
                            dateReadEnd = Date(),
                            isFinished = true)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                bookDetailViewModel.book.collect {book ->
                    book?.let { updateUi(it) }
                }
            }
        }
    }

    private fun updateUi(book: Book) {
        binding.apply {
            if (bookTitle.text.toString() != book.title) {
                bookTitle.setText(book.title)
            }
            bookDateReadStart.text = book.dateReadStart.toString()
            bookDateReadEnd.text = if (book.isFinished) book.dateReadEnd.toString() else ""
            bookFinished.isChecked = book.isFinished
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}