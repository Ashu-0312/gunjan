package app.gunjan.adapters

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.entity.DonateCoinResponse
import app.gunjan.fragments.HomeFragment
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class CoinsAdapter(
    var context: Context?,
    data: ArrayList<String>,
    fragment: HomeFragment
) : RecyclerView.Adapter<CoinsAdapter.ViewHolder>() {
    private var data: ArrayList<String> = data
    private var fragment: HomeFragment = fragment
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.coin_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name!!.text = data[position].toString()
        holder.itemView!!.setOnClickListener {
            if (FCSharedPreferances.getSharedPreferance(context).totaL_COINS.equals("") or FCSharedPreferances.getSharedPreferance(
                    context
                ).totaL_COINS.equals("0")
                or (FCSharedPreferances.getSharedPreferance(
                    context
                ).totaL_COINS.toInt() < data[position].toString().toInt())
            ) {
                Toast.makeText(
                    context,
                    context!!.getString(R.string.please_coins),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                fragment!!.donateCoins(data[position].toString())
            }
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView? = null

        init {
            name = itemView.findViewById(R.id.name)
        }
    }

}