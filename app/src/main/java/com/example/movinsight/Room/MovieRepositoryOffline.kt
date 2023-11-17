package com.example.movinsight.Room

import kotlinx.coroutines.flow.Flow

class MovieRepositoryOffline(private val movieDao: MovieDao) : MovieRepository {

    override fun getAllMoviesStream(): Flow<List<Movie>> = movieDao.getAllMovies()

    override fun getMovieStream(id: String): Flow<Movie?> = movieDao.getMovie(id)

    override suspend fun insertMovie(movie: Movie) = movieDao.insertMovie(movie)

    override suspend fun deleteMovie(movie: Movie) = movieDao.deleteMovie(movie)

    override suspend fun updateMovie(movie: Movie) = movieDao.updateMovie(movie)

}