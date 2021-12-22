package app.gunjan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import app.gunjan.R
import kotlinx.android.synthetic.main.activity_set_profile.*

class SetProfileActivity : AppCompatActivity() {
    private var flageye = true
    private var flageye2 = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_profile)
        initData()
    }

    private fun initData() {
        back.setOnClickListener { finish() }

        Continue.setOnClickListener {
            startActivity(Intent(this, ProfileIdentificationActivity::class.java))
        }

        flageye = false
        showPassword1.setImageDrawable(
            this@SetProfileActivity.resources.getDrawable(R.drawable.hide_password)
        )
        newPassword.transformationMethod = PasswordTransformationMethod()
        showPassword1.setOnClickListener {
            if (flageye) {
                flageye = false
                showPassword1.setImageDrawable(this@SetProfileActivity.resources.getDrawable(R.drawable.hide_password))
                newPassword.transformationMethod = PasswordTransformationMethod()
            } else {
                flageye = true
                showPassword1.setImageDrawable(
                    this@SetProfileActivity.resources.getDrawable(R.drawable.view_password)
                )
                newPassword.transformationMethod = null
            }
        }

        flageye2 = false
        confirmPassword.transformationMethod = PasswordTransformationMethod()
        showPassword2.setOnClickListener {
            if (flageye2) {
                flageye2 = false
                showPassword2.setImageDrawable(this@SetProfileActivity.resources.getDrawable(R.drawable.hide_password))
                confirmPassword.transformationMethod = PasswordTransformationMethod()
            } else {
                flageye2 = true
                showPassword2.setImageDrawable(
                    this@SetProfileActivity.resources.getDrawable(R.drawable.view_password)
                )
                confirmPassword.transformationMethod = null
            }
        }
    }
}