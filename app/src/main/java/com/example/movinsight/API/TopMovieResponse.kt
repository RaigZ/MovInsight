package com.example.movinsight.API

data class TopMovieResponse (
    val status: Boolean,
    val message: String,
    val timestamp: Long,
    val data: List<MovieItem>
)

data class MovieItem (
    val id: String,
    val originalTitleText: OriginalTitleText,
    val primaryImage: PrimaryImage,
    val ratingsSummary: RatingsSummary,
    val releaseYear: ReleaseYear,
    val titleText: TitleText,
    val releaseDate: ReleaseDate,
    val titleCertificate: TitleCertificate
)

data class OriginalTitleText(val text: String)
data class PrimaryImage(
    val id: String,
    val imageUrl: String,
    val imageWidth: Int,
    val imageHeight: Int
)
data class RatingsSummary(val aggregateRating: Float)
data class ReleaseYear(val year: Int)
data class TitleText(val text: String)
data class ReleaseDate(
    val day: Int,
    val month: Int,
    val year: Int
)
data class TitleCertificate(
    val rating: String,
    val ratingReason: String
)


