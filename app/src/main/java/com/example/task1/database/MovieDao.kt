package com.example.task1.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.task1.model.Movie


@Dao
interface MovieDao {
    @Query("SELECT * FROM Movie")
    fun getAllMovies() : LiveData<List<Movie>>

    @Insert
    suspend fun addNewMovieToBookmarked(movie : Movie) : Void

    @Query("DELETE FROM Movie WHERE id = :id")
    suspend fun deleteMovieFromBookmarked(id : Int)

    @Delete
    suspend fun deleteMovie(movie : Movie)

    // add new function to return a movie that have $id..
    @Query("SELECT * FROM Movie where id =:id")
    suspend fun searchForMovieById(id : Int) : List<Movie>
    
}