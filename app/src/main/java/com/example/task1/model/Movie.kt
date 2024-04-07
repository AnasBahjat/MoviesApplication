package com.example.task1.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Movie(val id : Int, val name :String, val language : String
                 , val genres : List<String>
                 , val runtime:Int, val rating : Rating
                 , val image : Image, val summary : String)
    : Parcelable


@Parcelize
data class Image(
    val medium: String,
    val original: String
) : Parcelable


@Parcelize
data class Rating(
    val average: Double
) : Parcelable
