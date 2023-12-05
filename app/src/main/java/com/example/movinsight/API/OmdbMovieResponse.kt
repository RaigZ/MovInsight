package com.example.movinsight.API

data class OmdbMovieResponse(
    val Title: String,
    val Year: String,
    val Rated: String,
    val Released: String,
    val Genre: String,
    val Director: String,
    val Writer: String,
    val Actors: String,
    val Plot: String,
    val Language: String,
    val Poster: String,
    val imdbRating: String
)
