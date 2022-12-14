package com.creatige.creatige.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creatige.creatige.R
import com.creatige.creatige.TimeFormatter
import com.creatige.creatige.models.comments
import com.creatige.creatige.models.posts
import com.parse.*


private const val TAG = "CommentAdapter"


class CommentAdapter(val context: Context, val comments: List<comments>, val post: posts) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.e(PostAdapter.TAG, "onCreateViewHolder")
        val view = LayoutInflater.from(context).inflate(R.layout.comment, parent, false)

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
        val timeOfCreation: TextView
        val optionsMenu : ImageButton



        init {
            ivProfilePicture = itemView.findViewById(R.id.ivProfileImage)
            tvUser = itemView.findViewById(R.id.commenter)
            tvComment = itemView.findViewById(R.id.comments)
            timeOfCreation = itemView.findViewById(R.id.createdAt)
            optionsMenu = itemView.findViewById(R.id.ib_commentOptions)
        }

        fun bind(comment: comments) {
            tvUser.text = comment.getUser()?.fetchIfNeeded()?.username
            tvComment.text = comment.getComment()
            timeOfCreation.text = TimeFormatter.getTimeDifference(comment.getTime())

            var profile: ParseFile = comment.getUser()?.get("avatar") as ParseFile
            Glide.with(itemView.context).load(profile.url).into(ivProfilePicture)
            if(post.getUser()?.username == ParseUser.getCurrentUser().username){
                Log.e(TAG, "Your own post")
                optionsMenu.visibility = View.VISIBLE
                menuClickListener()
            } else{
                Log.e(TAG, "Not your post")

                if (tvUser.text == ParseUser.getCurrentUser().username) {
                    optionsMenu.visibility = View.VISIBLE
                    menuClickListener()
                } else {
                    Log.e(TAG, "${tvUser.text} should not see the options")
                }
            }
        }
        private fun menuClickListener(){
            optionsMenu.setOnClickListener{
                popupMenus(it, comments)
            }
        }
        private fun popupMenus(it: View?, comment: List<comments>) {
            val popupMenus = PopupMenu(context,it)
            var comment = comments[adapterPosition]
            //if this post is your own post, you will have the ability to delete any and all comments
                popupMenus.inflate(R.menu.menu_post_options)
                popupMenus.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.delete ->{
                            Log.e(PostAdapter.TAG, "Delete was pressed")
                            deleteComment(comment)
                            true
                        }
                        else -> true
                    }
                }
                popupMenus.show()

        }
    }

    private fun deleteComment(comment: comments) {
        //TODO: add logic to delete comments here
        val query = ParseQuery.getQuery<ParseObject>("comments")

        query.getInBackground(comment.objectId) { `object`: ParseObject, e: ParseException? ->
            if (e == null) {
                //Object was fetched
                //Deletes the fetched ParseObject from the database
                `object`.deleteInBackground { e2: ParseException? ->
                    if (e2 == null) {
                        Toast.makeText(context, "Delete Successful", Toast.LENGTH_SHORT).show()
                    } else {
                        //Something went wrong while deleting the Object
                        Toast.makeText(context, "Error: " + e2.message, Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                //Something went wrong
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }

    }


    companion object{
        val TAG = "CommentAdapter"
    }
}


