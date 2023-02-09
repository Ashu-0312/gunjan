package app.gunjan.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import app.gunjan.R
import app.gunjan.entity.AddQueryResponse
import app.gunjan.entity.GetHelplineNumberResponse
import app.gunjan.entity.PrivacyPolicyResponse
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import kotlinx.android.synthetic.main.activity_contact_us.*
import kotlinx.android.synthetic.main.activity_contact_us.back
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactUsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)
        initData()
    }

    private fun initData() {
        back.setOnClickListener { finish() }

        Submit.setOnClickListener {
            if (subject.text.toString().trim() == "") {
                Toast.makeText(this, getString(R.string.please_subject), Toast.LENGTH_LONG).show()
            } else if (query.text.toString().trim() == "") {
                Toast.makeText(this, getString(R.string.please_query), android.widget.Toast.LENGTH_LONG).show()
            } else {
                val myDialog = ProjectUtill.showProgressDialog(this@ContactUsActivity)
                WebServiceRequest.getInstance().addQuery(
                    this, subject.text.toString().trim(),query.text.toString().trim(),
                    object : Callback<AddQueryResponse> {
                        override fun onResponse(
                            call: Call<AddQueryResponse>,
                            response: Response<AddQueryResponse>
                        ) {
                            myDialog.dismiss()
                            if (response != null) {
                                if (response.isSuccessful) {
                                    if (response.body()!!.code == 1) {
                                        Toast.makeText(
                                            this@ContactUsActivity, "" + response.body()!!.message,
                                            Toast.LENGTH_LONG
                                        ).show()
                                        finish()
                                    } else {
                                        ProjectUtill.printMessage(
                                            this@ContactUsActivity.window.decorView,
                                            response.body()?.message
                                        )
                                    }
                                } else {
                                    ProjectUtill.printErrorMessage(
                                        this@ContactUsActivity.window.decorView,
                                        ""
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    this@ContactUsActivity.window.decorView,
                                    ""
                                )
                            }
                        }

                        override fun onFailure(
                            call: Call<AddQueryResponse>,
                            t: Throwable
                        ) {
                            myDialog.dismiss()
                            ProjectUtill.printErrorMessage(
                                this@ContactUsActivity.window.decorView,
                                ""
                            )
                        }
                    })
            }
        }

    }
}