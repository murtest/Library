package ru.murtest.library

import java.util.Date
import java.util.UUID

data class Book(
    val id: UUID,
    val title: String,
    val dateReadStart: Date,
    val dateReadEnd: Date,
    val isFinished: Boolean
)