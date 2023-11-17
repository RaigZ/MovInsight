package com.example.movinsight.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)

abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    /* Allows access to the methods to create or get the
       and use the class name as the qualifier */
    companion object {
        @Volatile
        private var Instance: UserDatabase? = null // Instance object of database

        fun getDatabase(context: Context): UserDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, UserDatabase::class.java, "user_database")
                    .build() // Create database instance
                    .also { Instance  = it } // Keep reference to database
            }
        }
    }
}