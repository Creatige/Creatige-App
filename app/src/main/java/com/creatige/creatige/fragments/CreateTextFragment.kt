package com.creatige.creatige.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.fragment.app.Fragment
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

class CreateTextFragment : Fragment() {
    lateinit var ivGenerated: ImageView
    lateinit var btnGenerate: Button
    lateinit var etPrompt: EditText
    lateinit var parseFile: ParseFile
    lateinit var spinner: Spinner

    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    var requestHeaders = RequestHeaders()
    var params = RequestParams()
    var post = posts()
    var expanded = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

            val user = ParseUser.getCurrentUser()
            submitPost( user)
        }
    }


    private fun submitPost( user: ParseUser) {
        Log.i(TAG, "Submitting request to the API...")
        var prompt = etPrompt.text.toString()
        val seed = view?.findViewById<EditText>(R.id.et_seed)?.text.toString().toInt()
        val steps = view?.findViewById<SeekBar>(R.id.steps_seekbar)?.progress?.times(2)
        val sampler = view?.findViewById<Spinner>(R.id.spinner)?.selectedItem.toString()
        val height = view?.findViewById<SeekBar>(R.id.height_seekbar)?.progress?.times(64)
        val width = view?.findViewById<SeekBar>(R.id.width_seekbar)?.progress?.times(64)
        val guidance = view?.findViewById<SeekBar>(R.id.guid_seekbar)?.progress?.div(2)
        val negative = view?.findViewById<EditText>(R.id.et_neg_prompt)?.text.toString()
        if (negative != ""){
            prompt= "$prompt ### $negative"
        }
        val client = AsyncHttpClient()

        val json = "{\"prompt\":\"$prompt\"," +
                "\"params\":{" +
                "\"sampler_name\":\"$sampler\"," +
                "\"cfg_scale\":$guidance," +
                "\"seed\":\"$seed\"," +
                "\"height\":$height," +
                "\"width\":$width," +
                "\"karras\":false," +
                "\"steps\":$steps," +
                "\"n\":1 }," +
                "\"nsfw\":false," +
                "\"trusted_workers\":true," +
                "\"censor_nsfw\":true}"
//        var json = "{\"prompt\":\"$prompt\"}"
//        if(negative != null){
//            json = "{\"prompt\":\"$prompt ### $negative\"}"
//        }
        Log.i(TAG, "Sending JSON: $json")
        val body: RequestBody = json.toRequestBody(JSON)
        post = posts()
        post.setPrompt(prompt)
        post.setUser(user)
        requestHeaders["apikey"] = GlobalVariableClass.api_key
        requestHeaders["Accept"] = "application/json"
        //params["sampler_name"] = view?.findViewById<Spinner>(R.id.spinner).toString()
        //params["seed"] = view?.findViewById<EditText>(R.id.et_seed)?.text.toString()
        //TODO: see if there is an issue with the int values meant to be sent to the API call
        /*if (steps != null) {
            params["steps"] = steps.progress.toString()
        }
        if (height != null){
            params["height"] = height.progress.toString()
        }
        if (width != null){
            params["width"] = width.progress.toString()
        }
        if (guidance != null){
            params["cfg_scale"] = guidance.progress.toString()
        }*/
        client.post(GlobalVariableClass.api_generate_url, requestHeaders, params, body, object :
            JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "onFailure1111 $statusCode $response $headers")
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


    //this function allows us to retrieve the image from the API call
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

    companion object {
        const val TAG = "CreateTextFragment"
        const val wait_time = 30000L
    }
}

