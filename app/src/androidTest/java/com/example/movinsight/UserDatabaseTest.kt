package com.example.movinsight

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.movinsight.Room.User
import com.example.movinsight.Room.UserDao
import com.example.movinsight.Room.UserDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class UserDatabaseTest {
    private lateinit var db: UserDatabase
    private lateinit var userDao: UserDao
    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java).build()
        userDao = db.userDao()
        initData()
    }
    private fun initData() {
        val userNumber = 4
        for(i in 0 until userNumber) {
            val user = User(username = "username$i",
                            email = "email$i",
                            watchlist = "watchlist$i")
            userDao.insertUser(user)
        }
        val users = userDao.getAllUsers()
        assertEquals(4, users.size)
    }
    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }


    //// TESTS

    @Test
    fun removeUser() {
        val user = userDao.getAllUsers()[2]
        userDao.deleteUser(user)
        assertEquals("username3", userDao.getAllUsers()[2].username)
    }
}