package com.example.iums_system

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn =findViewById<Button>(R.id.btn1)
        btn.setOnClickListener(View.OnClickListener {
            var intent = Intent(this@MainActivity, AdminStudentActivity::class.java)
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

        val img1 = findViewById(R.id.img1) as ImageView
        val txt1 = findViewById(R.id.txt1) as TextView
        val txt2 = findViewById(R.id.txt2) as TextView
        val txt3 = findViewById(R.id.txt3) as TextView
        val btn1 = findViewById(R.id.btn1) as Button
        //set animation
        btn1.startAnimation(btt)
        txt1.startAnimation(ttb)
        img1.startAnimation(stb)
        txt2.startAnimation(ltr)
        txt3.startAnimation(stb2)



    }
}
