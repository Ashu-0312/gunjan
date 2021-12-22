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
import app.gunjan.R
import kotlinx.android.synthetic.main.activity_otp.*
import kotlinx.android.synthetic.main.activity_otp.privacy
import kotlinx.android.synthetic.main.activity_otp.tc

class OtpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        initData()
    }

    private fun initData() {
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
            startActivity(Intent(this, SetProfileActivity::class.java))
        }

        resend.setOnClickListener {
            txtTimer.start()
            timer.isEnabled = false
            resend.visibility = View.GONE
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
}