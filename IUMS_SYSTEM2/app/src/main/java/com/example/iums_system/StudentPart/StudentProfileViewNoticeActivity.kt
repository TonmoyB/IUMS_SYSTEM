package com.example.iums_system.StudentPart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.iums_system.AdminPart.AdminProfileNoticeDetailActivity
import com.example.iums_system.AdminPart.Constants
import com.example.iums_system.R
import com.example.iums_system.databinding.ActivityStudentProfileViewNoticeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class StudentProfileViewNoticeActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityStudentProfileViewNoticeBinding

    //book id
    var bookId = ""

    private companion object{
        const val TAG = "PDF_VIEW_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStudentProfileViewNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get bookId from previous intent using putExtra
        bookId = intent.getStringExtra("bookId")!!
        loadBook()

        binding.bckbtn.setOnClickListener {
            onBackPressed()
        }

    }

    private fun loadBook() {
        Log.d(TAG, "loadBookDetails: Get pdf from URL from db")

        //database reference to get book details eg get book url using book id
        //step1 get book url using book id
        val ref = FirebaseDatabase.getInstance().getReference("Books")
        ref.child(bookId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //getbookURL
                    val pdfUrl = snapshot.child("url").value
                    val pdfName = snapshot.child("title").value

                    binding.titleView.text = "$pdfName"

                    Log.d(TAG,"onDataChange: PDF_URL:$pdfUrl")

                    //step2 load pdf using url from firebase storage
                    //loadingBookFromURL("$pdfUrl")
                    showBook("$pdfUrl")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun showBook(pdfUrl: String) {
        Log.d(TAG,"LoadBookFromUrl: Get pdf from firebase storage using URL")

        val reference = FirebaseStorage.getInstance().getReferenceFromUrl(pdfUrl)
        reference.getBytes(Constants.MAX_BYTES_PDF)
            .addOnSuccessListener {bytes ->
                Log.d(TAG,"LoadBookFromUrl: pdf got URL")

                //load pdf
                binding.pdfView.fromBytes(bytes)
                    .swipeHorizontal(false) //set false to scroll vertical, set true to scroll horizontal
                    .onPageChange { page, pageCount ->
                        val currentPage = page+1
                        binding.subtitleView.text = "Page $currentPage of $pageCount"
                        Log.d(TAG,"LoadBookFromUrl: Page $currentPage of $pageCount")
                    }
                    .onError{t->
                        Log.d(TAG,"LoadBookFromUrl: ${t.message}")
                    }
                    .onPageError{page,t->
                        Log.d(TAG,"LoadBookFromUrl: $page, ${t.message}")
                    }
                    .load()
                binding.progressBarr.visibility = View.GONE
            }
            .addOnFailureListener { e->
                Log.d(TAG,"LoadBookFromUrl: failed to get URL due to ${e.message}")
                binding.progressBarr.visibility = View.GONE
            }
    }

}
