package com.example.task1.network


import com.example.task1.model.Movie
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("shows")
    fun getMovieById(): Call<List<Movie>>
}