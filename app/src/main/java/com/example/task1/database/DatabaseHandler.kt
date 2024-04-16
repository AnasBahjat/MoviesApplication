package com.example.task1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.task1.database.convertors.GenresConverter
import com.example.task1.database.convertors.ImageConverter
import com.example.task1.database.convertors.RatingConverter
import com.example.task1.model.Movie

@Database(entities = [Movie::class], version = 1)
@TypeConverters(value = [GenresConverter::class,ImageConverter::class,RatingConverter::class])
abstract class DatabaseHandler : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    companion object{
        private var databaseInstance : DatabaseHandler? = null

        fun getDatabase(context : Context) : DatabaseHandler{
            val tempInstance = databaseInstance
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext
                ,DatabaseHandler::class.java
                ,"task1DB").build()
                databaseInstance = instance
                return instance
            }
        }
    }
}