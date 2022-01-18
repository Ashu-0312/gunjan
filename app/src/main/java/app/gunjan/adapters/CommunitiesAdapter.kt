package app.gunjan.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.activities.CommunityDetailsActivity
import app.gunjan.activities.SwitchCommunityActivity
import app.gunjan.entity.CommunityListResponse
import app.gunjan.entity.PrivacyPolicyResponse
import app.gunjan.entity.SwitchCommunityResponse
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class CommunitiesAdapter(
    var context: Context?,
    data: ArrayList<CommunityListResponse.DataBean.CommunityListBean>
) : RecyclerView.Adapter<CommunitiesAdapter.ViewHolder>() {
    private var data: ArrayList<CommunityListResponse.DataBean.CommunityListBean> = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.communities_item, parent, false)
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
            if (data[position].activeCommunity){
                holder.status!!.text="Active"
            }else{
                holder.status!!.text="Switch"
            }

            holder.status!!.setOnClickListener {
                if (!data[position].activeCommunity){
                    val myDialog = ProjectUtill.showProgressDialog(context)
                    context?.let { it1 ->
                        WebServiceRequest.getInstance().switchCommunity(
                            it1,data[position].id.toString(),
                            object : Callback<SwitchCommunityResponse> {
                                override fun onResponse(
                                    call: Call<SwitchCommunityResponse>,
                                    response: Response<SwitchCommunityResponse>
                                ) {
                                    myDialog.dismiss()
                                    if (response != null) {
                                        if (response.isSuccessful) {
                                            if (response.body()!!.code == 1) {
                                                (context as SwitchCommunityActivity).resetAdapter()
                                            } else {
                                                ProjectUtill.printMessage(
                                                    (context as Activity).window.decorView,
                                                    response.body()?.message
                                                )
                                            }
                                        } else {
                                            ProjectUtill.printErrorMessage(
                                                (context as Activity).window.decorView,
                                                ""
                                            )
                                        }
                                    } else {
                                        ProjectUtill.printErrorMessage(
                                            (context as Activity).window.decorView,
                                            ""
                                        )
                                    }
                                }

                                override fun onFailure(
                                    call: Call<SwitchCommunityResponse>,
                                    t: Throwable
                                ) {
                                    myDialog.dismiss()
                                    ProjectUtill.printErrorMessage(
                                        (context as Activity).window.decorView,
                                        ""
                                    )
                                }
                            })
                    }
                }
            }

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
        var status: TextView? =null
        var pic: CircleImageView? =null
        var title: TextView? =null
        var about: TextView? =null
        init {
            status=itemView.findViewById(R.id.status)
            pic=itemView.findViewById(R.id.pic)
            title=itemView.findViewById(R.id.title)
            about=itemView.findViewById(R.id.about)
        }
    }

}