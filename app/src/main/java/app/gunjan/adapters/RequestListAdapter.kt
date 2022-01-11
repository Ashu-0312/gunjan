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
import app.gunjan.activities.RequestListActivity
import app.gunjan.entity.AcceptRejectRequestResponse
import app.gunjan.entity.RequestListResponse
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class RequestListAdapter(
    var context: Context?,
    data: ArrayList<RequestListResponse.DataBean.RequestListBean>
) : RecyclerView.Adapter<RequestListAdapter.ViewHolder>() {
    private var data: ArrayList<RequestListResponse.DataBean.RequestListBean> = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.request_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
             Glide.with(context!!).load(data[position].userDetails.image).placeholder(R.drawable.user_avatar).into(holder.pic!!)
            holder.name!!.text=data[position].userDetails.first_name+" "+data[position].userDetails.last_name
            holder.about!!.text=data[position].userDetails.about
        }catch (e:Exception){}

        holder.accept!!.setOnClickListener {
           acceptRejectRequest(data[position].id.toString(),"1")
        }

        holder.cancel!!.setOnClickListener {
            acceptRejectRequest(data[position].id.toString(),"2")
        }
    }

    private fun acceptRejectRequest(id:String,status:String){
        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let {
            WebServiceRequest.getInstance().acceptRejectRequest(
                it,id,status,
                object : Callback<AcceptRejectRequestResponse> {
                    override fun onResponse(
                        call: Call<AcceptRejectRequestResponse>,
                        response: Response<AcceptRejectRequestResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    (context as RequestListActivity).resetAdapter()
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
                        call: Call<AcceptRejectRequestResponse>,
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

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pic: CircleImageView? =null
        var name: TextView? =null
        var about: TextView? =null
        var cancel: TextView? =null
        var accept: TextView? =null
        init {
            pic=itemView.findViewById(R.id.pic)
            name=itemView.findViewById(R.id.name)
            about=itemView.findViewById(R.id.about)
            cancel=itemView.findViewById(R.id.cancel)
            accept=itemView.findViewById(R.id.accept)
        }
    }

}