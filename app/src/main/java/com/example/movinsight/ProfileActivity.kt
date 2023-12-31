package com.example.movinsight

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import android.widget.Toast
import com.example.movinsight.API.FirestoreService
import com.example.movinsight.Room.UserDatabase
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    var selectedImage: Uri? = null
    var selectedBitmap: Bitmap? = null

    private lateinit var emailProfileLayout: TextInputLayout
    private lateinit var passwordProfileLayout: TextInputLayout
    private lateinit var usernameProfile: TextView
    private lateinit var updateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        Log.d("ProfileActivity", FirestoreService.getUserEmail())

        emailProfileLayout = findViewById(R.id.email_profile)
        passwordProfileLayout = findViewById(R.id.password_profile)
        usernameProfile = findViewById(R.id.fullname_field)
        usernameProfile.text = FirestoreService.getUsername()
        updateButton = findViewById(R.id.update_button)

        findViewById<Button>(R.id.back_button).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        // User selects the "Edit Avatar" button
        findViewById<Button>(R.id.profile_button).setOnClickListener {
            // Go through permissions, make user select photo through gallery
            selectImage()
        }

        updateButton.setOnClickListener {
            handleUpdate()
        }

        // Applies the user's photo immediately if they have one saved
        lifecycleScope.launch {
            val db = UserDatabase.getInstance(applicationContext)
            val userDao = db.userDao()
            val user = userDao.getUser(FirestoreService.getUsername())

            if (user.picture == "none" || user.picture == "" || user.picture == " ") {
                Log.d("ProfileActivity", user.username + " has no selected picture.")
            } else {
                findViewById<ImageView>(R.id.profile_image).setImageBitmap(decodeImage(user.picture))
            }
        }
    }

    // Encodes the image into a string so it can be stored in the database
    private fun encodeImage(bitmap: Bitmap?): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    // Decodes the image from a string back to a bitmap so it can be displayed
    private fun decodeImage(string: String): Bitmap {
        val decodedString: ByteArray = Base64.decode(string, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        return decodedByte
    }

    fun selectImage() {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.READ_MEDIA_IMAGES
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES), 1)
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 2)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            selectedImage = data.data
        }
        selectedBitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
        findViewById<ImageView>(R.id.profile_image).setImageBitmap(selectedBitmap)

        // Update the user's picture in the database
        lifecycleScope.launch {
            val db = UserDatabase.getInstance(applicationContext)
            val userDao = db.userDao()
            val encoded: String = encodeImage(selectedBitmap)
            val user = userDao.getUser(FirestoreService.getUsername())
            userDao.updatePP(encoded, user.username)
        }

        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun handleUpdate() {
        val newEmail = emailProfileLayout.editText?.text.toString()
        val newPassword = passwordProfileLayout.editText?.text.toString()

        if (newEmail.isNotEmpty() && newPassword.isNotEmpty()) {
            updatePassword(newEmail, newPassword)
        } else {
            Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updatePassword(newEmail: String, newPassword: String) {
        val user = FirebaseAuth.getInstance().currentUser

        // Check if the provided new email matches the current user's email
        if (user?.email == newEmail) {
            // The provided email matches the current user's email
            user.updatePassword(newPassword).addOnCompleteListener { passwordUpdateTask ->
                if (passwordUpdateTask.isSuccessful) {
                    // Password updated successfully
                    Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    // Handle password update failure
                    Log.e(
                        "ProfileActivity",
                        "Failed to update password: ${passwordUpdateTask.exception?.message}"
                    )
                    Toast.makeText(this, "Failed to update password", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            // Provided email does not match the current user's email
            Toast.makeText(
                this,
                "Email does not match the current user's email",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}