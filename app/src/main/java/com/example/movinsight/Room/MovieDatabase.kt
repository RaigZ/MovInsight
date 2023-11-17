package com.example.movinsight.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Movie::class],
    version = 1,
    exportSchema = false
)

abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    /* Allows access to the methods to create or get the
       and use the class name as the qualifier */
    companion object {
        @Volatile
        private var Instance: MovieDatabase? = null // Instance object of database

        fun getDatabase(context: Context): MovieDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MovieDatabase::class.java, "movie_database")
                    .build() // Create database instance
                    .also { Instance  = it } // Keep reference to database
            }
        }
    }
}