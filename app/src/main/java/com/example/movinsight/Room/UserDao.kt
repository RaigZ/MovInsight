package com.example.movinsight.Room

import android.graphics.Bitmap
import androidx.room.*

@Dao
interface UserDao {

    // Inserts user into database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    // Deletes user from database
    @Delete
    suspend fun deleteUser(user: User)

    // Gets a user from database based on given username
    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getUser(username: String) : User

    @Query("UPDATE user SET picture=:new_picture WHERE username LIKE :user_name")
    suspend fun updatePP(new_picture: String, user_name: String)




    //// FOR UNIT TESTING

    // Gets all users
    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<User>

}