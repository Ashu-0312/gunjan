package app.gunjan.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import app.gunjan.R
import app.gunjan.entity.GenerateTokenResponse
import app.gunjan.entity.NotificationCountResponse
import app.gunjan.entity.UpdateDeviceTokenResponse
import app.gunjan.entity.UserDetailsResponse
import app.gunjan.fragments.HomeFragment
import app.gunjan.fragments.MembersFragment
import app.gunjan.fragments.MessagesFragment
import app.gunjan.fragments.ProfileFragment
import app.gunjan.twilio.Logger
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.twilio.chat.CallbackListener
import com.twilio.chat.ChatClient
import com.twilio.chat.ErrorInfo
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_settings.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

class HomeActivity : AppCompatActivity() {
    var fragment: Fragment? = null
    var myLocale: Locale? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initData()
    }

    private fun initData() {

          FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
              if (!task.isSuccessful) {
                  Log.w(ContentValues.TAG, "Fetching FCM registration token failed", task.exception)
                  return@OnCompleteListener
              }
              val myDialog = ProjectUtill.showProgressDialog(this)
              WebServiceRequest.getInstance().updateDeviceToken(
                  this, task.result!!, "android", FCSharedPreferances.getSharedPreferance(this).savE_LANG,
                  object : Callback<UpdateDeviceTokenResponse> {
                      override fun onResponse(
                          call: Call<UpdateDeviceTokenResponse>,
                          response: Response<UpdateDeviceTokenResponse>
                      ) {
                          myDialog.dismiss()
                          if (response != null) {
                              if (response.isSuccessful) {
                                  if (response.body()!!.code == 1) {
                                      setLocale(FCSharedPreferances.getSharedPreferance(this@HomeActivity).savE_LANG)
                                      FCSharedPreferances.getSharedPreferance(this@HomeActivity).devicE_ID =
                                          task.result!!
                                      initChatClient()
                                  } else {
                                      ProjectUtill.printMessage(
                                          this@HomeActivity!!.window.decorView,
                                          response.body()?.message
                                      )
                                  }
                              } else {
                                  ProjectUtill.printErrorMessage(
                                      this@HomeActivity!!.window.decorView,
                                      ""
                                  )
                              }
                          } else {
                              ProjectUtill.printErrorMessage(
                                  this@HomeActivity!!.window.decorView,
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
                              this@HomeActivity!!.window.decorView,
                              ""
                          )
                      }
                  })
          })

        if (FCSharedPreferances.getSharedPreferance(this).status.equals("edit")){
            FCSharedPreferances.getSharedPreferance(this).status=""
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
        }else if (FCSharedPreferances.getSharedPreferance(this).status.equals("members")){
            FCSharedPreferances.getSharedPreferance(this).status=""
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
        else {
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

        addCommunity.setOnClickListener {
             if (FCSharedPreferances.getSharedPreferance(this).iS_ACTIVE.equals("false")){
                Toast.makeText(this,getString(R.string.create_community),Toast.LENGTH_LONG).show()
            }else {
                 startActivity(Intent(this, AddPostActivity::class.java))
             }
        }

        notification.setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
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
        getNotificationCount()
        userDetails()
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

    private fun getNotificationCount() {
        WebServiceRequest.getInstance().getUnreadNotificationCount(
            this,
            object : Callback<NotificationCountResponse> {
                override fun onResponse(
                    call: Call<NotificationCountResponse>,
                    response: Response<NotificationCountResponse>
                ) {

                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                cartBadge.text = response.body()!!.data.notification.toString()
                            } else {
                                ProjectUtill.printMessage(
                                    this@HomeActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@HomeActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@HomeActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<NotificationCountResponse>,
                    t: Throwable
                ) {
                    ProjectUtill.printErrorMessage(
                        this@HomeActivity.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun userDetails(){
        WebServiceRequest.getInstance().userDetails(
            this,
            object : Callback<UserDetailsResponse> {
                override fun onResponse(
                    call: Call<UserDetailsResponse>,
                    response: Response<UserDetailsResponse>
                ) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                               FCSharedPreferances.getSharedPreferance(this@HomeActivity).iS_ADMIN=response.body()!!.data.isCommunityAdmin
                               FCSharedPreferances.getSharedPreferance(this@HomeActivity).iS_ACTIVE=response.body()!!.data.isActiveMember
                                FCSharedPreferances.getSharedPreferance(this@HomeActivity).useR_ID = response.body()!!.data.user.id.toString()
                                FCSharedPreferances.getSharedPreferance(this@HomeActivity).totaL_COINS = response.body()!!.data.user.total_available_coins.toString()
                            } else {
                                ProjectUtill.printMessage(
                                    this@HomeActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@HomeActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@HomeActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<UserDetailsResponse>,
                    t: Throwable
                ) {
                    ProjectUtill.printErrorMessage(
                        this@HomeActivity.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun initChatClient() {
        WebServiceRequest.getInstance().generateToken(
            this@HomeActivity,
            "chat",
            "",
            "",
            object : Callback<GenerateTokenResponse> {
                override fun onResponse(
                    call: Call<GenerateTokenResponse>,
                    response: Response<GenerateTokenResponse>,
                ) {
                    createChatClient(response.body()!!.data.token.token)
                }

                override fun onFailure(call: Call<GenerateTokenResponse>, t: Throwable) {}
            })
    }

    private fun createChatClient(token: String) {
        FCSharedPreferances.getSharedPreferance(this@HomeActivity).chaT_TOKEN = token
        val builder = ChatClient.Properties.Builder()
        builder.setRegion("us1")
        val props = builder.createProperties()
        ChatClient.create(this, token, props, object : CallbackListener<ChatClient>() {
            override fun onSuccess(chatClient: ChatClient) {
                Logger.show("success", "chatclient")
            }

            override fun onError(errorInfo: ErrorInfo) {
                super.onError(errorInfo)
                Logger.show("success: errorInfo", errorInfo.message)
            }
        })
    }

    fun setLocale(localeName: String) {
        myLocale = Locale(localeName)
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
        FCSharedPreferances.getSharedPreferance(this@HomeActivity).savE_LANG = localeName
    }

}