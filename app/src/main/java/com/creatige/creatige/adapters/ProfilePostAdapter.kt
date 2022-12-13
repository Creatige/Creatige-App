package com.creatige.creatige.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creatige.creatige.R
import com.creatige.creatige.activities.DetailActivity
import com.creatige.creatige.models.posts
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseQuery


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

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        //TODO: Get Users profile image to display it in the imageview (may need to create user class for this)
        //TODO: Get time of creation of the post
        val ivImage: ImageView

        init{
            ivImage = itemView.findViewById(R.id.imgPost)
        }

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener {
                val alertbox = AlertDialog.Builder(context)
                alertbox.setMessage("Are you sure you want to delete?")
                alertbox.setNegativeButton("Cancel"){ dialogInterface: DialogInterface, i: Int ->

                }
                alertbox.setPositiveButton(
                    "Delete"
                ) { arg0, arg1 ->
                    val query = ParseQuery.getQuery<ParseObject>("posts")
                    query.getInBackground(
                        posts[adapterPosition].objectId.toString()
                    ) { `object`: ParseObject, e: ParseException? ->
                        if (e == null) {
                            //Object was fetched
                            //Deletes the fetched ParseObject from the database
                            `object`.deleteInBackground { e2: ParseException? ->
                                if (e2 == null) {
                                    Toast.makeText(context, "Delete Successful", Toast.LENGTH_SHORT)
                                        .show()
                                } else {
                                    //Something went wrong while deleting the Object
                                    Toast.makeText(context, "Error: " + e2.message, Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        } else {
                            //Something went wrong
                            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                alertbox.show()



                return@setOnLongClickListener true
            }
        }

        fun bind(post: posts){
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

    }

}

