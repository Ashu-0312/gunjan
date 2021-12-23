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
import app.gunjan.adapters.ActiveMembersAdapter
import app.gunjan.adapters.FollowersAdapter

class FollowersListFragment : Fragment() {
    private var followerRecycler: RecyclerView? = null
    private var list: ArrayList<String> = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_followers_list, container, false)
        followerRecycler=view.findViewById(R.id.follower_recycler)
        initData()
        return view
    }

    private fun initData() {
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        var followerAdapter = FollowersAdapter(
            context, list
        )
        var layoutManager: LinearLayoutManager? = LinearLayoutManager(context)
        followerRecycler!!.layoutManager = layoutManager
        followerRecycler!!.adapter = followerAdapter
    }
}