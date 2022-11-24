package com.firebasestorage

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class FirebaseStorageManager {

    private val mStorageRef = FirebaseStorage.getInstance().reference
    private lateinit var mProgressDialog: ProgressDialog

    fun uploadImage(context: Context, imageFileUri: Uri) {
        val date = Date()
        val uploadTask = mStorageRef.child("posts/${date}.jpg").putFile(imageFileUri)
        uploadTask.addOnSuccessListener {
            println("imageUrl"+imageFileUri)
            Toast.makeText(context, "Image Upload success", Toast.LENGTH_SHORT).show()
            Log.e("Frebase", "Image Upload success")
            val uploadedURL = mStorageRef.child("posts/${date}.jpg").downloadUrl
            Log.e("Firebase", "Uploaded $uploadedURL")
        }.addOnFailureListener {
            Log.e("Frebase", "Image Upload fail")
            mProgressDialog.dismiss()
        }
    }




}