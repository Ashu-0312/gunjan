package app.gunjan.activities

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.gunjan.R
import app.gunjan.entity.CommunityDetailsResponse
import app.gunjan.entity.SendCommunityRequestResponse
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_community_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CommunityDetailsActivity : AppCompatActivity() {
    private var communityId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_details)
        initData()
    }

    private fun initData() {

        if (intent.getStringExtra("type").equals("normal")) {
            btnTxt.text = getString(R.string.leave_community)
            Leave.visibility = View.VISIBLE
            communityId = intent.getStringExtra("id").toString()
            getDetails()
        } else {
            if (FCSharedPreferances.getSharedPreferance(this).statuS_LOGIN.equals("true")) {
                btnTxt.text = getString(R.string.join_community)
                    communityId = intent.getStringExtra("id").toString()
                    if (FCSharedPreferances.getSharedPreferance(this).activE_COMMUNITY.equals(
                            communityId
                        )
                    ) {
                        Leave.visibility = View.GONE
                    } else {
                        Leave.visibility = View.VISIBLE
                    }
                    getDetails()
            } else {
                val intent = Intent(this@CommunityDetailsActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }

        back.setOnClickListener { finish() }

        Leave.setOnClickListener {
            if (intent.hasExtra("id")) {
                val intent = Intent(this, LeaveCommunityActivity::class.java)
                intent.putExtra("community_id", communityId)
                startActivity(intent)
            } else {
                val myDialog = ProjectUtill.showProgressDialog(this@CommunityDetailsActivity)
                WebServiceRequest.getInstance().sendCommunityRequest(
                    this, communityId!!,
                    object : Callback<SendCommunityRequestResponse> {
                        override fun onResponse(
                            call: Call<SendCommunityRequestResponse>,
                            response: Response<SendCommunityRequestResponse>
                        ) {
                            myDialog.dismiss()
                            if (response != null) {
                                if (response.isSuccessful) {
                                    if (response.body()!!.code == 1) {
                                        Toast.makeText(
                                            this@CommunityDetailsActivity,
                                            "" + response.body()!!.message,
                                            Toast.LENGTH_LONG
                                        ).show()
                                        joinDialog()
                                    } else {
                                        ProjectUtill.printMessage(
                                            this@CommunityDetailsActivity.window.decorView,
                                            response.body()?.message
                                        )
                                    }
                                } else {
                                    ProjectUtill.printErrorMessage(
                                        this@CommunityDetailsActivity.window.decorView,
                                        ""
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    this@CommunityDetailsActivity.window.decorView,
                                    ""
                                )
                            }
                        }

                        override fun onFailure(
                            call: Call<SendCommunityRequestResponse>,
                            t: Throwable
                        ) {
                            myDialog.dismiss()
                            ProjectUtill.printErrorMessage(
                                this@CommunityDetailsActivity.window.decorView,
                                ""
                            )
                        }
                    })
            }
        }
    }

    fun joinDialog() {
        val apply: LinearLayout?
        val pic: CircleImageView?
        val title: TextView?
        val close: ImageView?
        val dialog = Dialog(this)
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.joincommunity_dialog)
        dialog.setCancelable(true)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        close = dialog.findViewById(R.id.close)
        apply = dialog.findViewById(R.id.submit)
        title = dialog.findViewById(R.id.title)
        pic = dialog.findViewById(R.id.pic)
        title.text = intent.getStringExtra("title")
        Glide.with(this).load(intent.getStringExtra("pic")).placeholder(R.drawable.user_avatar)
            .into(pic)
        close.setOnClickListener { dialog.cancel() }

        apply.setOnClickListener {
            dialog.cancel()
            FCSharedPreferances.getSharedPreferance(this@CommunityDetailsActivity).statuS_LOGIN =
                "true"
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        dialog.show()
    }

    private fun getDetails() {
        val myDialog = ProjectUtill.showProgressDialog(this@CommunityDetailsActivity)
        WebServiceRequest.getInstance().getCommunityDetails(
            this, communityId!!,
            object : Callback<CommunityDetailsResponse> {
                override fun onResponse(
                    call: Call<CommunityDetailsResponse>,
                    response: Response<CommunityDetailsResponse>
                ) {
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                Glide.with(this@CommunityDetailsActivity)
                                    .load(response.body()!!.data.community_details.image)
                                    .placeholder(R.drawable.user_avatar).into(Pic)
                                Title.text = response.body()!!.data.community_details.title
                                About.text = response.body()!!.data.community_details.about
                            } else {
                                ProjectUtill.printMessage(
                                    this@CommunityDetailsActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@CommunityDetailsActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@CommunityDetailsActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<CommunityDetailsResponse>,
                    t: Throwable
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@CommunityDetailsActivity.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun appInstalledOrNot(uri: String): Boolean {
        val pm = packageManager
        var app_installed = false
        app_installed = try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
        return app_installed
    }
}