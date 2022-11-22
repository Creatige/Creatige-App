package com.creatige.creatige

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//only grab the id to then later call the Generate statusID
class GenerateAsync {

    @SerializedName("id")
    @Expose
    private var id: String = ""

    public fun getId():String {
        return id;
    }
}