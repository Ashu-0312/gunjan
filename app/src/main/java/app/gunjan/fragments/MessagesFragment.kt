package app.gunjan.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.activities.ChatActivity
import app.gunjan.adapters.MessagesAdapter
import app.gunjan.entity.GroupListResponse
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MessagesFragment : Fragment() {
    private var membersRecycler: RecyclerView? = null
    private var groupPic: CircleImageView? = null
    private var groupName: TextView? = null
    private var blankData: TextView? = null
    private var groupLayout: RelativeLayout? = null
    private var nameValue: String? = null
    private var picValue: String? = null
    private var channelId: String? = null
    private var otherId: String? = null
    private var list: ArrayList<GroupListResponse.DataBean.DefaultGroupListBean.ParticipantsBean> = ArrayList<GroupListResponse.DataBean.DefaultGroupListBean.ParticipantsBean>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_messages, container, false)
        membersRecycler = view.findViewById(R.id.members_recycler)
        groupPic = view.findViewById(R.id.group_pic)
        groupName = view.findViewById(R.id.group_name)
        groupLayout = view.findViewById(R.id.group_layout)
        blankData = view.findViewById(R.id.blank_data)
        initData()
        return view
    }

    private fun initData() {

        groupLayout!!.setOnClickListener {
            var intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("pic",picValue)
            intent.putExtra("name",nameValue)
            intent.putExtra("otherId",otherId)
            intent.putExtra("channelId",channelId)
            intent.putExtra("type","group_chat")
            startActivity(intent)
        }

        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let {
            WebServiceRequest.getInstance().getGroupList(
                it,
                object : Callback<GroupListResponse> {
                    override fun onResponse(
                        call: Call<GroupListResponse>,
                        response: Response<GroupListResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    if (response.body()!!.data.default_group_list==null){
                                        groupLayout!!.visibility=View.GONE
                                        blankData!!.visibility=View.VISIBLE
                                    }else {
                                        try {
                                            groupLayout!!.visibility=View.VISIBLE
                                            blankData!!.visibility=View.GONE
                                            channelId =
                                                response.body()!!.data.default_group_list.admin_member_details[0].group_id.toString()
                                            otherId =
                                                response.body()!!.data.default_group_list.admin_member_details[0].member_id.toString()
                                            nameValue =
                                                response.body()!!.data.default_group_list.group_details.group_name
                                            picValue =
                                                response.body()!!.data.default_group_list.group_details.image
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.default_group_list.group_details.image)
                                                .placeholder(R.drawable.user_avatar).into(
                                                    groupPic!!
                                                )
                                            groupName!!.text =
                                                response.body()!!.data.default_group_list.group_details.group_name

                                            list.addAll(response.body()!!.data.default_group_list.participants)
                                            var membersAdapter = MessagesAdapter(
                                                context, list
                                            )
                                            var layoutManager: LinearLayoutManager? =
                                                LinearLayoutManager(context)
                                            membersRecycler!!.layoutManager = layoutManager
                                            membersRecycler!!.adapter = membersAdapter
                                        } catch (e: Exception) {
                                        }
                                    }
                                } else {
                                    ProjectUtill.printMessage(
                                        activity!!.window.decorView,
                                        response.body()?.message
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    activity!!.window.decorView,
                                    ""
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                activity!!.window.decorView,
                                ""
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<GroupListResponse>,
                        t: Throwable
                    ) {
                        myDialog.dismiss()
                        ProjectUtill.printErrorMessage(
                            activity!!.window.decorView,
                            ""
                        )
                    }
                })
        }
    }

}