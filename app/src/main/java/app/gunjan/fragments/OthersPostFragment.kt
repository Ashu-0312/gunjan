package app.gunjan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.adapters.NotificationListAdapter
import app.gunjan.adapters.OtherPostsAdapter
import kotlinx.android.synthetic.main.activity_notification.*

class OthersPostFragment : Fragment() {
    private var list:ArrayList<String> = ArrayList<String>()
    private var postRecycler:RecyclerView?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_others_post, container, false)
        postRecycler=view.findViewById(R.id.post_recycler)
        initData()
        return view
    }

    private fun initData() {
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        var postAdapter = OtherPostsAdapter(
            context, list
        )
        var layoutManager: LinearLayoutManager? = LinearLayoutManager(context)
        postRecycler!!.layoutManager = layoutManager
        postRecycler!!.adapter = postAdapter
    }
}