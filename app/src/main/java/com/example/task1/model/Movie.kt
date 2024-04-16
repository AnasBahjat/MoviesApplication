package com.example.task1.model


import android.os.Parcelable

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
@Entity
@Parcelize
data class Movie(
     @PrimaryKey val id : Int,
     val name :String,
     val language : String,
     val genres : List<String>,
     val runtime:Int,
     val rating : Rating,
     val image : Image,
     val summary : String
)
    : Parcelable


