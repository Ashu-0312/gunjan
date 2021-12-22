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
import app.gunjan.activities.Model
import kotlin.collections.ArrayList

class AddEditInterestAdapter(
    var context: Context?,
    data: ArrayList<Model>
) :RecyclerView.Adapter<AddEditInterestAdapter.ViewHolder>() {
    private var data: ArrayList<Model> = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.editinterest_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (data[position].selected){
            holder.layout!!.background=context!!.getDrawable(R.drawable.button_bg)
            holder.name!!.setTextColor(context!!.resources.getColor(R.color.white))
        }else{
            holder.layout!!.background=context!!.getDrawable(R.drawable.circle_white)
            holder.name!!.setTextColor(context!!.resources.getColor(R.color.tab_txt))
        }
        holder.layout!!.setOnClickListener {
            data[position].selected = !data[position].selected
            if(data[position].selected) {
                holder.layout!!.background = context!!.resources.getDrawable(R.drawable.button_bg)
                holder.name!!.setTextColor(context!!.resources.getColor(R.color.white))
            }else{
                holder.name!!.setTextColor(context!!.resources.getColor(R.color.txt_color))
                holder.layout!!.background = context!!.resources.getDrawable(R.drawable.circle_white)
            }
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var layout: LinearLayout? =null
        var name: TextView? =null
        init {
            layout=itemView.findViewById(R.id.layout)
            name=itemView.findViewById(R.id.interest_txt)
        }
    }

}