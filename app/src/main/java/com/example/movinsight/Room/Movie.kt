package com.example.movinsight.Room

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverter

@Entity(tableName = "movie")
data class Movie (
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "originalTitleText") val originalTitleText: String,
    @ColumnInfo(name = "primaryImage") val primaryImage: String,
    @ColumnInfo(name = "ratingsSummary") val ratingsSummary: Float,
    @ColumnInfo(name = "releaseYear") val releaseYear: Int,
    @ColumnInfo(name = "titleText") val titleText: String,
    @ColumnInfo(name = "releaseDate") val releaseDate: Int,
    @ColumnInfo(name = "titleCertificate") val titleCertificate: String,

    /*
    // these data types don't seem to be working
    @ColumnInfo(name = "originalTitleText") val originalTitleText: OriginalTitleText,
    @ColumnInfo(name = "primaryImage") val primaryImage: PrimaryImage,
    @ColumnInfo(name = "ratingsSummary") val ratingsSummary: RatingsSummary,
    @ColumnInfo(name = "releaseYear") val releaseYear: ReleaseYear,
    @ColumnInfo(name = "titleText") val titleText: TitleText,
    @ColumnInfo(name = "releaseDate") val releaseDate: ReleaseDate,
    @ColumnInfo(name = "titleCertificate") val titleCertificate: TitleCertificate,
    */
)

class MovieConverters {

    //@TypeConverter
    //fun name

}