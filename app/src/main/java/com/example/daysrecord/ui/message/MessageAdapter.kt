package com.example.daysrecord.ui.message

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.daysrecord.R
import com.example.daysrecord.logic.model.Comment
import com.example.daysrecord.logic.model.Message
import kotlinx.android.synthetic.main.item_message.view.*

class MessageAdapter(val context: Context, val data: List<Message?>) :
    ListAdapter<Message ,MessageAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageAuthor = itemView.message_author
        val messageContent = itemView.message_content
        val messageTime = itemView.message_time
        val commentRecyclerView = itemView.comment_recycler
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        val holder = ViewHolder(view)
        holder.commentRecyclerView.adapter = CommentAdapter(context, ArrayList())
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        holder.commentRecyclerView.layoutManager = layoutManager
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.messageAuthor.text = currentList[position].author
        holder.messageContent.text = currentList[position].content
        holder.messageContent.setTextColor(Color.BLACK)
        holder.messageTime.text = currentList[position].time
        (holder.commentRecyclerView.adapter as ListAdapter<Comment, CommentAdapter.ViewHolder>).submitList(currentList[position].comments)
    }

    override fun onCurrentListChanged(
        previousList: MutableList<Message>,
        currentList: MutableList<Message>
    ) {
        return
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem == newItem
            }
        }
    }

}