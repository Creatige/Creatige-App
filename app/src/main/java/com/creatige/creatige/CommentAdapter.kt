package com.creatige.creatige

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.creatige.creatige.comments

private const val TAG = "CommentAdapter"


class CommentAdapter(val context: Context, val comments: List<comments>) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentAdapter.ViewHolder, position: Int) {
        val comments = comments.get(position)
        holder.bind(comments)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val ivProfilePicture: ImageView
        val tvUser: TextView
        val tvComment: TextView


        init {
            ivProfilePicture = itemView.findViewById(R.id.ivProfileImage)
            tvUser = itemView.findViewById(R.id.author)
            tvComment = itemView.findViewById(R.id.comments)
        }


        fun bind(comment: comments) {
            tvUser.text = comment.getUser()
            tvComment.text = comment.getComment()
        }
    }
}

