package ru.murtest.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import ru.murtest.library.databinding.FragmentBookDetailBinding
import java.util.*

class BookDetailFragment : Fragment() {

    private var _binding: FragmentBookDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private lateinit var book: Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        book = Book(
            id = UUID.randomUUID(),
            title = "",
            dateReadStart = Date(),
            dateReadEnd = Date(),
            isFinished = false,
            isFavorite = false
        )
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
                book = book.copy(title = text.toString())
            }

            bookDateReadStart.apply {
                text = book.dateReadStart.toString()
                isEnabled = false
            }

            bookDateReadEnd.apply {
                text = if (book.isFinished) book.dateReadEnd.toString() else ""
                isEnabled = false
            }

            bookFinished.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) {
                    book = book.copy(
                        isFinished = isChecked
                    )
                    bookDateReadEnd.text = ""
                } else {
                    book = book.copy(
                        dateReadEnd = Date(),
                        isFinished = isChecked
                    )
                    bookDateReadEnd.text = book.dateReadEnd.toString()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}