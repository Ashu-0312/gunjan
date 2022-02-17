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
import app.gunjan.entity.MemberListResponse
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*
import kotlin.collections.ArrayList

class ActiveMembersAdapter(
    var context: Context?,
    data: ArrayList<MemberListResponse.DataBean.MemberListBean>
) : RecyclerView.Adapter<ActiveMembersAdapter.ViewHolder>() {
    private var data: ArrayList<MemberListResponse.DataBean.MemberListBean> = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.member_item, parent, false)
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
            holder.about!!.text=data[position].userDetails.about
        }catch (e:Exception){}
        holder!!.itemView.setOnClickListener {
          /*  var intent = Intent(context, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context!!.startActivity(intent)*/
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var profilePic: CircleImageView? =null
        var name: TextView? =null
        var about: TextView? =null
        init {
            profilePic=itemView.findViewById(R.id.pic)
            name=itemView.findViewById(R.id.name)
            about=itemView.findViewById(R.id.about)
        }
    }

}