package app.gunjan.activities

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import app.gunjan.R
import app.gunjan.entity.CompleteProfileResponse
import app.gunjan.entity.PrivacyPolicyResponse
import app.gunjan.fragments.AboutYourSelfFragment
import app.gunjan.fragments.CommunityListFragment
import app.gunjan.fragments.CompleteProfileFragment
import app.gunjan.fragments.IdentificationFragment
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.PermissionUtil
import app.gunjan.utill.ProjectUtill
import app.gunjan.utill.UploadFileListener
import app.gunjan.webservices.WebServiceRequest
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.mobileconnectors.s3.transferutility.UploadOptions
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import kotlinx.android.synthetic.main.activity_set_profile.*
import kotlinx.android.synthetic.main.activity_set_profile.back
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.util.*

class SetProfileActivity : AppCompatActivity() {
    private var pathPic = ""
    private var awsPicUrl = ""
    private var fragment:Fragment?=null
    var progressdialog: ProgressDialog? = null

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