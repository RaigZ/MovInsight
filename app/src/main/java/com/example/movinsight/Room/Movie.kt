package com.example.movinsight.Room

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverter

@Entity(tableName = "movie")
data class Movie (
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    // OriginalTitleText object
    @ColumnInfo(name = "originalTitleText") val originalTitleText: String,
    // PrimaryImage object
    @ColumnInfo(name = "primaryImageID") val primaryImageID: String,
    @ColumnInfo(name = "primaryImageURL") val primaryImageURL: String,
    @ColumnInfo(name = "primaryImageWidth") val primaryImageWidth: Int,
    @ColumnInfo(name = "primaryImageHeight") val primaryImageHeight: Int,
    // RatingsSummary object
    @ColumnInfo(name = "ratingsSummary") val ratingsSummary: Float,
    // ReleaseYear object
    @ColumnInfo(name = "releaseYear") val releaseYear: Int,
    // TitleText object
    @ColumnInfo(name = "titleText") val titleText: String,
    // ReleaseDate object
    @ColumnInfo(name = "releaseDateDay") val releaseDateDay: Int,
    @ColumnInfo(name = "releaseDateMonth") val releaseDateMonth: Int,
    @ColumnInfo(name = "releaseDateYear") val releaseDateYear: Int,
    // TitleCertificate object
    @ColumnInfo(name = "titleCertificateRating") val titleCertificateRating: String,
    @ColumnInfo(name = "titleCertificateReason") val titleCertificateReason: String
)