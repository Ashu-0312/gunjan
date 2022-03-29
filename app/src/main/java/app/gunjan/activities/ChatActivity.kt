package app.gunjan.activities

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.gunjan.R
import app.gunjan.adapters.ChatAdapter
import app.gunjan.adapters.MemberListAdapter
import app.gunjan.entity.AddMemberinGroupResponse
import app.gunjan.entity.AllMembersListResponse
import app.gunjan.entity.TermsResponse
import app.gunjan.twilio.*
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.PermissionUtil
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.twilio.chat.CallbackListener
import com.twilio.chat.ChatClient
import com.twilio.chat.Message
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_chat.back
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.activity_tc.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.util.*

class ChatActivity : AppCompatActivity(), MessagesFetched, QuickstartChatManagerListener,
    ClientCreated, ChannelCreated, TokenResponseListener {
    private var page: Int? = 1
    var isLoading = false
    var isLastPage = false
    private var layoutManager: LinearLayoutManager? = null
    var progressDialog: ProgressDialog? = null
    private var pathPic = ""
    private var chatType:String? = null
    var chatAdapter: ChatAdapter? = null
    private var otherId: String? = null
    private var channelId: String? = null
    private var memberAdapter: MemberListAdapter?=null
    private var blankData: TextView?=null
    private var progressBar: ProgressBar?=null
    private var memberRecycler: RecyclerView? = null
    private val quickstartChatManager: QuickstartChatManager = QuickstartChatManager()
    var list: List<Message>? = null
    var memberList: ArrayList<AllMembersListResponse.DataBean.UserListBean> = ArrayList<AllMembersListResponse.DataBean.UserListBean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initData()
    }

    private fun initData() {
        try {
            userName.text = intent.getStringExtra("name")
            chatType = intent.getStringExtra("type")
            otherId = intent.getStringExtra("otherId")
            channelId = intent.getStringExtra("channelId")
            Glide.with(this).load(intent.getStringExtra("pic")).placeholder(R.drawable.user_avatar)
                .into(
                    userPic
                )

            if (chatType.equals("group_chat")){
                addGroup.visibility=View.VISIBLE
            }else{
                addGroup.visibility=View.GONE
            }
        } catch (e: Exception) { }
        progressDialog = ProgressDialog(this@ChatActivity, R.style.MyAlertDialogStyle)
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
        quickstartChatManager.setChatManagerListener(this@ChatActivity)
        quickstartChatManager.setClientListener(this@ChatActivity)
        quickstartChatManager.setChannelListener(this@ChatActivity)
        quickstartChatManager.setMessageFetchedListener(this@ChatActivity)

        quickstartChatManager.retrieveAccessTokenFromServer(
            this,
            FCSharedPreferances.getSharedPreferance(this@ChatActivity).token,
            this@ChatActivity,
            FCSharedPreferances.getSharedPreferance(this@ChatActivity).devicE_ID
        )
        list = ArrayList<Message>()
        swipeRefresh.setColorSchemeResources(R.color.pink)

        swipeRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            chatAdapter = ChatAdapter(this@ChatActivity, quickstartChatManager.messagesList)
            chatRecycler.setHasFixedSize(true)
            chatRecycler.layoutManager = LinearLayoutManager(this@ChatActivity)
            chatRecycler.adapter = chatAdapter
            Handler().postDelayed(
                { chatRecycler.scrollToPosition(quickstartChatManager.messagesList.size - 1) },
                500
            )
            swipeRefresh.isRefreshing = false
        })


        userPic.setOnClickListener {
            startActivity(Intent(this, OthersProfileActivity::class.java))
        }

        back.setOnClickListener { finish() }

        send.setOnClickListener {
            if (edtMessage.text.toString().trim() == "") {
            } else {
                quickstartChatManager.sendChatMessage(edtMessage.text.toString())
                edtMessage.text.clear()
            }
        }

        media.setOnClickListener {
            chooseMediaDialog()
        }

        addGroup.setOnClickListener {
            if (FCSharedPreferances.getSharedPreferance(this).iS_ADMIN=="true") {
                memberListDialog()
            }else{
                Toast.makeText(this, getString(R.string.not_admin), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun messagesFetched(success: Boolean, exception: Exception?) {
        if (success) {
            progressDialog!!.dismiss()
            chatAdapter = ChatAdapter(this@ChatActivity, quickstartChatManager.messagesList)
            chatRecycler.setHasFixedSize(true)
            chatRecycler.layoutManager = LinearLayoutManager(this@ChatActivity)
            chatRecycler.adapter = chatAdapter
            Handler().postDelayed(
                { chatRecycler.scrollToPosition(quickstartChatManager.messagesList.size - 1) },
                500
            )
        } else {
            Log.d("error", exception.toString() + "")
        }
    }

    override fun receivedNewMessage() {
        progressDialog!!.dismiss()
        chatAdapter!!.notifyItemInserted(quickstartChatManager.messagesList.size)
        chatRecycler.scrollToPosition(quickstartChatManager.messagesList.size - 1)
        quickstartChatManager.channel.messages.setLastConsumedMessageIndexWithResult(
            chatAdapter!!.itemCount.toLong(), object : CallbackListener<Long>() {
                override fun onSuccess(aLong: Long) {}
            })

        quickstartChatManager.channel.messages.setAllMessagesConsumedWithResult(object :
            CallbackListener<Long>() {
            override fun onSuccess(aLong: Long) {}
        })
    }

    override fun messageSentCallback() {
        progressDialog!!.dismiss()
        edtMessage.text.clear()
        chatAdapter!!.notifyItemInserted(quickstartChatManager.messagesList.size)
        chatRecycler.scrollToPosition(quickstartChatManager.messagesList.size - 1)
    }

    override fun clientCreated(
        chatClient: ChatClient?,
        success: Boolean,
        exception: java.lang.Exception?,
    ) {
        if (success) {
            var myId: String? = ""
            if (chatType=="individual_chat") {
                if (otherId!!.toInt() > FCSharedPreferances.getSharedPreferance(this@ChatActivity).useR_ID.toInt()
                ) myId =
                    FCSharedPreferances.getSharedPreferance(this@ChatActivity).useR_ID.toString() + "_" + otherId else myId =
                    "" + otherId + "_" + FCSharedPreferances.getSharedPreferance(
                        this@ChatActivity
                    ).useR_ID
                quickstartChatManager.loadChannels(myId, otherId, chatType)
            }else{
                myId=channelId
                quickstartChatManager.loadChannels(myId, otherId, chatType)
            }

        } else {
            Log.d("error2", exception.toString() + "")
            progressDialog!!.dismiss()
        }
    }

    override fun channelCreated(success: Boolean, exception: java.lang.Exception?) {
        if (success) {
            Handler().postDelayed({ quickstartChatManager.getMessagesList() }, 2000)
        } else {
            Log.d("error3", exception.toString() + "")
        }
    }

    override fun receivedTokenResponse(success: Boolean, exception: java.lang.Exception?) {
    }

    fun chooseMediaDialog() {
        var close: ImageView? = null
        var gallery: RelativeLayout? = null
        val capturePic: RelativeLayout
        val video: RelativeLayout
        val captureVideo: RelativeLayout
        val dialog = Dialog(this)
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.selectfile_dialog)
        dialog!!.setCancelable(true)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        close = dialog.findViewById(R.id.close)
        gallery = dialog.findViewById(R.id.gallery)
        capturePic = dialog.findViewById(R.id.rl_layout3)
        video = dialog.findViewById(R.id.rl_layout1)
        captureVideo = dialog.findViewById(R.id.rl_layout2)

        close.setOnClickListener {
            dialog.cancel()
        }

        gallery.setOnClickListener {
            if (checkPicturePermission()) {
                val pickPhoto = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(pickPhoto, 1)
                dialog.cancel()
            }
        }

        capturePic.setOnClickListener {
            if (checkPicturePermission()){
                val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePicture, 0)
                dialog.cancel()
            }
        }

        video.setOnClickListener {
            if (checkPicturePermission()) {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 2)
                dialog.cancel()
            }
        }

        captureVideo.setOnClickListener {
            if (checkPicturePermission()) {
                val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30)
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 3)
                startActivityForResult(intent, 3)
                dialog.cancel()
            }
        }

        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                0 -> {
                    val bip = data!!.extras!!["data"] as Bitmap?
                    Log.d("BitData", data!!.extras!!["data"].toString())
                    save(bip!!)
                }
                1 -> {
                    val selectedImage = data!!.data
                    pathPic = ProjectUtill.getPath(applicationContext, selectedImage)
                    try {
                        quickstartChatManager.sendMediaMessage(
                            pathPic,
                            "IMG_" + Calendar.getInstance().timeInMillis
                        )
                        progressDialog!!.show()
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                }
                2 -> {
                    val selectedImage1 = data!!.data
                    pathPic = ProjectUtill.getPath(this, selectedImage1)
                    val mp: MediaPlayer = MediaPlayer.create(this, Uri.parse(pathPic))
                    val duration = mp.duration
                    mp.release()

                    if (duration / 1000 > 20) {
                        Toast.makeText(this, R.string.bigger_video, Toast.LENGTH_SHORT).show()
                    } else {
                        progressDialog!!.show()
                        try {
                            quickstartChatManager.sendVideoMessage(
                                pathPic,
                                "VIDEO_" + Calendar.getInstance().timeInMillis
                            )
                            progressDialog!!.show()
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        }
                    }
                }
                3-> {
                    val vid = data!!.data
                    pathPic = ProjectUtill.getPath(this, vid)
                    progressDialog!!.show()
                    try {
                        quickstartChatManager.sendVideoMessage(
                            pathPic,
                            "VIDEO_" + Calendar.getInstance().timeInMillis
                        )
                        progressDialog!!.show()
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun save(bip: Bitmap) {
        val root = externalCacheDir!!.absolutePath
        val mkDir = File("$root/saveImage")
        mkDir.mkdirs()
        val generator = Random()
        var n = 10000
        n = generator.nextInt(n)
        val imageName = "Image-$n.jpg"
        val file = File(mkDir, imageName)
        if (file.exists()) file.delete()
        try {
            val out = FileOutputStream(file)
            bip.compress(Bitmap.CompressFormat.JPEG, 90, out)
            pathPic = file.absolutePath
            quickstartChatManager.sendMediaMessage(
                pathPic,
                "IMG_" + Calendar.getInstance().timeInMillis
            )
            progressDialog!!.show()
            out.flush()
            out.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.d("Exception", e.printStackTrace().toString())
        }
    }

    private fun checkPicturePermission(): Boolean {
        return if (PermissionUtil.verifyPermissions(
                this,
                PermissionUtil.getCameraPermissions()
            )
        ) {
            true
        } else {
            PermissionUtil.requestPermission(
                PermissionUtil.getCameraPermissions(),
                this
            )
            false
        }
    }

    fun memberListDialog() {
        var close: ImageView? = null
        var add: LinearLayout? = null
        val dialog = Dialog(this)
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.addgroup_dialog)
        dialog!!.setCancelable(true)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        close = dialog.findViewById(R.id.close)
        add = dialog.findViewById(R.id.Add)
        blankData = dialog.findViewById(R.id.blank_data)
        progressBar = dialog.findViewById(R.id.progress_bar)
        memberRecycler = dialog.findViewById(R.id.members_recycler)

        initializeAdapter()
        userListApi("1")

        close.setOnClickListener {
            dialog.cancel()
        }

        add.setOnClickListener {
            dialog.cancel()
            Gson().toJson(memberAdapter!!.getSelectedData())

            val myDialog = ProjectUtill.showProgressDialog(this@ChatActivity)
            WebServiceRequest.getInstance().addCommunityMember(
                this,Gson().toJson(memberAdapter!!.getSelectedData()),
                object : Callback<AddMemberinGroupResponse> {
                    override fun onResponse(
                        call: Call<AddMemberinGroupResponse>,
                        response: Response<AddMemberinGroupResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                } else {
                                    ProjectUtill.printMessage(
                                        this@ChatActivity.window.decorView,
                                        response.body()?.message
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    this@ChatActivity.window.decorView,
                                    ""
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@ChatActivity.window.decorView,
                                ""
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<AddMemberinGroupResponse>,
                        t: Throwable
                    ) {
                        myDialog.dismiss()
                        ProjectUtill.printErrorMessage(
                            this@ChatActivity.window.decorView,
                            ""
                        )
                    }
                })
        }
        dialog.show()
    }

    private fun userListApi(page: String) {
        isLoading = true
        val myDialog = ProjectUtill.showProgressDialog(this)
        WebServiceRequest.getInstance().getAllNonMemberList(
            this, page, "10",
            object : Callback<AllMembersListResponse> {
                override fun onResponse(
                    call: Call<AllMembersListResponse>,
                    response: Response<AllMembersListResponse>,
                ) {
                    isLoading = false
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                memberList.clear()
                                memberList.addAll(response.body()!!.data.user_list)
                                val prevSize: Int = response.body()!!.data.user_list.size
                                if (memberList.size == 0) {
                                    blankData!!.visibility = View.VISIBLE
                                    memberRecycler!!.visibility = View.GONE
                                } else {
                                    blankData!!.visibility = View.GONE
                                    memberRecycler!!.visibility = View.VISIBLE
                                    if (response.body()!!.data.user_list.size < 10) {
                                        isLastPage = true
                                    }
                                    if (memberList.size == 10) {
                                        memberAdapter!!.notifyDataSetChanged()
                                    } else {
                                        memberAdapter!!.notifyItemRangeChanged(
                                            prevSize,
                                            memberList.size
                                        )
                                    }
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@ChatActivity!!.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@ChatActivity!!.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@ChatActivity!!.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<AllMembersListResponse>,
                    t: Throwable,
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@ChatActivity!!.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun userListPaginationApi(page: String) {
        isLoading = true
        progressBar!!.visibility = View.VISIBLE
        WebServiceRequest.getInstance().getAllNonMemberList(
            this, page, "10",
            object : Callback<AllMembersListResponse> {
                override fun onResponse(
                    call: Call<AllMembersListResponse>,
                    response: Response<AllMembersListResponse>,
                ) {
                    isLoading = false
                    progressBar!!.visibility = View.GONE
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                memberList.addAll(response.body()!!.data.user_list)
                                val prevSize: Int = response.body()!!.data.user_list.size
                                if (memberList.size == 0) {
                                    blankData!!.visibility = View.VISIBLE
                                    memberRecycler!!.visibility = View.GONE
                                } else {
                                    blankData!!.visibility = View.GONE
                                    memberRecycler!!.visibility = View.VISIBLE
                                    if (response.body()!!.data.user_list.size < 10) {
                                        isLastPage = true
                                    }
                                    if (memberList.size == 10) {
                                        memberAdapter!!.notifyDataSetChanged()
                                    } else {
                                        memberAdapter!!.notifyItemRangeChanged(
                                            prevSize,
                                            memberList.size
                                        )
                                    }
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@ChatActivity!!.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@ChatActivity!!.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@ChatActivity!!.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<AllMembersListResponse>,
                    t: Throwable,
                ) {
                    progress_bar!!.visibility = View.GONE
                    ProjectUtill.printErrorMessage(
                        this@ChatActivity!!.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun initializeAdapter() {
        memberList.clear()
        page = 1
        isLastPage = false
        isLoading = false
        memberAdapter = MemberListAdapter(this, memberList)
        layoutManager = LinearLayoutManager(this)
        memberRecycler!!.layoutManager = layoutManager
        memberRecycler!!.adapter = memberAdapter
        memberRecycler!!.addOnScrollListener(recyclerViewOnScrollListener)
    }

    private val recyclerViewOnScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount: Int = layoutManager!!.childCount
                val totalItemCount: Int = layoutManager!!.itemCount
                val firstVisibleItemPosition: Int = layoutManager!!.findFirstVisibleItemPosition()
                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= memberList.size) {
                        isLoading = true
                        page = page!! + 1
                        userListPaginationApi(page.toString())
                    }
                }
            }
        }
}