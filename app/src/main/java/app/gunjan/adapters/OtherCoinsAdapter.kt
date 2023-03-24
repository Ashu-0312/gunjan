package app.gunjan.adapters

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.entity.DonateCoinResponse
import app.gunjan.fragments.HomeFragment
import app.gunjan.fragments.OthersPostFragment
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class OtherCoinsAdapter(
    var context: Context?,
    data: ArrayList<String>,
    fragment: OthersPostFragment
) : RecyclerView.Adapter<OtherCoinsAdapter.ViewHolder>() {
    private var data: ArrayList<String> = data
    private var fragment: OthersPostFragment = fragment
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.coin_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name!!.text = data[position].toString()
        if (position == 0) {
            holder.pic!!.setImageDrawable(context!!.resources.getDrawable(R.drawable.coin1))
        } else if (position == 1) {
            holder.pic!!.setImageDrawable(context!!.resources.getDrawable(R.drawable.coin2))
        } else if (position == 2) {
            holder.pic!!.setImageDrawable(context!!.resources.getDrawable(R.drawable.coin3))
        } else if (position == 3) {
            holder.pic!!.setImageDrawable(context!!.resources.getDrawable(R.drawable.coin4))
        } else if (position == 4) {
            holder.pic!!.setImageDrawable(context!!.resources.getDrawable(R.drawable.coin5))
        } else if (position == 5) {
            holder.pic!!.setImageDrawable(context!!.resources.getDrawable(R.drawable.coin6))
        } else if (position == 6) {
            holder.pic!!.setImageDrawable(context!!.resources.getDrawable(R.drawable.coin7))
        } else if (position == 7) {
            holder.pic!!.setImageDrawable(context!!.resources.getDrawable(R.drawable.coin8))
        } else if (position == 8) {
            holder.pic!!.setImageDrawable(context!!.resources.getDrawable(R.drawable.coin9))
        } else if (position == 9) {
            holder.pic!!.setImageDrawable(context!!.resources.getDrawable(R.drawable.coin10))
        } else if (position == 10) {
            holder.pic!!.setImageDrawable(context!!.resources.getDrawable(R.drawable.coin11))
        } else if (position == 11) {
            holder.pic!!.setImageDrawable(context!!.resources.getDrawable(R.drawable.coin12))
        } else if (position == 12) {
            holder.pic!!.setImageDrawable(context!!.resources.getDrawable(R.drawable.coin13))
        } else if (position == 13) {
            holder.pic!!.setImageDrawable(context!!.resources.getDrawable(R.drawable.coin14))
        } else if (position == 14) {
            holder.pic!!.setImageDrawable(context!!.resources.getDrawable(R.drawable.coin15))
        } else if (position == 15) {
            holder.pic!!.setImageDrawable(context!!.resources.getDrawable(R.drawable.coin16))
        } else if (position == 16) {
            holder.pic!!.setImageDrawable(context!!.resources.getDrawable(R.drawable.coin17))
        } else if (position == 17) {
            holder.pic!!.setImageDrawable(context!!.resources.getDrawable(R.drawable.coin18))
        } else if (position == 18) {
            holder.pic!!.setImageDrawable(context!!.resources.getDrawable(R.drawable.coin19))
        } else if (position == 19) {
            holder.pic!!.setImageDrawable(context!!.resources.getDrawable(R.drawable.coin20))
        }
        holder.itemView!!.setOnClickListener {
            if (FCSharedPreferances.getSharedPreferance(context).totaL_COINS.equals("") or FCSharedPreferances.getSharedPreferance(
                    context
                ).totaL_COINS.equals("0")
                or (FCSharedPreferances.getSharedPreferance(
                    context
                ).totaL_COINS.toInt() < data[position].toString().toInt())
            ) {
               fragment.toastDialog()
            } else {
                fragment.donateCoins(data[position].toString())
            }
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView? = null
        var pic: ImageView? = null

        init {
            name = itemView.findViewById(R.id.name)
            pic = itemView.findViewById(R.id.pic)
        }
    }

}