package ru.murtest.library.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.murtest.library.Book
import java.util.UUID

@Dao
interface BookDao {
    @Query("SELECT * FROM book")
    fun getBooks(): Flow<List<Book>>

    @Query("SELECT * FROM book WHERE id=(:id)")
    suspend fun getBook(id: UUID): Book

    @Update
    suspend fun updateBook(book: Book)

    @Insert
    suspend fun addBook(book: Book)
}