package com.example.iums_system.StudentPart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import com.example.iums_system.AdminPart.AdminLoginActivity
import com.example.iums_system.AdminPart.AdminProfileActivity
import com.example.iums_system.R
import com.example.iums_system.StudentPart.StudentProfileActivity
import com.example.iums_system.misc.AdminStudentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class StudentLogin : AppCompatActivity() {

    //private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private var currUser: FirebaseUser?=null
    private lateinit var Title: TextView
    private lateinit var ID: EditText
    private lateinit var Pass: EditText

    private lateinit var button: Button
    private lateinit var noAcc: TextView

    private lateinit var studentID:String
    private lateinit var studentMail:String
    private lateinit var studentPassword:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_login)

        initialize()
        intro_animation()

        supportActionBar?.hide()
        var btn = findViewById<ImageButton>(R.id.bckbtn)
        btn.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onStart() {
        super.onStart()

        //checkForLogin()
        checkIfButtonPressed()
        checkforSignupApproach()
    }

    override fun onBackPressed() {
        val intent = Intent( this@StudentLogin, AdminStudentActivity::class.java)
        startActivity(intent)
    }

    private fun initialize(){
        auth = Firebase.auth
        currUser = auth.currentUser

        ID = findViewById(R.id.idEt)
        Pass = findViewById(R.id.passwordEt)
        Title = findViewById(R.id.txt1)

        button = findViewById(R.id.btn1) as Button
        noAcc = findViewById(R.id.noAccount) as TextView

    }

    private fun checkIfButtonPressed() {

            button.setOnClickListener(View.OnClickListener {
                studentID = ID.text.toString()
                studentPassword = Pass.text.toString()
                studentMail = studentID + "@aust.edu"

                if(studentID.equals(""))
                {
                    Toast.makeText(this,"Credentials do not match!",Toast.LENGTH_LONG).show()
                }
                else if(studentPassword.equals(""))
                {
                    Toast.makeText(this,"Credentials do not match!",Toast.LENGTH_LONG).show()
                }
                else
                {

                    login(studentMail,studentPassword)

                }
            })
    }

    private fun login(Smail: String, Spass: String){
        //login for logging user

        auth.signInWithEmailAndPassword(Smail,Spass)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    //code for logging in user
                    val intent = Intent( this@StudentLogin, StudentProfileActivity::class.java)
                    intent.putExtra("studentID",studentID)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this,"Credentials do not match!",Toast.LENGTH_LONG).show()
                }
            }
    }


    private fun intro_animation() {
        //declare animation
        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
        val stb = AnimationUtils.loadAnimation(this, R.anim.stb);
        val stb2 = AnimationUtils.loadAnimation(this, R.anim.stb2);
        val btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        val ltr = AnimationUtils.loadAnimation(this, R.anim.ltr);
        val ltr2 = AnimationUtils.loadAnimation(this, R.anim.ltr2);
        val rtl = AnimationUtils.loadAnimation(this, R.anim.rtl);
        val rtl2 = AnimationUtils.loadAnimation(this, R.anim.rtl2);

        button.startAnimation(stb)
        ID.startAnimation(ltr)
        Pass.startAnimation(ltr2)
        Title.startAnimation(ttb)
    }




    private fun checkforSignupApproach(){
        noAcc.setOnClickListener(View.OnClickListener {
            var intent = Intent(this@StudentLogin, StudentSignup::class.java)
            startActivity(intent)
        })
    }
}