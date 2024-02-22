package com.mksoluation.ttcskillbook

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.mksoluation.ttcskillbook.databinding.ActivityAddSignalBinding
import java.util.*

class AddSignalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddSignalBinding
    private lateinit var dialog: Dialog
    private var imageUri : Uri?=null
    private var launchGelaryActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            imageUri = it.data!!.data
            binding.postImage.setImageURI(imageUri)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSignalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog = Dialog(this)
        dialog.setContentView(R.layout.progress_layout)
        dialog.setCancelable(true)
        binding.pickBtn.setOnClickListener {
            val intent = Intent("android.intent.action.GET_CONTENT")
            intent.type = "image/*"
            launchGelaryActivity.launch(intent)

        }
        binding.postBtn.setOnClickListener {
            validateData()
        }

    }
    private fun validateData() {

        if (binding.description.toString().isEmpty()) {
            binding.description.error = "Write something"
        }
        else if (imageUri == null){
            Toast.makeText(this, "Select Image ", Toast.LENGTH_SHORT).show()
        }
        else {
            uploadImages(imageUri!!)
            dialog.show()
        }
    }
    private fun uploadImages(uri: Uri) {
        val fileName = UUID.randomUUID().toString()+".jpg"
        val storageRef = FirebaseStorage.getInstance().reference.child("SignalDriving/$fileName")
        storageRef.putFile(uri).addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {image->
                storeData(image.toString())
            }
        }
            .addOnFailureListener{
                Toast.makeText(this, "Upload Storage Fail", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
    }
    private fun storeData(imageUrl: String) {
        val dbRef = FirebaseDatabase.getInstance().reference.child("Signal")
        val postId= dbRef.push().key
        val updateMap = HashMap<String,Any>()
        updateMap["SignalImageUrl"] = imageUrl
        updateMap["signalId"] = postId.toString()
        updateMap["description"] = binding.description.text.toString()
        if (postId != null) {
            dbRef.child(postId).setValue(updateMap).addOnSuccessListener {
                Toast.makeText(this, "post added", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Upload  Fail", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }
    }
}