package com.example.daysrecord.ui.events

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.daysrecord.R
import com.example.daysrecord.database.entity.Record
import com.example.daysrecord.ui.detail.RecordDetailActivity
import kotlinx.android.synthetic.main.item.view.*

class RecordAdapter(val context: Context, val data: List<Record>) :
        RecyclerView.Adapter<RecordAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val image = view.RecordImage
        val title = view.RecordTitle
        val recordLayout = view.recordLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        val holder = ViewHolder(view)
        holder.recordLayout.setOnClickListener {
            RecordDetailActivity.start(parent.context, data[holder.adapterPosition], holder.adapterPosition)
        }
        return holder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = data[position]
//        holder.image.setImageResource(R.drawable.ic_launcher_background)
        holder.title.text = record.title
        Glide.with(context).load(record.pictureId).into(holder.image)
    }

}