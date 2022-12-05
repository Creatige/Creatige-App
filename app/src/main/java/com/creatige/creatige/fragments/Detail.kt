package com.creatige.creatige.fragments

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creatige.creatige.*
import com.creatige.creatige.R
import com.parse.*


private const val TAG = "DetailActivity"
class Detail : AppCompatActivity() {

    private lateinit var ivProfileImage: ImageView
    private lateinit var author: TextView
    private lateinit var imgPost: ImageView
    private lateinit var commentRecyclerView: RecyclerView
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var submitComment:ImageButton
    private lateinit var composedComment:TextView
    private lateinit var userProfilePicture: ImageView
    private var allComments: MutableList<comments> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        ivProfileImage = findViewById(R.id.ivProfileImage)
        author = findViewById(R.id.author)
        imgPost = findViewById(R.id.imgPost)
        submitComment = findViewById<ImageButton>(R.id.submitComment)
        composedComment = findViewById(R.id.et_composeComment)
        userProfilePicture = findViewById<ImageView>(R.id.userProfilePicture)


        val Post = intent.getParcelableExtra<posts>(Post_Extra) as posts
        //val profile = intent.getParcelableExtra<ParseFile>("profile") as ParseFile
        val profile :ParseFile = Post.getUser()?.get("avatar") as ParseFile
        commentRecyclerView.layoutManager = LinearLayoutManager(this)


        Glide.with(this).load(Post.getImage()?.url).into(imgPost)
        author.text = Post.getUser()?.username
        Glide.with(this).load(profile.url).into(ivProfileImage)

        commentRecyclerView = findViewById<RecyclerView>(R.id.commentRecyclerView)
        commentAdapter = CommentAdapter(this, allComments)
        commentRecyclerView.adapter = commentAdapter


        queryComments(Post)

        submitComment.setOnClickListener(){
            submitComment(composedComment.text.toString(), ParseUser.getCurrentUser(), Post)
            queryComments(Post)
        }
    }


    fun queryComments(Post:posts){
        val query: ParseQuery<comments> = ParseQuery.getQuery(comments::class.java)
        query.whereEqualTo("post_id", Post)
        query.findInBackground { comments, e ->
            if (e != null) {
                //something went wrong
                Log.e(TAG, "Error fetching comments")
            } else {

                if (comments != null) {
                    for (com in comments) {
                        val comment = com.getComment()
                        Log.i(TAG, "comment is: $comment")
                    }
                    allComments.clear()
                    allComments.addAll(comments)
                    Log.e(TAG, "Allcomments during the query: $allComments")
                    commentAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    fun submitComment(entry: String, user:ParseUser, Post : posts){
        var comment = comments()
        comment.setUser(user)
        comment.setComment(entry)
        comment.put("post_id", Post)

        comment.saveInBackground{ exception ->
            if(exception != null){
                Log.e(TAG, "Error while saving comment")
                exception.printStackTrace()
                Toast.makeText(this, "Error while saving comment", Toast.LENGTH_SHORT).show()
            } else {
                Log.i(TAG, "Successfully saved comment")
                queryComments(Post)
            }
        }
    }

    companion object {
        const val TAG = "DetailView"
    }

}

