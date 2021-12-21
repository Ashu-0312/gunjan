package app.gunjan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import app.gunjan.R
import kotlinx.android.synthetic.main.activity_login.*

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
            startActivity(Intent(this,TcActivity::class.java))
        }

        privacy.setOnClickListener {
            startActivity(Intent(this,PrivacyPolicyActivity::class.java))
        }

        Login.setOnClickListener {
            startActivity(Intent(this,OtpActivity::class.java))
        }

        signUpPage.setOnClickListener {
            startActivity(Intent(this,MobileRegisterActivity::class.java))
        }
    }
}