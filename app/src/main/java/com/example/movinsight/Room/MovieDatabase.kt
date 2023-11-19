package com.example.movinsight.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Movie::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(MovieConverters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}