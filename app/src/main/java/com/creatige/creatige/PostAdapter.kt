package com.creatige.creatige

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PostAdapter(val context: Context, val posts: List<posts>) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts.get(position)
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        //TODO: Get Users profile image to display it in the imageview (may need to create user class for this)
        //TODO: Get time of creation of the post
        val ivProfileImage: ImageView
        val tvUsername: TextView
        val ivImage: ImageView
        val tvDescription: TextView

        init{
            ivProfileImage = itemView.findViewById((R.id.ivProfileImage))
            tvUsername = itemView.findViewById(R.id.author)
            ivImage = itemView.findViewById(R.id.imgPost)
            tvDescription = itemView.findViewById(R.id.postPrompt)
        }

        fun bind(post: posts){
            tvDescription.text = post.getPrompt()
            tvUsername.text = post.getUser()?.username

            Glide.with(itemView.context).load(post.getImage()?.url).into(ivImage)

            //TODO: Get users profile picture 


        }

    }

}

