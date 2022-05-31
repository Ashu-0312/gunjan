package app.gunjan.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import kotlin.collections.ArrayList

class FaqListAdapter(
    var context: Context?,
    data: ArrayList<String>
) : RecyclerView.Adapter<FaqListAdapter.ViewHolder>() {
    private var data: ArrayList<String> = data
    private var currentPosition = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.faq_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
          /*  holder.question!!.text = data[position].quesion
            holder.answer!!.text = data[position].answer*/
        }catch (e:Exception){}

        holder.answer!!.visibility = View.GONE
        holder.showDetails!!.setImageDrawable(context!!.getDrawable(R.drawable.red_drop_down))
        //if the position is equals to the item position which is to be expanded
        //if the position is equals to the item position which is to be expanded
        if (currentPosition == position) {
            //creating an animation
            val slideDown = AnimationUtils.loadAnimation(context, R.anim.animation)
           // holder.showDetails!!.setImageDrawable(context!!.getDrawable(R.drawable.red_drop_up))
            holder.showDetails!!.setImageDrawable(context!!.getDrawable(R.drawable.red_drop_down))
            //toggling visibility
            holder.answer!!.visibility = View.VISIBLE

            //adding sliding effect
            holder.answer!!.startAnimation(slideDown)
        }

        holder.showSubService!!.setOnClickListener { //getting the position of the item to expand it
            currentPosition = position

            //reloding the list
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var question:TextView?=null
        var answer:TextView?=null
        var showSubService:LinearLayout?=null
        var showDetails:ImageView?=null
        init {
            question = itemView.findViewById(R.id.question)
            answer = itemView.findViewById(R.id.answer)
            showSubService = itemView.findViewById(R.id.lefLayout)
            showDetails = itemView.findViewById(R.id.show_details)
        }
    }

}