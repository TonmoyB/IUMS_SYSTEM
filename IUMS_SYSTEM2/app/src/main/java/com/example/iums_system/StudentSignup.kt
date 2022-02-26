package com.example.iums_system

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
/*
class StudentSignup : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    /*
    private lateinit var Sname: EditText
    private lateinit var SID: EditText
    private lateinit var Smail:EditText
    private lateinit var Spass:EditText
    private lateinit var conSpass:EditText
    private lateinit var user:users
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_signup)

        //auth = FirebaseAuth.getInstance()

        database = Firebase.database.reference
        auth = Firebase.auth


        val btn = findViewById<Button>(R.id.btn1)
        //password er kahini ase!!!-----------------------------------------------------------------
        btn.setOnClickListener {

            val Sname = findViewById<EditText>(R.id.fnameEt).text.toString()
            val SID = findViewById<EditText>(R.id.idEt).text.toString()
            val SMail = findViewById<EditText>(R.id.emailEt).text.toString()
            val Spass = findViewById<EditText>(R.id.passwordEt).text.toString()
            val conSpass = findViewById<EditText>(R.id.conpasswordEt).text.toString()

            val user = users(Sname, SID, SMail, Spass)


            if (Sname.equals("")) {
                Toast.makeText(this, "Insert your full name", Toast.LENGTH_SHORT).show()
            } else if (SID.equals("")) {
                Toast.makeText(this, "Insert your Student ID", Toast.LENGTH_SHORT).show()
            } else if (SMail.equals("")) {
                Toast.makeText(this, "Insert your mail address", Toast.LENGTH_SHORT).show()
            } else if (!SMail.endsWith("@aust.edu")) {
                Toast.makeText(this, "Insert your educational mail address", Toast.LENGTH_SHORT)
                    .show()
            } else if (!SMail.equals(SID + "@aust.edu")) {
                Toast.makeText(
                    this,
                    "Institutional gmail ID does not match with your ID",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (conSpass.equals("")) {
                Toast.makeText(this, "Confirm your password!", Toast.LENGTH_SHORT).show()
            } else if (!Spass.equals(conSpass)) {
                Toast.makeText(this, "Your Password does not match!", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(SMail, Spass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            //code for successful signup
                            //Toast.makeText(this, "SignUp Successful!", Toast.LENGTH_LONG).show()
                            val curr = auth.currentUser

                            if (curr != null) {
                                val tempID = curr.uid
                                database.child("users").child(tempID).setValue(user)
                                    .addOnSuccessListener {
                                        Toast.makeText(this,"Sign Up Completed!", Toast.LENGTH_LONG).show()
                                        var intent = Intent(this@StudentSignup, StudentLogin::class.java)
                                        startActivity(intent)
                                    }.addOnFailureListener {
                                    Toast.makeText(this, "Sign Up Failed!", Toast.LENGTH_LONG)
                                        .show()

                                }

                            }

                        } else {
                            Toast.makeText(
                                this,
                                "SignUp unsuccessful! Error occurred!",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }


            }

            val btn1 = findViewById(R.id.btn1) as Button
            val acc = findViewById(R.id.Account) as TextView

            acc.setOnClickListener(View.OnClickListener {
                var intent = Intent(this@StudentSignup, StudentLogin::class.java)
                startActivity(intent)
            })


            //declare animation
            val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
            val stb = AnimationUtils.loadAnimation(this, R.anim.stb);
            val stb2 = AnimationUtils.loadAnimation(this, R.anim.stb2);
            val btt = AnimationUtils.loadAnimation(this, R.anim.btt);
            val ltr = AnimationUtils.loadAnimation(this, R.anim.ltr);
            val ltr2 = AnimationUtils.loadAnimation(this, R.anim.ltr2);
            val rtl = AnimationUtils.loadAnimation(this, R.anim.rtl);
            val rtl2 = AnimationUtils.loadAnimation(this, R.anim.rtl2);

            val txt1 = findViewById(R.id.txt1) as TextView
            val txt2 = findViewById(R.id.fnameEt) as EditText
            val txt3 = findViewById(R.id.idEt) as EditText
            val txt4 = findViewById(R.id.emailEt) as EditText
            val txt5 = findViewById(R.id.passwordEt) as EditText
            val txt6 = findViewById(R.id.conpasswordEt) as EditText

            btn1.startAnimation(stb)
            txt1.startAnimation(ttb)
            txt2.startAnimation(ltr)
            txt3.startAnimation(rtl)
            txt4.startAnimation(ltr)
            txt5.startAnimation(rtl)
            txt6.startAnimation(ltr)

        }

    }
}
*/

