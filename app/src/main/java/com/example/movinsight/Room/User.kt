package com.example.movinsight.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey val username: String, // User name
    @ColumnInfo(name = "email") val email: String, // User email
    @ColumnInfo(name = "watchlist") val watchlist: ArrayList<String> // User watchlist



    // old columns
    //@ColumnInfo(name = "date") val date: String, // Join date of user
    //@ColumnInfo(name = "bio") val description: String, // User bio
)