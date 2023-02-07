package ru.murtest.library

import androidx.lifecycle.ViewModel
import java.util.*

class BookListViewModel : ViewModel() {
    val books = mutableListOf<Book>()

    init {
        for (i in 0..100) {
            val book = Book(
                id = UUID.randomUUID(),
                title = "Book #$i",
                dateReadStart = Date(),
                dateReadEnd = Date(),
                isFinished = i % 2 == 0
            )

            books += book
        }
    }
}