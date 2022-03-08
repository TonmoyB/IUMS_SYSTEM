package com.example.iums_system.StudentPart

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.iums_system.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class UploadImage : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var img: Uri

    private lateinit var stdid: TextView
    private lateinit var btn: Button
    private lateinit var btn2: Button
    private lateinit var sImage: ImageView
    lateinit var Extraid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_image)

        init()

        Extraid = intent.getStringExtra("message").toString()
        stdid.setText("Imaage Name: " + Extraid)


        btn.setOnClickListener{
            selectImage()
        }
        btn2.setOnClickListener{
            uploadImage()
            val intent = Intent(this@UploadImage, SProfileEditInfoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun init(){
        //database init
        database = Firebase.database.reference
        auth = Firebase.auth

        stdid = findViewById(R.id.sid)
        sImage = findViewById(R.id.spic)
        btn = findViewById(R.id.select)
        btn2 = findViewById(R.id.upload)
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == RESULT_OK)
        {
            img = data?.data!!
            sImage.setImageURI(img)
        }
    }

    private fun uploadImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val filename = Extraid
        val storageReference = FirebaseStorage.getInstance().getReference("images/$filename")

        storageReference.putFile(img).
        addOnSuccessListener {
            sImage.setImageURI(img)
            Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show()
            if(progressDialog.isShowing)progressDialog.dismiss()
        }.addOnFailureListener{
            if(progressDialog.isShowing)progressDialog.dismiss()
            Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
        }
    }
}