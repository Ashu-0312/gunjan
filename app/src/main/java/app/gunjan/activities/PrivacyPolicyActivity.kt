package app.gunjan.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.text.HtmlCompat
import app.gunjan.R
import app.gunjan.entity.PrivacyPolicyResponse
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrivacyPolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)
        initData()
    }

    private fun initData() {
        back.setOnClickListener { finish() }

        val myDialog = ProjectUtill.showProgressDialog(this@PrivacyPolicyActivity)
        WebServiceRequest.getInstance().privacyPolicy(
            "en",
            object : Callback<PrivacyPolicyResponse> {
                override fun onResponse(
                    call: Call<PrivacyPolicyResponse>,
                    response: Response<PrivacyPolicyResponse>
                ) {
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                content.text = HtmlCompat.fromHtml(response.body()!!.data.privacyAndPolicy,
                                    HtmlCompat.FROM_HTML_MODE_LEGACY)
                            } else {
                                ProjectUtill.printMessage(
                                    this@PrivacyPolicyActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@PrivacyPolicyActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@PrivacyPolicyActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<PrivacyPolicyResponse>,
                    t: Throwable
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@PrivacyPolicyActivity.window.decorView,
                        ""
                    )
                }
            })
    }
}