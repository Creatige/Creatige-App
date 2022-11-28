package com.creatige.creatige.models

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.creatige.creatige.Post_Extra
import com.creatige.creatige.R
import com.creatige.creatige.posts
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery


private const val TAG = "DetailActivity"
class Detail : AppCompatActivity() {

    private lateinit var ivProfileImage: ImageView
    private lateinit var author: TextView
    private lateinit var imgPost: ImageView


    fun queryPosts(postId: String) {

        val query: ParseQuery<posts> = ParseQuery.getQuery(posts::class.java)
        query.include(postId)
        query.findInBackground(object : FindCallback<posts> {
            override fun done(posts: MutableList<posts>?, e: ParseException?) {
                if (e != null){
                    //something went wrong
                    Log.e(TAG,"Error fetching post")
                } else {
                    if (posts != null) {
                        for (post in posts) {
                        var Prompt = post.getPrompt()
                        Log.i(TAG, "post is $Prompt")
                        }

                    }
                }
            }


        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        ivProfileImage = findViewById(R.id.ivProfileImage)
        author = findViewById(R.id.author)
        imgPost = findViewById(R.id.imgPost)


        val Post = intent.getParcelableExtra<posts>(Post_Extra) as posts
        Glide.with(this).load(Post.getImage()?.url).into(imgPost)
        author.text = Post.getUser()?.username


    }
}