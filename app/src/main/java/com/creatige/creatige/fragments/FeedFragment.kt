package com.creatige.creatige.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creatige.creatige.PostAdapter
import com.creatige.creatige.R
import com.creatige.creatige.posts
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser


open class FeedFragment : Fragment() {
    lateinit var postsRecyclerView: RecyclerView
    lateinit var autoCompleteTextView : AutoCompleteTextView
    lateinit var adapter: PostAdapter
    lateinit var userSelected : String
    var allPosts: MutableList<posts> = mutableListOf()
    var allUsernames: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        //Accessing the views on the screen
        postsRecyclerView = view.findViewById<RecyclerView>(R.id.postRecyclerView)
        autoCompleteTextView = view.findViewById<AutoCompleteTextView>(R.id.searchBox)

        //setting up the adapter to allow Posts to be updated from within the query
        adapter = PostAdapter(requireContext(), allPosts)
        postsRecyclerView.adapter = adapter

        //allows the recyclerView to display posts fetched from the query
        postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        queryPosts()  //fills up the List with posts

        //get all username handles as strings
        allUsernames = queryUsernames()

        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(view.context, android.R.layout.simple_dropdown_item_1line, allUsernames)
        autoCompleteTextView.setAdapter(arrayAdapter)


        //takes input from searchBox when the searchButton is clicked
        view.findViewById<ImageButton>(R.id.searchButton)?.setOnClickListener{
            Toast.makeText(view.context, "Item selected was: $autoCompleteTextView", Toast.LENGTH_SHORT).show()
            val search = autoCompleteTextView.text.toString()
            searchDB(search)
        }
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


    open fun queryUsernames(): MutableList<String> {
        var usernames: MutableList<String> = mutableListOf()
        val query: ParseQuery<ParseUser> = ParseQuery.getQuery("_User")
        val users = query.find()
        if(users != null){
            for (user in users){
                //fill up the list with all of the available usernames to be selected from
                usernames.add(user.fetchIfNeeded().username.toString())
            }
        }
        return usernames
    }


    open fun searchDB(searchItem : String){
        val userQuery: ParseQuery<ParseUser> = ParseQuery.getQuery("_User")
        userQuery.whereEqualTo("username", searchItem)
        val user = userQuery.find() // all users that match the search input from user

        if(user != null){
            val query: ParseQuery<posts> = ParseQuery.getQuery(posts::class.java)
            query.whereEqualTo("user", user.get(0)) //gets the first match from the first query
            val posts = query.find()//fetches all posts that match the query
            if (posts != null){
                allPosts.clear()
                allPosts.addAll(posts)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), "User has no posts", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
        }
    }

    companion object{
        const val TAG = "FeedFragment"
    }
}