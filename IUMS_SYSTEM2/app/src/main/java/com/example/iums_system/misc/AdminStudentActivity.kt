package com.example.iums_system.misc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.iums_system.AdminPart.AdminLoginActivity
import com.example.iums_system.R
import com.example.iums_system.StudentPart.StudentLogin

class AdminStudentActivity : AppCompatActivity() {

    //private lateinit var
    private lateinit var Button1:Button
    private lateinit var Button2:Button

    private lateinit var Title:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_student)
        val btn =findViewById<Button>(R.id.btn2)

        initialize()
        intro_animation()

        supportActionBar?.hide()
        var bckbtn = findViewById<ImageButton>(R.id.bckbtn)
         bckbtn.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onStart() {
        super.onStart()

        Button1.setOnClickListener(View.OnClickListener {
            var intent = Intent(this@AdminStudentActivity, AdminLoginActivity::class.java)
            startActivity(intent)
        })

        Button2.setOnClickListener(View.OnClickListener {
            var intent = Intent(this@AdminStudentActivity, StudentLogin::class.java)
            startActivity(intent)
        })

    }
    private fun initialize(){
        Title = findViewById(R.id.txt1)
        Button1 = findViewById(R.id.btn1)
        Button2 = findViewById(R.id.btn2)
    }

    private fun intro_animation(){
        //declare animation
        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
        val stb = AnimationUtils.loadAnimation(this, R.anim.stb);
        val stb2 = AnimationUtils.loadAnimation(this, R.anim.stb2);
        val btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        val ltr = AnimationUtils.loadAnimation(this, R.anim.ltr);
        val ltr2 = AnimationUtils.loadAnimation(this, R.anim.ltr2);
        val rtl = AnimationUtils.loadAnimation(this, R.anim.rtl);
        val rtl2 = AnimationUtils.loadAnimation(this, R.anim.rtl2);


        Button1.startAnimation(ltr)
        Button2.startAnimation(ltr2)
        //txt1.startAnimation(stb)

    }

    override fun onBackPressed() {
        //super.onBackPressed()
        var intent = Intent(this@AdminStudentActivity, MainActivity::class.java)
        startActivity(intent)
    }
}