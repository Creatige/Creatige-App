package com.creatige.creatige.fragments

import android.os.Bundle
import android.os.TestLooperManager
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creatige.creatige.CommentAdapter
import com.creatige.creatige.Post_Extra
import com.creatige.creatige.R
import com.creatige.creatige.comments
import com.creatige.creatige.posts
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser


private const val TAG = "DetailActivity"
class Detail : AppCompatActivity() {

    private lateinit var ivAuthorImage: ImageView
    private lateinit var author: TextView
    private lateinit var imgPost: ImageView
    private lateinit var commentRecyclerView: RecyclerView
    private lateinit var adapter: CommentAdapter
    private lateinit var submitComment:ImageButton
    private lateinit var composedComment:TextView
    private lateinit var userProfilePicture: ImageView
    private var allComments: MutableList<comments> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        ivAuthorImage = findViewById(R.id.ivProfileImage)
        author = findViewById(R.id.author)
        imgPost = findViewById(R.id.imgPost)
        submitComment = findViewById<ImageButton>(R.id.submitComment)
        composedComment = findViewById(R.id.et_composeComment)
        userProfilePicture = findViewById<ImageView>(R.id.userProfilePicture)


        val Post = intent.getParcelableExtra<posts>(Post_Extra) as posts
        val postId = intent.getParcelableExtra<ParseObject>("postId").toString()
        Glide.with(this).load(Post.getImage()?.url).into(imgPost)
        author.text = Post.getUser()?.username

        if (postId != null) {
            queryComments(postId)
        }

        commentRecyclerView = findViewById<RecyclerView>(R.id.recycler)
        adapter = CommentAdapter(this, allComments)
        commentRecyclerView.adapter = adapter

        submitComment.setOnClickListener(){
            if (postId != null) {
                submitComment(composedComment.text.toString(), ParseUser.getCurrentUser(),postId)
            }
            if (postId != null) {
                queryComments(postId)
            }
        }


    }

    fun queryComments(postId: String){
        val query: ParseQuery<comments> = ParseQuery.getQuery(comments::class.java)
        //query.whereEqualTo("objectId", postId)
        query.whereEqualTo("post_id", postId)
        query.findInBackground { comments, e ->
            if (e != null) {
                //something went wrong
                Log.e(TAG, "Error fetching comments")
            } else {

                if (comments != null) {
                    Log.e(TAG, "$comments")
                    for (com in comments) {
                        val comment = com.getComment()
                        Log.i(TAG, "comment is $comment")
                        allComments.clear()
                        allComments.addAll(comments)
                        adapter.notifyDataSetChanged()

                    }
                }
            }
        }
    }

    fun submitComment(entry: String, user:ParseUser, postId: String){
        var comment2 =ParseObject("comments")
        comment2.put("post_id",postId)
        comment2.put("comment",entry)
        comment2.put("user_id",ParseUser.getCurrentUser())

        var comment = comments()
        comment.setUser(user)
        comment.setComment(entry)
        comment.put("post_id",ParseObject(postId))
        comment2.saveInBackground{ exception ->
            if(exception != null){
                Log.e(TAG, "Error while saving comment")
                exception.printStackTrace()
                Toast.makeText(this, "Error while saving comment", Toast.LENGTH_SHORT).show()
            } else {
                Log.i(TAG, "Successfully saved comment")
                queryComments(postId)
            }
        }
    }

    companion object {
        const val TAG = "DetailView"
    }

}

