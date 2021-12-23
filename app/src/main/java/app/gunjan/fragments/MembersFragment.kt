package app.gunjan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import app.gunjan.R
import app.gunjan.adapters.MembersTabAdapter
import com.google.android.material.tabs.TabLayout

class MembersFragment : Fragment() {
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_members, container, false)
        tabLayout = view.findViewById(R.id.tab_layout)
        viewPager = view.findViewById(R.id.view_pager)
        initData()
        return view
    }

    private fun initData() {
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Active Members"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Admin Members"))
        val tabsAdapter =
            MembersTabAdapter(
                activity?.supportFragmentManager,
                tabLayout!!.tabCount
            )
        viewPager!!.adapter = tabsAdapter
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}