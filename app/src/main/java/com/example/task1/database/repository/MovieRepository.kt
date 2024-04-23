package com.example.task1.database.repository

import android.util.Log
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

    suspend fun removeMovie(id : Int){
        movieDao.deleteMovieFromBookmarked(id)
    }

    suspend fun deleteMovie(movie: Movie) {
        movieDao.deleteMovie(movie)
    }

    suspend fun searchForMovie(id : Int) : List<Movie> {
        return movieDao.searchForMovieById(id)
    }



}