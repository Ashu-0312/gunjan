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
import app.gunjan.activities.OthersProfileActivity
import app.gunjan.fragments.HomeFragment
import java.util.*

class ReasonList2Adapter(
    var context: Context?,
    data: ArrayList<String>,
    homeFragment: HomeFragment
) : RecyclerView.Adapter<ReasonList2Adapter.ViewHolder>() {
    private var data: ArrayList<String> = data
    private var homeFragment: HomeFragment=homeFragment
    var selectedPosition = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.reason_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.reason!!.text=data[position].toString()
        if (selectedPosition === position) {
            holder.radio!!.setImageDrawable(context!!.resources.getDrawable(R.drawable.radio_selected))
        } else {
            holder.radio!!.setImageDrawable(context!!.resources.getDrawable(R.drawable.radio_unselected))
        }

        holder.itemView!!.setOnClickListener {
            if (data[position].equals("Other")){
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