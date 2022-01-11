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
import app.gunjan.activities.CommunityDetailsActivity
import app.gunjan.entity.CommunityListResponse
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.collections.ArrayList

class JoinedCommunitiesAdapter(
    var context: Context?,
    data: ArrayList<CommunityListResponse.DataBean.CommunityListBean>
) : RecyclerView.Adapter<JoinedCommunitiesAdapter.ViewHolder>() {
    private var data: ArrayList<CommunityListResponse.DataBean.CommunityListBean> = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.joinedcommunity_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            context?.let {
                Glide.with(it).load(data[position].image).placeholder(R.drawable.user_avatar).into(
                    holder!!.pic!!
                )
            }
            holder.title!!.text=data[position].title
            holder.about!!.text=data[position].about
        }catch (e:Exception){}

        holder.itemView!!.setOnClickListener {
            var intent = Intent(context,CommunityDetailsActivity::class.java)
            intent.putExtra("id",data[position].id.toString())
            context!!.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var requests: TextView? =null
        var pic: CircleImageView? =null
        var title: TextView? =null
        var about: TextView? =null
        init {
            requests=itemView.findViewById(R.id.requests)
            pic=itemView.findViewById(R.id.pic)
            title=itemView.findViewById(R.id.title)
            about=itemView.findViewById(R.id.about)
        }
    }

}