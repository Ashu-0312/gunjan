package app.gunjan.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.gunjan.R
import app.gunjan.adapters.ChatAdapter
import app.gunjan.adapters.MemberListAdapter
import app.gunjan.adapters.NotificationListAdapter
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {
    private var list:ArrayList<String> = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initData()
    }

    private fun initData() {
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        var chatAdapter = ChatAdapter(
            this, list
        )
        var layoutManager: LinearLayoutManager? = LinearLayoutManager(this)
        chatRecycler!!.layoutManager = layoutManager
        chatRecycler!!.adapter = chatAdapter
        swipeRefresh.setColorSchemeResources(R.color.pink)
        swipeRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            swipeRefresh.isRefreshing = false
        })

        back.setOnClickListener { finish() }

        send.setOnClickListener {
            edtMessage.text.clear()
        }

        media.setOnClickListener {
            chooseMediaDialog()
        }

        addGroup.setOnClickListener {
            memberListDialog()
        }
    }
    fun chooseMediaDialog() {
        var close: ImageView? = null
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

        close.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
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
            this, list
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