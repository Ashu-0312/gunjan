package app.gunjan.adapters

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.entity.NotificationListResponse
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NotificationListAdapter(
    var context: Context?,
    data: ArrayList<NotificationListResponse.DataBean.NotificationBean>,
) :RecyclerView.Adapter<NotificationListAdapter.ViewHolder>() {
    private var data: ArrayList<NotificationListResponse.DataBean.NotificationBean> = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.notification_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.title!!.text=data[position].body
            val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val output = SimpleDateFormat("HH:mm a")

            var d: Date? = null
            d = input.parse(data[position].createdAt)

            val formatted = output.format(d)
            Log.i("DATE", "" + formatted)
            holder.time!!.text = formatted.toString()
        }catch (e:Exception){}
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView? =null
        var time: TextView? =null
        init {
            title=itemView.findViewById(R.id.title)
            time=itemView.findViewById(R.id.time)
        }
    }

}