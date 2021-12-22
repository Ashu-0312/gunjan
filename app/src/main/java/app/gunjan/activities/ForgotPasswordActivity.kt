package app.gunjan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.gunjan.R
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_forgot_password.ccp
import kotlinx.android.synthetic.main.activity_forgot_password.iv_flag

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        initData()
    }

    private fun initData() {
        ccp.imageViewFlag = iv_flag
        ccp.setOnCountryChangeListener { ccp.imageViewFlag = iv_flag }
        ccp.resetToDefaultCountry()
        ccp.setDefaultCountryUsingNameCode(ccp.defaultCountryNameCode)
        back.setOnClickListener { finish() }

        Submit.setOnClickListener {
            startActivity(Intent(this, OtpActivity::class.java))
        }
    }
}