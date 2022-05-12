package app.gunjan.activities

import android.app.Activity
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
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.adapters.FollowerFollowingTabAdapter
import app.gunjan.adapters.OthersTabAdapter
import app.gunjan.adapters.ReasonListAdapter
import app.gunjan.entity.FollowUserResponse
import app.gunjan.entity.OtherUserDetailsResponse
import app.gunjan.entity.UnfollowUserResponse
import app.gunjan.entity.UserDetailsResponse
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_others_profile.*
import kotlinx.android.synthetic.main.activity_others_profile.back
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OthersProfileActivity : AppCompatActivity() {
    private var animShow: Animation? = null
    private var reasonList: ArrayList<String> = ArrayList<String>()
    private var reasonLayout: LinearLayout? = null
    private var id:String?=""
    private var pic:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_others_profile)
        initData()
    }

    private fun initData() {
        animShow = AnimationUtils.loadAnimation(this, R.anim.move_right_in_activity)
        reasonList.add(getString(R.string.spam))
        reasonList.add(getString(R.string.abusive))
        reasonList.add(getString(R.string.fake))
        reasonList.add(getString(R.string.hate))
        reasonList.add(getString(R.string.obscene))
        reasonList.add(getString(R.string.other))
        tab_layout!!.addTab(tab_layout!!.newTab().setText(getString(R.string.about_tab)))
        tab_layout!!.addTab(tab_layout!!.newTab().setText(getString(R.string.post_tab)))
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

        userDetails()

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

        Message.setOnClickListener {
            var intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("pic",pic)
            intent.putExtra("name",userName!!.text.toString().trim())
            intent.putExtra("otherId",id)
            intent.putExtra("type","individual_chat")
            intent.putExtra("channelId","fjsdb")
            startActivity(intent)
        }

        toggleButton.setOnCheckedChangeListener { _, b ->
            if (b) {
                followUserApi()
            } else {
                unfollowUserApi()
            }
        }
    }

    private fun userDetails() {
        val myDialog = ProjectUtill.showProgressDialog(this)
        WebServiceRequest.getInstance().otherUserProfile(
            this,FCSharedPreferances.getSharedPreferance(this).otheR_ID,
            object : Callback<OtherUserDetailsResponse> {
                override fun onResponse(
                    call: Call<OtherUserDetailsResponse>,
                    response: Response<OtherUserDetailsResponse>
                ) {
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                try {
                                    if (response.body()!!.data.user.image != null) {
                                        Glide.with(this@OthersProfileActivity)
                                            .load(response.body()!!.data.user.image)
                                            .placeholder(R.drawable.user_avatar)
                                            .into(userPic)
                                        pic=response.body()!!.data.user.image
                                        id=response.body()!!.data.user.id.toString()
                                    }
                                    userName!!.text = response.body()!!.data.user.first_name+" "+response.body()!!.data.user.last_name
                                    About.text=response.body()!!.data.user.about
                                    followerCount.text = response.body()!!.data.follower_count.toString()
                                    followingCount.text = response.body()!!.data.following_count.toString()
                                    coins!!.text = response.body()!!.data.user.total_available_coins.toString()

                                    toggleButton.isChecked =
                                        response.body()!!.data.following_this_user
                                }catch (e:Exception){}
                            } else {
                                ProjectUtill.printMessage(
                                    this@OthersProfileActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@OthersProfileActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@OthersProfileActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<OtherUserDetailsResponse>,
                    t: Throwable
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@OthersProfileActivity.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun followUserApi() {
        val myDialog = ProjectUtill.showProgressDialog(this)
        WebServiceRequest.getInstance().followUser(
            this,FCSharedPreferances.getSharedPreferance(this).otheR_ID,
            object : Callback<FollowUserResponse> {
                override fun onResponse(
                    call: Call<FollowUserResponse>,
                    response: Response<FollowUserResponse>
                ) {
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                followerCount.text = response.body()!!.data.follower_count.toString()
                                followingCount.text = response.body()!!.data.following_count.toString()
                            } else {
                                ProjectUtill.printMessage(
                                    this@OthersProfileActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@OthersProfileActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@OthersProfileActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<FollowUserResponse>,
                    t: Throwable
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@OthersProfileActivity.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun unfollowUserApi() {
        val myDialog = ProjectUtill.showProgressDialog(this)
        WebServiceRequest.getInstance().unFollowUser(
            this,FCSharedPreferances.getSharedPreferance(this).otheR_ID,
            object : Callback<UnfollowUserResponse> {
                override fun onResponse(
                    call: Call<UnfollowUserResponse>,
                    response: Response<UnfollowUserResponse>
                ) {
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                followerCount.text = response.body()!!.data.follower_count.toString()
                                followingCount.text = response.body()!!.data.following_count.toString()
                            } else {
                                ProjectUtill.printMessage(
                                    this@OthersProfileActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@OthersProfileActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@OthersProfileActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<UnfollowUserResponse>,
                    t: Throwable
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@OthersProfileActivity.window.decorView,
                        ""
                    )
                }
            })
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
        if (status == "1") {
            reasonLayout!!.visibility = View.VISIBLE
            reasonLayout!!.startAnimation(animShow)
        } else {
            reasonLayout!!.visibility = View.GONE
        }
    }

}