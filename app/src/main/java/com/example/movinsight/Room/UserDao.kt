package com.example.movinsight.Room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User) // Inserts a user

    @Query("SELECT * FROM user WHERE name = :name")
    suspend fun getUser(name: String) : Flow<String> // Gets user based on id

    // PROBABLY NOT NEEDED - MOSTLY FOR UNIT TESTING
    @Update
    suspend fun updateUser(user: User) // Updates a user

    // PROBABLY NOT NEEDED - MOSTLY FOR UNIT TESTING
    @Delete
    suspend fun deleteUser(user: User) // Deletes a user

    // PROBABLY NOT NEEDED - MOSTLY FOR UNIT TESTING
    @Query("SELECT * FROM user")
    fun loadAllUsers(): Flow<List<User>> // Loads all user

}