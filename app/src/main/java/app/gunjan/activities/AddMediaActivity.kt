package app.gunjan.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import app.gunjan.R
import app.gunjan.adapters.CommunityListAdapter
import app.gunjan.adapters.MediaLinkAdapter
import kotlinx.android.synthetic.main.activity_add_media.*
import kotlinx.android.synthetic.main.activity_add_media.back

class AddMediaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_media)
        initData()
    }

    private fun initData() {

        back.setOnClickListener { finish() }

    }
}