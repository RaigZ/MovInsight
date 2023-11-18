package com.example.movinsight.Room

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "user")
data class User (
    @PrimaryKey @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "watchlist") val watchlist: ArrayList<String>
)

class Converters {

    // Converts a given string to an ArrayList<String>
    // Needed since Room does not accept Array<String> as a type
    @TypeConverter
    fun fromString(value: String?) : ArrayList<String> {
        val listType = object :TypeToken<ArrayList<String>>(){}.type
        return Gson().fromJson(value, listType)
    }

    // Converts a given ArrayList<String> to a single string
    @TypeConverter
    fun fromArrayList(list: ArrayList<String>?) : String {
        return Gson().toJson(list)
    }
}