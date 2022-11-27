package com.creatige.creatige.models

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.creatige.creatige.Post_Extra
import com.creatige.creatige.R
import com.creatige.creatige.posts
import com.parse.ParseObject


private const val TAG = "DetailActivity"
class Detail : AppCompatActivity() {

    private lateinit var ivProfileImage: ImageView
    private lateinit var author: TextView
    private lateinit var imgPost: ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        ivProfileImage = findViewById(R.id.ivProfileImage)
        author = findViewById(R.id.author)
        imgPost = findViewById(R.id.imgPost)


        val Posts = intent.getParcelableExtra<posts>(Post_Extra) as posts

        val prompt = posts.toString()
        Log.i(TAG, "Post is $prompt")

        Log.i(TAG, "Post is $Posts")

        author.text = posts.KEY_USER


    }
}