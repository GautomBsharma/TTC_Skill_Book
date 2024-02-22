package com.mksoluation.ttcskillbook

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.mksoluation.ttcskillbook.databinding.ActivityAddLearnBinding
import java.util.*

class AddLearnActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddLearnBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var dialog: Dialog
    private var selectedItem :Any = ""
    private var imageUri : Uri?=null
    private var launchGelaryActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            imageUri = it.data!!.data
            binding.postImage.setImageURI(imageUri)
            binding.postImage.visibility = View.VISIBLE
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddLearnBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        dialog = Dialog(this)
        dialog.setContentView(R.layout.progress_layout)
        dialog.setCancelable(true)
        val district = resources.getStringArray(R.array.course_name)
        val arrayAdapter = ArrayAdapter(this,R.layout.drop_doun_item,district)
        binding.autoText.setAdapter(arrayAdapter)
        binding.autoText.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            selectedItem = adapterView.getItemAtPosition(i)
            //Toast.makeText(this, "selected $selectedItem", Toast.LENGTH_SHORT).show()
        }
        binding.pickBtn.setOnClickListener {
            val intent = Intent("android.intent.action.GET_CONTENT")
            intent.type = "image/*"
            launchGelaryActivity.launch(intent)

        }
        binding.postBtn.setOnClickListener {
            if (selectedItem.toString().isEmpty()) {
                Toast.makeText(this, "Select Course must", Toast.LENGTH_SHORT).show()
            } else {
                if (imageUri == null || imageUri.toString().isEmpty()) {
                    storeData2()
                } else {
                    uploadImages(imageUri!!)
                    dialog.show()
                }
            }
        }
    }

    private fun storeData2() {
        val dbRef = FirebaseDatabase.getInstance().reference.child("Learn").child(selectedItem.toString())
        val postId= dbRef.push().key
        val updateMap = HashMap<String,Any>()
        updateMap["learnImageUrl"] = ""
        updateMap["learnId"] = postId.toString()
        updateMap["description"] = binding.description.text.toString()
        updateMap["question"] = binding.tvQuestion.text.toString()

        if (postId != null) {
            dbRef.child(postId).setValue(updateMap).addOnSuccessListener {
                Toast.makeText(this, "Learn added", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Upload  Fail", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }
    }

    private fun uploadImages(uri: Uri) {

        val fileName = UUID.randomUUID().toString()+".jpg"
        val storageRef = FirebaseStorage.getInstance().reference.child("Learn$selectedItem/$fileName")
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
        val dbRef = FirebaseDatabase.getInstance().reference.child("Learn").child(selectedItem.toString())
        val postId= dbRef.push().key
        val updateMap = HashMap<String,Any>()
        updateMap["learnImageUrl"] = imageUrl
        updateMap["learnId"] = postId.toString()
        updateMap["description"] = binding.description.text.toString()
        updateMap["question"] = binding.tvQuestion.text.toString()

        if (postId != null) {
            dbRef.child(postId).setValue(updateMap).addOnSuccessListener {
                Toast.makeText(this, "Learn added", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Upload  Fail", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }
    }
}