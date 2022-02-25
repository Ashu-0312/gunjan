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
import app.gunjan.activities.ChatActivity
import app.gunjan.activities.HomeActivity
import app.gunjan.entity.GroupListResponse
import app.gunjan.utill.FCSharedPreferances
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.collections.ArrayList

class MessagesAdapter(
    var context: Context?,
    data: ArrayList<GroupListResponse.DataBean.DefaultGroupListBean.ParticipantsBean>
) : RecyclerView.Adapter<MessagesAdapter.ViewHolder>() {
    private var data: ArrayList<GroupListResponse.DataBean.DefaultGroupListBean.ParticipantsBean> =
        data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.messages_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            context?.let {
                Glide.with(it).load(data[position].participants_details.image)
                    .placeholder(R.drawable.user_avatar).into(
                        holder!!.pic!!
                    )
            }
            holder.name!!.text=data[position].participants_details.first_name+" "+data[position].participants_details.last_name
            holder.about!!.text=data[position].participants_details.about
        } catch (e: Exception) {
        }
        holder!!.itemView.setOnClickListener {
            if (FCSharedPreferances.getSharedPreferance(context).useR_ID.toString()==data[position].participants_details.id.toString()){
                FCSharedPreferances.getSharedPreferance(context).status =
                    "edit"
                FCSharedPreferances.getSharedPreferance(context).otheR_ID=data[position].participants_details.id.toString()
                var intent = Intent(
                    context,
                    HomeActivity::class.java
                )
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context!!.startActivity(intent)
            }else {
                var intent = Intent(context, ChatActivity::class.java)
                intent.putExtra("pic",data[position].participants_details.image
                )
                intent.putExtra("name",data[position].participants_details.first_name+" "+data[position].participants_details.last_name)
                intent.putExtra("otherId",data[position].participants_details.id.toString())
                intent.putExtra("type","individual_chat")
                intent.putExtra("channelId","djg")
                context!!.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pic: CircleImageView? = null
        var name: TextView? = null
        var about: TextView? = null

        init {
            pic = itemView.findViewById(R.id.pic)
            name = itemView.findViewById(R.id.name)
            about = itemView.findViewById(R.id.about)
        }
    }

}