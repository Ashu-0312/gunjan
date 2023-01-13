package app.gunjan.activities

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.gunjan.R
import app.gunjan.entity.DeleteAccountResponse
import app.gunjan.entity.LogoutResponse
import app.gunjan.entity.UpdateDeviceTokenResponse
import app.gunjan.entity.UserDetailsResponse
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_settings.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SettingsActivity : AppCompatActivity() {
    private var myLocale:Locale?=null
    private var currentLang:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initData()
    }

    private fun initData() {

        userDetails()
        logout!!.setOnClickListener {
            val myDialog = ProjectUtill.showProgressDialog(this)
                WebServiceRequest.getInstance().logout(
                    this,
                    object : Callback<LogoutResponse> {
                        override fun onResponse(
                            call: Call<LogoutResponse>,
                            response: Response<LogoutResponse>
                        ) {
                            myDialog.dismiss()
                            if (response != null) {
                                if (response.isSuccessful) {
                                    if (response.body()!!.code == 1) {
                                        FCSharedPreferances.getSharedPreferance(this@SettingsActivity).statuS_LOGIN =
                                            "false"
                                        FCSharedPreferances.getSharedPreferance(this@SettingsActivity).tokeN_STATUS = "true"
                                        FCSharedPreferances.getSharedPreferance(this@SettingsActivity).token =
                                            ""
                                        var intent = Intent(
                                            this@SettingsActivity,
                                            LoginActivity::class.java
                                        )
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        startActivity(intent)
                                    } else {
                                        ProjectUtill.printMessage(
                                            this@SettingsActivity!!.window.decorView,
                                            response.body()?.message
                                        )
                                        FCSharedPreferances.getSharedPreferance(this@SettingsActivity).statuS_LOGIN =
                                            "false"
                                        FCSharedPreferances.getSharedPreferance(this@SettingsActivity).tokeN_STATUS = "true"
                                        FCSharedPreferances.getSharedPreferance(this@SettingsActivity).token =
                                            ""
                                        var intent = Intent(
                                            this@SettingsActivity,
                                            LoginActivity::class.java
                                        )
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        startActivity(intent)
                                    }
                                } else {
                                    ProjectUtill.printErrorMessage(
                                        this@SettingsActivity!!.window.decorView,
                                        ""
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    this@SettingsActivity!!.window.decorView,
                                    ""
                                )
                            }
                        }

                        override fun onFailure(
                            call: Call<LogoutResponse>,
                            t: Throwable
                        ) {
                            myDialog.dismiss()
                            ProjectUtill.printErrorMessage(
                                this@SettingsActivity!!.window.decorView,
                                ""
                            )
                        }
                    })
            }

        languageChange.setOnClickListener {
             languageDialog()
        }

        guideline.setOnClickListener {
            startActivity(Intent(this, GuidelinesActivity::class.java))
        }

        leave_community!!.setOnClickListener {
            startActivity(Intent(this, JoinedCommunitesActivity::class.java))
        }

        my_community!!.setOnClickListener {
            startActivity(Intent(this, MyCommunitesActivity::class.java))
        }

        block_list!!.setOnClickListener {
            startActivity(Intent(this, BlockListActivity::class.java))
        }

        tc!!.setOnClickListener {
            startActivity(Intent(this, TcActivity::class.java))
        }

        addMedia!!.setOnClickListener {
            startActivity(Intent(this, AddMediaActivity::class.java))
        }

        contact_us!!.setOnClickListener {
            startActivity(Intent(this, ContactUsActivity::class.java))
        }

        switch_community!!.setOnClickListener {
            startActivity(Intent(this, SwitchCommunityActivity::class.java))

        }

        community_help!!.setOnClickListener {
            startActivity(Intent(this, CommunityHelpActivity::class.java))
        }

        privacy!!.setOnClickListener {
            startActivity(Intent(this, PrivacyPolicyActivity::class.java))
        }

        editProfile!!.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }

        delete_account!!.setOnClickListener { deleteAccountDialog() }

        back.setOnClickListener { finish() }
    }

    fun deleteAccountDialog() {
        var yes: LinearLayout? = null
        var no: LinearLayout? = null
        var close: ImageView? = null
        val dialog = Dialog(this)
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.delete_dialog)
        dialog!!.setCancelable(true)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        yes = dialog.findViewById(R.id.yes)
        no = dialog.findViewById(R.id.no)
        close = dialog.findViewById(R.id.close)
        yes.setOnClickListener {
            dialog.cancel()
            deleteAccount()
        }

        no.setOnClickListener {
            dialog.cancel()
        }

        close.setOnClickListener {
            dialog.cancel()

        }
        dialog.show()
    }

    private fun userDetails(){
        val myDialog = ProjectUtill.showProgressDialog(this)
            WebServiceRequest.getInstance().userDetails(
                this,
                object : Callback<UserDetailsResponse> {
                    override fun onResponse(
                        call: Call<UserDetailsResponse>,
                        response: Response<UserDetailsResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    try {
                                        if (response.body()!!.data.user.image != null) {
                                            Glide.with(this@SettingsActivity)
                                                .load(response.body()!!.data.user.image)
                                                .placeholder(R.drawable.user_avatar)
                                                .into(userPic)
                                        }
                                        profileName!!.text =
                                            response.body()!!.data.user.profile_name
                                        FCSharedPreferances.getSharedPreferance(this@SettingsActivity).activE_COMMUNITY =
                                            response.body()!!.data.user.active_community.toString()
                                        coins.text = response.body()!!.data.user.total_available_coins.toString()
                                    } catch (e: Exception) {
                                    }
                                } else {
                                    ProjectUtill.printMessage(
                                        this@SettingsActivity.window.decorView,
                                        response.body()?.message
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    this@SettingsActivity.window.decorView,
                                    ""
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@SettingsActivity.window.decorView,
                                ""
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<UserDetailsResponse>,
                        t: Throwable
                    ) {
                        myDialog.dismiss()
                        ProjectUtill.printErrorMessage(
                            this@SettingsActivity.window.decorView,
                            ""
                        )
                    }
                })
    }

    private fun deleteAccount() {
        val myDialog = ProjectUtill.showProgressDialog(this)
            WebServiceRequest.getInstance().deleteAccount(
                this,
                object : Callback<DeleteAccountResponse> {
                    override fun onResponse(
                        call: Call<DeleteAccountResponse>,
                        response: Response<DeleteAccountResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    FCSharedPreferances.getSharedPreferance(this@SettingsActivity).statuS_LOGIN =
                                        "false"
                                    var intent = Intent(
                                        this@SettingsActivity,
                                        LoginActivity::class.java
                                    )
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                } else {
                                    ProjectUtill.printMessage(
                                        this@SettingsActivity.window.decorView,
                                        response.body()?.message
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    this@SettingsActivity.window.decorView,
                                    ""
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@SettingsActivity.window.decorView,
                                ""
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<DeleteAccountResponse>,
                        t: Throwable
                    ) {
                        myDialog.dismiss()
                        ProjectUtill.printErrorMessage(
                            this@SettingsActivity.window.decorView,
                            ""
                        )
                    }
                })
    }

    fun setLocale(localeName: String?) {
        myLocale = Locale(localeName)
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
        FCSharedPreferances.getSharedPreferance(this@SettingsActivity).setSAVE_LANG(localeName)
        val refresh = Intent(this, HomeActivity::class.java)
        refresh.putExtra(currentLang, localeName)
        startActivity(refresh)
    }

    fun languageDialog() {
        val english: RelativeLayout
        val hindi: RelativeLayout
        val close: ImageView
        val dialog = Dialog(this@SettingsActivity)
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.language_dialog)
        dialog.setCancelable(true)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.FILL_PARENT,
            WindowManager.LayoutParams.FILL_PARENT
        )
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        english = dialog.findViewById(R.id.rl_layout)
        hindi = dialog.findViewById(R.id.rl_layout1)
        close = dialog.findViewById(R.id.close)
        close.setOnClickListener { dialog.cancel() }
        english.setOnClickListener {
            dialog.cancel()
            if (FCSharedPreferances.getSharedPreferance(this).devicE_ID.equals("")){
                setLocale("en")
            }else {
                val myDialog = ProjectUtill.showProgressDialog(this)
                WebServiceRequest.getInstance().updateDeviceToken(
                    this,
                    FCSharedPreferances.getSharedPreferance(this@SettingsActivity).devicE_ID,
                    "android",
                    "en",
                    object : Callback<UpdateDeviceTokenResponse> {
                        override fun onResponse(
                            call: Call<UpdateDeviceTokenResponse>,
                            response: Response<UpdateDeviceTokenResponse>
                        ) {
                            myDialog.dismiss()
                            if (response != null) {
                                if (response.isSuccessful) {
                                    if (response.body()!!.code == 1) {
                                        setLocale("en")
                                    } else {
                                        ProjectUtill.printMessage(
                                            this@SettingsActivity!!.window.decorView,
                                            response.body()?.message
                                        )
                                    }
                                } else {
                                    ProjectUtill.printErrorMessage(
                                        this@SettingsActivity!!.window.decorView,
                                        ""
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    this@SettingsActivity!!.window.decorView,
                                    ""
                                )
                            }
                        }

                        override fun onFailure(
                            call: Call<UpdateDeviceTokenResponse>,
                            t: Throwable
                        ) {
                            myDialog.dismiss()
                            ProjectUtill.printErrorMessage(
                                this@SettingsActivity!!.window.decorView,
                                ""
                            )
                        }
                    })
            }
        }
        hindi.setOnClickListener {
            dialog.cancel()
            if (FCSharedPreferances.getSharedPreferance(this).devicE_ID.equals("")){
                setLocale("hi")
            }else {
                val myDialog = ProjectUtill.showProgressDialog(this)
                WebServiceRequest.getInstance().updateDeviceToken(
                    this,
                    FCSharedPreferances.getSharedPreferance(this@SettingsActivity).devicE_ID,
                    "android",
                    "en",
                    object : Callback<UpdateDeviceTokenResponse> {
                        override fun onResponse(
                            call: Call<UpdateDeviceTokenResponse>,
                            response: Response<UpdateDeviceTokenResponse>
                        ) {
                            myDialog.dismiss()
                            if (response != null) {
                                if (response.isSuccessful) {
                                    if (response.body()!!.code == 1) {
                                        setLocale("hi")
                                    } else {
                                        ProjectUtill.printMessage(
                                            this@SettingsActivity!!.window.decorView,
                                            response.body()?.message
                                        )
                                    }
                                } else {
                                    ProjectUtill.printErrorMessage(
                                        this@SettingsActivity!!.window.decorView,
                                        ""
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    this@SettingsActivity!!.window.decorView,
                                    ""
                                )
                            }
                        }

                        override fun onFailure(
                            call: Call<UpdateDeviceTokenResponse>,
                            t: Throwable
                        ) {
                            myDialog.dismiss()
                            ProjectUtill.printErrorMessage(
                                this@SettingsActivity!!.window.decorView,
                                ""
                            )
                        }
                    })
            }
        }
        dialog.show()
    }
}