package com.example.movinsight.Room

import androidx.room.*

@Dao
interface MovieDao {

    // Inserts movie into database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    // Deletes movie from database
    @Delete
    suspend fun deleteMovie(movie: Movie)

    // Gets a movie from database based on given ID
    @Query("SELECT * FROM movie WHERE id = :id")
    suspend fun getMovie(id: String) : Movie



    //// FOR UNIT TESTING

    // Gets all movies
    @Query("SELECT * FROM movie")
    suspend fun getAllMovies(): List<Movie>
}