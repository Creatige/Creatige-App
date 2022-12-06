package com.creatige.creatige

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creatige.creatige.fragments.Detail
import com.parse.ParseFile


const val Post_Extra = "Post_Extra"

class PostAdapter(val context: Context, val posts: ArrayList<posts>) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

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

    fun clear() {
        posts.clear()
        notifyDataSetChanged()
    }

    // Add a list of items -- change to type used
    fun addAll(tweetList: List<posts>) {
        posts.addAll(tweetList)
        notifyDataSetChanged()
    }

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        //TODO: Get Users profile image to display it in the imageview (may need to create user class for this)
        val ivProfileImage: ImageView
        val tvUsername: TextView
        val ivImage: ImageView
        val tvDescription: TextView
        val tvCreatedAT: TextView
        val ivProfileImg: ImageView

        init{
            ivProfileImage = itemView.findViewById((R.id.ivProfileImage))
            tvUsername = itemView.findViewById(R.id.author)
            ivImage = itemView.findViewById(R.id.imgPost)
            ivProfileImg = itemView.findViewById(R.id.ivProfileImage)
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

            val profile :ParseFile = post.getUser()?.get("avatar") as ParseFile
            Log.i(TAG, "Tvcreated is ${post.getTime()}")

            Glide.with(itemView.context).load(profile.url).into(ivProfileImg)
            Glide.with(itemView.context).load(post.getImage()?.url).into(ivImage)


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

