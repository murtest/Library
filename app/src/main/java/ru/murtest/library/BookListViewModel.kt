package ru.murtest.library

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

private const val TAG = "BookListViewModel"

class BookListViewModel : ViewModel() {
    private val bookRepository = BookRepository.get()

    private val _books: MutableStateFlow<List<Book>> = MutableStateFlow(emptyList())
    val books: StateFlow<List<Book>>
        get() = _books.asStateFlow()

    init {
        viewModelScope.launch {
            bookRepository.getBooks().collect {
                _books.value = it
            }
        }
    }

    suspend fun addBook(book: Book) {
        bookRepository.addBook(book)
    }
}