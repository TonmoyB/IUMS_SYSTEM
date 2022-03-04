package com.example.iums_system.StudentPart

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.iums_system.R
import com.example.iums_system.databinding.ActivitySprofileEditInfoBinding
import com.example.iums_system.databinding.ActivityStudentUserProfileBinding
import com.example.iums_system.misc.AdminStudentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import java.io.File
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.Inflater

class StudentUserProfileActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityStudentUserProfileBinding
    private lateinit var fname: String
    private lateinit var extraID: String
    private lateinit var id: TextView

    private var firebaseStorage: FirebaseStorage?=null
    private var storageReference: StorageReference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_user_profile)

        database = Firebase.database.reference
        auth = Firebase.auth
        binding = ActivityStudentUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        id = findViewById(R.id.sid)

        //----Data_Show---
        val curr = auth.currentUser
        if(curr !=null) {
            val tempID = curr.uid
            database.child ("users").child (tempID).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for(postSnapshot in dataSnapshot.children) {
                        val key = postSnapshot.key
                        val data = postSnapshot.value
                        var temp = ""

                        when (key) {
                            "sname" -> {
                                temp = ""
                                temp += binding.sname.text.toString()
                                temp += data.toString()
                                binding.sname.text = temp
                            }
                            "sid" -> {
                                temp = ""
                                temp += id.text as String
                                temp += data.toString()
                                id.text = temp
                            }
                            "smail" -> {
                                temp = ""
                                temp += binding.smail.text.toString()
                                temp += data.toString()
                                binding.smail.text = temp
                            }
                            "sdept" -> {
                                temp = ""
                                temp += binding.sdept.text.toString()
                                temp += data.toString()
                                binding.sdept.text = temp
                            }
                            "ssemester" -> {
                                temp = ""
                                temp += binding.ssemester.text.toString()
                                temp += data.toString()
                                binding.ssemester.text = temp
                            }
                            "syear" -> {
                                temp = ""
                                temp += binding.syear.text.toString()
                                temp += data.toString()
                                binding.syear.text = temp
                            }
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
        }
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Fetching...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        storageReference = Firebase.storage.reference

        val fname = intent.getStringExtra("message")
        Toast.makeText(this, fname, Toast.LENGTH_LONG).show()
        storageReference!!.child("images/${fname}"!!)
            .downloadUrl.addOnSuccessListener {uri->
                if(progressDialog.isShowing)
                    progressDialog.dismiss()

                Picasso.get().load(uri).into(binding.spic)
                Toast.makeText(this, "Image Fetched Successfully!", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener{

                if(progressDialog.isShowing)
                    progressDialog.dismiss()

                Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
            }

    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this, id.text.toString(), Toast.LENGTH_LONG).show()
    }
}