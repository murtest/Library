package ru.murtest.library

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.murtest.library.database.BookDatabase
import java.util.*

private const val DATABASE_NAME = "book-database"

class BookRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
) {

    private val database: BookDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            BookDatabase::class.java,
            DATABASE_NAME
        )
        .build()

    fun getBooks(): Flow<List<Book>> = database.bookDao().getBooks()

    suspend fun getBook(id: UUID): Book = database.bookDao().getBook(id)

    fun updateBook(book: Book) {
        coroutineScope.launch {
            database.bookDao().updateBook(book)
        }
    }

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