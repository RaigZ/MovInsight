package com.example.movinsight.Room

import android.graphics.Bitmap
import androidx.room.*

@Dao
interface UserDao {

    // Inserts user into database
    @Insert(onConflict = OnConflictStrategy.IGNORE)//originally .REPLACE
    suspend fun insertUser(user: User)

    // Deletes user from database
    @Delete
    suspend fun deleteUser(user: User)

    // Gets a user from database based on given username
    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getUser(username: String) : User

    // Updates a user's picture based on given username
    @Query("UPDATE user SET picture=:newpicture WHERE username LIKE :username")
    suspend fun updatePP(newpicture: String, username: String)

    // Updates a user's watchlist (whether adding/removing)
    @Query("UPDATE user SET watchlist=:watchlist WHERE username LIKE :username")
    suspend fun updateWatchlist(watchlist: ArrayList<String>, username: String)




    //// FOR UNIT TESTING

    // Gets all users
    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<User>

}