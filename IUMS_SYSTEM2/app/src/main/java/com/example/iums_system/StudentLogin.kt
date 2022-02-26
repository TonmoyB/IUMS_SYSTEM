package com.example.iums_system

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class StudentLogin : AppCompatActivity() {

private lateinit var database: DatabaseReference
private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_login)

        val btn = findViewById<Button>(R.id.btn1)
        btn.setOnClickListener(View.OnClickListener {

            auth = FirebaseAuth.getInstance()

            val SID = findViewById<EditText>(R.id.idEt).text.toString()
            val Spass = findViewById<EditText>(R.id.passwordEt).text.toString()
            val Smail = SID+"@aust.edu"

            if(SID.equals(""))
            {
                Toast.makeText(this,"Credentials do not match!",Toast.LENGTH_LONG).show()
            }
            else if(Spass.equals(""))
            {
                Toast.makeText(this,"Credentials do not match!",Toast.LENGTH_LONG).show()
            }
            else
            {

                login(Smail,Spass)
            }
        })



        val noAcc = findViewById(R.id.noAccount) as TextView
        noAcc.setOnClickListener(View.OnClickListener {
            var intent = Intent(this@StudentLogin, StudentSignup::class.java)
            startActivity(intent)
        })

        //declare animation
        val ttb = AnimationUtils.loadAnimation(this,R.anim.ttb);
        val stb = AnimationUtils.loadAnimation(this,R.anim.stb);
        val stb2 = AnimationUtils.loadAnimation(this,R.anim.stb2);
        val btt = AnimationUtils.loadAnimation(this,R.anim.btt);
        val ltr = AnimationUtils.loadAnimation(this,R.anim.ltr);
        val ltr2 = AnimationUtils.loadAnimation(this,R.anim.ltr2);
        val rtl = AnimationUtils.loadAnimation(this,R.anim.rtl);
        val rtl2 = AnimationUtils.loadAnimation(this,R.anim.rtl2);

        val txt1 = findViewById(R.id.txt1) as TextView
        val btn1 = findViewById(R.id.btn1) as Button
        //val btn2 = findViewById(R.id.btn2) as Button
        val id = findViewById(R.id.idEt) as EditText
        val pass = findViewById(R.id.passwordEt) as EditText

        btn1.startAnimation(stb)
        id.startAnimation(ltr)
        pass.startAnimation(ltr2)
        txt1.startAnimation(ttb)

    }

    private fun login(Smail: String, Spass: String){
        //login for logging user

        auth.signInWithEmailAndPassword(Smail,Spass)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    //code for logging in user
                    val intent = Intent( this@StudentLogin, StudentProfileActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this,"Credentials do not match!",Toast.LENGTH_LONG).show()
                }
            }
    }
}