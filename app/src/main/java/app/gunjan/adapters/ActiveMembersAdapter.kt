package app.gunjan.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import app.gunjan.activities.ChatActivity
import app.gunjan.activities.HomeActivity
import app.gunjan.activities.OthersProfileActivity
import app.gunjan.activities.RequestListActivity
import app.gunjan.entity.MakeAdminResponse
import app.gunjan.entity.MemberListResponse
import app.gunjan.fragments.ActiveMembersFragment
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class ActiveMembersAdapter(
    var context: Context?,
    data: ArrayList<MemberListResponse.DataBean.MemberListBean>,
    fragment: ActiveMembersFragment
) : RecyclerView.Adapter<ActiveMembersAdapter.ViewHolder>() {
    private var data: ArrayList<MemberListResponse.DataBean.MemberListBean> = data
    private var fragment:ActiveMembersFragment?=fragment
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
            if (data[position].isAdmin){
                holder.makeAdmin!!.visibility=View.GONE
            }else{
                holder.makeAdmin!!.visibility=View.VISIBLE
            }

            if (FCSharedPreferances.getSharedPreferance(context).useR_ID.toString()==data[position].userId.toString()){
                holder.chatIcon!!.visibility=View.GONE
            }else{
                holder.chatIcon!!.visibility=View.VISIBLE
            }
            holder.about!!.text = data[position].userDetails.last_message.toString()
        }catch (e:Exception){}

        holder!!.makeAdmin!!.setOnClickListener {
            if (FCSharedPreferances.getSharedPreferance(context).iS_ADMIN=="true"){
                val myDialog = ProjectUtill.showProgressDialog(context)
                context?.let {
                    WebServiceRequest.getInstance().makeAdmin(
                        it,data[position].userDetails.id.toString(),
                        object : Callback<MakeAdminResponse> {
                            override fun onResponse(
                                call: Call<MakeAdminResponse>,
                                response: Response<MakeAdminResponse>
                            ) {
                                myDialog.dismiss()
                                if (response != null) {
                                    if (response.isSuccessful) {
                                        if (response.body()!!.code == 1) {
                                            fragment!!.resetAdapter()
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
                                call: Call<MakeAdminResponse>,
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
            }else{
                Toast.makeText(context,"You are not admin of community.",Toast.LENGTH_LONG).show()
            }
        }
        holder!!.itemView.setOnClickListener {
            if (FCSharedPreferances.getSharedPreferance(context).useR_ID.toString()==data[position].userId.toString()){
                FCSharedPreferances.getSharedPreferance(context).status =
                    "edit"
                FCSharedPreferances.getSharedPreferance(context).otheR_ID=data[position].userId.toString()
                var intent = Intent(
                    context,
                    HomeActivity::class.java
                )
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context!!.startActivity(intent)
            }else {
                FCSharedPreferances.getSharedPreferance(context).otheR_ID = data[position].userId.toString()
                var intent = Intent(context, ChatActivity::class.java)
                intent.putExtra("pic",data[position].userDetails.image)
                intent.putExtra("name",data[position].userDetails.first_name+" "+data[position].userDetails.last_name)
                intent.putExtra("otherId",data[position].userId.toString())
                intent.putExtra("type","individual_chat")
                intent.putExtra("channelId","fjsdb")
                context!!.startActivity(intent)
            }
        }

        holder!!.profilePic!!.setOnClickListener {
            if (FCSharedPreferances.getSharedPreferance(context).useR_ID.toString()==data[position].userId.toString()){
                FCSharedPreferances.getSharedPreferance(context).status =
                    "edit"
                FCSharedPreferances.getSharedPreferance(context).otheR_ID=data[position].userId.toString()
                var intent = Intent(
                    context,
                    HomeActivity::class.java
                )
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context!!.startActivity(intent)
            }else {
                FCSharedPreferances.getSharedPreferance(context).otheR_ID=data[position].userId.toString()
                context!!.startActivity(Intent(context, OthersProfileActivity::class.java))
            }
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    fun setMessage(l: Int, messageBody: String?) {
        try {
            data[l].userDetails.last_message = messageBody
            notifyItemChanged(l)
        }catch (e:Exception){}
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var profilePic: CircleImageView? =null
        var name: TextView? =null
        var about: TextView? =null
        var makeAdmin: TextView? =null
        var chatIcon: ImageView? =null
        init {
            profilePic=itemView.findViewById(R.id.pic)
            name=itemView.findViewById(R.id.name)
            about=itemView.findViewById(R.id.about)
            makeAdmin=itemView.findViewById(R.id.make_admin)
            chatIcon=itemView.findViewById(R.id.chat_icon)
        }
    }

}