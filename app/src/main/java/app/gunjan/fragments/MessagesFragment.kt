package app.gunjan.fragments

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.activities.ChatActivity
import app.gunjan.adapters.MessagesAdapter
import app.gunjan.entity.GroupListResponse
import app.gunjan.twilio.ClientCreated
import app.gunjan.twilio.Logger
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import com.twilio.chat.*
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MessagesFragment : Fragment(),ClientCreated {
    private var membersRecycler: RecyclerView? = null
    private var groupPic: CircleImageView? = null
    var chatClient: ChatClient? = null
    private var groupName: TextView? = null
    private var blankData: TextView? = null
    private var groupLayout: RelativeLayout? = null
    private var nameValue: String? = null
    private var picValue: String? = null
    private var channelId: String? = null
    private var otherId: String? = null
    private var groupMessage: TextView? = null
    private var membersAdapter:MessagesAdapter?=null
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
        groupMessage = view.findViewById(R.id.group_message)
        initData()
        return view
    }

    private fun initData() {
        createChatClient(FCSharedPreferances.getSharedPreferance(context).chaT_TOKEN)
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
                                             membersAdapter = MessagesAdapter(
                                                context, list
                                            )
                                            var layoutManager: LinearLayoutManager? =
                                                LinearLayoutManager(context)
                                            membersRecycler!!.layoutManager = layoutManager
                                            membersRecycler!!.adapter = membersAdapter
                                        } catch (e: Exception) {
                                        }
                                    }
                                    setUser()
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
    private fun setUser() {
        if (chatClient != null) {
            for (p in list.indices) {
                var myId: String? = ""
                if (list[p].participants_details.id!!.toInt() > FCSharedPreferances.getSharedPreferance(context).useR_ID.toInt()
                ) myId =
                    FCSharedPreferances.getSharedPreferance(context).useR_ID.toString() + "_" + list[p].participants_details.id else myId =
                    "" + list[p].participants_details.id + "_" + FCSharedPreferances.getSharedPreferance(context
                    ).useR_ID
                Task1(membersAdapter,chatClient).execute(p.toString(), myId, list[p].participants_details.id.toString())
                Task2(groupMessage,chatClient).execute(p.toString(), channelId, otherId)
            }
        }
    }
    override fun clientCreated(chatClient: ChatClient?, success: Boolean, exception: Exception?) {
        this.chatClient = chatClient
    }

    class Task1(chatAdapterr: MessagesAdapter?, chatClient: ChatClient?) : AsyncTask<String?, String?, String?>() {
        var chatAdapter: MessagesAdapter? =chatAdapterr
        var chatClient: ChatClient?=chatClient
        override fun doInBackground(vararg params: String?): String? {
            chatClient!!.channels.getChannel(params[1], object : CallbackListener<Channel>() {
                override fun onSuccess(channel: Channel) {
                    Handler().postDelayed({
                        if (channel.messages != null) {
                            channel.messages.getLastMessages(
                                1,
                                object : CallbackListener<List<Message>>() {
                                    override fun onSuccess(messages: List<Message>) {
                                        if (messages != null) {
                                            if (messages.isNotEmpty()) {
                                                chatAdapter!!.setMessage(
                                                    params[0]!!.toInt(),
                                                    messages[0].messageBody
                                                )
                                            }
                                        }
                                    }

                                    override fun onError(errorInfo: ErrorInfo) {
                                        super.onError(errorInfo)
                                        Log.d("error3", errorInfo.message)
                                    }
                                })
                        }
                    }, 2000)
                }

                override fun onError(errorInfo: ErrorInfo) {
                    super.onError(errorInfo)
                    Log.d("error2", errorInfo.message)
                }
            })
            return null
        }
    }

    class Task2(groupMessage: TextView?, chatClient: ChatClient?) : AsyncTask<String?, String?, String?>() {
        var chatClient: ChatClient?=chatClient
        var groupMessage: TextView?=groupMessage
        override fun doInBackground(vararg params: String?): String? {
            chatClient!!.channels.getChannel(params[1], object : CallbackListener<Channel>() {
                override fun onSuccess(channel: Channel) {
                    Handler().postDelayed({
                        if (channel.messages != null) {
                            channel.messages.getLastMessages(
                                1,
                                object : CallbackListener<List<Message>>() {
                                    override fun onSuccess(messages: List<Message>) {
                                        if (messages != null) {
                                            if (messages.isNotEmpty()) {
                                                    groupMessage!!.text=messages[0].messageBody
                                            }
                                        }
                                    }

                                    override fun onError(errorInfo: ErrorInfo) {
                                        super.onError(errorInfo)
                                        Log.d("error3", errorInfo.message)
                                    }
                                })
                        }
                    }, 2000)
                }

                override fun onError(errorInfo: ErrorInfo) {
                    super.onError(errorInfo)
                    Log.d("error2", errorInfo.message)
                }
            })
            return null
        }
    }

    private fun createChatClient(token: String) {
        val builder = ChatClient.Properties.Builder()
        builder.setRegion("us1")
        val props = builder.createProperties()
        context?.let {
            ChatClient.create(
                it,
                token,
                props,
                object : CallbackListener<ChatClient>() {
                    override fun onSuccess(chatClient: ChatClient) {
                        //Toast.makeText(HomeActivity.this, R.string.success_chat, Toast.LENGTH_LONG).show();
                        Logger.show("success", "chatclient")
                        this@MessagesFragment.chatClient = chatClient
                        setUser()
                    }

                    override fun onError(errorInfo: ErrorInfo) {
                        super.onError(errorInfo)
                        //Toast.makeText(HomeActivity.this, R.string.failed_chat, Toast.LENGTH_LONG).show();
                        Logger.show("success: errorInfo", errorInfo.message)
                    }
                })
        }
    }
}