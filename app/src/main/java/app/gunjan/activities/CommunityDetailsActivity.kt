package app.gunjan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.gunjan.R
import app.gunjan.entity.CommunityDetailsResponse
import app.gunjan.entity.PrivacyPolicyResponse
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_community_details.*
import kotlinx.android.synthetic.main.activity_community_details.back
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunityDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_details)
        initData()
    }

    private fun initData() {

        getDetails()
        back.setOnClickListener { finish() }

        Leave.setOnClickListener {
            var intent=Intent(this,LeaveCommunityActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getDetails() {
        val myDialog = ProjectUtill.showProgressDialog(this@CommunityDetailsActivity)
        WebServiceRequest.getInstance().getCommunityDetails(
            this,intent.getStringExtra("id").toString(),
            object : Callback<CommunityDetailsResponse> {
                override fun onResponse(
                    call: Call<CommunityDetailsResponse>,
                    response: Response<CommunityDetailsResponse>
                ) {
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                Glide.with(this@CommunityDetailsActivity).load(response.body()!!.data.community_details.image).placeholder(R.drawable.user_avatar).into(Pic)
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