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
import app.gunjan.activities.OthersProfileActivity
import app.gunjan.entity.FollowingListResponse
import app.gunjan.utill.FCSharedPreferances
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.Exception
import kotlin.collections.ArrayList

class FollowingAdapter(
    var context: Context?,
    data: ArrayList<FollowingListResponse.DataBean.UserListBean>
) : RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {
    private var data: ArrayList<FollowingListResponse.DataBean.UserListBean> = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.follower_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            context?.let {
                Glide.with(it).load(data[position].partnerDetails.image)
                    .placeholder(R.drawable.user_avatar).into(
                        holder.profilePic!!
                    )
            }
            holder.name!!.text=data[position].partnerDetails.first_name+" "+data[position].partnerDetails.last_name
        } catch (e: Exception) { }

        holder!!.itemView!!.setOnClickListener {
            if (FCSharedPreferances.getSharedPreferance(context).useR_ID.toString()==data[position].partnerDetails.id.toString()){
                FCSharedPreferances.getSharedPreferance(context).status =
                    "edit"
                FCSharedPreferances.getSharedPreferance(context).otheR_ID=data[position].partnerDetails.id.toString()
                var intent = Intent(
                    context,
                    HomeActivity::class.java
                )
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context!!.startActivity(intent)
            }else {
                FCSharedPreferances.getSharedPreferance(context).otheR_ID=data[position].partnerDetails.id.toString()
                context!!.startActivity(Intent(context, OthersProfileActivity::class.java))
            }
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var profilePic: CircleImageView? = null
        var name: TextView? = null

        init {
            profilePic = itemView.findViewById(R.id.pic)
            name = itemView.findViewById(R.id.name)
        }
    }

}