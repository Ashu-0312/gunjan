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
import app.gunjan.entity.AllMembersListResponse
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class MemberListAdapter(
    var context: Context?,
    data: ArrayList<AllMembersListResponse.DataBean.UserListBean>
) : RecyclerView.Adapter<MemberListAdapter.ViewHolder>() {
    private var data: ArrayList<AllMembersListResponse.DataBean.UserListBean> = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.memberlist_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.checkbox!!.isSelected = data[position].selected
            context?.let { Glide.with(it).load(data[position].userDetails.image).placeholder(R.drawable.user_avatar).into(
                holder.pic!!
            ) }
            holder.name!!.text=data[position].userDetails.first_name+" "+data[position].userDetails.last_name
            holder.about!!.text=data[position].userDetails.about
        }catch (e: Exception){}

        holder.checkbox!!.setOnClickListener(View.OnClickListener {
            data[position].selected = !data[position].selected
            notifyItemChanged(position)

        })


    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pic: CircleImageView? =null
        var name: TextView? =null
        var about: TextView? =null
        var checkbox: ImageView? =null
        init {
            pic=itemView.findViewById(R.id.pic)
            name=itemView.findViewById(R.id.name)
            about=itemView.findViewById(R.id.about)
            checkbox=itemView.findViewById(R.id.cb_selector)
        }
    }

}