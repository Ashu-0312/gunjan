package app.gunjan.activities

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.gunjan.R
import app.gunjan.adapters.ChatAdapter
import app.gunjan.adapters.MemberListAdapter
import app.gunjan.adapters.ShowInterestAdapter
import app.gunjan.entity.ShowInterestModel
import app.gunjan.twilio.*
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.PermissionUtil
import app.gunjan.utill.ProjectUtill
import com.bumptech.glide.Glide
import com.twilio.chat.CallbackListener
import com.twilio.chat.ChatClient
import com.twilio.chat.Message
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_chat.back
import kotlinx.android.synthetic.main.activity_edit_profile.*
import java.io.File
import java.io.FileNotFoundException
import java.util.*

class ChatActivity : AppCompatActivity(), MessagesFetched, QuickstartChatManagerListener,
    ClientCreated, ChannelCreated, TokenResponseListener {
    var progressDialog: ProgressDialog? = null
    private var pathPic = ""
    private var chatType:String? = null
    var chatAdapter: ChatAdapter? = null
    private var otherId: String? = null
    private var channelId: String? = null
    private val quickstartChatManager: QuickstartChatManager = QuickstartChatManager()
    var list: List<Message>? = null
    var memberList: ArrayList<String> = ArrayList<String>()
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
        } catch (e: Exception) {
        }
        memberList.add("")
        memberList.add("")
        memberList.add("")
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
            memberListDialog()
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
                quickstartChatManager.loadChannels(myId,otherId,chatType)
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

        capturePic.setOnClickListener {  }
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
               /* 0 -> {
                    val bip = data!!.extras!!["data"] as Bitmap?
                    Log.d("BitData", data!!.extras!!["data"].toString())
                    save(bip!!)
                }*/
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
            }
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
        var memberRecycler: RecyclerView? = null
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
        memberRecycler = dialog.findViewById(R.id.members_recycler)

        var memberAdapter = MemberListAdapter(
            this, memberList
        )
        var layoutManager: LinearLayoutManager? = LinearLayoutManager(this)
        memberRecycler!!.layoutManager = layoutManager
        memberRecycler!!.adapter = memberAdapter

        close.setOnClickListener {
            dialog.cancel()
        }

        add.setOnClickListener { dialog.cancel() }
        dialog.show()
    }
}