package com.example.iums_system

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView

class AdminStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_student)
        val btn =findViewById<Button>(R.id.btn2)
        btn.setOnClickListener(View.OnClickListener {
            var intent = Intent(this@AdminStudentActivity, StudentLogin::class.java)
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
        val btn2 = findViewById(R.id.btn2) as Button

        btn1.startAnimation(ltr)
        btn2.startAnimation(ltr2)
        //txt1.startAnimation(stb)


    }
}