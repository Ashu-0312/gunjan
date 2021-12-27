package app.gunjan.adapters

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.activities.OthersProfileActivity
import app.gunjan.fragments.HomeFragment
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


class HomePostsAdapter(
    var context: Context?,
    data: ArrayList<String>,
    homeFragment: HomeFragment
) : RecyclerView.Adapter<HomePostsAdapter.ViewHolder>() {
    private var data: ArrayList<String> = data
    private var  homeFragment: HomeFragment=homeFragment
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
                holder.description!!.setMaxLines(4) //your TextView
                holder.showMore!!.text = "Showmore..."
            }
        })

        holder.share!!.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBodyText = "Gunjan App"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject here")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText)
            context!!.startActivity(sharingIntent)
        }

        holder.menu!!.setOnClickListener {
            homeFragment.postreportDialog()
        }

        holder.profile!!.setOnClickListener {
            context!!.startActivity(Intent(context, OthersProfileActivity::class.java))
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var description: TextView? =null
        var showMore: TextView? =null
        var share: LinearLayout? =null
        var menu: ImageView? =null
        var profile: CircleImageView? =null
        init {
            description=itemView.findViewById(R.id.description)
            showMore=itemView.findViewById(R.id.show_more)
            share=itemView.findViewById(R.id.share)
            menu=itemView.findViewById(R.id.menu)
            profile=itemView.findViewById(R.id.user_profile)
        }
    }

}