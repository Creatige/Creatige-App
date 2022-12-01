package com.creatige.creatige

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser

@ParseClassName("comments")
class comments():ParseObject() {
    fun getUser(): String?{
        return getString(KEY_USER)
    }

    fun setUser(user: ParseUser){
        put(KEY_USER, user)
    }

    fun getComment():String?{
        return getString(KEY_COMMENT)
    }

    fun setComment(comment: String){
        put(KEY_COMMENT, comment)
    }
    fun setPostId(postId: String){
        put("post_id", postId)
    }

    companion object{
        const val KEY_USER = "user_id"
        const val KEY_COMMENT = "comment"
    }
}

