package com.example.iums_system.StudentPart

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import com.example.iums_system.R

import com.example.iums_system.misc.AdminStudentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
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
    private lateinit var UID:String
    private var storageReference: StorageReference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_user_profile)

        initialization()
        getProfileData()
        setImage()

        supportActionBar?.hide()
        var btn = findViewById<ImageButton>(R.id.bckbtn)
        btn.setOnClickListener {
            onBackPressed()
        }

    }

    private fun getProfileData() {
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
                    var ID =        snapshot.child("sid").value
                    var NAME =      snapshot.child("sname").value
                    var EMAIL =     snapshot.child("smail").value
                    var DEPT =      snapshot.child("sdept").value
                    var SEMESTER =  snapshot.child("ssemester").value
                    var YEAR =      snapshot.child("syear").value
                    var ADMITTED_YEAR = "$SEMESTER" + " " + "$YEAR"
                    var YEARSEMESTER = snapshot.child("syearSemester").value

                   var TV_NAME = findViewById<TextView>(R.id.sname)
                   var TV_ID = findViewById<TextView>(R.id.sid)
                   var TV_EMAIL = findViewById<TextView>(R.id.smail)
                   var TV_DEPT = findViewById<TextView>(R.id.sdept)
                   var TV_ADMITTED_SESSION = findViewById<TextView>(R.id.admitted_session)
                   var TV_YEARSEMESTER = findViewById<TextView>(R.id.yearSemester)

                    TV_NAME.text = "$NAME"
                    TV_ID.text = "$ID"
                    TV_EMAIL.text = "$EMAIL"
                    TV_DEPT.text = "$DEPT"
                    TV_ADMITTED_SESSION.text = "$ADMITTED_YEAR"
                    TV_YEARSEMESTER.text = "$YEARSEMESTER"

                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun setImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Fetching...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        storageReference = Firebase.storage.reference
        val spic = findViewById<ImageView>(R.id.spic)

        val id = intent.getStringExtra("message")
        storageReference!!.child("images/${id}"!!)
            .downloadUrl.addOnSuccessListener {uri->
                if(progressDialog.isShowing)
                    progressDialog.dismiss()

                Picasso.get().load(uri).into(spic)
                Toast.makeText(this, "Image Fetched Successfully!", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener{

                if(progressDialog.isShowing)
                    progressDialog.dismiss()

                Toast.makeText(this, "No Image Found!", Toast.LENGTH_SHORT).show()
            }
    }



    private fun initialization() {
        database = Firebase.database.reference
        auth = Firebase.auth


    }

}