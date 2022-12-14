package com.creatige.creatige.models

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser
import java.util.*

@ParseClassName("comments")
class comments():ParseObject() {

    fun getUser(): ParseUser?{
        return getParseUser(KEY_USER)
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
        put(KEY_POST_ID, postId)
    }
    fun getPostId():ParseObject?{
        return getParseObject(KEY_POST_ID)
    }
    fun getTime(): Date?{
        return createdAt
    }

    companion object{
        const val KEY_USER = "user_id"
        const val KEY_COMMENT = "comment"
        const val KEY_POST_ID = "postId"
    }
}

