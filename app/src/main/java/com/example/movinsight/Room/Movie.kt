package com.example.movinsight.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movinsight.API.OriginalTitleText
import com.example.movinsight.API.PrimaryImage
import com.example.movinsight.API.RatingsSummary
import com.example.movinsight.API.ReleaseDate
import com.example.movinsight.API.ReleaseYear
import com.example.movinsight.API.TitleCertificate
import com.example.movinsight.API.TitleText

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey val id: String, // ID of movie (Based on IMDb)
    @ColumnInfo(name = "originalTitle") val originalTitleText: OriginalTitleText,
    @ColumnInfo(name = "primaryImage") val primaryImage: PrimaryImage,
    @ColumnInfo(name = "ratingsSummary") val ratingsSummary: RatingsSummary,
    @ColumnInfo(name = "releaseYear") val releaseYear: ReleaseYear,
    @ColumnInfo(name = "titleText") val titleText: TitleText,
    @ColumnInfo(name = "releaseDate") val releaseDate: ReleaseDate,
    @ColumnInfo(name = "titleCertificate") val titleCertificate: TitleCertificate



    /*
    old columns
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
    */
)