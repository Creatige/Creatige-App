package com.creatige.creatige.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.RelativeLayout.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestHeaders
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.creatige.creatige.GlobalVariableClass
import com.creatige.creatige.R
import com.creatige.creatige.fragments.CreateImageFragment.Companion.wait_time
import com.creatige.creatige.posts
import com.parse.ParseFile
import com.parse.ParseUser
import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File


class CreateTextFragment : Fragment() {
    lateinit var ivGenerated: ImageView
    lateinit var btnGenerate: Button
    lateinit var etPrompt: EditText
    lateinit var parseFile: ParseFile
    lateinit var spinner: Spinner
    lateinit var btnSwitchModes : Button
    lateinit var ivCaptured : ImageView
    val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034
    var photoFile: File? = null
    val photoFileName = "photo.jpg"
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    var requestHeaders = RequestHeaders()
    var params = RequestParams()
    var post = posts()
    var expanded = false
    var modeImageEnabled = false
    var tookPicture = false
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
        ivCaptured = view.findViewById<ImageView>(R.id.ivCaptured)
        btnGenerate = view.findViewById<Button>(R.id.btnGenerate)
        etPrompt = view.findViewById<EditText>(R.id.etPrompt)
        spinner = view.findViewById(R.id.spinner)
        btnSwitchModes = view.findViewById(R.id.btnSwitchModes)


