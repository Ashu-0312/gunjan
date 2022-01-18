package app.gunjan.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.entity.InterestListResponse
import app.gunjan.entity.ShowInterestModel
import kotlin.collections.ArrayList

class AddEditInterestAdapter(
    var context: Context?,
    data: ArrayList<InterestListResponse.DataBean.InterestBean>
) : RecyclerView.Adapter<AddEditInterestAdapter.ViewHolder>() {
    private var data: ArrayList<InterestListResponse.DataBean.InterestBean> = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.editinterest_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name!!.text=data[position].name
        if (data[position].added) {
            holder.layout!!.background = context!!.getDrawable(R.drawable.button_bg)
            holder.name!!.setTextColor(context!!.resources.getColor(R.color.white))
        } else {
            holder.layout!!.background = context!!.getDrawable(R.drawable.circle_white)
            holder.name!!.setTextColor(context!!.resources.getColor(R.color.tab_txt))
        }
        holder.layout!!.setOnClickListener {
            data[position].added = !data[position].added
            if (data[position].added) {
                holder.layout!!.background = context!!.resources.getDrawable(R.drawable.button_bg)
                holder.name!!.setTextColor(context!!.resources.getColor(R.color.white))
            } else {
                holder.name!!.setTextColor(context!!.resources.getColor(R.color.txt_color))
                holder.layout!!.background =
                    context!!.resources.getDrawable(R.drawable.circle_white)
            }
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    fun getSelectedData(): ArrayList<ShowInterestModel>? {
        val jsonArray = ArrayList<ShowInterestModel>()
        try {
            for (i in data.indices) {
                if (data[i].added) {
                    jsonArray.add(ShowInterestModel(data[i].name.toString(),data[i].id.toString()))
                }
            }
        } catch (e: Exception) {
        }
        return jsonArray
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var layout: LinearLayout? = null
        var name: TextView? = null

        init {
            layout = itemView.findViewById(R.id.layout)
            name = itemView.findViewById(R.id.interest_txt)
        }
    }

}