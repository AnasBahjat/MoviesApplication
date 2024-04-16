package com.example.task1.database.repository

import androidx.lifecycle.LiveData
import com.example.task1.database.MovieDao
import com.example.task1.model.Movie

class MovieRepository(private val movieDao : MovieDao) {

    fun getAllMovies(): LiveData<List<Movie>> {
        return movieDao.getAllMovies()
    }
    suspend fun addMovie(movie : Movie){
        movieDao.addNewMovieToBookmarked(movie)
    }

    fun removeMovie(id : Int){
        movieDao.deleteMovieFromBookmarked(id)
    }
}