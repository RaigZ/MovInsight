package com.example.movinsight.Room

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "user")
data class User (
    @PrimaryKey @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "watchlist") val watchlist: String

    /*
    // this data type doesn't seem to work
    @ColumnInfo(name = "watchlist") val watchlist: ArrayList<String>
    */
)