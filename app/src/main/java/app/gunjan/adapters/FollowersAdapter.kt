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
import app.gunjan.entity.FollowerListResponse
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.Exception
import kotlin.collections.ArrayList

class FollowersAdapter(
    var context: Context?,
    data: ArrayList<FollowerListResponse.DataBean.UserListBean>
) : RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {
    private var data: ArrayList<FollowerListResponse.DataBean.UserListBean> = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.follower_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            context?.let {
                Glide.with(it).load(data[position].userDetails.image)
                    .placeholder(R.drawable.user_avatar).into(
                        holder.profilePic!!
                    )
            }
            holder.name!!.text=data[position].userDetails.first_name+" "+data[position].userDetails.last_name
            holder.description!!.text=data[position].userDetails.about
        } catch (e: Exception) {
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var profilePic: CircleImageView? = null
        var name: TextView? = null
        var description: TextView? = null

        init {
            profilePic = itemView.findViewById(R.id.pic)
            name = itemView.findViewById(R.id.name)
            description = itemView.findViewById(R.id.description)
        }
    }

}