package com.creatige.creatige

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creatige.creatige.fragments.Detail
import com.parse.ParseObject


const val Post_Extra = "Post_Extra"

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

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        //TODO: Get Users profile image to display it in the imageview (may need to create user class for this)
        val ivProfileImage: ImageView
        val tvUsername: TextView
        val ivImage: ImageView
        val tvDescription: TextView
        val tvCreatedAT: TextView

        init{
            ivProfileImage = itemView.findViewById((R.id.ivProfileImage))
            tvUsername = itemView.findViewById(R.id.author)
            ivImage = itemView.findViewById(R.id.imgPost)
            tvDescription = itemView.findViewById(R.id.postPrompt)
            tvCreatedAT = itemView.findViewById(R.id.createdAt)
        }

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(post: posts){
            tvDescription.text = post.getPrompt()
            tvUsername.text = post.getUser()?.username
            tvCreatedAT.text = TimeFormatter.getTimeDifference(post.getTime())

            Log.i(TAG, "Tvcreated is ${post.getTime()}")

            Glide.with(itemView.context).load(post.getImage()?.url).into(ivImage)

            //TODO: Get users profile picture 


        }

        override fun onClick(v: View?) {
            val post = posts[adapterPosition]
            val intent = Intent(context, Detail::class.java)
            val postID = post.objectId
            intent.putExtra("postID", postID)
            intent.putExtra(Post_Extra, post)
            context.startActivity(intent)
        }


    }
    companion object{
        val TAG = "PostAdapter"
    }
}

