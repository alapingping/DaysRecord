package com.example.daysrecord.ui.events

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.daysrecord.MainActivity
import com.example.daysrecord.R
import com.example.daysrecord.Utils.Companion.getTransformedTime
import com.example.daysrecord.database.entity.Record
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.item.view.*

class RecordAdapter(val context: Context, val data: List<Record>) :
        RecyclerView.Adapter<RecordAdapter.ViewHolder>() {


    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.record_title
        val content: TextView = view.record_content
        val time: TextView = view.record_time
        val recordLayout: MaterialCardView = view.recordLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        val holder = ViewHolder(view)
        holder.recordLayout.setOnClickListener {
            RecordDetailActivity.start(parent.context, data[holder.adapterPosition])
        }
        return holder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = data[position]
        holder.title.text = getPreviewTitle(record)
        holder.content.text = getPreviewContent(record.content)
        holder.time.text = getTransformedTime(record.time)
    }

    private fun getPreviewTitle(record: Record): String {
        val title = if (!record.title.equals("")) record.title else
            record.content.substringBefore('\n')
         return if (title.length > 8) "${title.subSequence(0, 6)}..." else title
    }

    private fun getPreviewContent(content: String):String {
        var lines = 0
        var charsPerLine = 0
        var end = 0
        for (char in content) {
            if (lines == 10) {
                break
            }
            end++
            if (charsPerLine < 10 && char != '\n') {
                charsPerLine++
                continue
            }
            if (charsPerLine == 10) {
                charsPerLine = 1
                lines++
            }
            if (char == '\n') {
                lines++
                charsPerLine = 0
            }
        }
        return if (lines == 10) content.substring(0, end - 1) else content
    }



}