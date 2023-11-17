package com.example.movinsight.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // User ID (Increments on its own)
    @ColumnInfo(name = "name") val name: String, // User name
    @ColumnInfo(name = "date") val date: String, // Join date of user
    @ColumnInfo(name = "bio") val description: String, // User bio
)