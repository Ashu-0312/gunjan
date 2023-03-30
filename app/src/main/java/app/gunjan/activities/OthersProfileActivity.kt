package app.gunjan.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.adapters.OthersTabAdapter
import app.gunjan.adapters.ReasonList2Adapter
import app.gunjan.entity.*
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.utill.RecyclerItemClickListener
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_others_profile.*
import kotlinx.android.synthetic.main.activity_others_profile.back
import kotlinx.android.synthetic.main.activity_others_profile.userName
import kotlinx.android.synthetic.main.activity_others_profile.userPic
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OthersProfileActivity : BaseActivity(),RecyclerItemClickListener {

    private var animShow: Animation? = null
    private var reasonList: ArrayList<String> = ArrayList()
    private var reasonLayout: LinearLayout? = null
    private var id:String?=""
    private var pic:String?=""
    private var Status: String? = "2"
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
            val intent = Intent(this, ChatActivity::class.java)
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

        reportUser.setOnClickListener {
           reportDialog(FCSharedPreferances.getSharedPreferance(this).otheR_ID)
        }

        blockUser.setOnClickListener {
            blockDialog(FCSharedPreferances.getSharedPreferance(this).otheR_ID)
        }
    }

    private fun reportDialog(userId: String) {
        val yes: LinearLayout?
        val no: LinearLayout?
        val close: ImageView?
        val edtReason: EditText?
        val reasonRecycler: RecyclerView?
        val dialog = Dialog(this)
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.report_dialog)
        dialog.setCancelable(true)
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
        edtReason = dialog.findViewById(R.id.reason_edt)
        reasonLayout = dialog.findViewById(R.id.reasonLayout)

        val myDialog = ProjectUtill.showProgressDialog(this)
            WebServiceRequest.getInstance().reasonList(
                this,
                object : Callback<ReasonListResponse> {
                    override fun onResponse(
                        call: Call<ReasonListResponse>,
                        response: Response<ReasonListResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    val reasonAdapter = ReasonList2Adapter(
                                        this@OthersProfileActivity,
                                        response.body()!!.data.reason_list,
                                        this@OthersProfileActivity
                                    )
                                    val layoutManager =
                                        LinearLayoutManager(this@OthersProfileActivity)
                                    reasonRecycler.layoutManager = layoutManager
                                    reasonRecycler.adapter = reasonAdapter
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
                        call: Call<ReasonListResponse>,
                        t: Throwable
                    ) {
                        myDialog.dismiss()
                        ProjectUtill.printErrorMessage(
                            this@OthersProfileActivity.window.decorView,
                            ""
                        )
                    }
                })

        yes.setOnClickListener {
            if (Status.equals("1")) {
                if (edtReason.text.toString().trim() == "") {
                    Toast.makeText(this, getString(R.string.please_reason), Toast.LENGTH_LONG)
                        .show()
                } else {
                    dialog.cancel()
                    val myDialog = ProjectUtill.showProgressDialog(this)
                        WebServiceRequest.getInstance().reportUser(
                            this,
                            userId,
                            FCSharedPreferances.getSharedPreferance(this).reasoN_ID,
                            edtReason.text.toString(),
                            object : Callback<ReportReasonResponse> {
                                override fun onResponse(
                                    call: Call<ReportReasonResponse>,
                                    response: Response<ReportReasonResponse>
                                ) {
                                    myDialog.dismiss()
                                    if (response != null) {
                                        if (response.isSuccessful) {
                                            if (response.body()!!.code == 1) {
                                                Toast.makeText(
                                                    this@OthersProfileActivity,
                                                    "" + response.body()!!.message,
                                                    Toast.LENGTH_LONG
                                                ).show()
                                                Status = "2"
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
                                    call: Call<ReportReasonResponse>,
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
            } else {
                dialog.cancel()
                val myDialog = ProjectUtill.showProgressDialog(this)
                    WebServiceRequest.getInstance().reportUser(
                        this, userId, FCSharedPreferances.getSharedPreferance(this).reasoN_ID, "",
                        object : Callback<ReportReasonResponse> {
                            override fun onResponse(
                                call: Call<ReportReasonResponse>,
                                response: Response<ReportReasonResponse>
                            ) {
                                myDialog.dismiss()
                                if (response != null) {
                                    if (response.isSuccessful) {
                                        if (response.body()!!.code == 1) {
                                            Toast.makeText(
                                                this@OthersProfileActivity,
                                                "" + response.body()!!.message,
                                                Toast.LENGTH_LONG
                                            ).show()
                                            Status = "2"
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
                                call: Call<ReportReasonResponse>,
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
        }

        no.setOnClickListener {
            dialog.cancel()
        }

        close.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }

    private fun blockDialog(userId: String) {
        val yes: LinearLayout?
        val no: LinearLayout?
        val close: ImageView?
        val dialog = Dialog(this)
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.block_dialog)
        dialog.setCancelable(true)
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
        yes.setOnClickListener {
            dialog.cancel()
            val myDialog = ProjectUtill.showProgressDialog(this)
                WebServiceRequest.getInstance().blockUnblockUser(
                    this, userId, "0",
                    object : Callback<BlockUnblockUserResponse> {
                        override fun onResponse(
                            call: Call<BlockUnblockUserResponse>,
                            response: Response<BlockUnblockUserResponse>
                        ) {
                            myDialog.dismiss()
                            if (response != null) {
                                if (response.isSuccessful) {
                                    if (response.body()!!.code == 1) {
                                        val intent = Intent(this@OthersProfileActivity, HomeActivity::class.java)
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        startActivity(intent)
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
                            call: Call<BlockUnblockUserResponse>,
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

        no.setOnClickListener {
            dialog.cancel()
        }

        close.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
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
                                    toggleButton.isChecked = response.body()!!.data.isFollowing_this_user
                                    if(response.body()!!.data.user.social_media_details == null){
                                        SocialProfile.visibility = View.GONE
                                    }else if(response.body()!!.data.user.social_media_details.facebook.equals("") &&
                                        response.body()!!.data.user.social_media_details.instagram.equals("") &&
                                        response.body()!!.data.user.social_media_details.linkedin.equals("") &&
                                        response.body()!!.data.user.social_media_details.youtube.equals("")){
                                        SocialProfile.visibility = View.GONE
                                    }else{
                                        SocialProfile.visibility = View.VISIBLE
                                    }
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

    fun showReasonLayout(status: String) {
        if (status == "1") {
            reasonLayout!!.visibility = View.VISIBLE
            reasonLayout!!.startAnimation(animShow)
        } else {
            reasonLayout!!.visibility = View.GONE
        }
    }

    override fun onItemClick(parentPos: Int, childPos: Int, data: Any, type: String) {

    }

}