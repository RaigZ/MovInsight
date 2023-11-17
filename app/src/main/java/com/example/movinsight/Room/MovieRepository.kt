package com.example.movinsight.Room

import kotlinx.coroutines.flow.Flow

// Repository that provides methods of a movie from a given data source
interface MovieRepository {

    // Get all movies from the the given data source
    fun getAllMoviesStream(): Flow<List<Movie>>

    // Get a movie from the given data source based on given ID
    fun getMovieStream(id: String): Flow<Movie?>

    // Insert movie in the data source
    suspend fun insertMovie(movie: Movie)

    // Delete movie from the data source
    suspend fun deleteMovie(movie: Movie)

    // Update movie in the data source
    suspend fun updateMovie(movie: Movie)
}