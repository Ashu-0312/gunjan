package app.gunjan.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import app.gunjan.R
import app.gunjan.adapters.JoinedEventUsersAdapter
import kotlinx.android.synthetic.main.activity_joined_event_user_list.*

class JoinedEventUserListActivity : AppCompatActivity() {
    private var list:ArrayList<String> = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_joined_event_user_list)
        initData()
    }

    private fun initData() {
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        back.setOnClickListener { finish() }
        var listAdapter = JoinedEventUsersAdapter(this,list)
        userRecycler.layoutManager = LinearLayoutManager(this)
        userRecycler.adapter = listAdapter
    }
}