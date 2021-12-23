package app.gunjan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.gunjan.R
import app.gunjan.adapters.FollowerFollowingTabAdapter
import app.gunjan.adapters.OthersTabAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_others_profile.*
import kotlinx.android.synthetic.main.activity_others_profile.back

class OthersProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_others_profile)
        initData()
    }

    private fun initData() {

        tab_layout!!.addTab(tab_layout!!.newTab().setText("About"))
        tab_layout!!.addTab(tab_layout!!.newTab().setText("Post"))
        val tabsAdapter =
            OthersTabAdapter(
                supportFragmentManager,
                tab_layout!!.tabCount
            )
        view_pager!!.adapter = tabsAdapter
        view_pager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        tab_layout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                view_pager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        back.setOnClickListener { finish() }

        follower.setOnClickListener {
            startActivity(Intent(this, FollowFollowerActivity::class.java))
        }

        following.setOnClickListener {
            startActivity(Intent(this, FollowFollowerActivity::class.java))
        }

        SocialProfile.setOnClickListener {
            startActivity(Intent(this, SocialProfileActivity::class.java))
        }
    }
}