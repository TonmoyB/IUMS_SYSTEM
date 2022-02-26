package com.example.iums_system.AdminPart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.iums_system.R
import com.example.iums_system.misc.AdminStudentActivity
//import com.example.iums_system.StudentPart.StudentProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AdminLoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private var currUser:FirebaseUser?=null

    private lateinit var txt1:TextView
    private lateinit var ID:EditText
    private lateinit var Pass:EditText

    private lateinit var button:Button

    private lateinit var AdminID:String
    private lateinit var Password:String
    private lateinit var AdminMail:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        initialize()
        //checkForLogin()
        intro_animation()
        checkIfButtonPressed()
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onBackPressed() {
        val intent = Intent( this@AdminLoginActivity, AdminStudentActivity::class.java)
        startActivity(intent)
    }

    private fun checkIfButtonPressed() {


        button.setOnClickListener(View.OnClickListener {

            AdminID = ID.text.toString()
            Password = Pass.text.toString()

            if(AdminID.equals(""))
            {
                Toast.makeText(this,"Credentials do not match!",Toast.LENGTH_LONG).show()
            }
            else if(AdminID.length != 3)
            {
                Toast.makeText(this,"Invalid ID!",Toast.LENGTH_LONG).show()
            }
            else if(Password.equals(""))
            {
                Toast.makeText(this,"Credentials do not match!",Toast.LENGTH_LONG).show()
            }
            else
            {

                AdminMail = AdminID+"@aust.edu"
                login(AdminMail,Password)
            }
        })
    }

    private fun initialize(){
        auth = Firebase.auth

        ID = findViewById(R.id.idEt)
        Pass = findViewById(R.id.passwordEt)
        txt1 = findViewById(R.id.txt1)

        button = findViewById(R.id.btn1) as Button

        currUser = auth.currentUser
    }
    private fun intro_animation() {
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
        txt1.startAnimation(ttb)
    }

    private fun login(Smail: String, Spass: String){
        //login for logging user

        auth.signInWithEmailAndPassword(Smail,Spass)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    //code for logging in user
                        Toast.makeText(this, "Credentials matched!",Toast.LENGTH_LONG).show()
                    val intent = Intent( this@AdminLoginActivity, AdminProfileActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this,"Credentials do not match!", Toast.LENGTH_LONG).show()
                }
            }
    }

}