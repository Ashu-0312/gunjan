package app.gunjan.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.entity.ShowInterestModel
import kotlin.collections.ArrayList

class ShowInterestAdapter(
    var context: Context?,
    data: ArrayList<ShowInterestModel>
) : RecyclerView.Adapter<ShowInterestAdapter.ViewHolder>() {
    private var data: ArrayList<ShowInterestModel> = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.interest_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name!!.text=data[position].name
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView? =null
        init {
            name=itemView.findViewById(R.id.interest_txt)
        }
    }

}