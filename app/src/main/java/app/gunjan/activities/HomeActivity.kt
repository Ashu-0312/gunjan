package app.gunjan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import app.gunjan.R
import app.gunjan.fragments.HomeFragment
import app.gunjan.fragments.MembersFragment
import app.gunjan.fragments.MessagesFragment
import app.gunjan.fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlin.system.exitProcess

class HomeActivity : AppCompatActivity() {
    var fragment: Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initData()
    }

    private fun initData() {
        home_txt.setTextColor(resources.getColor(R.color.pink))
        home_icon.setImageDrawable(resources.getDrawable(R.drawable.home_selected))
        fragment = HomeFragment()
        loadFragment(fragment!!)
        addCommunity.setOnClickListener {
            startActivity(Intent(this,AddCommunityActivity::class.java))
        }

        notification.setOnClickListener {
            startActivity(Intent(this,JoinCommunityActivity::class.java))
        }

        home.setOnClickListener {
            home_txt.setTextColor(resources.getColor(R.color.pink))
            member_txt.setTextColor(resources.getColor(R.color.txt_color))
            message_txt.setTextColor(resources.getColor(R.color.txt_color))
            account_txt.setTextColor(resources.getColor(R.color.txt_color))
            home_icon.setImageDrawable(resources.getDrawable(R.drawable.home_selected))
            member_icon.setImageDrawable(resources.getDrawable(R.drawable.member_not_selected))
            message_icon.setImageDrawable(resources.getDrawable(R.drawable.message_not_selected))
            account_icon.setImageDrawable(resources.getDrawable(R.drawable.profile_not_selected))
            fragment = HomeFragment()
            loadFragment(fragment!!)
        }

        member.setOnClickListener {
            home_txt.setTextColor(resources.getColor(R.color.txt_color))
            member_txt.setTextColor(resources.getColor(R.color.pink))
            message_txt.setTextColor(resources.getColor(R.color.txt_color))
            account_txt.setTextColor(resources.getColor(R.color.txt_color))
            home_icon.setImageDrawable(resources.getDrawable(R.drawable.home_not_selected))
            member_icon.setImageDrawable(resources.getDrawable(R.drawable.member_selected))
            message_icon.setImageDrawable(resources.getDrawable(R.drawable.message_not_selected))
            account_icon.setImageDrawable(resources.getDrawable(R.drawable.profile_not_selected))
            fragment = MembersFragment()
            loadFragment(fragment!!)
        }

        messages.setOnClickListener {
            home_txt.setTextColor(resources.getColor(R.color.txt_color))
            member_txt.setTextColor(resources.getColor(R.color.txt_color))
            message_txt.setTextColor(resources.getColor(R.color.pink))
            account_txt.setTextColor(resources.getColor(R.color.txt_color))
            home_icon.setImageDrawable(resources.getDrawable(R.drawable.home_not_selected))
            member_icon.setImageDrawable(resources.getDrawable(R.drawable.member_not_selected))
            message_icon.setImageDrawable(resources.getDrawable(R.drawable.message_selected))
            account_icon.setImageDrawable(resources.getDrawable(R.drawable.profile_not_selected))
            fragment = MessagesFragment()
            loadFragment(fragment!!)
        }

        account.setOnClickListener {
            home_txt.setTextColor(resources.getColor(R.color.txt_color))
            member_txt.setTextColor(resources.getColor(R.color.txt_color))
            message_txt.setTextColor(resources.getColor(R.color.txt_color))
            account_txt.setTextColor(resources.getColor(R.color.pink))
            home_icon.setImageDrawable(resources.getDrawable(R.drawable.home_not_selected))
            member_icon.setImageDrawable(resources.getDrawable(R.drawable.member_not_selected))
            message_icon.setImageDrawable(resources.getDrawable(R.drawable.message_not_selected))
            account_icon.setImageDrawable(resources.getDrawable(R.drawable.profile_selected))
            fragment = ProfileFragment()
            loadFragment(fragment!!)
        }
    }
    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    override fun onBackPressed() {
        findViewById<View>(R.id.frame_container).visibility = View.VISIBLE
        when {
            supportFragmentManager.findFragmentById(R.id.frame_container) is HomeFragment -> {
                exitProcess(1)
            }
            supportFragmentManager.findFragmentById(R.id.frame_container) is MembersFragment -> {
                startActivity(Intent(this@HomeActivity, HomeActivity::class.java))
                finish()
            }
            supportFragmentManager.findFragmentById(R.id.frame_container) is MessagesFragment -> {
                startActivity(Intent(this@HomeActivity, HomeActivity::class.java))
                finish()
            }
            supportFragmentManager.findFragmentById(R.id.frame_container) is ProfileFragment -> {
                startActivity(Intent(this@HomeActivity, HomeActivity::class.java))
                finish()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

}