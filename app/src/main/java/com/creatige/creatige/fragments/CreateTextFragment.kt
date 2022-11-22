package com.creatige.creatige.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestHeaders
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.creatige.creatige.posts
import com.creatige.creatige.R
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser
import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.ByteArrayOutputStream


private const val URL = "https://stablehorde.net/api/v2/generate/async"

class CreateTextFragment : Fragment() {
    lateinit var ivGenerated:ImageView
    lateinit var btnGenerate:Button
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    var requestHeaders = RequestHeaders()
    var params = RequestParams()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun getImg(id:String){
        val idUrl = "https://stablehorde.net/api/v2/generate/status/$id"
        val client = AsyncHttpClient()
        client.get(idUrl,object :JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "onFailure $statusCode $response ")
            }
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                Log.e(TAG, "Retrieved image  $statusCode $json")
                //Change if we do multiple generations in the future
                val jsonObject = json?.jsonObject
                val generations = jsonObject?.getJSONArray("generations")
                val jsonObjectImg = generations?.getJSONObject(0)
                val img64 = jsonObjectImg?.getString("img")
                val decodedString: ByteArray = Base64.decode(img64, Base64.DEFAULT)
                ivGenerated.setImageBitmap(
                    BitmapFactory.decodeByteArray(decodedString, 0, decodedString
                        .size))
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Remove this and set to the actual image we get back from the API
        ivGenerated = view.findViewById<ImageView>(R.id.ivGenerated)
        btnGenerate = view.findViewById<Button>(R.id.btnGenerate)


        btnGenerate.setOnClickListener{
            val prompt = view.findViewById<EditText>(R.id.etPrompt).text.toString()
            val user = ParseUser.getCurrentUser()
            submitPost(prompt,user, ivGenerated)
        }
    }

    private fun submitPost(prompt: String, user: ParseUser, ivGenerated: ImageView) {
        val post = posts()
        post.setPrompt(prompt)
        post.setUser(user)
        val json = "{\"prompt\":\"$prompt\"}"
        requestHeaders["apikey"] = "QcRxfonkWODntMJO7sOiNA"
        requestHeaders["Accept"] = "application/json"
        val client = AsyncHttpClient()
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
                val jsonObject = json?.jsonObject
                val id = jsonObject?.getString("id")
                if (id != null) {
                    val handler = Handler()
                    handler.postDelayed(Runnable {
                        getImg(id)
                    }, 30000)
                }
            }
        })
//        ivGenerated.buildDrawingCache()
//        val bmap: Bitmap = ivGenerated.getDrawingCache()
//
//        // Convert it to byte
//        // Convert it to byte
//        val stream = ByteArrayOutputStream()
//        // Compress image to lower quality scale 1 - 100
//        // Compress image to lower quality scale 1 - 100
//        bmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//        val image = stream.toByteArray()
//
//        // Create the ParseFile
//
//        // Create the ParseFile
//        val file = ParseFile("androidbegin.png", image)

        //post.setImage(file)

        //TODO: Add a loading bar to indicate the saving of the post? or at least the generation of an image
        post.saveInBackground{exception ->
            if(exception != null){
                Log.e(TAG, "Error while saving post")
                exception.printStackTrace()
                Toast.makeText(requireContext(), "Error while saving post", Toast.LENGTH_SHORT).show()
            } else {
                Log.i(TAG, "Successfully saved post")
                //TODO: Resetting the EditText field to be empty
                //TODO: Reset the ImageView to empty
            }
        }

    }


    companion object {
        val TAG = "CreateTextFragment"
    }
}

