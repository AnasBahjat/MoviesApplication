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

}