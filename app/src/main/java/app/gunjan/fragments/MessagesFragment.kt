package app.gunjan.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.adapters.AdminMembersAdapter
import app.gunjan.adapters.MessagesAdapter
import app.gunjan.adapters.ReasonListAdapter

class MessagesFragment : Fragment() {
    private var listRecycler: RecyclerView? = null
    private var list: ArrayList<String> = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_messages, container, false)
        listRecycler = view.findViewById(R.id.messages_recycler)
        initData()
        return view
    }

    private fun initData() {
        list.add("")
        list.add("")
        list.add("")
        list.add("")

        var messagesAdapter = MessagesAdapter(
            context, list
        )
        var layoutManager: LinearLayoutManager? = LinearLayoutManager(context)
        listRecycler!!.layoutManager = layoutManager
        listRecycler!!.adapter = messagesAdapter
    }

}