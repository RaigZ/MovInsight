package com.example.movinsight

//import org.junit.jupiter.api.Assertions.*
import android.widget.ImageView
import android.widget.TextView
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MovieInfoActivityTest
{
    @Test
    fun checkElementValidity() {
        val mock: MovieInfoActivity = mock(MovieInfoActivity::class.java)
        //needed: mock movieinfoclass to test

        //test fot these elements existance: if they return null rather than
        //throw error, they exist.
        val add = mock.findViewById<TextView>(R.id.addMovie)
        assertNull(add)
        val image = mock.findViewById<TextView>(R.id.movieImage)
        assertNull(image)
        val title = mock.findViewById<TextView>(R.id.movieList)
        assertNull(image)
    }


}