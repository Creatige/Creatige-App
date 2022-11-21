package com.creatige.creatige;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Generation {

    @SerializedName("img")
    @Expose
    private lateinit var img: List<String>

    public fun getImg(): List<String> {
        return img
    }
}