        val layoutParams = LayoutParams(to_dp(160), to_dp(160))
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        layoutParams.setMargins(0, to_dp(5), to_dp(25), to_dp(20));
        ivGenerated.layoutParams = layoutParams

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
            submitPost(user)
        }

        // Switch modes
        btnSwitchModes.setOnClickListener {
            if (modeImageEnabled) {
                // Switch to text mode
                btnSwitchModes.text = "Text Mode"
                modeImageEnabled = false
                ivCaptured.visibility = View.GONE

                val layoutParams = LayoutParams(
                    to_dp(160), to_dp(160)
                )
                layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                layoutParams.setMargins(0, to_dp(5), to_dp(25), to_dp(20));
                ivGenerated.layoutParams = layoutParams
            } else {
                // Switch to image mode
                btnSwitchModes.text = "Image Mode"
                modeImageEnabled = true
                ivCaptured.visibility = View.VISIBLE

                val layoutParams = LayoutParams(to_dp(160), to_dp(160));

                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
                layoutParams.setMargins(0, to_dp(5), to_dp(25), to_dp(20));
                ivGenerated.layoutParams = layoutParams;
            }
        }
        ivCaptured.setOnClickListener{
            onLaunchCamera()
        }
    }
    private fun onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName)
        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        if (photoFile != null) {
            val fileProvider: Uri =
                FileProvider.getUriForFile(requireContext(), "com.codepath.fileprovider", photoFile!!)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.
            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.
            if (intent.resolveActivity(requireContext().packageManager) != null) {
                // Start the image capture intent to take photo
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
            }
        }
    }
    private fun getPhotoFileUri(fileName: String): File {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        val mediaStorageDir =
            File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG)
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(TAG, "failed to create directory")
        }
        // Return the file target for the photo based on filename
        return File(mediaStorageDir.path + File.separator + fileName)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
            if(resultCode== AppCompatActivity.RESULT_OK){
                val takenImage = BitmapFactory.decodeFile(photoFile!!.absolutePath)
                ivCaptured.setImageBitmap(takenImage)
                tookPicture = true
            }else{
                Toast.makeText(requireContext(), "Error taking picture", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun submitPost( user: ParseUser) {
        var prompt = etPrompt.text.toString()
        val seed = view?.findViewById<EditText>(R.id.et_seed)?.text.toString()
        val steps = view?.findViewById<SeekBar>(R.id.steps_seekbar)?.progress?.times(2)
        val sampler = view?.findViewById<Spinner>(R.id.spinner)?.selectedItem.toString()
        val height = view?.findViewById<SeekBar>(R.id.height_seekbar)?.progress?.times(64)
        val width = view?.findViewById<SeekBar>(R.id.width_seekbar)?.progress?.times(64)
        val guidance = view?.findViewById<SeekBar>(R.id.guid_seekbar)?.progress?.div(2)
        val negative = view?.findViewById<EditText>(R.id.et_neg_prompt)?.text.toString()
        val denoising = view?.findViewById<SeekBar>(R.id.denoising)?.progress?.toFloat()?.div(20)
        if (negative != ""){
            prompt= "$prompt ### $negative"
        }
        val client = AsyncHttpClient()
        var json = ""
        if (modeImageEnabled){
            if(tookPicture){
                val takenImage = BitmapFactory.decodeFile(photoFile!!.absolutePath)
                val out = Bitmap.createScaledBitmap(takenImage, 512, 512, false)
                val byteArrayOutputStream = ByteArrayOutputStream()
                out.compress(Bitmap.CompressFormat.WEBP, 50, byteArrayOutputStream)
                val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
                var encoded = Base64.encodeToString(byteArray, Base64.NO_WRAP)
                json = "{\"prompt\":\"$prompt\"," +
                        "\"params\":{" +
                        "\"sampler_name\":\"$sampler\"," +
                        "\"cfg_scale\":$guidance," +
                        " \"denoising_strength\":$denoising," +
                        "\"seed\":\"$seed\"," +
                        "\"height\":$height," +
                        "\"width\":$width," +
                        "\"karras\":false," +
                        "\"steps\":$steps," +
                        "\"n\":1 }," +
                        "\"nsfw\":${GlobalVariableClass.nsfw_enabled}," +
                        "\"trusted_workers\":${GlobalVariableClass.trusted_workers}," +
                        "\"censor_nsfw\":${GlobalVariableClass.censor_nsfw}," +
                        "\"source_image\":\"$encoded\"," +
                        " \"source_processing\":\"img2img\"}"
            }else{
                Toast.makeText(
                    requireContext(),
                    "Please take a picture",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }else {
            json = "{\"prompt\":\"$prompt\"," +
                    "\"params\":{" +
                    "\"sampler_name\":\"$sampler\"," +
                    "\"cfg_scale\":$guidance," +
                    "\"seed\":\"$seed\"," +
                    "\"height\":$height," +
                    "\"width\":$width," +
                    "\"karras\":false," +
                    "\"steps\":$steps," +
                    "\"n\":1 }," +
                    "\"nsfw\":${GlobalVariableClass.nsfw_enabled}," +
                    "\"trusted_workers\":${GlobalVariableClass.trusted_workers}," +
                    "\"censor_nsfw\":${GlobalVariableClass.censor_nsfw}}"
        }
        if (!modeImageEnabled || tookPicture) {
            Log.i(TAG, "Submitting request to the API...")
            Toast.makeText(
                requireContext(),
                "Sending generation request",
                Toast.LENGTH_SHORT
            ).show()
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
                    // TODO REMOVE
                    Looper.prepare()
                    Toast.makeText(
                        requireContext(),
                        "Could not send generation request (Error $statusCode)",
                        Toast.LENGTH_LONG
                    ).show()
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
        }
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
                // TODO REMOVE
                Looper.prepare()
                Toast.makeText(
                    requireContext(),
                    "Could not retrieve generated image (Error $statusCode)",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                Log.e(TAG, "Retrieved image  $statusCode $json")
                //Change if we do multiple generations in the future
                val jsonObject = json?.jsonObject
                val generations = jsonObject?.getJSONArray("generations")
                val jsonObjectImg = generations?.getJSONObject(0)
                val img64 = jsonObjectImg?.getString("img")
                val decodedString: ByteArray = Base64.decode(img64, Base64.DEFAULT)
                Glide.with(this@CreateTextFragment).load(BitmapFactory.decodeByteArray(
                    decodedString, 0, decodedString
                        .size
                )).into(ivGenerated)
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
                        Toast.makeText(
                            requireContext(),
                            "Successfully saved post",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

    fun to_dp(px: Int): Int {
        return ((px * resources.displayMetrics.density).toInt())
    }

    companion object {
        const val TAG = "CreateTextFragment"
        const val wait_time = 30000L
    }
}

