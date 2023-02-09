package app.gunjan.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import app.gunjan.R
import app.gunjan.fragments.AboutYourSelfFragment
import app.gunjan.fragments.CommunityListFragment
import app.gunjan.fragments.CompleteProfileFragment
import app.gunjan.fragments.IdentificationFragment
import app.gunjan.utill.FCSharedPreferances
import kotlinx.android.synthetic.main.activity_set_profile.*
import kotlinx.android.synthetic.main.activity_set_profile.back

class SetProfileActivity : BaseActivity() {
    private var fragment:Fragment?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_profile)
        initData()
    }

    private fun initData() {
        back!!.setOnClickListener {
            when {
                supportFragmentManager.findFragmentById(R.id.frame_container) is CompleteProfileFragment -> {
                    finish()
                }
                supportFragmentManager.findFragmentById(R.id.frame_container) is CommunityListFragment -> {
                    titleHeader.text=getString(R.string.about_yourself)
                    fragment = AboutYourSelfFragment()
                    loadFragment(fragment!!)
                }
                supportFragmentManager.findFragmentById(R.id.frame_container) is AboutYourSelfFragment -> {
                    titleHeader.text=getString(R.string.profile_identification)
                    fragment = IdentificationFragment()
                    loadFragment(fragment!!)
                }
                supportFragmentManager.findFragmentById(R.id.frame_container) is IdentificationFragment -> {
                    titleHeader.text=getString(R.string.profile_setup)
                    fragment = CompleteProfileFragment()
                    loadFragment(fragment!!)
                }
                else -> {
                    super.onBackPressed()
                }
            }
        }
        when {
            FCSharedPreferances.getSharedPreferance(this).profilE_STAGE.equals("2") -> {
                loadIdentificationFragment()
            }
            FCSharedPreferances.getSharedPreferance(this).profilE_STAGE.equals("3") -> {
                loadAboutFragment()
            }
            FCSharedPreferances.getSharedPreferance(this).profilE_STAGE.equals("4") -> {
                loadCommunityActivity()
            }
            FCSharedPreferances.getSharedPreferance(this).profilE_STAGE.equals("1") -> {
                fragment = CompleteProfileFragment()
                loadFragment(fragment!!)
            }
            else -> {
                fragment = CompleteProfileFragment()
                loadFragment(fragment!!)
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun loadIdentificationFragment(){
        titleHeader.text=getString(R.string.profile_identification)
        fragment = IdentificationFragment()
        loadFragment(fragment!!)
    }

    fun loadAboutFragment(){
        titleHeader.text=getString(R.string.about_yourself)
        fragment = AboutYourSelfFragment()
        loadFragment(fragment!!)
    }

    fun loadCommunityActivity(){
        titleHeader.text=getString(R.string.select_your_community)
        fragment = CommunityListFragment()
        loadFragment(fragment!!)
    }

    override fun onBackPressed() {
        findViewById<View>(R.id.frame_container).visibility = View.VISIBLE
        when {
            supportFragmentManager.findFragmentById(R.id.frame_container) is CompleteProfileFragment -> {
                finish()
            }
            supportFragmentManager.findFragmentById(R.id.frame_container) is CommunityListFragment -> {
                titleHeader.text=getString(R.string.about_yourself)
                fragment = AboutYourSelfFragment()
                loadFragment(fragment!!)
            }
            supportFragmentManager.findFragmentById(R.id.frame_container) is AboutYourSelfFragment -> {
                titleHeader.text=getString(R.string.profile_identification)
                fragment = IdentificationFragment()
                loadFragment(fragment!!)
            }
            supportFragmentManager.findFragmentById(R.id.frame_container) is IdentificationFragment -> {
                titleHeader.text=getString(R.string.profile_setup)
                fragment = CompleteProfileFragment()
                loadFragment(fragment!!)
            }
            else -> {
                super.onBackPressed()
            }
        }
    }
}