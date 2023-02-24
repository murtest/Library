package ru.murtest.library.database

import androidx.room.Dao
import androidx.room.Query
import ru.murtest.library.Book
import java.util.UUID

@Dao
interface BookDao {
    @Query("SELECT * FROM book")
    suspend fun getBooks(): List<Book>

    @Query("SELECT * FROM book WHERE id=(:id)")
    suspend fun getBook(id: UUID): Book
}