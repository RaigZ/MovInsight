package com.example.movinsight

import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import org.junit.Assert
import org.junit.Test
//import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.*



@RunWith(MockitoJUnitRunner::class)
class MovInsightViewModelTest {

    @Test
    fun checkElementValidity() {
        val mock: MovInsightViewModel = Mockito.mock(MovInsightViewModel::class.java)
        //needed: mock movieinfoclass to test

        //test fot these elements existance
        assertNotNull(mock)

    }
}