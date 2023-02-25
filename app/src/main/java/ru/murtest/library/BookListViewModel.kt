package ru.murtest.library

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

private const val TAG = "BookListViewModel"

class BookListViewModel : ViewModel() {
    private val bookRepository = BookRepository.get()

    val books = mutableListOf<Book>()

    init {
        viewModelScope.launch {
            books += loadBooks()
        }
    }



    suspend fun addBook(book: Book) {
        bookRepository.addBook(book)
    }

    suspend fun loadBooks(): List<Book> {
        return bookRepository.getBooks()
    }
}