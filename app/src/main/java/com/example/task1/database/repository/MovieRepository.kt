package com.example.task1.database.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.task1.database.MovieDao
import com.example.task1.model.Movie

class MovieRepository(private val movieDao : MovieDao) {

    fun getAllMovies(): LiveData<List<Movie>> {
        Log.d("the data ----> ${movieDao.getAllMovies().value}","the data ----> ${movieDao.getAllMovies().value}")
        return movieDao.getAllMovies()
    }
    suspend fun addMovie(movie : Movie){
        movieDao.addNewMovieToBookmarked(movie)
        Log.d("Movie added ---->","Movie added ----> ${movie.name} 2222")
    }

    fun removeMovie(id : Int){
        movieDao.deleteMovieFromBookmarked(id)
    }
}