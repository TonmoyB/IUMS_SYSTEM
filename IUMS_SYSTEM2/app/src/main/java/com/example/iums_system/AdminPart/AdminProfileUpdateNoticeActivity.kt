package com.example.iums_system.AdminPart

import android.app.AlertDialog
import android.app.Application
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.iums_system.R
import com.example.iums_system.databinding.ActivityAdminProfileUpdateNoticeBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AdminProfileUpdateNoticeActivity : AppCompatActivity() {

    //setup view binding activity_pdf_add -->ActivityAdminProfileNoticeActivity
    private lateinit var binding: ActivityAdminProfileUpdateNoticeBinding

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //progress dialog (show while uploading pdf)
    private lateinit var progressDialog: ProgressDialog

    //arraylist to hold up pdf catagories
    //private lateinit var catagoryArrayList: ArrayList

    //uri of picked pdf
    private var pdfUri: Uri?=null

    //TAG
    private val TAG = "PDF_ADD_TAG"






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminProfileUpdateNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_admin_profile_notice)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        //------------------loadPdfCatagories()

        //setup progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        ///handle click, goback
        binding.bckbtn.setOnClickListener {
            onBackPressed()
        }
/*
        //handle click,show catagory pick dialog
        binding.catagoryPDF.setOnClickListener {
            //------catagoryPickDialog()

        }
*/
        //handle click,pick pdf intent
        binding.btn1.setOnClickListener {
            pdfPickIntent()
        }

        //handle click, start uploading pdf
        binding.btn2.setOnClickListener {
            //step1: validate data
            //step2: upload pdf to firebase storage
            //step3: get url of uploaded pdf
            //step4: upload pdf info to firebase db

            validateData()
        }


    }

    private var title =""
    private var description = ""
    private var catagory = ""

    private fun validateData() {
        //step1: validate data
        Log.d(TAG, "validateData: validating data")

        //get data
        title=binding.pdfTitleEt.text.toString().trim()
        description=binding.pdfDescripEt.text.toString().trim()
        //catagory =binding.catagoryPDF.text.toString().trim()

        //validate data
        if(title.isEmpty()){
            Toast.makeText(this,"Enter Title", Toast.LENGTH_SHORT).show()
        }
        else if(description.isEmpty()){
            Toast.makeText(this,"Enter Description", Toast.LENGTH_SHORT).show()
        }
        /*else if(catagory.isEmpty()){
            Toast.makeText(this,"Pick catagory", Toast.LENGTH_SHORT).show()
        }*/
        else if(pdfUri == null){
            Toast.makeText(this,"Pick pdf", Toast.LENGTH_SHORT).show()
        }
        else{
            //data validated, begin upload
            uploadPDFtoStorage()
        }

        //step2: upload pdf to firebase storage
        //step3: get url of uploaded pdf
        //step4: upload pdf info to firebase db
    }

    private fun uploadPDFtoStorage() {
        //step2: upload pdf to firebase storage
        Log.d(TAG, "uploadPDFtoStorage: uploading to storage")

        //show progress dialog
        progressDialog.setMessage("uploading pdf")
        progressDialog.show()

        //timestamp
        val timestamp = System.currentTimeMillis()

        //path of pdf in firebase storage
        val filePathAndName = "Books/$timestamp"

        //storage reference
        val storageReference = FirebaseStorage.getInstance().getReference(filePathAndName)
        storageReference.putFile(pdfUri!!).addOnSuccessListener {taskSnapshot->
            Log.d(TAG,"uploadPdftoStorage: PDF uploaded now, getting url")

            //step3: get url of uploaded pdf
            val uriTask: Task<Uri> = taskSnapshot.storage.downloadUrl
            while(!uriTask.isSuccessful);
            val uploadedPdfUrl = "${uriTask.result}"

            uploadPdfInfoToDb(uploadedPdfUrl,timestamp)

        }.addOnFailureListener{e->
            Log.d(TAG, "uploadPdfToStorage: failed to upload due to ${e.message}")
            progressDialog.dismiss()
            Toast.makeText(this,"Failed to upload due to ${e.message}", Toast.LENGTH_SHORT).show()

        }

    }

    private fun uploadPdfInfoToDb(uploadedPdfUrl: String, timestamp: Long) {
        //step4:upload pdf info to firebase db
        Log.d(TAG, "uploadPdfInfoToDb: uploading to db")
        progressDialog.setMessage("uploading pdf info")

        //uid of current user
        val uid = firebaseAuth.uid

        //setup data to upload
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap["uid"] = "$uid"
        hashMap["id"] = "$timestamp"
        hashMap["title"] = "$title"
        hashMap["description"] = "$description"
        //hashMap["catagoryId"] = "$selectedCatagoryId"
        hashMap["url"] = "$uploadedPdfUrl"
        hashMap["timestamp"] = timestamp
        //hashMap["viewsCount"] = 0
        //hashMap["downloadsCount"] = 0

        //db reference DB > Books > BookId > (Book Info)
        val ref = FirebaseDatabase.getInstance().getReference("Books")
        ref.child("$timestamp")
            .setValue(hashMap)
            .addOnSuccessListener {
                Log.d(TAG, "uploadPdfintoDb:  uploaded to db")
                progressDialog.dismiss()
                Toast.makeText(this,"uploaded",Toast.LENGTH_SHORT).show()
                pdfUri = null
            }
            .addOnFailureListener{e->
                Log.d(TAG, "uploadPdfintoDb: failed to upload due to ${e.message}")
                progressDialog.dismiss()
                Toast.makeText(this,"Failed to upload due to ${e.message}", Toast.LENGTH_SHORT).show()

            }

    }


    /* private fun loadPdfCatagories(){
         Log.d(TAG, "loadpdfCatagories: Loading pdf catagories")

         //init arraylist
         catagoryArrayList = ArrayList()

         //db reference to load catagories DF > catagories

         val ref = FirebaseDatabase.getInstance().getReference("Catagories")
         ref.addListenerForSingleValueEvent(Object : VaueEventListener{
             ovverride fun onDataChange(snapshot: DataSnapshot){
                 //clear list before adding data
                 catagoryArrayList.clear()
                 for(ds in snapshot.children){
                     //get data
                     val model = ds.getValue(ModelCatagory::class.java)

                     //add to arrayList
                     catagoryArrayList.add(model!!)
                 }
             }
         })
     }*/

    private var selectedCatagoryId = ""
    private var selectedCatagoryTitle = ""

    /*private fun catagoryPickDialog(){
        Log.d(TAG, "catagoryPickDialog: Showing pdf catagory, pick dialog")

        //get string array of catagories from arraylist
        val catagoriesArray = arrayOfNulls<String>(catagoryArrayList.size)
        for(i in catagoryArrayList.indices){
            catagoriesArray[i] = catagoryArrayList[i].catagory
        }

        //alert dialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pick Catagory").setItems(catagoriesArray){dialog,which ->
            //handle item click
            //get clicked item
            selectedCatagoryTitle = catagoryArrayList[which].catagory
            selectedCatagoryId = catagoryArrayList[which].id

            //set catagory to textview
            binding.catagoryPDF.text = selectedCatagoryTitle

            Log.d(TAG, "catagoryPickDialog: Selected Catagory ID: $selectedCatagoryId")
            Log.d(TAG,"catagoryPickDialog: Selected Catagory Title: $selectedCatagoryTitle")
        }.show()
    }*/

    private fun pdfPickIntent(){
        Log.d(TAG, "pdfPickIntent: starting pdf pick intent")

        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT

        pdfActivityResultLauncher.launch(intent)

    }
    val pdfActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult>{ result ->
            if(result.resultCode == RESULT_OK){
                Log.d(TAG,"PDF Picked")
                pdfUri = result.data!!.data
            }
            else{
                Log.d(TAG,"PDF Pick cancelled")
                Toast.makeText(this,"cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    )

}