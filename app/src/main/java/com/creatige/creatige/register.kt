package com.creatige.creatige

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.parse.ParseUser

class register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


//if user already logged, don't want user to keep logging in

        if(ParseUser.getCurrentUser() != null) {
            goToMainActivity()
        }

        //login for user

        findViewById<Button>(R.id.register_button).setOnClickListener{

            val username = findViewById<EditText>(R.id.et_username).text.toString()
            val password = findViewById<EditText>(R.id.et_password).text.toString()
            signup(username,password)


        }


    }

    private fun signup(username: String, password: String) {

        val user = ParseUser()

        // Set fields for the user to be created
        user.setUsername(username)
        user.setPassword(password)

        user.signUpInBackground { e ->
            if (e == null) {
                Log.i(TAG, "Register success")
                goToMainActivity()
            } else {
                e.printStackTrace()
                Toast.makeText(this, "error signing up", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun goToMainActivity(){

        val intent = Intent(this@register,MainActivity::class.java)
        startActivity(intent)
        finish()

    }

    companion object{

        const val TAG = "Register"
    }

}





