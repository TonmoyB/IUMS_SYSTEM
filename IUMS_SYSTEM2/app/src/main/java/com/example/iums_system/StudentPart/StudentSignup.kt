package com.example.iums_system.StudentPart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.iums_system.R
import com.example.iums_system.misc.users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

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
    private lateinit var jumpSignin: TextView

    private lateinit var Sname: String
    private lateinit var SID: String
    private lateinit var SMail: String
    private lateinit var Spass: String
    private lateinit var conSpass: String
    private lateinit var tempID: String
    private lateinit var Sdept: String
    private lateinit var Ssemester: String
    private lateinit var Syear: String


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
        jumpSignin = findViewById((R.id.Account)) as TextView
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
        var flag = false
        if (Sname.equals("")) {
            Toast.makeText(this, "Insert your full name", Toast.LENGTH_SHORT).show()
            flag = true
        }
        if (SID.equals("")) {
            Toast.makeText(this, "Insert your Student ID", Toast.LENGTH_SHORT).show()
            flag = true
        }
        if (SID.length != 9 ) {
            Toast.makeText(this, "Invalid Student ID Length", Toast.LENGTH_SHORT).show()
            flag =true
        }
        if (SID.length == 9 ) {
            /*
            if(SID.get(0)> '2')
            {
                Toast.makeText(this, "(0)>2", Toast.LENGTH_SHORT).show()
                flag= true
            }
            if(SID.get(2) != '0')
            {
                Toast.makeText(this, "(2)!=0", Toast.LENGTH_SHORT).show()
                flag= true
            }
            if(SID.get(3)> '2')
            {
                Toast.makeText(this, "(3)>2", Toast.LENGTH_SHORT).show()
                flag= true
            }
            if(SID.get(4)!= '0')
            {
                Toast.makeText(this, "(4)!=0", Toast.LENGTH_SHORT).show()
                flag= true
            }
            if(SID.get(5)== '9' || SID.get(5)=='0')
            {
                Toast.makeText(this, "(5)9/0", Toast.LENGTH_SHORT).show()
                flag = true
            }
             */
            if(SID.get(0).toString()+SID.get(1).toString() > "22" )
            {
                Toast.makeText(this, "Invalid student admission year", Toast.LENGTH_SHORT).show()
            }
            /*if( SID.get(2).toString()+SID.get(3).toString() != "01" || SID.get(2).toString()+SID.get(3).toString() != "02" )
            {
                Toast.makeText(this, SID.get(2).toString()+SID.get(3).toString() +" is not a valid session", Toast.LENGTH_SHORT).show()
            }
            if( SID.get(4).toString()+SID.get(5).toString() != "01" || SID.get(4).toString()+SID.get(5).toString() != "02" || SID.get(4).toString()+SID.get(5).toString() != "03" || SID.get(4).toString()+SID.get(5).toString() != "04" || SID.get(4).toString()+SID.get(5).toString() != "05" || SID.get(4).toString()+SID.get(5).toString() != "06" || SID.get(4).toString()+SID.get(5).toString() != "07" || SID.get(4).toString()+SID.get(5).toString() != "08" )
            {
                Toast.makeText(this, SID.get(4).toString()+SID.get(5).toString() +" does not mean any valid department", Toast.LENGTH_SHORT).show()
            }

             */
            if( SID.get(2).toString()+SID.get(3).toString() > "02" )
            {
                Toast.makeText(this, SID.get(2).toString()+SID.get(3).toString() +" is not a valid session", Toast.LENGTH_SHORT).show()
            }
            else if( SID.get(2).toString()+SID.get(3).toString() == "00" )
            {
                Toast.makeText(this, SID.get(2).toString()+SID.get(3).toString() +" is not a valid session", Toast.LENGTH_SHORT).show()
            }
            if(SID.get(4).toString()+SID.get(5).toString() > "08")
            {
                Toast.makeText(this, SID.get(4).toString()+SID.get(5).toString() +" does not mean any valid department", Toast.LENGTH_SHORT).show()
            }
            else if(SID.get(4).toString()+SID.get(5).toString() == "00")
            {
                Toast.makeText(this, SID.get(4).toString()+SID.get(5).toString() +" does not mean any valid department", Toast.LENGTH_SHORT).show()
            }

            checkStudentCapacity()


        }
        if (SMail.equals("")) {
            Toast.makeText(this, "Insert your mail address", Toast.LENGTH_SHORT).show()
            flag = true
        }
        else if (!SMail.endsWith("@aust.edu")) {
            Toast.makeText(this, "Insert your educational mail address", Toast.LENGTH_SHORT).show()
            flag = true
        }
        else if (!SMail.equals(SID + "@aust.edu")) {
            Toast.makeText(this, "Institutional gmail ID does not match with your ID", Toast.LENGTH_SHORT).show()
            flag = true
        }
        if (conSpass.equals("")) {
            Toast.makeText(this, "Confirm your password!", Toast.LENGTH_SHORT).show()
            flag = true
        }
        if (!Spass.equals(conSpass)) {
            Toast.makeText(this, "Your Password does not match!", Toast.LENGTH_SHORT).show()
            flag = true
        }

        if(flag == false)
        {
            authentication()
        }


    }

    private fun checkStudentCapacity() {
        if( SID.get(2).toString()+SID.get(3).toString() == "01" )//Spring
        {
            if(SID.get(4).toString()+SID.get(5).toString() == "01")//ARC
            {
                if( SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() > "100" || SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() == "000" )
                {
                    Toast.makeText(this,"Student ID not valid!",Toast.LENGTH_SHORT).show()
                }
            }
            else if(SID.get(4).toString()+SID.get(5).toString() == "02")//BBA
            {
                Toast.makeText(this,"Sorry! BBA program does not support yet.",Toast.LENGTH_SHORT).show()

            }
            else if(SID.get(4).toString()+SID.get(5).toString() == "03")//CIVIL
            {
                if( SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() > "200"  || SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() == "000" )
                {
                    Toast.makeText(this,"Student ID not valid!",Toast.LENGTH_SHORT).show()
                }
            }
            else if(SID.get(4).toString()+SID.get(5).toString() == "04")//CSE
            {
                if( SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() > "200"  || SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() == "000" )
                {
                    Toast.makeText(this,"Student ID not valid!",Toast.LENGTH_SHORT).show()
                }
            }
            else if(SID.get(4).toString()+SID.get(5).toString() == "05")//EEE
            {
                if( SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() > "250"  || SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() == "000" )
                {
                    Toast.makeText(this,"Student ID not valid!",Toast.LENGTH_SHORT).show()
                }
            }
            else if(SID.get(4).toString()+SID.get(5).toString() == "06")//TE
            {
                if( SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() > "200"  || SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() == "000" )
                {
                    Toast.makeText(this,"Student ID not valid!",Toast.LENGTH_SHORT).show()
                }
            }
            else if(SID.get(4).toString()+SID.get(5).toString() == "07")//IPE
            {
                if( SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() > "150"  || SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() == "000" )
                {
                    Toast.makeText(this,"Student ID not valid!",Toast.LENGTH_SHORT).show()
                }
            }
            else if(SID.get(4).toString()+SID.get(5).toString() == "08")//ME
            {
                if( SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() > "150"  || SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() == "000" )
                {
                    Toast.makeText(this,"Student ID not valid!",Toast.LENGTH_SHORT).show()
                }
            }

        }
        else if( SID.get(2).toString()+SID.get(3).toString() == "02" )//Fall
        {
            if(SID.get(4).toString()+SID.get(5).toString() == "01")//ARC
            {
                if( SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() > "100" || SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() == "000" )
                {
                    Toast.makeText(this,"Student ID not valid!",Toast.LENGTH_SHORT).show()
                }
            }
            else if(SID.get(4).toString()+SID.get(5).toString() == "02")//BBA
            {
                Toast.makeText(this,"Sorry! BBA program does not support yet.",Toast.LENGTH_SHORT).show()

            }
            else if(SID.get(4).toString()+SID.get(5).toString() == "03")//CIVIL
            {
                if( SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() > "150"  || SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() == "000" )
                {
                    Toast.makeText(this,"Student ID not valid!",Toast.LENGTH_SHORT).show()
                }
            }
            else if(SID.get(4).toString()+SID.get(5).toString() == "04")//CSE
            {
                if( SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() > "150"  || SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() == "000" )
                {
                    Toast.makeText(this,"Student ID not valid!",Toast.LENGTH_SHORT).show()
                }
            }
            else if(SID.get(4).toString()+SID.get(5).toString() == "05")//EEE
            {
                if( SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() > "200"  || SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() == "000" )
                {
                    Toast.makeText(this,"Student ID not valid!",Toast.LENGTH_SHORT).show()
                }
            }
            else if(SID.get(4).toString()+SID.get(5).toString() == "06")//TE
            {
                if( SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() > "150"  || SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() == "000" )
                {
                    Toast.makeText(this,"Student ID not valid!",Toast.LENGTH_SHORT).show()
                }
            }
            else if(SID.get(4).toString()+SID.get(5).toString() == "07")//IPE
            {
                if( SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() > "150"  || SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() == "000" )
                {
                    Toast.makeText(this,"Student ID not valid!",Toast.LENGTH_SHORT).show()
                }
            }
            else if(SID.get(4).toString()+SID.get(5).toString() == "08")//ME
            {
                if( SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() > "150"  || SID.get(6).toString()+SID.get(7).toString()+SID.get(8).toString() == "000" )
                {
                    Toast.makeText(this,"Student ID not valid!",Toast.LENGTH_SHORT).show()
                }
            }
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
        Sname = student_name.text.toString().uppercase()
        SID = student_id.text.toString()
        SMail = student_mail.text.toString()
        Spass = student_password.text.toString()
        conSpass = student_confirm_password.text.toString()

        Syear = "20"+SID.get(0).toString()+SID.get(1).toString()
        if(SID.get(2).toString()+SID.get(3).toString() == "01"){
            Ssemester = "Spring"
        }
        else{
            Ssemester = "Fall"
        }
        if(SID.get(4).toString()+SID.get(5).toString() == "01"){
            Sdept = "Architecture"
        }
        else if(SID.get(4).toString()+SID.get(5).toString() == "02"){
            Sdept = "BBA"
        }
        else if(SID.get(4).toString()+SID.get(5).toString() == "03"){
            Sdept = "Civil Engineering"
        }
        else if(SID.get(4).toString()+SID.get(5).toString() == "04"){
            Sdept = "Computer Science and Engineering"
        }
        else if(SID.get(4).toString()+SID.get(5).toString() == "05"){
            Sdept = "Electrical and Electronics Engineering"
        }
        else if(SID.get(4).toString()+SID.get(5).toString() == "06"){
            Sdept = "Textile Engineering"
        }
        else if(SID.get(4).toString()+SID.get(5).toString() == "07"){
            Sdept = "Industrial and Production Engineering"
        }
        else if(SID.get(4).toString()+SID.get(5).toString() == "08"){
            Sdept = "Mechanical Engineering"
        }

    }

    private fun signup(){

        Toast.makeText(this, "Sign Up Completed!", Toast.LENGTH_LONG).show()
        user = users(Sname, SID, SMail, Spass, Syear, Ssemester, Sdept)
        database.child("users").child(tempID).setValue(user)
            .addOnSuccessListener {
                var intent = Intent(this@StudentSignup, StudentLogin::class.java)
                startActivity(intent)


            }.addOnFailureListener {
                Toast.makeText(this, "Sign Up Failed!", Toast.LENGTH_LONG).show()
            }
    }
}

