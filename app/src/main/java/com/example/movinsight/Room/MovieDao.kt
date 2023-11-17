package com.example.movinsight.Room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie) // Inserts a movie

    @Delete
    suspend fun deleteMovie(movie: Movie) // Deletes a movie

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getMovie(id: String) : Flow<Movie> // Gets movie based on ID

    // PROBABLY NOT NEEDED - MOSTLY FOR UNIT TESTING
    @Update
    fun updateMovie(movie: Movie) // Updates a movie

    // PROBABLY NOT NEEDED - MOSTLY FOR UNIT TESTING
    @Query("SELECT * FROM movie")
    fun getAllMovies(): Flow<List<Movie>> // Loads all movies

}