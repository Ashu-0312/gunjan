package app.gunjan.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.gunjan.R
import app.gunjan.entity.OtherUserDetailsResponse
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_social_profile.*
import kotlinx.android.synthetic.main.activity_social_profile.back
import kotlinx.android.synthetic.main.activity_social_profile.fbName
import kotlinx.android.synthetic.main.activity_social_profile.instaName
import kotlinx.android.synthetic.main.activity_social_profile.linkedInName
import kotlinx.android.synthetic.main.activity_social_profile.youtubeName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SocialProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social_profile)
        initData()
    }

    private fun initData() {
        back.setOnClickListener { finish() }

        userDetails()
    }
    private fun userDetails(){
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
                                        Glide.with(this@SocialProfileActivity)
                                            .load(response.body()!!.data.user.image)
                                            .placeholder(R.drawable.user_avatar)
                                            .into(userPic)
                                    }
                                    userName!!.text = response.body()!!.data.user.first_name+" "+response.body()!!.data.user.last_name
                                    userDesignation!!.text = response.body()!!.data.user.designation
                                    fbName.text = response.body()!!.data.user.social_media_details.facebook
                                    youtubeName.text = response.body()!!.data.user.social_media_details.youtube
                                    instaName.text = response.body()!!.data.user.social_media_details.instagram
                                    linkedInName.text = response.body()!!.data.user.social_media_details.linkedin
                                } catch (e: Exception) {}
                            } else {
                                ProjectUtill.printMessage(
                                    this@SocialProfileActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@SocialProfileActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@SocialProfileActivity.window.decorView,
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
                        this@SocialProfileActivity.window.decorView,
                        ""
                    )
                }
            })
    }
}