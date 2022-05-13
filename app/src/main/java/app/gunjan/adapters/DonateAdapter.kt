package app.gunjan.adapters

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.activities.HomeActivity
import app.gunjan.entity.DonationListResponse
import java.util.*

class DonateAdapter(
    var context: Context?,
    data: MutableList<DonationListResponse.DataBean.DonationListBean>
) : RecyclerView.Adapter<DonateAdapter.ViewHolder>() {
    private var data: MutableList<DonationListResponse.DataBean.DonationListBean> = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.donate_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.communityName!!.text = data[position].receiver_community_details.title
        holder.coin!!.text = data[position].total_coins.toString()
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var communityName: TextView? =null
        var coin: TextView? =null
        init {
            communityName=itemView.findViewById(R.id.community_name)
            coin=itemView.findViewById(R.id.coin)
        }
    }

}