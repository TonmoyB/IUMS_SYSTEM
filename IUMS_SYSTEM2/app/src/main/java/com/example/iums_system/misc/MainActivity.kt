package com.example.iums_system.misc

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.iums_system.R
import org.w3c.dom.Text
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var title: TextView
    private lateinit var aust_logo:ImageView
    private lateinit var subtitle: TextView
    private lateinit var actiontitle:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()

    }

    override fun onStart() {
        super.onStart()
        intro_animation()
        checkIfNEXTisClicked()
    }

    private fun initialize(){
        button = findViewById(R.id.btn1)
        aust_logo = findViewById(R.id.img1)
        title = findViewById(R.id.txt1)
        subtitle = findViewById(R.id.txt2)
        actiontitle = findViewById(R.id.txt3)
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

        //set animation
        button.startAnimation(btt)
        title.startAnimation(ttb)
        aust_logo.startAnimation(stb)
        subtitle.startAnimation(ltr)
        actiontitle.startAnimation(stb2)
    }

    private fun checkIfNEXTisClicked()
    {
        button.setOnClickListener(View.OnClickListener {
            var intent = Intent(this@MainActivity, AdminStudentActivity::class.java)
            startActivity(intent)
        })
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to Exit?")
        builder.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.cancel()
        })
        builder.setPositiveButton("Yes",DialogInterface.OnClickListener { dialogInterface, i ->
            //finish()
            MainActivity().finish();
            finishAffinity();
            System.exit(0);
            //System.exit(0);
        })
        val alertDialog = builder.create()
        alertDialog.show()
    }

}
