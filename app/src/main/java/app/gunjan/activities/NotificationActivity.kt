package app.gunjan.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import app.gunjan.R
import app.gunjan.adapters.NotificationListAdapter
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationActivity : AppCompatActivity() {
    private var list: ArrayList<String> = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        initData()
    }

    private fun initData() {

        back.setOnClickListener { finish() }

        list.add("")
        list.add("")
        list.add("")

        var notificationAdapter = NotificationListAdapter(
            this, list
        )
        var layoutManager: LinearLayoutManager? = LinearLayoutManager(this)
        notificationRecycler!!.layoutManager = layoutManager
        notificationRecycler!!.adapter = notificationAdapter
    }
}