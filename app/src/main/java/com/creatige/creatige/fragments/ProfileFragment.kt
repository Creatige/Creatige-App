package com.creatige.creatige.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.creatige.creatige.LoginActivity
import com.creatige.creatige.R
import com.creatige.creatige.posts
import com.parse.ParseQuery
import com.parse.ParseUser


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var tvNumberPhotos: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var btnLogout = view.findViewById<Button>(R.id.btnLogout)
        var btnSettings = view.findViewById<ImageButton>(R.id.btnSettings)
        var ivProfileImage = view.findViewById<ImageView>(R.id.ivProfileImage)
        var tvUsername = view.findViewById<TextView>(R.id.tvUsername)
        tvNumberPhotos = view.findViewById<TextView>(R.id.tvNumberPhotos)

        var numberOfPosts = 0


        btnLogout.setOnClickListener {
            logoutUser();
        }

        btnSettings.setOnClickListener {
            // TODO: Uncomment when Settings activity is created
            //var intent = Intent(context, SettingsActivity::class.java)
            //startActivity(intent)
        }

        Glide.with(this@ProfileFragment).load(ParseUser.getCurrentUser().getParseFile("avatar")?.url).into(ivProfileImage)
        tvUsername.text = ParseUser.getCurrentUser().username
        findNumberPosts()

    }

    private fun logoutUser() {
        ParseUser.logOut()
        ParseUser.getCurrentUser()

        var intent = Intent(context, LoginActivity::class.java)
        // This prevents the user to go back to the previous activity, which causes the app to crash as there is no user logged in
        // It clears the activity stack
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    // function to get the number of photos the user has posted

    private fun findNumberPosts(){
        var query = ParseQuery.getQuery(posts::class.java)
        query.whereEqualTo("user", ParseUser.getCurrentUser())
        query.countInBackground { count, e ->
            if (e == null) {
                tvNumberPhotos.text =  "${count} images generated"
            } else {
                Log.e("ProfileFragment", "Error counting posts: $e")
            }
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}