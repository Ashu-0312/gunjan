package app.gunjan.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.activities.AddCommunityActivity
import app.gunjan.adapters.CommunityListAdapter

class CommunityListFragment : Fragment() {
    private var list: ArrayList<String> = ArrayList<String>()
    private var communityRecycler:RecyclerView?=null
    private var addCommunity:ImageView?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_community_list, container, false)
        communityRecycler=view.findViewById(R.id.community_recycler)
        addCommunity=view.findViewById(R.id.add_community)
        initData()
        return view
    }

    private fun initData() {
        addCommunity!!.setOnClickListener {
            startActivity(Intent(context, AddCommunityActivity::class.java))
        }
        list.add("")
        list.add("")
        list.add("")

        var communityAdapter = CommunityListAdapter(
            context, list
        )
        var layoutManager: LinearLayoutManager? = LinearLayoutManager(context)
        communityRecycler!!.layoutManager = layoutManager
        communityRecycler!!.adapter = communityAdapter
    }

}