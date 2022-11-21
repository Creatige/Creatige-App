package com.creatige.creatige

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.creatige.creatige.fragments.CreateTextFragment
import com.creatige.creatige.fragments.FeedFragment
import com.creatige.creatige.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.ParseUser

class generateImage {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //if user already logged in, don't want to keep logging in

        if(ParseUser.getCurrentUser() != null) {
            goToMainActivity()
        }

        //switching screens from login to register

        val screen_login = findViewById<TextView>(R.id.new_registe);

        screen_login.setOnClickListener({
            val screen_register = Intent(this, register::class.java);
            startActivity(screen_register);

        })


        //login for user

        findViewById<Button>(R.id.login_button).setOnClickListener{

            val username = findViewById<EditText>(R.id.et_username).text.toString()
            val password = findViewById<EditText>(R.id.et_password).text.toString()
            loginUser(username,password)


        }



    }

    private fun loginUser(username: String, password: String) {

        ParseUser.logInInBackground(username, password, ({ user, e ->
            if (user != null) {
                Log.i(TAG, "login success")
                goToMainActivity()
            } else {

                e.printStackTrace()
                Toast.makeText(this, "error logging in", Toast.LENGTH_SHORT).show()

            }})
        )

    }

    private fun goToMainActivity(){

        val intent = Intent(this@LoginActivity,MainActivity::class.java)
        startActivity(intent)
        finish()

    }

    companion object{

        const val TAG = "LoginActivity"
    }


}