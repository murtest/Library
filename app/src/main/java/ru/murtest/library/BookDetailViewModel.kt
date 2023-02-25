package ru.murtest.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class BookDetailViewModel(bookId: UUID) : ViewModel() {
    private val bookRepository = BookRepository.get()

    private val _book: MutableStateFlow<Book?> = MutableStateFlow(null)
    val book: StateFlow<Book?> = _book.asStateFlow()

    init {
        viewModelScope.launch {
            _book.value = bookRepository.getBook(bookId)
        }
    }

    fun updateBook(onUpdate: (Book) -> Book) {
        _book.update { oldBook ->
            oldBook?.let { onUpdate(it) }
        }
    }

    override fun onCleared() {
        super.onCleared()

        book.value?.let { bookRepository.updateBook(it) }
    }
}

class BookDetailViewModelFactory(
    private val bookId: UUID
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookDetailViewModel(bookId) as T
    }
}