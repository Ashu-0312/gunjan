package app.gunjan.activities

import android.os.Bundle
import androidx.core.text.HtmlCompat
import app.gunjan.R
import app.gunjan.entity.CommunityGuidelineResponse
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import kotlinx.android.synthetic.main.activity_privacy_policy.back
import kotlinx.android.synthetic.main.activity_privacy_policy.content
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GuidelinesActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guideline)
        initData()
    }

    private fun initData() {

        back.setOnClickListener { finish() }

        val myDialog = ProjectUtill.showProgressDialog(this@GuidelinesActivity)
        WebServiceRequest.getInstance().communityGuideline(
            "en",
            object : Callback<CommunityGuidelineResponse> {
                override fun onResponse(
                    call: Call<CommunityGuidelineResponse>,
                    response: Response<CommunityGuidelineResponse>
                ) {
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                content.text = HtmlCompat.fromHtml(
                                    response.body()!!.data.community_guideline,
                                    HtmlCompat.FROM_HTML_MODE_LEGACY
                                )
                            } else {
                                ProjectUtill.printMessage(
                                    this@GuidelinesActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@GuidelinesActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@GuidelinesActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<CommunityGuidelineResponse>,
                    t: Throwable
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@GuidelinesActivity.window.decorView,
                        ""
                    )
                }
            })
    }
}