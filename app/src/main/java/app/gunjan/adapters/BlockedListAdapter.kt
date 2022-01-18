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
import app.gunjan.activities.BlockListActivity
import app.gunjan.activities.ChatActivity
import app.gunjan.activities.SwitchCommunityActivity
import app.gunjan.entity.BlockUnblockUserResponse
import app.gunjan.entity.BlockedUserListResponse
import app.gunjan.entity.SwitchCommunityResponse
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class BlockedListAdapter(
    var context: Context?,
    data: ArrayList<BlockedUserListResponse.DataBean.MemberListBean>
) : RecyclerView.Adapter<BlockedListAdapter.ViewHolder>() {
    private var data: ArrayList<BlockedUserListResponse.DataBean.MemberListBean> = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.block_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            context?.let {
                Glide.with(it).load(data[position].userDetails.image)
                    .placeholder(R.drawable.user_avatar).into(holder!!.pic!!)
            }
            holder.name!!.text =
                data[position].userDetails.first_name + " " + data[position].userDetails.last_name
        } catch (e: Exception) {
        }

        holder.unblock!!.setOnClickListener {
            val myDialog = ProjectUtill.showProgressDialog(context)
            context?.let { it1 ->
                WebServiceRequest.getInstance().blockUnblockUser(
                    it1, data[position].userId.toString(), "1",
                    object : Callback<BlockUnblockUserResponse> {
                        override fun onResponse(
                            call: Call<BlockUnblockUserResponse>,
                            response: Response<BlockUnblockUserResponse>
                        ) {
                            myDialog.dismiss()
                            if (response != null) {
                                if (response.isSuccessful) {
                                    if (response.body()!!.code == 1) {
                                        (context as BlockListActivity).resetAdapter()
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
                            call: Call<BlockUnblockUserResponse>,
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
    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pic: CircleImageView? =null
        var name: TextView? =null
        var unblock: TextView? =null
        init {
            pic=itemView.findViewById(R.id.pic)
            unblock=itemView.findViewById(R.id.unblock)
            name=itemView.findViewById(R.id.name)
        }
    }

}