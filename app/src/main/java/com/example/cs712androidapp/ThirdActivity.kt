package com.example.cs712androidapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ThirdActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }

    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        val btnCapture = findViewById<Button>(R.id.btnCapture)
        imageView = findViewById(R.id.imageView)

        btnCapture.setOnClickListener {

            // Camera intent
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            // Gallery intent
            val pickPhotoIntent = Intent(Intent.ACTION_PICK)
            pickPhotoIntent.type = "image/*"

            // Combine both options
            val chooser = Intent.createChooser(pickPhotoIntent, "Select or Capture Image")
            chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(takePictureIntent))

            startActivityForResult(chooser, REQUEST_IMAGE_CAPTURE)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            // Case 1: Camera returns thumbnail
            val bitmap = data?.extras?.get("data") as? Bitmap

            if (bitmap != null) {
                imageView.setImageBitmap(bitmap)
            }
            // Case 2: Gallery image selected
            else if (data?.data != null) {
                imageView.setImageURI(data.data)
            }
            // Case 3: Failure
            else {
                Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
            }
        }
    }
}