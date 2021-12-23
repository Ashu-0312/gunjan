package app.gunjan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.adapters.DonateAdapter
import app.gunjan.adapters.FollowingAdapter

class DonationFragment : Fragment() {
    private var donationRecycler: RecyclerView? = null
    private var list: ArrayList<String> = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_donation, container, false)
        donationRecycler=view.findViewById(R.id.donate_recycler)
        initData()
        return view
    }

    private fun initData() {
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        var donationAdapter = DonateAdapter(
            context, list
        )
        var layoutManager: LinearLayoutManager? = LinearLayoutManager(context)
        donationRecycler!!.layoutManager = layoutManager
        donationRecycler!!.adapter = donationAdapter
    }
}