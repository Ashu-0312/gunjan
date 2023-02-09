package app.gunjan.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.core.text.HtmlCompat
import app.gunjan.R
import app.gunjan.entity.PrivacyPolicyResponse
import app.gunjan.entity.TermsResponse
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import kotlinx.android.synthetic.main.activity_tc.*
import kotlinx.android.synthetic.main.activity_tc.back
import kotlinx.android.synthetic.main.activity_tc.content
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TcActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tc)
        initData()
    }

    private fun initData() {
        back.setOnClickListener { finish() }

        val myDialog = ProjectUtill.showProgressDialog(this@TcActivity)
        WebServiceRequest.getInstance().termAndConditions(
            "en",
            object : Callback<TermsResponse> {
                override fun onResponse(
                    call: Call<TermsResponse>,
                    response: Response<TermsResponse>
                ) {
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                content.text = HtmlCompat.fromHtml(response.body()!!.data.termAndConditions,
                                    HtmlCompat.FROM_HTML_MODE_LEGACY)
                            } else {
                                ProjectUtill.printMessage(
                                    this@TcActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@TcActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@TcActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<TermsResponse>,
                    t: Throwable
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@TcActivity.window.decorView,
                        ""
                    )
                }
            })
    }
}