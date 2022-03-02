package com.example.iums_system.StudentPart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.iums_system.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class StudentUserProfileActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var NAME: TextView
    private lateinit var ID: TextView
    private lateinit var MAIL: TextView
    private lateinit var DEPT: TextView
    private lateinit var SEMESTER: TextView
    private lateinit var YEAR: TextView
    private lateinit var studentName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_home)

        init()

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
                                temp += NAME.text as String
                                temp += data.toString()
                                NAME.text = temp
                            }
                            "sid" -> {
                                temp = ""
                                temp += ID.text as String
                                temp += data.toString()
                                ID.text = temp
                            }
                            "smail" -> {
                                temp = ""
                                temp += MAIL.text as String
                                temp += data.toString()
                                temp += " "
                                MAIL.text = temp
                            }
                            "sdept" -> {
                                temp = ""
                                temp += DEPT.text as String
                                temp += data.toString()
                                DEPT.text = temp
                            }
                            "ssemester" -> {
                                temp = ""
                                temp += SEMESTER.text as String
                                temp += data.toString()
                                SEMESTER.text = temp
                            }
                            "syear" -> {
                                temp = ""
                                temp += YEAR.text as String
                                temp += data.toString()
                                YEAR.text = temp
                            }
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
        }
    }

    private fun init(){
        //database init
        database = Firebase.database.reference
        auth = Firebase.auth

        NAME = findViewById(R.id.sname)
        ID = findViewById(R.id.sid)
        MAIL = findViewById(R.id.smail)
        DEPT = findViewById(R.id.sdept)
        SEMESTER = findViewById(R.id.ssemester)
        YEAR = findViewById(R.id.syear)
    }

}