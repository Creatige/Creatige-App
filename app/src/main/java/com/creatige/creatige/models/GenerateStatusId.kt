package com.creatige.creatige

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.List

public class GenerateStatusId {

    @SerializedName("generations")
    @Expose
    private var generations: kotlin.collections.List<String>? = null

    public fun getGenerations(): kotlin.collections.List<String>? {
        return generations;
    }


}