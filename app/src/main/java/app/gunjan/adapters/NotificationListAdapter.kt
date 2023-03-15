package app.gunjan.adapters

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.activities.MyCommunitesActivity
import app.gunjan.activities.RequestListActivity
import app.gunjan.entity.NotificationListResponse
import app.gunjan.utill.ProjectUtill
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NotificationListAdapter(
    var context: Context?,
    data: ArrayList<NotificationListResponse.DataBean.NotificationBean>,
) : RecyclerView.Adapter<NotificationListAdapter.ViewHolder>() {
    private var data: ArrayList<NotificationListResponse.DataBean.NotificationBean> = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.notification_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.title!!.text = data[position].body
            val c = Calendar.getInstance().time
            println("Current time => $c")
            var format = SimpleDateFormat("yyyy-MM-dd")
            val date1 = format.parse(data[position].createdAt)
            val date2 = format.format(date1)
            format =
                if (date2.endsWith("01") && !date2.endsWith("11")) SimpleDateFormat("MMM d'st', yyyy") else if (date2.endsWith(
                        "02"
                    ) && !date2.endsWith("12")
                ) SimpleDateFormat("MMM d'nd', yyyy") else if (date2.endsWith("03") && !date2.endsWith(
                        "13"
                    )
                ) SimpleDateFormat(" MMM d'rd', yyyy") else SimpleDateFormat("MMM d'th', yyyy")
            val yourDate = format.format(date1)
            val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            input.timeZone = TimeZone.getTimeZone("GMT")
            val output = SimpleDateFormat("HH:mm")

            var d: Date? = null
            d = input.parse(data[position].createdAt)

            val formatted = output.format(d)

            holder.time!!.text = "$yourDate " + ProjectUtill.getFormatedDateTime(
                "$formatted:00",
                "HH:mm:ss",
                "hh:mm a"
            )
        } catch (e: Exception) {
        }

        holder.itemView!!.setOnClickListener {
            if (data[position].body.contains("sent a request")) {
                if (data[position].requestId != null) {
                    val intent = Intent(context, RequestListActivity::class.java)
                    intent.putExtra("community_id", data[position].requestId)
                    context!!.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView? = null
        var time: TextView? = null

        init {
            title = itemView.findViewById(R.id.title)
            time = itemView.findViewById(R.id.time)
        }
    }

}