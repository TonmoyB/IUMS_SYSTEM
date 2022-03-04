package com.example.iums_system.AdminPart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.iums_system.R
import com.example.iums_system.databinding.ActivityAdminProfileViewNoticeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminProfileViewNoticeActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding:ActivityAdminProfileViewNoticeBinding

    private companion object{
        const val TAG = "PDF_LIST_ADMIN"
    }

    //catagory id, title
    //private var catagoryId = ""
    //private var catagory = ""

    //arraylist holding books
    private lateinit var pdfArrayList: ArrayList<ModelPdf>
    //adapter
    private lateinit var adapterPdfAdmin: AdapterPdfAdmin


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminProfileViewNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get from intent, that we passed from adapter
        //val intent = intent
        //catagoryId = intent.getStringExtra("catagoryId")!!
        //catagory = intent.getStringExtra("catagory")!!

        //set pdf catagory
        //binding.subtitle.text = catagory

        //load pdf/notices
        loadPdfList()

        //search
        //binding.searchEt.addText

        //handle click, go back
        binding.bckbtn.setOnClickListener{
            onBackPressed()
        }
        binding.updtbtn.setOnClickListener {
            Toast.makeText(applicationContext, "Clicked Update Notice", Toast.LENGTH_SHORT).show()
            val intent = Intent( this@AdminProfileViewNoticeActivity, AdminProfileUpdateNoticeActivity::class.java)
            startActivity(intent)
        }




    }

    private fun loadPdfList() {
        //init arraylist
        pdfArrayList = ArrayList()

        val ref = FirebaseDatabase.getInstance().getReference("Books")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear list before start adding data into it
                pdfArrayList.clear()
                for(ds in snapshot.children){
                    //get data
                    val model = ds.getValue(ModelPdf::class.java)
                    //add to list
                    if (model != null) {
                        pdfArrayList.add(model)
                        Log.d(TAG,"onDataChange: ${model.title} ")
                    }

                }
                //setup adapter
                adapterPdfAdmin = AdapterPdfAdmin(this@AdminProfileViewNoticeActivity,pdfArrayList)
                binding.pdfsRv.adapter = adapterPdfAdmin


            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}