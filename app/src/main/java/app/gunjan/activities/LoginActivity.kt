package app.gunjan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import app.gunjan.R
import app.gunjan.entity.LoginResponse
import app.gunjan.entity.SignupResponse
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.ccp
import kotlinx.android.synthetic.main.activity_login.edtMobile
import kotlinx.android.synthetic.main.activity_login.iv_flag
import kotlinx.android.synthetic.main.activity_login.privacy
import kotlinx.android.synthetic.main.activity_login.tc
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private var flageye = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initData()
    }

    private fun initData() {
        ccp.imageViewFlag = iv_flag
        ccp.setOnCountryChangeListener { ccp.imageViewFlag = iv_flag }
        ccp.resetToDefaultCountry()
        ccp.setDefaultCountryUsingNameCode(ccp.defaultCountryNameCode)
        flageye = false

        tc.setOnClickListener {
            startActivity(Intent(this, TcActivity::class.java))
        }

        privacy.setOnClickListener {
            startActivity(Intent(this, PrivacyPolicyActivity::class.java))
        }

        Login.setOnClickListener {
            if (validate()) {
                val myDialog = ProjectUtill.showProgressDialog(this@LoginActivity)
                WebServiceRequest.getInstance().login(
                    "en",edtMobile.text.toString().trim(),ccp.selectedCountryCodeWithPlus,"android",
                    object : Callback<LoginResponse> {
                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            myDialog.dismiss()
                            if (response != null) {
                                if (response.isSuccessful) {
                                    if (response.body()!!.code == 1) {
                                        Toast.makeText(
                                            this@LoginActivity,
                                            "" + response.body()!!.message,
                                            Toast.LENGTH_LONG
                                        ).show()
                                        var intent =
                                            Intent(
                                                this@LoginActivity,
                                                OtpActivity::class.java
                                            )
                                        intent.putExtra(
                                            "mobile",
                                            edtMobile.text.toString().trim()
                                        )

                                        intent.putExtra(
                                            "code",
                                            ccp.selectedCountryCodeWithPlus.toString()
                                        )
                                        startActivity(intent)
                                    } else {
                                        ProjectUtill.printMessage(
                                            this@LoginActivity.window.decorView,
                                            response.body()?.message
                                        )
                                    }
                                } else {
                                    ProjectUtill.printErrorMessage(
                                        this@LoginActivity.window.decorView,
                                        ""
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    this@LoginActivity.window.decorView,
                                    ""
                                )
                            }
                        }

                        override fun onFailure(
                            call: Call<LoginResponse>,
                            t: Throwable
                        ) {
                            myDialog.dismiss()
                            ProjectUtill.printErrorMessage(
                                this@LoginActivity.window.decorView,
                                ""
                            )
                        }
                    })
            }
        }

        signUpPage.setOnClickListener {
            startActivity(Intent(this, MobileRegisterActivity::class.java))
        }
    }
    private fun validate(): Boolean {
        if (edtMobile.text.toString().trim().equals("", ignoreCase = true)) {
            edtMobile.requestFocus()
            edtMobile.error = getString(R.string.enter_mobile)
            return false
        } else if (edtMobile.text.toString().trim().length < 10) {
            edtMobile.requestFocus()
            edtMobile.error = getString(R.string.valid_mobile)
            return false
        }
        return true
    }
}