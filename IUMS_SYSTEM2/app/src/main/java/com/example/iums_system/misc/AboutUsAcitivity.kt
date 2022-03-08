package com.example.iums_system.misc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.iums_system.R

class AboutUsAcitivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us_acitivity)

        supportActionBar?.hide()
        var btn = findViewById<ImageButton>(R.id.bckbtn)
        btn.setOnClickListener {
            onBackPressed()
        }

        intro_animation()

        var info58 = findViewById<ImageButton>(R.id.info58)
        var info59 = findViewById<ImageButton>(R.id.info59)


        info58.setOnClickListener {
            Toast.makeText(this,"Majorly Focused On: xml design, Firebase Database Handling [PDF Store, Retrieve, View, Delete], Debugging, Admin Part Handle etc.",Toast.LENGTH_LONG).show()
            Toast.makeText(this,"Also Handled: Concept Development, Firebase Database Handling [Data Store, Retrieve, show, Delete], Student Part Handle etc.",Toast.LENGTH_LONG).show()
        }
        info59.setOnClickListener {
            Toast.makeText(this,"Majorly Focused On: Project Monitoring, Firebase Database Handling [IMAGE Store, Retrieve, View, Delete], Debugging, Student Part Handle etc.",Toast.LENGTH_LONG).show()
            Toast.makeText(this,"Also Handled: Concept Development, Firebase Database Handling [Data Store, Retrieve, show, Delete], Admin Part Handle etc.",Toast.LENGTH_LONG).show()
        }



    }


    private fun intro_animation(){
        //declare animation
        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
        val ttb2 = AnimationUtils.loadAnimation(this, R.anim.ttb2);
        val stb = AnimationUtils.loadAnimation(this, R.anim.stb);
        val stb2 = AnimationUtils.loadAnimation(this, R.anim.stb2);
        val btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        val ltr = AnimationUtils.loadAnimation(this, R.anim.ltr);
        val ltr2 = AnimationUtils.loadAnimation(this, R.anim.ltr2);
        val rtl = AnimationUtils.loadAnimation(this, R.anim.rtl);
        val rtl2 = AnimationUtils.loadAnimation(this, R.anim.rtl2);

        var card58 = findViewById<CardView>(R.id.card58)
        var card59 = findViewById<CardView>(R.id.card59)
        var sir = findViewById<TextView>(R.id.sir)

        //set animation
        sir.startAnimation(stb2)
        card58.startAnimation(ttb)
        card59.startAnimation(ttb2)


    }
}