package com.example.task1.database.convertors

import androidx.room.TypeConverter
import com.example.task1.model.Image
import com.google.gson.Gson

class ImageConverter {
    @TypeConverter
    fun fromImage(image : Image) : String {
        return Gson().toJson(image)
    }
    @TypeConverter
    fun toImage(imageString : String) : Image{
        return Gson().fromJson(imageString,Image::class.java)
    }
}