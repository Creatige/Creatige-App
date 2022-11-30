package com.creatige.creatige.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestHeaders
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.creatige.creatige.GlobalVariableClass
import com.creatige.creatige.R
import com.creatige.creatige.posts
import com.parse.ParseFile
import com.parse.ParseUser
import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class CreateImageFragment : Fragment() {
    lateinit var ivGenerated: ImageView
    lateinit var btnGenerate: Button
    lateinit var etPrompt: EditText
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    var requestHeaders = RequestHeaders()
    var params = RequestParams()
    lateinit var parseFile: ParseFile
    var post = posts()
    var expanded = false
    lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun getImg(id: String) {
        val idUrl = GlobalVariableClass.api_retrieve_img_url + id
        val client = AsyncHttpClient()
        client.get(idUrl, object : JsonHttpResponseHandler() {
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
                    BitmapFactory.decodeByteArray(
                        decodedString, 0, decodedString
                            .size
                    )
                )
                parseFile = ParseFile("photo.webp", decodedString)
                post.setImage(parseFile)
                post.saveInBackground { exception ->
                    if (exception != null) {
                        Log.e(TAG, "Error while saving post")
                        exception.printStackTrace()
                        Toast.makeText(
                            requireContext(),
                            "Error while saving post",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Log.i(TAG, "Successfully saved post")
                    }
                }
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
        etPrompt = view.findViewById<EditText>(R.id.etPrompt)
        ivGenerated = view.findViewById<ImageView>(R.id.ivGenerated)
        btnGenerate = view.findViewById<Button>(R.id.btnGenerate)
        etPrompt = view.findViewById<EditText>(R.id.etPrompt)
        spinner = view.findViewById(R.id.spinner)
        view.findViewById<Button>(R.id.options_expand).setOnClickListener {
            if (expanded) {
                view.findViewById<LinearLayout>(R.id.adv_list).visibility = View.GONE
                expanded = false
            } else {
                view.findViewById<LinearLayout>(R.id.adv_list).visibility = View.VISIBLE
                expanded = true
            }
        }
        ArrayAdapter.createFromResource(
            view.context,
            R.array.sampler,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        btnGenerate.setOnClickListener {
            val prompt = etPrompt.text.toString()
            val user = ParseUser.getCurrentUser()
            submitPost(prompt, user)
        }
    }

    private fun submitPost(prompt: String, user: ParseUser) {
        post = posts()
        post.setPrompt(prompt)
        post.setUser(user)
        val json = "{\"prompt\":\"$prompt\"}"
        requestHeaders["apikey"] = GlobalVariableClass.api_key
        requestHeaders["Accept"] = "application/json"
        val client = AsyncHttpClient()
        val body: RequestBody = json.toRequestBody(JSON)
        client.post(GlobalVariableClass.api_generate_url, requestHeaders, params, body, object :
            JsonHttpResponseHandler() {
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
                    handler.postDelayed({
                        getImg(id)

                    }, wait_time)
                }
            }
        })

        //TODO: Add a loading bar to indicate the saving of the post? or at least the generation of an image


    }


    companion object {
        const val TAG = "CreateImageFragment"
        const val wait_time = 30000L
    }
}