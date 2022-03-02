package com.example.iums_system.AdminPart

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import com.example.iums_system.R
import com.example.iums_system.StudentPart.StudentHomeActivity
import com.example.iums_system.StudentPart.StudentLogin
import com.example.iums_system.misc.users
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AdminProfileActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    private lateinit var user: users

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private lateinit var Name: TextView

    private lateinit var navV: NavigationView
    private lateinit var drawerLay: DrawerLayout
    private var currUser: FirebaseUser?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_profile)

        init()
        drawer()
    }

    override fun onStart() {
        super.onStart()
        updateData()
    }
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do you want to Log out?")
        builder.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.cancel()
        })
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
            logout()
        })
        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init(){
        //database init
        database = Firebase.database.reference
        auth = Firebase.auth

        Name = findViewById(R.id.username2)
        //sample_student_name = findViewById(R.id.sample_student)

        navV = findViewById(R.id.admin_nav_view)
        drawerLay = findViewById(R.id.drawerLayout2)
        currUser = auth.currentUser
    }
    private fun updateData(){

    }

    private fun drawer(){
        //val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        //val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this,drawerLay, R.string.open, R.string.close)
        drawerLay.addDrawerListener(toggle)

        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navV.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.admin_home -> {

                    Toast.makeText(applicationContext, "Clicked Home", Toast.LENGTH_SHORT).show()
                }

                R.id.admin_login -> {
                    logout()
                }


            }
            true
        }
    }

    private fun logout(){
        Toast.makeText(applicationContext,"Logout Successful", Toast.LENGTH_SHORT).show()
        val curr = auth.currentUser
        if(curr!=null)
        {
            auth.signOut()
            val intent = Intent( this@AdminProfileActivity, AdminLoginActivity::class.java)
            startActivity(intent)
        }
    }
}