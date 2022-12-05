package com.creatige.creatige.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creatige.creatige.R
import com.creatige.creatige.posts

class ProfilePostAdapter(val context: Context, val posts: List<posts>) : RecyclerView.Adapter<ProfilePostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_profile_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        //TODO: Get Users profile image to display it in the imageview (may need to create user class for this)
        //TODO: Get time of creation of the post
        val ivImage: ImageView

        init{
            ivImage = itemView.findViewById(R.id.imgPost)
        }
        fun bind(post: posts){
            Glide.with(itemView.context).load(post.getImage()?.url).into(ivImage)
        }

    }

}

