package com.creatige.creatige.fragments

import com.codepath.asynchttpclient.AsyncHttpClient
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codepath.asynchttpclient.RequestHeaders
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.creatige.creatige.JSONHeaderInterceptor
import com.creatige.creatige.R
import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject


private const val URL = "https://stablehorde.net/api/v2/generate/async"

class CreateTextFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val json = "{\"prompt\":\"tree\"}"
        val jsonObject = JSONObject(json)
        Log.e(TAG, jsonObject.toString())
        val client = AsyncHttpClient()
        val params = RequestParams()
        val requestHeaders = RequestHeaders()
        requestHeaders["apikey"] = "QcRxfonkWODntMJO7sOiNA"
        requestHeaders["Accept"] = "application/json"
        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body : RequestBody = RequestBody.create(JSON,json)
        client.post(URL,requestHeaders,params,body,object: JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "onFailure $statusCode $response $headers")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                Log.e(TAG, "onSuccess $statusCode $json")

            }

        })





    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_text, container, false)
    }



    companion object {
        val TAG = "CreateTextFragment"
    }
}

