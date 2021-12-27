package app.gunjan.activities

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.adapters.FollowerFollowingTabAdapter
import app.gunjan.adapters.OthersTabAdapter
import app.gunjan.adapters.ReasonListAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_others_profile.*
import kotlinx.android.synthetic.main.activity_others_profile.back

class OthersProfileActivity : AppCompatActivity() {
    private var animShow: Animation? = null
    private var reasonList: ArrayList<String> = ArrayList<String>()
    private var reasonLayout: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_others_profile)
        initData()
    }

    private fun initData() {
        animShow = AnimationUtils.loadAnimation(this, R.anim.move_right_in_activity)
        reasonList.add("Spam")
        reasonList.add("Abusive Language")
        reasonList.add("Fake Post")
        reasonList.add("Hate Speech")
        reasonList.add("Obscene Post")
        reasonList.add("Other")
        tab_layout!!.addTab(tab_layout!!.newTab().setText("About"))
        tab_layout!!.addTab(tab_layout!!.newTab().setText("Post"))
        val tabsAdapter =
            OthersTabAdapter(
                supportFragmentManager,
                tab_layout!!.tabCount
            )
        view_pager!!.adapter = tabsAdapter
        view_pager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        tab_layout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                view_pager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        back.setOnClickListener { finish() }

        follower.setOnClickListener {
            startActivity(Intent(this, FollowFollowerActivity::class.java))
        }

        following.setOnClickListener {
            startActivity(Intent(this, FollowFollowerActivity::class.java))
        }

        SocialProfile.setOnClickListener {
            startActivity(Intent(this, SocialProfileActivity::class.java))
        }
    }


    fun blockDialog() {
        var yes: LinearLayout? = null
        var no: LinearLayout? = null
        var close: ImageView? = null
        val dialog = Dialog(this)
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.block_dialog)
        dialog!!.setCancelable(true)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        yes = dialog.findViewById(R.id.yes)
        no = dialog.findViewById(R.id.no)
        close = dialog.findViewById(R.id.close)
        yes.setOnClickListener { dialog.cancel() }

        no.setOnClickListener {
            dialog.cancel()
        }

        close.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }

    fun reportDialog() {
        var yes: LinearLayout? = null
        var no: LinearLayout? = null
        var close: ImageView? = null
        var reasonRecycler: RecyclerView? = null
        val dialog = Dialog(this)
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.report_dialog)
        dialog!!.setCancelable(true)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        yes = dialog.findViewById(R.id.yes)
        no = dialog.findViewById(R.id.no)
        reasonRecycler = dialog.findViewById(R.id.reason_recycler)
        close = dialog.findViewById(R.id.close)
        reasonLayout = dialog.findViewById(R.id.reasonLayout)
        var reasonAdapter = ReasonListAdapter(
            this, reasonList
        )
        var layoutManager: LinearLayoutManager? = LinearLayoutManager(this)
        reasonRecycler!!.layoutManager = layoutManager
        reasonRecycler!!.adapter = reasonAdapter
        yes.setOnClickListener { dialog.cancel() }

        no.setOnClickListener {
            dialog.cancel()
        }

        close.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }

    fun postreportDialog() {
        var close: ImageView? = null
        var report: RelativeLayout? = null
        var copyPost: RelativeLayout? = null
        var block: RelativeLayout? = null
        val dialog = Dialog(this)
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.postreport_dialog)
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
        report = dialog.findViewById(R.id.report)
        copyPost = dialog.findViewById(R.id.copy_post)
        block = dialog.findViewById(R.id.block)

        close.setOnClickListener {
            dialog.cancel()
        }

        report.setOnClickListener { reportDialog() }

        block.setOnClickListener {
            blockDialog()
        }
        dialog.show()
    }

    fun showReasonLayout(status: String) {
        if (status.equals("1")) {
            reasonLayout!!.visibility = View.VISIBLE
            reasonLayout!!.startAnimation(animShow)
        } else {
            reasonLayout!!.visibility = View.GONE
        }
    }

}