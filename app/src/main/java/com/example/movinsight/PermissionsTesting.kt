package com.example.movinsight

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat

class PermissionsTesting : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissions_testing)

        /*
        // User presses "Open Gallery"
        findViewById<Button>(R.id.bOpenGallery).setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 3)
        }
        */

        findViewById<Button>(R.id.bOpenGallery).setOnClickListener {
            //permissionRequest()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && data != null) {
            val selectedImage : Uri? = data.data
            val imageView : ImageView = findViewById(R.id.imageView)
            imageView.setImageURI(selectedImage)
        }
    }

    /*
    private fun permissionRequest() {
        var permissionList = mutableListOf<String>()
        if(!(ActivityCompat.checkSelfPermission(this, Manifest.permission.)))
    }
    */

}