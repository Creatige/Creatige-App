package com.creatige.creatige.activities

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.creatige.creatige.R
import com.creatige.creatige.fragments.CreateTextFragment
import com.creatige.creatige.fragments.FeedFragment
import com.creatige.creatige.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setting the orientation to portrait
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


        val fragmentManager: FragmentManager = supportFragmentManager

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {

                item ->

            var fragmentToShow : Fragment? = null

            when(item.itemId){

                R.id.action_feed -> {
                    fragmentToShow = FeedFragment()
                }

                R.id.action_create -> {
                    fragmentToShow = CreateTextFragment()
                }

                R.id.action_profile -> {
                    fragmentToShow = ProfileFragment()
                }
            }

            if (fragmentToShow!=null){
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow).commit()
            }

            // returns true to say that we have handled this user interaction
            true
        }


        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id
            .action_create


    }

}
