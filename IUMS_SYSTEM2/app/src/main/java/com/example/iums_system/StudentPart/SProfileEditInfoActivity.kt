package com.example.iums_system.StudentPart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.iums_system.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SProfileEditInfoActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

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

        init()

    }

    override fun onStart() {
        super.onStart()
        btn.setOnClickListener(){
            getData()

            update()
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
        if(curr!=null)
        {
            val tempID = curr.uid
            val user = mapOf<String, String>(
                "sname" to stName,
                "spass" to stPass,
                "sid" to stID,
                "smail" to stMail
            )
            database.child(tempID).updateChildren(user).addOnSuccessListener {

            }
        }
    }

}