package com.example.daysrecord.ui.message

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.daysrecord.R
import com.example.daysrecord.Utils.Companion.showToast
import com.example.daysrecord.logic.model.Comment
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentAdapter(val context: Context, val data: List<Comment>) :
    ListAdapter<Comment, CommentAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val commentText = itemView.comment_text
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val builder: SpannableStringBuilder = SpannableStringBuilder()
        val author: SpannableString = SpannableString(currentList[position].author)
        val content: SpannableString = SpannableString(currentList[position].content)
        builder.append(author)
        builder.append(": ")
        builder.append(content)
        builder.setSpan(ForegroundColorSpan(context.resources.getColor(R.color.name)), 0, author.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
//        builder.setSpan(object : ClickableSpan() {
//            override fun onClick(widget: View) {
//                "author is clicked".showToast()
//            }
//
//        }, 0, author.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        builder.setSpan(ForegroundColorSpan(Color.BLACK), author.length + 2, builder.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        holder.commentText.text = builder
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem == newItem
            }
        }
    }

}