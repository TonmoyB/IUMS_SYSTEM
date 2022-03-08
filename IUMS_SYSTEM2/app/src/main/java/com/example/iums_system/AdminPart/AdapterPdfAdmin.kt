package com.example.iums_system.AdminPart

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.iums_system.databinding.RowPdfAdminBinding

class AdapterPdfAdmin :RecyclerView.Adapter<AdapterPdfAdmin.HolderPdfAdmin>, Filterable {

    //context
    private var context: Context

    //arraylist to hold pdfs
    public var pdfArrayList: ArrayList<ModelPdf>
    private val filterList:ArrayList<ModelPdf>

    //viewBinding
    private lateinit var binding: RowPdfAdminBinding

    //filter object
    var filter: FilterPdfAdmin?=null

    //constructor
    constructor(context: Context, pdfArrayList: ArrayList<ModelPdf>) : super() {
        this.context = context
        this.pdfArrayList = pdfArrayList
        this.filterList = pdfArrayList
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderPdfAdmin {
        //bind inflate layout row_pdf_admin.xml
        binding = RowPdfAdminBinding.inflate(LayoutInflater.from(context), parent,false)
        return HolderPdfAdmin(binding.root)
    }

    override fun onBindViewHolder(holder: HolderPdfAdmin, position: Int) {
        //get data, set data, handle click etc

        //get data
        val model = pdfArrayList[position]
        val pdfId = model.id
        //val catagoryId = model.catagoryId
        val title = model.title
        val description = model.description
        val pdfUrl = model.url
        val timestamp = model.timestamp
        //convert timestamp to dd/MM/yyyy
        val formattedDate = MyApplication.formatTimeStamp(timestamp)

        //set data
        holder.titleBar.text = title
        holder.descriptionBar.text = description
        holder.dateBar.text = formattedDate

        //load further data like catagory, pdf from url etc
        //MyApplication.loadCatagory(catagoryId, holder.noticeBar)

        //we don't need page number here, pas null for page number
        MyApplication.loadPdfFromUrlSinglePage(pdfUrl,title,holder.pdfView,holder.progressBar,null)

        //load pdf size
        MyApplication.loadPdfSize(pdfUrl,title,holder.sizeBar)

        //handle click, show dialog with options 1) Edit book, 2)Delete Book
        holder.morebtn.setOnClickListener{
            moreOptionsDialog(model,holder)
        }
        //lets create an application class that will contain the functions that will be used multiple places


        //handle item click, open pdfDetail activity
        holder.itemView.setOnClickListener {
            //intent with bookId
            val intent = Intent(context,AdminProfileNoticeDetailActivity::class.java)
            intent.putExtra("bookId",pdfId)
            context.startActivity(intent)
        }

    }

    private fun moreOptionsDialog(model: ModelPdf, holder: AdapterPdfAdmin.HolderPdfAdmin) {
        //get id, url, title of book
        val bookId = model.id
        val bookUrl = model.url
        val bookTitle =model.title

        //options to show in dialog
        val options = arrayOf("No", "Yes")

        //alert dialog
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Do you want to Delete the book?")
            .setItems(options){dialog, position->
                //handle item click
                if(position==0){
                    //No clicked
                    dialog.cancel()

                }
                else if (position == 1){
                    //delete clicked, the delete will be handled in MyApplication
                    MyApplication.deleteBook(context,bookId,bookUrl,bookTitle)
                }
            }
            .show()
    }

    override fun getItemCount(): Int {
        return pdfArrayList.size    //item count
    }



    override fun getFilter(): Filter {
        if(filter == null){
            filter = FilterPdfAdmin(filterList,this)
        }
        return filter as FilterPdfAdmin

    }

    //view holder class for row_pdf_admin
    inner class HolderPdfAdmin(itemView: View) :RecyclerView.ViewHolder(itemView){
        //UI Views of row_pdf_admin.xml
        val pdfView = binding.pdfView
        val progressBar = binding.progressBar
        val titleBar = binding.titleBar
        val descriptionBar = binding.descriptionBar
        val noticeBar = binding.noticeBar
        val sizeBar = binding.sizeBar
        val dateBar = binding.dateBar
        val morebtn = binding.morebtn

    }

}