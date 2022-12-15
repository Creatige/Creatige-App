package com.creatige.creatige.activities

import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creatige.creatige.R
import com.creatige.creatige.adapters.CommentAdapter
import com.creatige.creatige.adapters.Post_Extra
import com.creatige.creatige.models.comments
import com.creatige.creatige.models.favorites
import com.creatige.creatige.models.posts
import com.parse.*


class DetailActivity : AppCompatActivity() {
    private lateinit var ivProfileImage: ImageView
    private lateinit var author: TextView
    private lateinit var imgPost: ImageView
    private lateinit var timeOfCreation: TextView
    private lateinit var deletePost: ImageButton
    private lateinit var downloadButton: ImageButton
    private lateinit var favoritesButton: ImageButton
    private lateinit var commentRecyclerView: RecyclerView
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var submitComment:ImageButton
    private lateinit var composedComment:TextView
    private lateinit var userProfilePicture: ImageView
    private lateinit var prompt: TextView
    private lateinit var negativePrompt : TextView
    private lateinit var favoritesCount: TextView
    private lateinit var negativePromptLabel:TextView
    private lateinit var commentOptions: ImageButton
    private var allComments: MutableList<comments> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        val Post = intent.getParcelableExtra<posts>(Post_Extra) as posts
        val profile :ParseFile = Post.getUser()?.get("avatar") as ParseFile
        val currentUserPFP : ParseFile = ParseUser.getCurrentUser()?.get("avatar") as ParseFile

        ivProfileImage = findViewById(R.id.ivProfileImage)
        author = findViewById(R.id.author)
        imgPost = findViewById(R.id.imgPost)
        submitComment = findViewById<ImageButton>(R.id.submitComment)
        composedComment = findViewById(R.id.et_composeComment)
        userProfilePicture = findViewById<ImageView>(R.id.userProfilePicture)
        timeOfCreation = findViewById(R.id.tv_timeOfCreation)
        deletePost = findViewById(R.id.ib_deleteOption)
        downloadButton = findViewById(R.id.ib_download)
        favoritesButton = findViewById(R.id.ib_favorite)
        prompt = findViewById(R.id.tv_prompt)
        negativePrompt = findViewById(R.id.tv_negativePrompt)
        favoritesCount = findViewById(R.id.tv_favoritesCount)
        negativePromptLabel = findViewById(R.id.tv_negLabel)

        var textToSplit = Post.getPrompt()
        var delimeter = "###"
        val parts = textToSplit?.split(delimeter)


        Glide.with(this).load(Post.getImage()?.url).into(imgPost)
        author.text = Post.getUser()?.username
        prompt.text = parts?.get(0)
        if(parts?.size!! > 1){
            negativePrompt.text = parts.get(1)
        } else {
            negativePromptLabel.visibility = View.INVISIBLE
            negativePrompt.visibility = View.INVISIBLE
        }
        Glide.with(this).load(profile.url).into(ivProfileImage)
        Glide.with(this).load(currentUserPFP.url).into(userProfilePicture)
        favoritesCount.text = countFavorites(Post).toString()

        commentRecyclerView = findViewById<RecyclerView>(R.id.commentRecyclerView)
        commentAdapter = CommentAdapter(this, allComments, Post)
        commentRecyclerView.layoutManager = LinearLayoutManager(this)
        commentRecyclerView.adapter = commentAdapter
        commentRecyclerView.layoutManager = LinearLayoutManager(this)

        queryComments(Post)

