package com.example.iums_system.AdminPart

import android.app.Application
import android.app.ProgressDialog
import android.content.Context
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.github.barteksc.pdfviewer.PDFView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storageMetadata
import java.sql.Timestamp
import java.util.*

class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
    }

    companion object{

        //created a static method to convert timestamp to proper date format, so we can use it everywhere in project, no need to rewrite again
        fun formatTimeStamp(timestamp: Long) : String{
            val cal = Calendar.getInstance(Locale.ENGLISH)
            cal.timeInMillis = timestamp

            //format dd/MM/yyyy
            return DateFormat.format("dd/MM/yyyy",cal).toString()
        }

        //function to get pdf size
        fun loadPdfSize(pdfUrl: String, pdfTitle: String, sizeBar: TextView){
            val TAG = "PDF_SIZE_TAG"

            //using url we can get file and its metadata from firebase storage
            val ref = FirebaseStorage.getInstance().getReferenceFromUrl(pdfUrl)
            ref.metadata
                .addOnSuccessListener { storageMetadata ->
                    Log.d(TAG,"LoadPdfSize:got metadata")
                    val bytes = storageMetadata.sizeBytes.toDouble()

                    //convert bytes to KB/MB
                    val kb = bytes/1024
                    val mb = kb/1024

                    if(mb>=1){
                        sizeBar.text = "${String.format("%.2f",mb)} MB"
                    }
                    else if(kb>=1){
                        sizeBar.text = "${String.format("%.2f",kb)} KB"
                    }
                    else{
                        sizeBar.text = "${String.format("%.2f",bytes)} bytes"
                    }

                 }
                .addOnFailureListener{e->
                    //failed to get metadata
                    Log.d(TAG,"LoadPdfSize: Failed to get metadata due to ${e.message}")
                }
        }
        /*instead of making new function loadPdfPageCount() to just load pages count it would be more good to use some existing function to do that
        * i.e. loadPdfFromUrlSinglePage
        * we will add another parameter of type textview e.g.pageBar
        * whenever we call that function
        *   1)if we require page number we will pass pagesBar (TextView)
        *   2)if we don't require page number we will pass null
        * and in function if pagesBar (TextView) parameter is not null we will set the page number count
        * */

        fun loadPdfFromUrlSinglePage(pdfUrl: String,
                                     pdfTitle: String,
                                     pdfView: PDFView,
                                     progressBar: ProgressBar,
                                     pagesBar: TextView?){
            val TAG = "PDF_THUMBNAIL_TAG"

            //using url we can get file and its metadata from firebase storage
            val ref = FirebaseStorage.getInstance().getReferenceFromUrl(pdfUrl)
            ref.getBytes(Constants.MAX_BYTES_PDF)
                .addOnSuccessListener { bytes ->
                    Log.d(TAG,"LoadPdfSize:Size bytes $bytes ")

                    //SET TO PDFVIEW
                    pdfView.fromBytes(bytes)
                        .pages(0)//show first page only
                        .spacing(0)
                        .swipeHorizontal(false)
                        .enableSwipe(false)
                        .onError {t->
                            progressBar.visibility = View.INVISIBLE
                            Log.d(TAG, "loadPdfFromUrlSinglePage: ${t.message}")
                        }
                        .onPageError { page, t->
                            progressBar.visibility = View.INVISIBLE
                            Log.d(TAG, "loadPdfFromUrlSinglePage: ${t.message}")
                        }
                        .onLoad { nbPages ->
                            Log.d(TAG, "loadPdfFromUrlSinglePage: Pages: $nbPages")
                            //pdf loaded, we can set page count, pdf thumbnail
                            progressBar.visibility = View.INVISIBLE

                            //if pagesBar param is not null then set page numbers
                            if(pagesBar != null){
                                pagesBar.text = "$nbPages"
                            }
                        }
                        .load()

                }
                .addOnFailureListener{e->
                    //failed to get metadata
                    Log.d(TAG,"LoadPdfSize: Failed to get metadata due to ${e.message}")
                }

        }

        /*
    fun loadCatagory(catagoryId: String, catagoryBar:TextView){
        //load catagory using catagory id from firebase
        val ref= FirebaseDatabase.getInstance().getReference("Catagories")
        ref.child(catagoryId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //get catagory
                    val catagory:String = "${snapshot.child("catagory").value}"

                    //set catagory
                    catagoryBar.text = catagory

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }*/


        fun deleteBook(context: Context, bookId:String, bookUrl:String, bookTitle:String){
            //param details
            //1) context, used when require e.g. for progress dialog, toast
            //2) bookId, to delete book from firebase db
            //3) bookUrl, delete book from firebase storage
            //4) bookTitle, show in dialog etc.
            val TAG = "DELETE_BOOK_TAG"

            Log.d(TAG,"deletebook: deleting..")

            //progress dialog
            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Please Wait")
            progressDialog.setMessage("Deleting $bookTitle..")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            Log.d(TAG,"deletebook: deleting from storage..")
            val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(bookUrl)
            storageReference.delete()
                .addOnSuccessListener {
                    Log.d(TAG,"deletebook: deleted from storage")
                    Log.d(TAG,"deletebook: deleting from DB now..")

                    val ref = FirebaseDatabase.getInstance().getReference("Books")
                    ref.child(bookId)
                        .removeValue()
                        .addOnSuccessListener {
                            progressDialog.dismiss()
                            Log.d(TAG,"deletebook: successfully deleted from db")
                            Toast.makeText(context, "Deleted from Database",Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e->
                            progressDialog.dismiss()
                            Log.d(TAG,"deletebook: failed to delete from db due to ${e.message}")
                            Toast.makeText(context, "failed to delete from db due to ${e.message}",Toast.LENGTH_SHORT).show()

                        }


                }
                .addOnFailureListener { e->
                    Log.d(TAG,"deletebook: failed to delete from storage due to ${e.message}..")
                    Toast.makeText(context,"Failed to delete from storage due to ${e.message}", Toast.LENGTH_SHORT).show()
                }



        }

    }


}