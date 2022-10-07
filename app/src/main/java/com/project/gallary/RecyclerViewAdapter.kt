package com.project.gallary

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.io.File

class RecyclerViewAdapter(
    private val imagePathArrayList: ArrayList<String>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imagePathArrayList.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val imgFile: File = File(imagePathArrayList[position])
        if (imgFile.exists()) {
            Picasso.get().load(imgFile).placeholder(R.drawable.ic_launcher_background).into(holder.imageIV)
            holder.itemView.setOnClickListener{
                it.setOnClickListener {
                    val i = Intent(context, ImageDetailActivity::class.java)
                    i.putExtra("imgPath", imagePathArrayList[position])
                    context.startActivity(i)
                }
            }
        }
    }

    class RecyclerViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageIV: ImageView = itemView.findViewById(R.id.idIVImage)
    }
}