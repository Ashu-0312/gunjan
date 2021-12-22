package app.gunjan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.gunjan.R
import kotlinx.android.synthetic.main.activity_profile_identification.*

class ProfileIdentificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_identification)
        initData()
    }

    private fun initData() {
        back.setOnClickListener { finish() }

        AddPhoto.setOnClickListener {
            startActivity(Intent(this, AddAboutActivity::class.java))
        }
    }
}