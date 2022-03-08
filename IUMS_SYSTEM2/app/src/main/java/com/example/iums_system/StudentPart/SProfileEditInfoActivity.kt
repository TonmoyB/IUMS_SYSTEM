package com.example.iums_system.StudentPart

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.iums_system.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SProfileEditInfoActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private lateinit var img: Uri

    private lateinit var NAME: EditText
    private lateinit var ID: EditText
    private lateinit var MAIL: EditText
    private lateinit var DEPT: EditText
    private lateinit var SEMESTER: EditText
    private lateinit var YEAR: EditText
    private lateinit var PASS: EditText

    private lateinit var btn: Button
    private lateinit var btn2: Button

    private lateinit var UID:String
    var idd = ""
    private lateinit var stName: String
    private lateinit var stID: String
    private lateinit var stPass: String
    private lateinit var stMail: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sprofile_edit_info)

        init()
        placeText()

        supportActionBar?.hide()
        var btn = findViewById<ImageButton>(R.id.bckbtn)
        btn.setOnClickListener {
            onBackPressed()
        }

    }

    private fun placeText() {
        val curr = auth.currentUser
        //currUser = curr
        if(curr !=null)
        {
            UID = curr.uid
        }

        val ref = FirebaseDatabase.getInstance().getReference("users")
        ref.child(UID)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //getbookURL
                    val studid = snapshot.child("sid").value

                    idd = "$studid"

                    val studname = snapshot.child("sname").value
                    val studpass = snapshot.child("spass").value
                    val studmail = snapshot.child("smail").value

                    var TV1 = findViewById<TextView>(R.id.fnameEt)
                    var TV2 = findViewById<TextView>(R.id.idEt)
                    var TV3 = findViewById<TextView>(R.id.emailEt)
                    var TV4 = findViewById<TextView>(R.id.passwordEt)
                    TV1.text = "$studname"
                    TV2.text = "$studid"

                    TV3.text = "$studmail"
                    TV4.text = "$studpass"

                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    override fun onStart() {
        super.onStart()
        btn.setOnClickListener(){

            getData()
            update()



            val intent = Intent(this@SProfileEditInfoActivity, StudentProfileActivity::class.java)
            intent.putExtra("studentID", idd)
            startActivity(intent)

        }

        //---Pic_Select+Up---
        btn2.setOnClickListener{
            val extraID = intent.getStringExtra("messages")
            val intent = Intent(this@SProfileEditInfoActivity, UploadImage::class.java)
            intent.putExtra("message", extraID)
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
        btn2 = findViewById(R.id.select)
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