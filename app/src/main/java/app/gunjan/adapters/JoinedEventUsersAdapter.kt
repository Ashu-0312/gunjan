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

class JoinedEventUsersAdapter(
    var context: Context?,
    data: ArrayList<String>
) : RecyclerView.Adapter<JoinedEventUsersAdapter.ViewHolder>() {
    private var data: ArrayList<String> =
        data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.eventjoin_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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