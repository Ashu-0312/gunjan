package app.gunjan.adapters

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.activities.HomeActivity
import app.gunjan.activities.JoinCommunityActivity
import java.util.*

class CommunityListAdapter(
    var context: Context?,
    data: ArrayList<String>
) : RecyclerView.Adapter<CommunityListAdapter.ViewHolder>() {
    private var data: ArrayList<String> = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.community_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder!!.itemView.setOnClickListener {
            var intent = Intent(context, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context!!.startActivity(intent)
        }

        holder.sendRequest!!.setOnClickListener {
            context!!.startActivity(Intent(context,JoinCommunityActivity::class.java))
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sendRequest: LinearLayout? =null
        init {
            sendRequest=itemView.findViewById(R.id.send_request)
        }
    }

}