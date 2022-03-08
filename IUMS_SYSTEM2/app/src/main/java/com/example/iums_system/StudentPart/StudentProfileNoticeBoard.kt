package com.example.iums_system.StudentPart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.iums_system.AdminPart.AdapterPdfAdmin
import com.example.iums_system.AdminPart.AdminProfileViewNoticeActivity
import com.example.iums_system.AdminPart.ModelPdf
import com.example.iums_system.R
import com.example.iums_system.databinding.ActivityStudentProfileNoticeBoardBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class StudentProfileNoticeBoard : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityStudentProfileNoticeBoardBinding

    private companion object{
        const val TAG = "PDF_LIST_ADMIN"
    }

    //catagory id, title
    //private var catagoryId = ""
    //private var catagory = ""

    //arraylist holding books
    private lateinit var pdfArrayList: ArrayList<ModelPdf>
    //adapter
    private lateinit var adapterPdfStudent: AdapterPdfStudent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentProfileNoticeBoardBinding.inflate(layoutInflater)
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

    }

    private fun loadPdfList() {
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
                adapterPdfStudent = AdapterPdfStudent(this@StudentProfileNoticeBoard,pdfArrayList)
                binding.pdfsRv.adapter = adapterPdfStudent

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}