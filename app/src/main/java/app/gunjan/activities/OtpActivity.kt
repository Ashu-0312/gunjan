package app.gunjan.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import app.gunjan.R
import app.gunjan.entity.ResendOtpResponse
import app.gunjan.entity.VerifyOtpResponse
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import kotlinx.android.synthetic.main.activity_otp.*
import kotlinx.android.synthetic.main.activity_otp.privacy
import kotlinx.android.synthetic.main.activity_otp.tc
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        initData()
    }

    private fun initData() {
        mobileTxt.text = intent.getStringExtra("code") + " " + intent.getStringExtra("mobile")
        val txtTimer = object : CountDownTimer(61000, 1000) {
            override fun onTick(l: Long) {
                if (l >= 61000) {
                    timer.text = "01:" + l / 2000
                } else {
                    if (l < 1000) {
                        timer.text = "00:0" + l / 1000
                    } else {
                        timer.text = "00:" + l / 1000
                    }
                }
            }

            override fun onFinish() {
                resend.visibility = View.VISIBLE
            }
        }.start()
        back.setOnClickListener { finish() }

        tc.setOnClickListener {
            startActivity(Intent(this, TcActivity::class.java))
        }

        privacy.setOnClickListener {
            startActivity(Intent(this, PrivacyPolicyActivity::class.java))
        }


        Verify.setOnClickListener {
            if ((otp1.text.toString().trim() + otp2.text.toString()
                    .trim() + otp3.text.toString()
                    .trim() + otp4.text.toString().trim()).length != 4
            ) {
                Toast.makeText(this@OtpActivity, getString(R.string.enter_otp), Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (intent.getStringExtra("type").equals("other")) {
                    verifyOtpApi("other")
                } else if (intent.getStringExtra("type").equals("edit")) {
                    verifyOtpApi("edit_mobile")
                }
            }
        }

        resend.setOnClickListener {
            val myDialog = ProjectUtill.showProgressDialog(this@OtpActivity)
            WebServiceRequest.getInstance().resendOtp(
                "en",
                intent.getStringExtra("mobile").toString(),
                intent.getStringExtra("code").toString(), "android",
                object : Callback<ResendOtpResponse> {
                    override fun onResponse(
                        call: Call<ResendOtpResponse>,
                        response: Response<ResendOtpResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    Toast.makeText(
                                        this@OtpActivity,
                                        "" + response.body()!!.message,
                                        Toast.LENGTH_LONG
                                    ).show()

                                    txtTimer.start()
                                    timer.isEnabled = false
                                    resend.visibility = View.GONE
                                } else {
                                    ProjectUtill.printMessage(
                                        this@OtpActivity.window.decorView,
                                        response.body()?.message
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    this@OtpActivity.window.decorView,
                                    ""
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@OtpActivity.window.decorView,
                                ""
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<ResendOtpResponse>,
                        t: Throwable
                    ) {
                        myDialog.dismiss()
                        ProjectUtill.printErrorMessage(
                            this@OtpActivity.window.decorView,
                            ""
                        )
                    }
                })
        }

        resend.visibility = View.GONE

        otp2!!.setOnKeyListener { v, keyCode, event -> //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                //this is for backspace
                if (otp2!!.text.toString().isEmpty()) otp1!!.requestFocus()
            }
            false
        }

        otp3!!.setOnKeyListener { v, keyCode, event -> //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                //this is for backspace
                if (otp3!!.text.toString().isEmpty()) otp2!!.requestFocus()
            }
            false
        }

        otp4!!.setOnKeyListener { v, keyCode, event -> //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                //this is for backspace
                if (otp4!!.text.toString().isEmpty()) otp3!!.requestFocus()
            }
            false
        }

        otp1!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (otp1!!.text.toString().trim { it <= ' ' }.length == 1) otp2!!.requestFocus()
            }
        })
        otp2!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (otp2!!.text.toString().trim { it <= ' ' }.length == 1) otp3!!.requestFocus()
            }
        })
        otp3!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                s.toString()
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                s.toString()
            }

            override fun afterTextChanged(s: Editable) {
                if (otp3!!.text.toString().trim { it <= ' ' }.length == 1) otp4!!.requestFocus()
            }
        })

        otp4!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                val keyboard: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (otp4!!.text.toString()
                        .trim { it <= ' ' }.length == 1
                ) keyboard.hideSoftInputFromWindow(otp4.windowToken, 0)
            }
        })
    }

    private fun verifyOtpApi(type: String) {
        val myDialog = ProjectUtill.showProgressDialog(this@OtpActivity)
        WebServiceRequest.getInstance().verifyOtp(
            this,
            "en",
            intent.getStringExtra("mobile").toString(),
            intent.getStringExtra("code").toString(),
            "android",
            otp1.text.toString().trim() + otp2.text.toString()
                .trim() + otp3.text.toString()
                .trim() + otp4.text.toString().trim(),
            type,
            object : Callback<VerifyOtpResponse> {
                override fun onResponse(
                    call: Call<VerifyOtpResponse>,
                    response: Response<VerifyOtpResponse>
                ) {
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                if (type == "other") {
                                    FCSharedPreferances.getSharedPreferance(this@OtpActivity).token =
                                        response.body()!!.data.token
                                    FCSharedPreferances.getSharedPreferance(this@OtpActivity).useR_ID =
                                        response.body()!!.data.user.id.toString()
                                    /* FCSharedPreferances.getSharedPreferance(this@OtpActivity).savE_LANG =
                                         response.body()!!.data.user.language*/
                                    if (response.body()!!.data.user.profile_stage.equals("5")) {
                                        FCSharedPreferances.getSharedPreferance(this@OtpActivity).statuS_LOGIN =
                                            "true"
                                        var intent =
                                            Intent(this@OtpActivity, HomeActivity::class.java)
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        startActivity(intent)
                                    } else {
                                        FCSharedPreferances.getSharedPreferance(this@OtpActivity).profilE_STAGE =
                                            response.body()!!.data.user.profile_stage
                                        var intent = Intent(
                                            this@OtpActivity,
                                            SetProfileActivity::class.java
                                        )
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        startActivity(intent)
                                    }
                                } else if (type == "edit_mobile") {
                                    var intent = Intent(this@OtpActivity, HomeActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@OtpActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@OtpActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@OtpActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<VerifyOtpResponse>,
                    t: Throwable
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@OtpActivity.window.decorView,
                        ""
                    )
                }
            })
    }
}