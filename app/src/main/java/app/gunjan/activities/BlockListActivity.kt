package app.gunjan.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import app.gunjan.R
import app.gunjan.adapters.BlockedListAdapter
import app.gunjan.adapters.NotificationListAdapter
import kotlinx.android.synthetic.main.activity_block_list.*
import kotlinx.android.synthetic.main.activity_block_list.back
import kotlinx.android.synthetic.main.activity_notification.*

class BlockListActivity : AppCompatActivity() {
    private var list:ArrayList<String> = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block_list)
        initData()
    }

    private fun initData() {
        back.setOnClickListener { finish() }

        list.add("")
        list.add("")
        list.add("")
        list.add("")
        var blockAdapter = BlockedListAdapter(
            this, list
        )
        var layoutManager: LinearLayoutManager? = LinearLayoutManager(this)
        blockRecycler!!.layoutManager = layoutManager
        blockRecycler!!.adapter = blockAdapter
    }
}