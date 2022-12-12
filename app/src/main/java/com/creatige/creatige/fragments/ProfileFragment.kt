package com.creatige.creatige.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.creatige.creatige.R
import com.creatige.creatige.activities.SettingsActivity
import com.creatige.creatige.adapters.ProfilePostAdapter
import com.creatige.creatige.models.posts
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
    lateinit var swipeContainer: SwipeRefreshLayout


    lateinit var adapter: ProfilePostAdapter
    var allPosts: MutableList<posts> = mutableListOf()
    lateinit var tvNumberPhotos: TextView
    lateinit var query: ParseQuery<posts>
    lateinit var rvPosts: RecyclerView
    lateinit var ivProfileImage : ImageView

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

        //this is where we set up our views and click listeners

        rvPosts = view.findViewById<RecyclerView>(R.id.rvPosts)
        adapter = ProfilePostAdapter(requireContext(), allPosts)
        rvPosts.adapter = adapter

//        rvPosts.layoutManager = LinearLayoutManager(requireContext())

        rvPosts.layoutManager = GridLayoutManager (requireContext(), 2)

        queryPosts()

        swipeContainer = view.findViewById(R.id.swipeContainer)

        swipeContainer.setOnRefreshListener {
            Log.i(FeedFragment.TAG,"Refreshing timeline")
            queryPosts()
        }

        swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);


        var btnSettings = view.findViewById<ImageButton>(R.id.btnSettings)
        ivProfileImage = view.findViewById<ImageView>(R.id.ivProfileImage)
        var tvUsername = view.findViewById<TextView>(R.id.tvUsername)
        tvNumberPhotos = view.findViewById<TextView>(R.id.tvNumberPhotos)

        var numberOfPosts = 0




        btnSettings.setOnClickListener {
            // TODO: Uncomment when Settings activity is created
            var intent = Intent(context, SettingsActivity::class.java)
            startActivity(intent)
        }

        Glide.with(this@ProfileFragment).load(ParseUser.getCurrentUser().getParseFile("avatar")?.url).into(ivProfileImage)
        tvUsername.text = ParseUser.getCurrentUser().username
        findNumberPosts()



    }





    override fun onResume() {
        super.onResume()
        Log.i("ProfileFragment", "onResume")
        updateUIOnResume()
    }


    private fun updateUIOnResume(){
        // updates the profile picture if it has been changed in Settings
        Glide.with(this@ProfileFragment).load(ParseUser.getCurrentUser().getParseFile("avatar")?.url).into(ivProfileImage)
    }

    // function to get the number of photos the user has posted

    private fun findNumberPosts(){
        var query = ParseQuery.getQuery(posts::class.java)
        query.whereEqualTo("user", ParseUser.getCurrentUser())
        query.countInBackground { count, e ->
            if (e == null) {
                tvNumberPhotos.text =  "$count images generated"
            } else {
                Log.e("ProfileFragment", "Error counting posts: $e")
            }
        }
    }


    private fun queryPosts() {
        val parseUser = ParseUser.getCurrentUser()
        //specify which class to query
        query = ParseQuery<posts>("posts").whereContainedIn("user", listOf(parseUser))
        //find all post objects
        query.include(posts.KEY_USER)
        query.setLimit(2000)
        query.addDescendingOrder("createdAt")
        //TODO: Only return the most recent 20 posts

        query.findInBackground { posts, e ->
            if (e != null) {
                Log.e(FeedFragment.TAG, "Error fetching posts")
            } else {
                if (posts != null) {
                    for (post in posts) {
                        Log.i(
                            FeedFragment.TAG,
                            "Post:" + post.getPrompt() + ", username: " + post.getUser()?.username
                        )
                    }


                    allPosts.clear()
                    allPosts.addAll(posts)
                    adapter.notifyDataSetChanged()
                    swipeContainer.setRefreshing(false)



                    //TODO: Implement the logic to set the swipecontainer to stop spinning around like its really silly for spinning around really
                    //swipeContainer.setRefreshing(false)
                }
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

        const val TAG = "ProfileFragment"
    }

}