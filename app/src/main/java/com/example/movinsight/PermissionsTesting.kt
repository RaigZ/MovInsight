package com.example.movinsight

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import android.Manifest
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.media.Image
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat
import java.lang.Exception

class PermissionsTesting : AppCompatActivity() {

    //private var permGranted : Boolean = false
    var selectedImage : Uri? = null
    var selectedBitmap : Bitmap? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissions_testing)

        findViewById<Button>(R.id.bOpenGallery).setOnClickListener {
            selectImage()
        }

        /*
        findViewById<Button>(R.id.bOpenGallery).setOnClickListener {
            permissionRequest()
            if (permGranted) {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 3)
            }
        }
        */

    }

    fun selectImage() {
        if(ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES), 1)
        }
        else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 2)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == 1) {
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            selectedImage = data.data
        }
        selectedBitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
        findViewById<ImageView>(R.id.imageView).setImageBitmap(selectedBitmap)
        super.onActivityResult(requestCode, resultCode, data)
    }

    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && data != null) {
            val selectedImage : Uri? = data.data
            val imageView : ImageView = findViewById(R.id.imageView)
            imageView.setImageURI(selectedImage)
        }
    }

    private fun permissionRequest() {
        var permissionList = mutableListOf<String>()
        /*
        if(!(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if(!(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        */
        if((ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED)) {
            permissionList.add(Manifest.permission.READ_MEDIA_IMAGES)
        }
        if(!(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED)) {
            permissionList.add(Manifest.permission.READ_MEDIA_IMAGES)
        }
        if(permissionList.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toTypedArray(), 100)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            100 -> {
                for (index in grantResults.indices) {
                    if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                        permGranted = true
                    }
                }
            }
        }
    }
    */

}