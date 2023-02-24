package ru.murtest.library

import android.app.Application

class LibraryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        BookRepository.initialize(this)
    }
}