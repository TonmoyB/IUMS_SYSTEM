package com.example.iums_system.StudentPart

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.iums_system.R
import com.example.iums_system.databinding.ActivityImageUploadBinding
import com.example.iums_system.databinding.ActivityStudentUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class ImageUpload : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityImageUploadBinding
    private lateinit var img: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = Firebase.database.reference
        auth = Firebase.auth

        binding = ActivityImageUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //----Data_Show---
        val curr = auth.currentUser
        if(curr !=null) {
            val tempID = curr.uid
            database.child ("users").child (tempID).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for(postSnapshot in dataSnapshot.children) {
                        val key = postSnapshot.key
                        val data = postSnapshot.value
                        var temp = ""

                        when (key) {
                            "sid" -> {
                                temp = ""
                                temp += binding.sid.text.toString()
                                temp += data.toString()
                                binding.sid.text = temp
                            }
                        }
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
        }

        //---Pic_Select+Up---
        binding.select.setOnClickListener{
            selectImage()
        }
        binding.upload.setOnClickListener{
            uploadImage()

        }

    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == RESULT_OK)
        {
            img = data?.data!!
            binding.spic.setImageURI(img)
        }
    }

    private fun uploadImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val filename = binding.sid.text.toString()
        val storageReference = FirebaseStorage.getInstance().getReference("images/$filename")

        storageReference.putFile(img).
            addOnSuccessListener {
                binding.spic.setImageURI(img)
                Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show()
                if(progressDialog.isShowing)progressDialog.dismiss()
            }.addOnFailureListener{
                if(progressDialog.isShowing)progressDialog.dismiss()
                Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
            }
    }
}