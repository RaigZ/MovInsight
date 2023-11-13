package com.example.movinsight.API

data class SearchMovieResponse (
    val status: Boolean,
    val message: String,
    val timestamp: Float,
    val data: List<searchItem>
)

data class searchItem(
    val id: String,
    val qid: String,
    val title: String,
    val year: Int,
    val stars: String,
    val q: String,
    val image: String
)