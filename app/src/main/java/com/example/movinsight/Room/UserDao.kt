package com.example.movinsight.Room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User) // Inserts a user

    @Query("SELECT * FROM user WHERE username = :username")
    fun getUser(username: String) : Flow<String> // Gets user based on given username



    // PROBABLY NOT NEEDED - MOSTLY FOR UNIT TESTING
    @Update
    fun updateUser(user: User) // Updates a user

    // PROBABLY NOT NEEDED - MOSTLY FOR UNIT TESTING
    @Delete
    fun deleteUser(user: User) // Deletes a user

    // PROBABLY NOT NEEDED - MOSTLY FOR UNIT TESTING
    @Query("SELECT * FROM user")
    fun loadAllUsers(): List<User> // Loads all users

}