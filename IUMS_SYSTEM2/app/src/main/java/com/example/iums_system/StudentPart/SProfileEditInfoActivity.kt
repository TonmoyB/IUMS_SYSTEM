package com.example.iums_system.StudentPart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.iums_system.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class SProfileEditInfoActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var NAME: TextView
    private lateinit var ID: TextView
    private lateinit var MAIL: TextView
    private lateinit var DEPT: TextView
    private lateinit var SEMESTER: TextView
    private lateinit var YEAR: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sprofile_edit_info)
    }


}