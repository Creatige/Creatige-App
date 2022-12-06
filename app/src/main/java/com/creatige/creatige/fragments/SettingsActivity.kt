package com.creatige.creatige.fragments

import android.R.attr.button
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.widget.addTextChangedListener
import com.creatige.creatige.LoginActivity
import com.creatige.creatige.R
import com.parse.ParseFile
import com.parse.ParseUser
import java.io.File


class SettingsActivity: AppCompatActivity() {
    val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034
    private lateinit var ivProfileImg: ImageView
    private lateinit var btnChangeProfileImg: Button
    private lateinit var etOldPass : EditText
    private lateinit var author : TextView
    private lateinit var etNewPass : EditText
    private lateinit var etConfirmPass : EditText
    private lateinit var spinner: Spinner
    private lateinit var btnChangePw: Button
    var photoFile: File? = null
    val photoFileName = "photo.jpg"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        spinner = findViewById(R.id.spinner_visibility)
        ivProfileImg = findViewById(R.id.ivProfileImage)
        btnChangeProfileImg = findViewById(R.id.BtnchangePic)
        etOldPass = findViewById(R.id.et_oldPass)
        etNewPass = findViewById(R.id.et_newpass)
        etConfirmPass = findViewById(R.id.et_confirm_pass)
        btnChangePw=findViewById(R.id.btn_changePass)
        author=findViewById(R.id.user_name)
        author.text = ParseUser.getCurrentUser()?.username.toString()
        ArrayAdapter.createFromResource(
            this,
            R.array.visibility,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        var btnLogout = findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            logoutUser();
        }
        etConfirmPass.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                btnChangePw.isEnabled = etConfirmPass.text.toString() == etNewPass.text.toString()
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })
        etNewPass.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                btnChangePw.isEnabled = etConfirmPass.text.toString() == etNewPass.text.toString()
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })


        btnChangePw.isEnabled = etConfirmPass.text.toString() == etNewPass.text.toString()

        btnChangeProfileImg.setOnClickListener {
            onLaunchCamera()
        }

        btnChangePw.setOnClickListener {

            val old = etOldPass.text
            val new = etNewPass.text
            val confirm = etConfirmPass.text

            if(old.isNotEmpty()){
                val user :ParseUser = ParseUser.getCurrentUser()
                ParseUser.logInInBackground(user.username.toString(), old.toString(), ({ user, e ->
                    if (user != null) {
                        user.put("password",new.toString())
                        user.saveInBackground { e ->
                            if (e == null) {
                                //Save successfull
                                Toast.makeText(this, "Save Successful", Toast.LENGTH_SHORT).show()
                            } else {
                                // Something went wrong while saving
                                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                        Log.i(TAG, "password is correct")
                    } else {
                        e.printStackTrace()
                        Log.i(TAG, "incorrect password")
                        Log.e(TAG, "$e")
                    }})
                )




            }
        }

    }private fun logoutUser() {
        ParseUser.logOut()
        ParseUser.getCurrentUser()

        var intent = Intent(this, LoginActivity::class.java)
        // This prevents the user to go back to the previous activity, which causes the app to crash as there is no user logged in
        // It clears the activity stack
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
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
                FileProvider.getUriForFile(this, "com.codepath.fileprovider", photoFile!!)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.
            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.
            if (intent.resolveActivity(this.packageManager) != null) {
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
            File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                CreateTextFragment.TAG
            )
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(CreateTextFragment.TAG, "failed to create directory")
        }
        // Return the file target for the photo based on filename
        return File(mediaStorageDir.path + File.separator + fileName)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
            if(resultCode== RESULT_OK){
                val takenImage = BitmapFactory.decodeFile(photoFile!!.absolutePath)
                ivProfileImg.setImageBitmap(takenImage)
                val user = ParseUser.getCurrentUser()

                user.put("avatar", ParseFile(photoFile))
                user.saveInBackground{e->
                    if (e == null) {
                        //Save successfully
                        Toast.makeText(this, "Avatar saved successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        // Something went wrong while saving
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                    }

                }
            }else{
                Toast.makeText(this, "Error taking picture", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun tryLoginAgain(username: String, password: String) {



    }
    companion object {
        const val TAG = "SettingsActivity"
    }
}