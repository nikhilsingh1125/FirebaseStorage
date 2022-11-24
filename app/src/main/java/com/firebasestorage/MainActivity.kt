package com.firebasestorage

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class MainActivity : AppCompatActivity() {


    lateinit var activity: MainActivity
    lateinit var imageView: ImageView
    lateinit var upload: AppCompatButton
    private val mStorageRef = FirebaseStorage.getInstance().reference
    val date = Date()
    val imgRef = mStorageRef.child("posts/${date}.jpg")
    val ONE_MEGABYTE = (1024 * 1024).toLong()
    lateinit var imgURI : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity = this




        imageView = findViewById(R.id.imageView)
        upload = findViewById(R.id.btnUpload)

        imageView.setOnClickListener {
            selectImageFromGallery()
        }

        upload.setOnClickListener {

            imgURI = (upload.tag as Uri?)!!
            if(imgURI == null){
                Toast.makeText(this,"Please select image first",Toast.LENGTH_SHORT).show()
            }else{
                FirebaseStorageManager().uploadImage(this,imgURI)
            }
        }

        imgRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            Glide.with(activity)
                .load(imgURI)
                .into(imageView);
        }

    }

    private fun selectImageFromGallery() {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!

            // Use Uri object instead of File to avoid storage permissions
            imageView.setImageURI(uri)
            upload.setTag(uri)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }



}


