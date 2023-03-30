package app.gunjan.activities

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.widget.Toast
import app.gunjan.R
import app.gunjan.entity.SignupResponse
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import kotlinx.android.synthetic.main.activity_mobile_register.*
import kotlinx.android.synthetic.main.activity_mobile_register.back
import kotlinx.android.synthetic.main.activity_mobile_register.ccp
import kotlinx.android.synthetic.main.activity_mobile_register.edtMobile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MobileRegisterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobile_register)
        initData()
    }

    private fun initData() {
        edtMobile.keyListener = DigitsKeyListener.getInstance("0123456789")
        edtMobile.inputType = InputType.TYPE_CLASS_NUMBER
        back.setOnClickListener { finish() }

        tc.setOnClickListener {
            startActivity(Intent(this, TcActivity::class.java))
        }

        privacy.setOnClickListener {
            startActivity(Intent(this, PrivacyPolicyActivity::class.java))
        }

        Next.setOnClickListener {
            if (validate()) {
                val myDialog = ProjectUtill.showProgressDialog(this@MobileRegisterActivity)
                WebServiceRequest.getInstance().signup(
                    "en", edtMobile.text.toString().trim(), "+91", "android",
                    object : Callback<SignupResponse> {
                        override fun onResponse(
                            call: Call<SignupResponse>,
                            response: Response<SignupResponse>
                        ) {
                            myDialog.dismiss()
                            if (response != null) {
                                if (response.isSuccessful) {
                                    if (response.body()!!.code == 1) {
                                        FCSharedPreferances.getSharedPreferance(this@MobileRegisterActivity).token =
                                            ""
                                        Toast.makeText(
                                            this@MobileRegisterActivity,
                                            "" + response.body()!!.message,
                                            Toast.LENGTH_LONG
                                        ).show()
                                        val intent =
                                            Intent(
                                                this@MobileRegisterActivity,
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
                                        intent.putExtra(
                                            "type",
                                            "other"
                                        )
                                        startActivity(intent)
                                    } else {
                                        ProjectUtill.printMessage2(
                                            this@MobileRegisterActivity.window.decorView,
                                            response.body()?.message
                                        )
                                    }
                                } else {
                                    ProjectUtill.printMessage2(
                                        this@MobileRegisterActivity.window.decorView,
                                        getString(R.string.check_internet)
                                    )
                                }
                            } else {
                                ProjectUtill.printMessage2(
                                    this@MobileRegisterActivity.window.decorView,
                                    getString(R.string.check_internet)
                                )
                            }
                        }

                        override fun onFailure(
                            call: Call<SignupResponse>,
                            t: Throwable
                        ) {
                            myDialog.dismiss()
                            ProjectUtill.printMessage2(
                                this@MobileRegisterActivity.window.decorView,
                                getString(R.string.check_internet)
                            )
                        }
                    })
            }
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