        submitComment.setOnClickListener(){
            submitComment(composedComment.text.toString(), ParseUser.getCurrentUser(), Post)
            queryComments(Post)
            val inputMethodManager: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.getApplicationWindowToken(), 0)
            composedComment.setText("")
        }

        if(Post.getUser()!!.fetchIfNeeded().username == ParseUser.getCurrentUser().fetchIfNeeded().username){
            deletePost.visibility = View.VISIBLE
        }

        deletePost.setOnClickListener(){
            //TODO: Insert logic to open up the pop up menu and delete the post
            deletePost(Post)
        }

        downloadButton.setOnClickListener(){ //Using this to share for now
            //TODO: Insert logic here to download the image onto your phone
            val b=BitmapFactory.decodeResource(resources,R.drawable.imgPost)
            val intent= Intent()
            intent.action=Intent.ACTION_SEND

            val path= MediaStore.Images.Media.insertImage(contentResolver,b,"Title",null)
        }

        if (isLiked(Post, ParseUser.getCurrentUser())){
            //user has already set post to favorites, set icon to be full
            favoritesButton.setImageResource(R.drawable.ic_bookmark_clicked)
            Log.e(TAG, "USER HAS ALREADY FAVORITED THE POST")
        }


        favoritesButton.setOnClickListener() {
            if (isLiked(Post, ParseUser.getCurrentUser())) {
                favoritesButton.setImageResource(R.drawable.ic_bookmark_unclicked)
                Log.e(TAG, "THIS IS TO UNFAVORITE POST")
                unfavoritePost(Post, ParseUser.getCurrentUser())
                favoritesCount.text = countFavorites(Post).toString()
            } else {
                favoritesButton.setImageResource(R.drawable.ic_bookmark_clicked)
                Log.e(TAG, "THIS IS TO FAVORITE POST")
                favoritePost(Post, ParseUser.getCurrentUser())
                favoritesCount.text = countFavorites(Post).toString()
            }
        }
    }

    fun queryComments(Post:posts){
        val query: ParseQuery<comments> = ParseQuery.getQuery(comments::class.java)
        query.whereEqualTo("post_id", Post)
        query.addDescendingOrder("createdAt")
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

    private fun deletePost(post: posts) {
        val alertbox = AlertDialog.Builder(this)
        alertbox.setMessage("Are you sure you want to delete?")
        alertbox.setNegativeButton("Cancel"){ dialogInterface: DialogInterface, i: Int -> }
        alertbox.setPositiveButton("Delete"){arg0, arg1 ->
            val query = ParseQuery.getQuery<ParseObject>("posts")
            query.getInBackground(post.objectId) { `object`, e ->
                if (e == null) {
                    //Object was fetched
                    //Deletes the fetched ParseObject from the database
                    `object`.deleteInBackground { e2 ->
                        if (e2 == null) {
//                            Toast.makeText(this, "Delete Successful", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            //Something went wrong while deleting the Object
                            Toast.makeText(this, "Error: " + e2.printStackTrace(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    //Something went wrong
                    Toast.makeText(this,"Error: "+ e.printStackTrace(), Toast.LENGTH_SHORT).show()
                }
            }
        }
        alertbox.show()
    }

    fun countFavorites(Post:posts): Int{
        val likesQuery = ParseQuery(favorites::class.java)
        likesQuery.whereEqualTo(favorites.KEY_USER,ParseUser.getCurrentUser())
        likesQuery.whereEqualTo(favorites.KEY_POST,Post)
        val likes = likesQuery.find()

        return likes.size
    }

    //function that just checks if the user has put this in their favorites
    fun isLiked(post: posts, user: ParseUser): Boolean{
        val likesQuery = ParseQuery(favorites::class.java)
        likesQuery.whereEqualTo(favorites.KEY_USER,user)
        likesQuery.whereEqualTo(favorites.KEY_POST,post)

        val likes = likesQuery.find()

        return likes.size != 0
    }

    //allows user to add the post to the user favorites
    fun favoritePost(post: posts, user: ParseUser){
        var newFav = favorites()
        newFav.setUser(user)
        newFav.setPost(post)
        newFav.saveInBackground{ exception ->
            if(exception != null){
                Log.e(TAG, "Error while adding post to favorites")
                exception.printStackTrace()
            } else {
                Log.i(TAG, "Successfully added post to favorites")

            }
        }
    }

    //allows user to unlike the post

    fun unfavoritePost(post: posts, user: ParseUser) {
        //TODO: remove user from the liked posts list
        val query = ParseQuery.getQuery<ParseObject>("favorites")
        query.whereEqualTo(favorites.KEY_POST, post)
        query.whereEqualTo(favorites.KEY_USER, user)
        val favorite = query.find()
        // Retrieve the object by id
        //retrieve the first object in the list to delete
        //TODO: THIS MIGHT CAUSE ISSUES LATER
        query.getInBackground(favorite.get(0).objectId) { `object`: ParseObject, e: ParseException? ->
            if (e == null) {
                //Object was fetched
                //Deletes the fetched ParseObject from the database
                `object`.deleteInBackground { e2: ParseException? ->
                    if (e2 == null) {
                        Log.e(TAG, "Successfully removed post from user favorites")
                    } else {
                        //Something went wrong while deleting the Object
                        Toast.makeText(this, "Error: " + e2.message, Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                //Something went wrong
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val TAG = "DetailView"
    }

}

