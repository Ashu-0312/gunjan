package app.gunjan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import app.gunjan.R
import app.gunjan.adapters.CommunityListAdapter
import kotlinx.android.synthetic.main.activity_community_list.*

class CommunityListActivity : AppCompatActivity() {
    private var list: ArrayList<String> = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_list)
        initData()
    }

    private fun initData() {

        back.setOnClickListener { finish() }

        addCommunity.setOnClickListener {
            startActivity(Intent(this, AddCommunityActivity::class.java))
        }
        list.add("")
        list.add("")
        list.add("")

        var communityAdapter = CommunityListAdapter(
            this, list
        )
        var layoutManager: LinearLayoutManager? = LinearLayoutManager(this)
        communityRecycler!!.layoutManager = layoutManager
        communityRecycler!!.adapter = communityAdapter
    }
}