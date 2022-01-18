package app.gunjan.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.entity.ReasonListResponse
import app.gunjan.fragments.HomeFragment
import app.gunjan.utill.FCSharedPreferances
import java.util.*

class ReasonList2Adapter(
    var context: Context?,
    data: MutableList<ReasonListResponse.DataBean.ReasonListBean>,
    homeFragment: HomeFragment
) : RecyclerView.Adapter<ReasonList2Adapter.ViewHolder>() {
    private var data: MutableList<ReasonListResponse.DataBean.ReasonListBean> = data
    private var homeFragment: HomeFragment=homeFragment
    var selectedPosition = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.reason_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.reason!!.text=data[position].reason
        if (selectedPosition === position) {
            holder.radio!!.setImageDrawable(context!!.resources.getDrawable(R.drawable.radio_selected))
            FCSharedPreferances.getSharedPreferance(context).reasoN_ID=data[position].id.toString()
        } else {
            holder.radio!!.setImageDrawable(context!!.resources.getDrawable(R.drawable.radio_unselected))
        }

        holder.itemView!!.setOnClickListener {
            FCSharedPreferances.getSharedPreferance(context).reasoN_ID=data[position].id.toString()
            if (data[position].reason.equals("Other")){
                selectedPosition = position
                notifyDataSetChanged()
                homeFragment.showReasonLayout("1")
            }else{
                selectedPosition = position
                notifyDataSetChanged()
                homeFragment.showReasonLayout("2")
            }
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var radio: ImageView? =null
        var reason: TextView? =null
        init {
            radio=itemView.findViewById(R.id.radio_button)
            reason=itemView.findViewById(R.id.reason)
        }
    }

}