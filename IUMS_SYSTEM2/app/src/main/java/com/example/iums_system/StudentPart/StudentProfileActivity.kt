package com.example.iums_system.StudentPart

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
import com.example.iums_system.misc.users
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class StudentProfileActivity : AppCompatActivity() {

    lateinit var toggle:ActionBarDrawerToggle

    private lateinit var user: users

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private lateinit var Name: TextView
    private lateinit var sample_student_name: TextView

    private lateinit var studentName: String

    private lateinit var navView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private var currUser: FirebaseUser?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_profile)

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

        Name = findViewById(R.id.username)
        //sample_student_name = findViewById(R.id.sample_student)

        navView = findViewById(R.id.nav_view)
        drawerLayout = findViewById(R.id.drawerLayout)
        currUser = auth.currentUser
    }
    private fun updateData(){
        val curr = auth.currentUser
        //currUser = curr
        if(curr !=null)
        {
            val tempID = curr.uid

            database.child("users").child(tempID).child("sname").get().addOnSuccessListener {
                Name.text = (it.value as CharSequence?)
                //sample_student_name.text = (it.value as CharSequence?)
                Toast.makeText(this,"Student Data Fetched",Toast.LENGTH_LONG).show()
            }
        }
        else
        {
            Toast.makeText(this,"not ok",Toast.LENGTH_LONG).show()
            val intent = Intent( this@StudentProfileActivity, StudentLogin::class.java)
            startActivity(intent)
        }
    }

    private fun drawer(){
        //val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        //val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this,drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.st_home -> {
                    Toast.makeText(applicationContext, "Clicked Home", Toast.LENGTH_SHORT).show()
                }
                R.id.st_profile ->{
                    val intent = Intent( this@StudentProfileActivity, StudentUserProfileActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(applicationContext,"Clicked Profile",Toast.LENGTH_SHORT).show()
                }
                R.id.st_FAQ -> Toast.makeText(applicationContext,"Clicked FAQ",Toast.LENGTH_SHORT).show()
                R.id.st_edit ->{
                    val intent = Intent( this@StudentProfileActivity, SProfileEditInfoActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(applicationContext,"Clicked Edit",Toast.LENGTH_SHORT).show()
                }
                R.id.st_logout -> {
                    logout()
                }

                R.id.sample_student -> Toast.makeText(applicationContext,"Clicked Name",Toast.LENGTH_SHORT).show()
                R.id.st_notice -> Toast.makeText(applicationContext,"Clicked Notice",Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

    private fun logout(){
        Toast.makeText(applicationContext,"Logout Successful",Toast.LENGTH_SHORT).show()
        val curr = auth.currentUser
        if(curr!=null)
        {
            auth.signOut()
            val intent = Intent( this@StudentProfileActivity, StudentLogin::class.java)
            startActivity(intent)
        }
    }
}