package app.gunjan.adapters

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.activities.ChatActivity
import app.gunjan.activities.HomeActivity
import app.gunjan.activities.OthersProfileActivity
import app.gunjan.entity.MemberListResponse
import app.gunjan.utill.FCSharedPreferances
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.collections.ArrayList

class AdminMembersAdapter(
    var context: Context?,
    data: ArrayList<MemberListResponse.DataBean.MemberListBean>
) : RecyclerView.Adapter<AdminMembersAdapter.ViewHolder>() {
    private var data: ArrayList<MemberListResponse.DataBean.MemberListBean> = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.admin_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            context?.let {
                Glide.with(it).load(data[position].userDetails.image).placeholder(R.drawable.user_avatar).into(
                    holder.profilePic!!
                )
            }
            holder.name!!.text=data[position].userDetails.first_name+" "+data[position].userDetails.last_name
            if (FCSharedPreferances.getSharedPreferance(context).useR_ID.toString()==data[position].userId.toString()){
                holder.chatIcon!!.visibility=View.GONE
            }else{
                holder.chatIcon!!.visibility=View.VISIBLE
            }
            holder.about!!.text = data[position].userDetails.last_message.toString()
        }catch (e:Exception){}
        holder!!.itemView.setOnClickListener {
            if (FCSharedPreferances.getSharedPreferance(context).useR_ID.toString()==data[position].userId.toString()){
                FCSharedPreferances.getSharedPreferance(context).status =
                    "edit"
                FCSharedPreferances.getSharedPreferance(context).otheR_ID=data[position].userId.toString()
                var intent = Intent(
                    context,
                    HomeActivity::class.java
                )
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context!!.startActivity(intent)
            }else {
                FCSharedPreferances.getSharedPreferance(context).otheR_ID = data[position].userId.toString()
                var intent = Intent(context, ChatActivity::class.java)
                intent.putExtra("pic",data[position].userDetails.image)
                intent.putExtra("name",data[position].userDetails.first_name+" "+data[position].userDetails.last_name)
                intent.putExtra("otherId",data[position].userId.toString())
                intent.putExtra("type","individual_chat")
                intent.putExtra("channelId","sd")
                context!!.startActivity(intent)
            }
        }

        holder!!.profilePic!!.setOnClickListener {
            if (FCSharedPreferances.getSharedPreferance(context).useR_ID.toString()==data[position].userId.toString()){
                FCSharedPreferances.getSharedPreferance(context).status =
                    "edit"
                FCSharedPreferances.getSharedPreferance(context).otheR_ID=data[position].userId.toString()
                var intent = Intent(
                    context,
                    HomeActivity::class.java
                )
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context!!.startActivity(intent)
            }else {
                FCSharedPreferances.getSharedPreferance(context).otheR_ID=data[position].userId.toString()
                context!!.startActivity(Intent(context, OthersProfileActivity::class.java))
            }
        }
    }

    fun setMessage(l: Int, messageBody: String?) {
        data[l].userDetails.last_message = messageBody
        notifyItemChanged(l)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var profilePic: CircleImageView? =null
        var name: TextView? =null
        var about: TextView? =null
        var chatIcon: ImageView? =null
        init {
            profilePic=itemView.findViewById(R.id.pic)
            name=itemView.findViewById(R.id.name)
            about=itemView.findViewById(R.id.about)
            chatIcon=itemView.findViewById(R.id.chat_icon)
        }
    }

}