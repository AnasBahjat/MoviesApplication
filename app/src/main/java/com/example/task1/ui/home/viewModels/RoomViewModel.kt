package com.example.task1.ui.home.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.task1.database.DatabaseHandler
import com.example.task1.database.repository.MovieRepository
import com.example.task1.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomViewModel(application : Application) : AndroidViewModel(application){
  //  private lateinit var getAllMovies : LiveData<List<Movie>>
    private val repo : MovieRepository
    init {
        val movieDao = DatabaseHandler.getDatabase(application).movieDao()
        repo = MovieRepository(movieDao)
    }

    fun getAllMovies()  : LiveData<List<Movie>>{
        return repo.getAllMovies()
    }

    fun addMovie(movie : Movie){
        viewModelScope.launch(Dispatchers.IO ) {
            repo.addMovie(movie)
        }
    }

    fun deleteMovie(id : Int){
            repo.removeMovie(id)
    }


}