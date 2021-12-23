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
import java.util.*

class OtherPostsAdapter(
    var context: Context?,
    data: ArrayList<String>
) : RecyclerView.Adapter<OtherPostsAdapter.ViewHolder>() {
    private var data: ArrayList<String> = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.otherpost_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.showMore!!.setOnClickListener(View.OnClickListener {
            if (holder.showMore!!.getText().toString().equals("Showmore...")) {
                holder.description!!.setMaxLines(Int.MAX_VALUE) //your TextView
                holder.showMore!!.text = "Showless"
            } else {
                holder.description!!.setMaxLines(3) //your TextView
                holder.showMore!!.text = "Showmore..."
            }
        })
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var description: TextView? =null
        var showMore: TextView? =null
        init {
            description=itemView.findViewById(R.id.description)
            showMore=itemView.findViewById(R.id.show_more)
        }
    }

}