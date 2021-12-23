package app.gunjan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.adapters.FollowersAdapter
import app.gunjan.adapters.FollowingAdapter

class FollowingListFragment : Fragment() {
    private var followingRecycler: RecyclerView? = null
    private var list: ArrayList<String> = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_following_list, container, false)
        followingRecycler=view.findViewById(R.id.following_recycler)
        initData()
        return view
    }

    private fun initData() {
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        var followingAdapter = FollowingAdapter(
            context, list
        )
        var layoutManager: LinearLayoutManager? = LinearLayoutManager(context)
        followingRecycler!!.layoutManager = layoutManager
        followingRecycler!!.adapter = followingAdapter
    }
}