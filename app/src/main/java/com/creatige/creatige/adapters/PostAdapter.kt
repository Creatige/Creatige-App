package com.creatige.creatige.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creatige.creatige.R
import com.creatige.creatige.TimeFormatter
import com.creatige.creatige.activities.DetailActivity
import com.creatige.creatige.models.posts
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser


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

        fun bind(post: posts){

            var textToSplit = post.getPrompt()
            var delimeter = "###"
            val parts = textToSplit?.split(delimeter)

            tvDescription.text = parts?.get(0)
            tvUsername.text = post.getUser()?.fetchIfNeeded()?.username
            tvCreatedAT.text = TimeFormatter.getTimeDifference(post.getTime())
            
            val profile :ParseFile = post.getUser()?.get("avatar") as ParseFile
            Log.i(TAG, "Tvcreated is ${post.getTime()}")
            Glide.with(itemView.context).load(profile.url).into(ivProfileImg)
            Glide.with(itemView.context).load(post.getImage()?.url).into(ivImage)
        }

        override fun onClick(v: View?) {
            val post = posts[adapterPosition]
            val intent = Intent(context, DetailActivity::class.java)
            val postID = post.objectId
            intent.putExtra("postID", postID)
            intent.putExtra(Post_Extra, post)
            context.startActivity(intent)
        }

        init {
            itemView.setOnClickListener(this)
        }

    }

    companion object{
        val TAG = "PostAdapter"
    }
}

