package ru.murtest.library

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID
@Entity
data class Book(
    @PrimaryKey val id: UUID,
    val title: String,
    val dateReadStart: Date,
    val dateReadEnd: Date,
    val isFinished: Boolean
)