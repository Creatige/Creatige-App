package com.creatige.creatige.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creatige.creatige.PostAdapter
import com.creatige.creatige.R
import com.creatige.creatige.posts
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery


open class FeedFragment : Fragment() {
    lateinit var postsRecyclerView: RecyclerView
    lateinit var adapter: PostAdapter
    var allPosts: MutableList<posts> = mutableListOf()

    //TODO: implement swiperefreshlayout
    //lateinit var swipeContainer:SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        queryPosts()
        //TODO: IMPLEMENT THE SWIPE CONTAINER LOGIC AND CREATE SWIPECONTAINER KOTLIN FILE
//        swipeContainer = view.findViewById(R.id.swipeContainer)
//        swipeContainer.setOnRefreshListener{
//            queryPosts()
//        }

        //this is where we set up our views and click listeners

        postsRecyclerView = view.findViewById<RecyclerView>(R.id.postRecyclerView)
        adapter = PostAdapter(requireContext(), allPosts)
        postsRecyclerView.adapter = adapter

        postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Pedro: commented it out because this was being called twice
//        queryPosts()

    }
    open fun queryPosts() {
        //specify which class to query
        val query: ParseQuery<posts> = ParseQuery.getQuery(posts::class.java)
        //find all post objects
        query.include(posts.KEY_USER)
        query.setLimit(20)
        query.addDescendingOrder("createdAt")
        //TODO: Only return the most recent 20 posts

        query.findInBackground(object : FindCallback<posts> {
            override fun done(posts: MutableList<posts>?, e: ParseException?){
                if(e != null){
                    Log.e(TAG, "Error fetching posts")
                } else {
                    if (posts != null){
                        for(post in posts){
                            Log.i(TAG, "Post:" + post.getPrompt()+ ", username: "+ post.getUser()?.username + "CreatedAt:" + post.getTime())
                        }
                        allPosts.clear()
                        allPosts.addAll(posts)
                        adapter.notifyDataSetChanged()
                        //TODO: Implement the logic to set the swipecontainer to stop spinning around like its really silly for spinning around really
                        //swipeContainer.setRefreshing(false)
                    }
                }

            }
        })

    }
    open fun searchDB(searchItem : String){
        //specify which class to query
        var allPosts: MutableList<posts> = mutableListOf()
        val query: ParseQuery<posts> = ParseQuery.getQuery(posts::class.java)
        query.include(posts.KEY_PROMPT)
        view?.findViewById<Button>(R.id.searchButton)?.setOnClickListener{
            val search = view?.findViewById<EditText>(R.id.searchBox)?.text.toString()

        }

        query.findInBackground(object : FindCallback<posts> {
            override fun done(posts: MutableList<posts>?, e: ParseException?){
                if(e != null){
                    Log.e(FeedFragment.TAG, "Error fetching posts")
                } else {
                    if (posts != null){
                        for(post in posts){
                            Log.i(FeedFragment.TAG, "Post:" + post.getPrompt()+ ", username: "+ post.getUser()?.username)
                        }
                        allPosts.clear()
                        allPosts.addAll(posts)
                        adapter.notifyDataSetChanged()
                        //TODO: Implement the logic to set the swipecontainer to stop spinning around like its really silly for spinning around really
                        //swipeContainer.setRefreshing(false)
                    }
                }

            }
        })
    }
    companion object{
        const val TAG = "FeedFragment"
    }


}