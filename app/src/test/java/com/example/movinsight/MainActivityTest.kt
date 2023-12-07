package com.example.movinsight

//import org.junit.jupiter.api.Assertions.*
import com.example.movinsight.fragments.DisplayFragment
import com.example.movinsight.fragments.LoginFragment
import com.example.movinsight.fragments.SignupFragment
import org.junit.Assert.*
import org.junit.Test

class MainActivityTest {
    @Test
    fun testFragmentExists() {
        //checks to make sure  fragment exists
        assertNotNull(DisplayFragment)
        assertNotNull(SignupFragment)
        assertNotNull(LoginFragment)
    }


}