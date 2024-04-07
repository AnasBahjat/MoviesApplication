package com.example.task1.utils

import android.content.Context
import android.content.SharedPreferences
import java.util.StringTokenizer

class SharedPrefManager (private val context : Context){
    private val sharedPreferences : SharedPreferences = context.getSharedPreferences("taskSharedPreferences",Context.MODE_PRIVATE)

    fun getMovieSavedStatus(id : Int) : Int {
        val moviesList = getIdsList()
        if(moviesList.contains(id))
            return 1
        return 0
    }

    fun getIdsList() : MutableList<Int>{
        val arrayData = sharedPreferences.getString("IDsArray","")
        val st = StringTokenizer(arrayData,",")
        val listOfIds = mutableListOf<Int>()
        while ( st.hasMoreTokens()){
            listOfIds.add(st.nextToken().toInt())
        }
        return listOfIds
    }


    fun addIdToList(id : Int){
        val list = getIdsList()
        list.add(id)
        val updatedListString = list.joinToString(",")
        sharedPreferences.edit().putString("IDsArray",updatedListString).apply()
    }

    fun removeIdFromList(id : Int){
        val list = getIdsList()
        list.remove(id)
        val updatedListString = list.joinToString(",")
        sharedPreferences.edit().putString("IDsArray",updatedListString).apply()
    }
}