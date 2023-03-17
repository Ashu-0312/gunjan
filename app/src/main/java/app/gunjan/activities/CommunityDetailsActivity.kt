package app.gunjan.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import app.gunjan.R
import app.gunjan.entity.CommunityDetailsResponse
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_community_details.*
import kotlinx.android.synthetic.main.activity_community_details.back
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunityDetailsActivity : BaseActivity() {
    private var communityId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_details)
        initData()
    }

    private fun initData() {
        if (intent.hasExtra("id")) {
            communityId = intent.getStringExtra("id").toString()
        } else {
            if (FCSharedPreferances.getSharedPreferance(this).statuS_LOGIN.equals("true")) {
                val appLinkAction: String? = intent?.action
                val appLinkData: Uri? = intent?.data
                if (Intent.ACTION_VIEW == appLinkAction && appLinkData != null) {
                    communityId = appLinkData.getQueryParameter("cid")
                }
            }else{
                val intent = Intent(this@CommunityDetailsActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
        getDetails()
        back.setOnClickListener { finish() }

        Leave.setOnClickListener {
            val intent = Intent(this, LeaveCommunityActivity::class.java)
            intent.putExtra("community_id", communityId)
            startActivity(intent)
        }
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
}