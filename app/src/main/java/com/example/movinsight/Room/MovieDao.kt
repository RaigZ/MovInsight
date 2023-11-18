package com.example.movinsight.Room

import androidx.room.*

@Dao
interface MovieDao {

    // Inserts movie into database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    // Deletes movie from database
    @Delete
    fun deleteMovie(movie: Movie)

    // Gets a movie from database based on given ID
    @Query("SELECT * FROM movie WHERE id = :id")
    fun getMovie(id: String) : Movie

}