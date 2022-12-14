package com.creatige.creatige

import android.app.Application
import com.creatige.creatige.models.comments
import com.creatige.creatige.models.favorites
import com.creatige.creatige.models.posts
import com.parse.Parse
import com.parse.ParseObject

class CreatigeApplication : Application() {
    override fun onCreate(){
        super.onCreate()
        ParseObject.registerSubclass(posts::class.java)
        ParseObject.registerSubclass(comments::class.java)
        ParseObject.registerSubclass(favorites::class.java)
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());

    }
}

