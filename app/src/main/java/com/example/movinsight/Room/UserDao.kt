package com.example.movinsight.Room

import androidx.room.*

@Dao
interface UserDao {

    // Inserts user into database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    // Deletes user from database
    @Delete
    fun deleteUser(user: User)

    // Gets a user from database based on given username
    @Query("SELECT * FROM user WHERE username = :username")
    fun getUser(username: String) : User

}