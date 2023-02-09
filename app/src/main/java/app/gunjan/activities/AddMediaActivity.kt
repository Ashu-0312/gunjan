package app.gunjan.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.gunjan.R
import app.gunjan.entity.AddSocialMediaResponse
import app.gunjan.entity.UserDetailsResponse
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_add_media.*
import kotlinx.android.synthetic.main.activity_add_media.back
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddMediaActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_media)
        initData()
    }

    private fun initData() {

        userDetails()
        back.setOnClickListener { finish() }

        Save.setOnClickListener {
            val myDialog = ProjectUtill.showProgressDialog(this@AddMediaActivity)
            WebServiceRequest.getInstance().addSocialMedia(
                this,fbName.text.toString().trim(),
                instaName.text.toString().trim(),
                youtubeName.text.toString().trim(),
                linkedInName.text.toString().trim(),
                object : Callback<AddSocialMediaResponse> {
                    override fun onResponse(
                        call: Call<AddSocialMediaResponse>,
                        response: Response<AddSocialMediaResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    finish()
                                } else {
                                    ProjectUtill.printMessage(
                                        this@AddMediaActivity.window.decorView,
                                        response.body()?.message
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    this@AddMediaActivity.window.decorView,
                                    ""
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@AddMediaActivity.window.decorView,
                                ""
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<AddSocialMediaResponse>,
                        t: Throwable
                    ) {
                        myDialog.dismiss()
                        ProjectUtill.printErrorMessage(
                            this@AddMediaActivity.window.decorView,
                            ""
                        )
                    }
                })
        }
    }

    private fun userDetails(){
        val myDialog = ProjectUtill.showProgressDialog(this)
        WebServiceRequest.getInstance().userDetails(
            this,
            object : Callback<UserDetailsResponse> {
                override fun onResponse(
                    call: Call<UserDetailsResponse>,
                    response: Response<UserDetailsResponse>
                ) {
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                try {
                                    if (response.body()!!.data.user.image != null) {
                                        Glide.with(this@AddMediaActivity)
                                            .load(response.body()!!.data.user.image)
                                            .placeholder(R.drawable.user_avatar)
                                            .into(userPic)
                                    }
                                    userName!!.text = response.body()!!.data.user.profile_name
                                    userDesignation!!.text = response.body()!!.data.user.designation
                                    fbName.setText(response.body()!!.data.user.social_media_details.facebook)
                                    youtubeName.setText(response.body()!!.data.user.social_media_details.youtube)
                                    instaName.setText(response.body()!!.data.user.social_media_details.instagram)
                                    linkedInName.setText(response.body()!!.data.user.social_media_details.linkedin)
                                } catch (e: Exception) {}
                            } else {
                                ProjectUtill.printMessage(
                                    this@AddMediaActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@AddMediaActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@AddMediaActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<UserDetailsResponse>,
                    t: Throwable
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@AddMediaActivity.window.decorView,
                        ""
                    )
                }
            })
    }
}