package com.creatige.creatige

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.creatige.creatige.fragments.CreateTextFragment
import com.creatige.creatige.fragments.FeedFragment
import com.creatige.creatige.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val fragmentManager: FragmentManager = supportFragmentManager

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {

                item ->

            var fragmentToShow : Fragment? = null

            when(item.itemId){

                R.id.action_feed -> {

                    fragmentToShow = FeedFragment()

//                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                }

                R.id.action_create -> {
                    fragmentToShow = CreateTextFragment()
                }

                R.id.action_profile -> {
                    fragmentToShow = ProfileFragment()

//                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                }
            }

            if (fragmentToShow!=null){

//                Toast.makeText(this, "Fragment is not null", Toast.LENGTH_SHORT).show()
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow).commit()
            }

            // returns true to say that we have handled this user interaction
            true
        }


        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id
            .action_create


    }

}
