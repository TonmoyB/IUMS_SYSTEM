package com.example.iums_system.StudentPart

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.iums_system.AdminPart.ModelPdf
import com.example.iums_system.AdminPart.MyApplication
import com.example.iums_system.databinding.RowPdfStudentBinding

class AdapterPdfStudent : RecyclerView.Adapter<AdapterPdfStudent.HolderPdfStudent> {

    //context, get using constructor
    private var context: Context

    //arrayList to hold pdfs, get using constructor
    private var pdfArrayList: ArrayList<ModelPdf>

    //viewBinding row pdf user xml
    private lateinit var binding: RowPdfStudentBinding

    constructor(context: Context, pdfArrayList: ArrayList<ModelPdf>) {
        this.context = context
        this.pdfArrayList = pdfArrayList
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderPdfStudent {
        binding = RowPdfStudentBinding.inflate(LayoutInflater.from(context),parent,false)

        return HolderPdfStudent(binding.root)
    }

    override fun onBindViewHolder(holder: HolderPdfStudent, position: Int) {
        //get data
        val model = pdfArrayList[position]
        val bookId = model.id
        //catagory?
        val title = model.title
        val description = model.description
        val uid = model.uid
        val url = model.url
        val timestamp = model.timestamp
        //convert time
        val date = MyApplication.formatTimeStamp(timestamp)

        //set data
        holder.titleBar.text = title
        holder.descriptionBar.text = description
        holder.dateBar.text = date

        MyApplication.loadPdfFromUrlSinglePage(url, title, holder.pdfView, holder.progressBar,null)//no need number of pages

        //MyApplication.loadCatagory?

        MyApplication.loadPdfSize(url,title,holder.sizeBar)

        //handle click
        holder.itemView.setOnClickListener {
            val intent = Intent(context, StudentProfileViewNoticeActivity::class.java)
            intent.putExtra("bookId",bookId)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return pdfArrayList.size
    }



    //view holder class row pdf student
    inner class HolderPdfStudent(itemView: View): RecyclerView.ViewHolder(itemView){
        //init UI components from row pdf student xml
        var pdfView = binding.pdfView
        var progressBar = binding.progressBar
        var titleBar = binding.titleBar
        var descriptionBar = binding.descriptionBar
        var noticeBar = binding.noticeBar
        //catagory?
        var sizeBar = binding.sizeBar
        var dateBar = binding.dateBar



    }
}