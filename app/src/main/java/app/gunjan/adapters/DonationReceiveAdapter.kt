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
import app.gunjan.entity.ReceivedCoinListResponse
import java.util.*

class DonationReceiveAdapter(
    var context: Context?,
    data: MutableList<ReceivedCoinListResponse.DataBean.DonationListBean>
) : RecyclerView.Adapter<DonationReceiveAdapter.ViewHolder>() {
    private var data: MutableList<ReceivedCoinListResponse.DataBean.DonationListBean> = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.donatereceive_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.communityName!!.text = data[position].user_details.first_name+" "+data[position].user_details.last_name
            holder.coin!!.text = data[position].total_coins.toString()
        }catch (e:Exception){}
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