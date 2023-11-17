package com.example.movinsight.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class Movie(
    // All of these may not be used
    @PrimaryKey val id: String, // ID of movie (Based on IMDb)
    @ColumnInfo(name = "title") val title: String, // Title
    @ColumnInfo(name = "type") val type: String, // Type of movie (Movie, TV Show, Documentary)
    @ColumnInfo(name = "genre") val genre: String, // Title
    @ColumnInfo(name = "date") val date: String, // Release date
    @ColumnInfo(name = "runtime") val runtime: Int, // Length of movie (in minutes)
    @ColumnInfo(name = "rating") val rating: String, // Movie rating (PG, PG-13, R)
    @ColumnInfo(name = "director") val director: String, // Director
    @ColumnInfo(name = "cast") val cast: String, // Cast
    @ColumnInfo(name = "score") val score: Long, // User score for movie
    @ColumnInfo(name = "duration") val duration: String // Duration of tv series (2010-2022)
)