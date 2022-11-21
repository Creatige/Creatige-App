package com.creatige.creatige.fragments

import com.codepath.asynchttpclient.AsyncHttpClient
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.creatige.creatige.JSONHeaderInterceptor
import com.creatige.creatige.R
import okhttp3.Headers
import okhttp3.OkHttpClient
import org.json.JSONObject


private const val URL = "https://stablehorde.net/api/v2/generate/async"

class CreateTextFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val json = JSONObject("{prompt:tree}")


        val client = AsyncHttpClient()



        client.post(URL, object:JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "onFailure $response")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                Log.i(TAG, "onSuccess: JSON data $json")
            }

        })


        fun provideHttpClient(): OkHttpClient{
            val okHttpClientBuilder = OkHttpClient.Builder()
            okHttpClientBuilder.addInterceptor(JSONHeaderInterceptor())
            return okHttpClientBuilder.build()
        }


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

