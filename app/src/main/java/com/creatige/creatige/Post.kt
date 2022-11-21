package com.creatige.creatige

import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser

//Prompt : String
//Image : File
//User : User


@ParseClassName("posts")
class Post() : ParseObject() {
    fun getPrompt(): String?{
        return getString(KEY_PROMPT)
    }
    fun setPrompt(prompt: String){
        put(KEY_PROMPT, prompt)
    }

    fun getImage(): ParseFile? {
        return getParseFile(KEY_IMAGE)
    }

    fun setImage(parsefile: ParseFile){
        put(KEY_IMAGE, parsefile)
    }
    fun getUser(): ParseUser?{
        return getParseUser(KEY_USER)
    }

    fun setUser(user: ParseUser){
        put(KEY_USER, user)
    }


    companion object{
        const val KEY_PROMPT = "prompt"
        const val KEY_IMAGE = "image"
        const val KEY_USER = "user"

    }
}