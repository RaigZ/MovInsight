package com.example.movinsight

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

import android.widget.Button
import android.widget.Toast
import com.example.movinsight.API.FirestoreService
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    private lateinit var emailProfileLayout: TextInputLayout
    private lateinit var passwordProfileLayout: TextInputLayout
    private lateinit var updateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        Log.d("ProfileActivity", FirestoreService.getUserEmail())

        emailProfileLayout = findViewById(R.id.email_profile)
        passwordProfileLayout = findViewById(R.id.password_profile)
        updateButton = findViewById(R.id.update_button)

        findViewById<Button>(R.id.back_button).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        updateButton.setOnClickListener {
            handleUpdate()
        }
    }

    private fun handleUpdate() {
        val newEmail = emailProfileLayout.editText?.text.toString()
        val newPassword = passwordProfileLayout.editText?.text.toString()

        if (newEmail.isNotEmpty() && newPassword.isNotEmpty()) {
            updateEmailAndPassword(newEmail, newPassword)
        } else {
            Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateEmailAndPassword(newEmail: String, newPassword: String) {
        val user = FirebaseAuth.getInstance().currentUser

        user?.updateEmail(newEmail)?.addOnCompleteListener { emailUpdateTask ->
            if (emailUpdateTask.isSuccessful) {
                // Email updated successfully, now update password
                user.updatePassword(newPassword).addOnCompleteListener { passwordUpdateTask ->
                    if (passwordUpdateTask.isSuccessful) {
                        // Password updated successfully
                        Toast.makeText(this, "Email and Password updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        // Handle password update failure
                        Log.e("ProfileActivity", "Failed to update password: ${passwordUpdateTask.exception?.message}")
                        Toast.makeText(this, "Failed to update password", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                // Handle email update failure
                Log.e("ProfileActivity", "Failed to update email: ${emailUpdateTask.exception?.message}")
                Toast.makeText(this, "Failed to update email", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