class StudentSignup : AppCompatActivity() {

    private lateinit var user: users

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private lateinit var page_title: TextView

    private lateinit var student_name: EditText
    private lateinit var student_id: EditText
    private lateinit var student_mail: EditText
    private lateinit var student_password: EditText
    private lateinit var student_confirm_password: EditText

    private lateinit var button1: Button

    private lateinit var Sname: String
    private lateinit var SID: String
    private lateinit var SMail: String
    private lateinit var Spass: String
    private lateinit var conSpass: String
    private lateinit var tempID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_signup)

        initialize()
        //declare animation
        intro_animation()

    }

    override fun onStart() {
        super.onStart()

        //password er kahini ase!!!-----------------------------------------------------------------
        button1.setOnClickListener {
            conditionCheck()

        }
        val btn1 = findViewById(R.id.btn1) as Button
        val acc = findViewById(R.id.Account) as TextView
        acc.setOnClickListener(View.OnClickListener {
            var intent = Intent(this@StudentSignup, StudentLogin::class.java)
            startActivity(intent)
        })


    }

    private fun initialize() {
        //database init
        database = Firebase.database.reference
        auth = Firebase.auth
        //button init
        button1 = findViewById(R.id.btn1) as Button
        //title init
        page_title = findViewById(R.id.txt1) as TextView
        //input data init
        student_name = findViewById(R.id.fnameEt) as EditText
        student_id = findViewById(R.id.idEt) as EditText
        student_mail = findViewById(R.id.emailEt) as EditText
        student_password = findViewById(R.id.passwordEt) as EditText
        student_confirm_password = findViewById(R.id.conpasswordEt) as EditText

        //animation init


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

        button1.startAnimation(stb)
        page_title.startAnimation(ttb)
        student_name.startAnimation(ltr)
        student_id.startAnimation(rtl)
        student_mail.startAnimation(ltr)
        student_password.startAnimation(rtl)
        student_confirm_password.startAnimation(ltr)
    }

    private fun conditionCheck() {


        //val user = users(Sname, SID, SMail, Spass)
        parseData()

        if (Sname.equals("")) {
            Toast.makeText(this, "Insert your full name", Toast.LENGTH_SHORT).show()
        } else if (SID.equals("")) {
            Toast.makeText(this, "Insert your Student ID", Toast.LENGTH_SHORT).show()
        } else if (SMail.equals("")) {
            Toast.makeText(this, "Insert your mail address", Toast.LENGTH_SHORT).show()
        } else if (!SMail.endsWith("@aust.edu")) {
            Toast.makeText(this, "Insert your educational mail address", Toast.LENGTH_SHORT)
                .show()
        } else if (!SMail.equals(SID + "@aust.edu")) {
            Toast.makeText(
                this,
                "Institutional gmail ID does not match with your ID",
                Toast.LENGTH_SHORT
            ).show()
        } else if (conSpass.equals("")) {
            Toast.makeText(this, "Confirm your password!", Toast.LENGTH_SHORT).show()
        } else if (!Spass.equals(conSpass)) {
            Toast.makeText(this, "Your Password does not match!", Toast.LENGTH_SHORT).show()
        } else {

            authentication()

        }
    }

    private fun authentication() {
        auth.createUserWithEmailAndPassword(SMail, Spass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val curr = auth.currentUser
                    if (curr != null) {
                        tempID = curr.uid
                        signup()
                    }

                } else {
                    Toast.makeText(this, "SignUp unsuccessful! Error occurred!", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun parseData(){
        Sname = student_name.text.toString()
        SID = student_id.text.toString()
        SMail = student_mail.text.toString()
        Spass = student_password.text.toString()
        conSpass = student_confirm_password.text.toString()
    }
    private fun signup(){

        Toast.makeText(this, "Sign Up Completed!", Toast.LENGTH_LONG).show()
        database.child("users").child(tempID).setValue(user)
            .addOnSuccessListener {
                var intent = Intent(this@StudentSignup, StudentLogin::class.java)
                startActivity(intent)


            }.addOnFailureListener {
                Toast.makeText(this, "Sign Up Failed!", Toast.LENGTH_LONG).show()
            }
    }
}

