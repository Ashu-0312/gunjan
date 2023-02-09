package app.gunjan.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.gunjan.R
import app.gunjan.adapters.DonationHelpTabAdapter
import app.gunjan.adapters.FollowerFollowingTabAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_community_help.*

class CommunityHelpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_help)
        initData()
    }

    private fun initData() {
        tab_layout!!.addTab(tab_layout!!.newTab().setText(getString(R.string.donate)))
        tab_layout!!.addTab(tab_layout!!.newTab().setText(getString(R.string.received)))
        val tabsAdapter =
            DonationHelpTabAdapter(
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
    }
}