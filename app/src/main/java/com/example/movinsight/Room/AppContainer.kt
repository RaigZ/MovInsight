package com.example.movinsight.Room

import android.content.Context

// App container for Dependency injection
interface AppContainer {
    val movieRepository: MovieRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    // Instantiates the database and passes in the DAO instance to the MovieRepositoryOffline class
    override val movieRepository: MovieRepository by lazy {
        MovieRepositoryOffline(MovieDatabase.getDatabase(context).movieDao())
    }
}