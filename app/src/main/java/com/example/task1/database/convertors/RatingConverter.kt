package com.example.task1.database.convertors

import androidx.room.TypeConverter
import com.example.task1.model.Rating
import com.google.gson.Gson

class RatingConverter {
    @TypeConverter
    fun fromRating(rating : Rating) : String {
        return Gson().toJson(rating)
    }

    @TypeConverter
    fun toRating(ratingString : String) : Rating{
        return Gson().fromJson(ratingString,Rating::class.java)
    }
}