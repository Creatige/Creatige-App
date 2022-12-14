package com.creatige.creatige.models

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser

@ParseClassName("favorites")
class favorites: ParseObject() {
    fun getUser():ParseUser?{
        return getParseUser(KEY_USER)
    }
    fun setUser(user: ParseUser){
        put(KEY_USER, user)
    }
    fun getPost():ParseObject?{
        return getParseObject(KEY_POST)
    }
    fun setPost(post: ParseObject){
        put(KEY_POST, post)
    }

    companion object{
        const val KEY_USER = "user"
        const val KEY_POST = "post"
    }
}