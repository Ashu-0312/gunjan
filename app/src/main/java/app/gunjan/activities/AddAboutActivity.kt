package app.gunjan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.gunjan.R
import kotlinx.android.synthetic.main.activity_add_about.*

class AddAboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_about)
        initData()
    }

    private fun initData() {
        back.setOnClickListener { finish() }

        Continue.setOnClickListener {
            startActivity(Intent(this, CommunityListActivity::class.java))
        }
    }
}