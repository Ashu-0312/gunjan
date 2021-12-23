package app.gunjan.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.gunjan.R
import kotlinx.android.synthetic.main.activity_social_profile.*

class SocialProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social_profile)
        initData()
    }

    private fun initData() {
        back.setOnClickListener { finish() }
    }
}