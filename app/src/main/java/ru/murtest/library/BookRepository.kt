package ru.murtest.library

import android.content.Context
import androidx.room.Room
import ru.murtest.library.database.BookDatabase
import java.util.*

private const val DATABASE_NAME = "book-database"

class BookRepository private constructor(context: Context) {

    private val database: BookDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            BookDatabase::class.java,
            DATABASE_NAME
        )
        .build()

    suspend fun getBooks(): List<Book> = database.bookDao().getBooks()

    suspend fun getBook(id: UUID): Book = database.bookDao().getBook(id)

    suspend fun addBook(book: Book) {
        database.bookDao().addBook(book)
    }

    companion object {
        private var INSTANCE: BookRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = BookRepository(context)
            }
        }

        fun get(): BookRepository {
            return INSTANCE ?:
            throw IllegalStateException("BookRepository must be initialized")
        }
    }
}