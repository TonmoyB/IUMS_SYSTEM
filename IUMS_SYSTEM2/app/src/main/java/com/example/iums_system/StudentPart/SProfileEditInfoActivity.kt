package com.example.iums_system.StudentPart

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.iums_system.R
import com.example.iums_system.databinding.ActivitySprofileEditInfoBinding
import com.example.iums_system.databinding.ActivityStudentUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SProfileEditInfoActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivitySprofileEditInfoBinding
    private lateinit var img: Uri

    private lateinit var NAME: EditText
    private lateinit var ID: EditText
    private lateinit var MAIL: EditText
    private lateinit var DEPT: EditText
    private lateinit var SEMESTER: EditText
    private lateinit var YEAR: EditText
    private lateinit var PASS: EditText

    private lateinit var btn: Button
    private lateinit var stName: String
    private lateinit var stID: String
    private lateinit var stPass: String
    private lateinit var stMail: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sprofile_edit_info)

        binding = ActivitySprofileEditInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

    }

    override fun onStart() {
        super.onStart()
        btn.setOnClickListener(){

            getData()
            update()

        }
        //---Pic_Select+Up---
        binding.select.setOnClickListener{
            val intent = Intent( this, ImageUpload::class.java)
            startActivity(intent)
        }

    }

    private fun getData() {
        stName = NAME.text.toString()
        stID = ID.text.toString()
        stMail = MAIL.text.toString()
        stPass = PASS.text.toString()
    }

    private fun init(){
        //database init
        database = Firebase.database.reference
        auth = Firebase.auth

        NAME = findViewById(R.id.fnameEt)
        ID = findViewById(R.id.idEt)
        MAIL = findViewById(R.id.emailEt)
        PASS = findViewById(R.id.passwordEt)
        btn = findViewById(R.id.btn1)
    }

    private fun update(){
        database = FirebaseDatabase.getInstance().getReference("users")
        val curr = auth.currentUser
        if(curr!=null) {
            val tempID = curr.uid
            val user = mapOf<String, String>(
                "sname" to stName,
                "spass" to stPass,
                "sid" to stID,
                "smail" to stMail
            )
            database.child(tempID).updateChildren(user).addOnSuccessListener {
                Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}