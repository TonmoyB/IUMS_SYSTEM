
package com.example.iums_system.StudentPart

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import com.example.iums_system.AdminPart.AdminProfileNoticeDetailActivity
import com.example.iums_system.R
import com.example.iums_system.misc.AboutUsAcitivity
import com.example.iums_system.misc.users
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class StudentProfileActivity : AppCompatActivity() {

    lateinit var toggle:ActionBarDrawerToggle

    private lateinit var user: users

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var UID: String

    private lateinit var stdID: String
    private lateinit var Name: TextView
    private lateinit var navView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private var currUser: FirebaseUser?=null

    private var storageReference: StorageReference?=null
    private var idd = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_profile)

        init()
        drawer()
        setHeader()

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

    private fun setHeader() {
        val curr = auth.currentUser
        //currUser = curr
        if(curr !=null)
        {
            UID = curr.uid
        }

        val ref = FirebaseDatabase.getInstance().getReference("users")
        ref.child(UID)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //getbookURL
                    val studid = snapshot.child("sid").value

                    idd = "$studid"

                    val studname = snapshot.child("sname").value
                    val studmail = snapshot.child("smail").value

                    var TV1 = findViewById<TextView>(R.id.sample_student)
                    var TV2 = findViewById<TextView>(R.id.sub)
                    TV1.text = "$studname"
                    TV2.text = "$studmail"

                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Fetching...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        storageReference = Firebase.storage.reference

        storageReference!!.child("images/${idd}"!!)
            .downloadUrl.addOnSuccessListener {uri->
                if(progressDialog.isShowing)
                    progressDialog.dismiss()

                val img = findViewById<ImageView>(R.id.st_pic)

                Picasso.get().load(uri).into(img)
                Toast.makeText(this, "Image Fetched Successfully!", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener{

                if(progressDialog.isShowing)
                    progressDialog.dismiss()

                Toast.makeText(this, "Image Load Failed!", Toast.LENGTH_SHORT).show()
            }

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

        idd = intent.getStringExtra("studentID")!!
    }

    private fun updateData(){
        val curr = auth.currentUser
        //currUser = curr
        if(curr !=null)
        {

            UID = curr.uid

        }
        database.child("users").child(UID).child("sname").get().addOnSuccessListener {
            Name.text = it.value as CharSequence?
            //sample_student_name.text = (it.value as CharSequence?)
            Toast.makeText(this,"Student Data Fetched",Toast.LENGTH_LONG).show()
        }
        database.child("users").child(UID).child("sid").get().addOnSuccessListener {
            stdID = (it.value as CharSequence?).toString()
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

                R.id.st_profile ->{
                    val intent = Intent( this@StudentProfileActivity, StudentUserProfileActivity::class.java)
                    intent.putExtra("message", stdID)
                    startActivity(intent)
                    Toast.makeText(applicationContext,"Clicked Profile",Toast.LENGTH_SHORT).show()
                }
                R.id.aboutUS -> {
                    Toast.makeText(applicationContext, "Clicked FAQ", Toast.LENGTH_SHORT).show()
                }
                R.id.st_edit ->{
                    val intent = Intent( this@StudentProfileActivity, SProfileEditInfoActivity::class.java)
                    intent.putExtra("messages", stdID)
                    startActivity(intent)
                    Toast.makeText(applicationContext,"Clicked Edit",Toast.LENGTH_SHORT).show()
                }
                R.id.st_logout -> {
                    logout()
                }

                R.id.sample_student -> Toast.makeText(applicationContext,"Clicked Name",Toast.LENGTH_SHORT).show()
                R.id.st_notice -> {
                    Toast.makeText(applicationContext, "Clicked Notice", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@StudentProfileActivity, StudentProfileNoticeBoard::class.java)
                    startActivity(intent)
                }

                R.id.aboutUs -> {
                    val intent = Intent( this@StudentProfileActivity, AboutUsAcitivity::class.java)
                    startActivity(intent)
                }
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



/*

//SAVEPOINT-----------------------------------------------------------------------------------------------------
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
    private lateinit var UID: String

    private lateinit var stdID: String
    private lateinit var Name: TextView
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

            UID = curr.uid

        }
        database.child("users").child(UID).child("sname").get().addOnSuccessListener {
            Name.text = it.value as CharSequence?
            //sample_student_name.text = (it.value as CharSequence?)
            Toast.makeText(this,"Student Data Fetched",Toast.LENGTH_LONG).show()
        }
        database.child("users").child(UID).child("sid").get().addOnSuccessListener {
            stdID = (it.value as CharSequence?).toString()
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
                    intent.putExtra("message", stdID)
                    startActivity(intent)
                    Toast.makeText(applicationContext,"Clicked Profile",Toast.LENGTH_SHORT).show()
                }
                R.id.st_FAQ -> Toast.makeText(applicationContext,"Clicked FAQ",Toast.LENGTH_SHORT).show()
                R.id.st_edit ->{
                    val intent = Intent( this@StudentProfileActivity, SProfileEditInfoActivity::class.java)
                    intent.putExtra("messages", stdID)
                    startActivity(intent)
                    Toast.makeText(applicationContext,"Clicked Edit",Toast.LENGTH_SHORT).show()
                }
                R.id.st_logout -> {
                    logout()
                }

                R.id.sample_student -> Toast.makeText(applicationContext,"Clicked Name",Toast.LENGTH_SHORT).show()
                R.id.st_notice -> {
                    Toast.makeText(applicationContext, "Clicked Notice", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@StudentProfileActivity, StudentProfileNoticeBoard::class.java)
                    startActivity(intent)
                }
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
 */