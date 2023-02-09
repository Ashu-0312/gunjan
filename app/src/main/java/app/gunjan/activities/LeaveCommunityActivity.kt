package app.gunjan.activities

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import app.gunjan.R
import app.gunjan.entity.CommunityDetailsResponse
import app.gunjan.entity.LeaveCommunityResponse
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_community_details.*
import kotlinx.android.synthetic.main.activity_leave_community.*
import kotlinx.android.synthetic.main.activity_leave_community.About
import kotlinx.android.synthetic.main.activity_leave_community.Leave
import kotlinx.android.synthetic.main.activity_leave_community.Pic
import kotlinx.android.synthetic.main.activity_leave_community.back
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LeaveCommunityActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leave_community)
        initData()
    }

    private fun initData() {
        getDetails()
        back.setOnClickListener { finish() }

        Leave.setOnClickListener {
            if(confirmRadio.isChecked){
                leaveCommunityDialog()
            }
            else{
                Toast.makeText(this,getString(R.string.confirm_leave),Toast.LENGTH_LONG).show()
            }
        }
    }

    fun leaveCommunityDialog() {
        var yes: LinearLayout? = null
        var no: LinearLayout? = null
        val dialog = Dialog(this)
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.leave_dialog)
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
        yes.setOnClickListener {
            dialog.cancel()
            leaveCommunity()
        }

        no.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }

    private fun getDetails() {
        val myDialog = ProjectUtill.showProgressDialog(this@LeaveCommunityActivity)
        WebServiceRequest.getInstance().getCommunityDetails(
            this,intent.getStringExtra("community_id").toString(),
            object : Callback<CommunityDetailsResponse> {
                override fun onResponse(
                    call: Call<CommunityDetailsResponse>,
                    response: Response<CommunityDetailsResponse>
                ) {
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                Glide.with(this@LeaveCommunityActivity).load(response.body()!!.data.community_details.image).placeholder(R.drawable.user_avatar).into(Pic)
                                Name.text = response.body()!!.data.community_details.title
                                About.text = response.body()!!.data.community_details.about
                            } else {
                                ProjectUtill.printMessage(
                                    this@LeaveCommunityActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@LeaveCommunityActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@LeaveCommunityActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<CommunityDetailsResponse>,
                    t: Throwable
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@LeaveCommunityActivity.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun leaveCommunity() {
        val myDialog = ProjectUtill.showProgressDialog(this@LeaveCommunityActivity)
        WebServiceRequest.getInstance().leaveCommunity(
            this,intent.getStringExtra("community_id").toString(),
            object : Callback<LeaveCommunityResponse> {
                override fun onResponse(
                    call: Call<LeaveCommunityResponse>,
                    response: Response<LeaveCommunityResponse>
                ) {
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                              Toast.makeText(this@LeaveCommunityActivity,""+response.body()!!.message,Toast.LENGTH_LONG).show()
                                FCSharedPreferances.getSharedPreferance(this@LeaveCommunityActivity).status =
                                    "edit"
                                var intent = Intent(
                                    this@LeaveCommunityActivity,
                                    HomeActivity::class.java
                                )
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            } else {
                                ProjectUtill.printMessage(
                                    this@LeaveCommunityActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@LeaveCommunityActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@LeaveCommunityActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<LeaveCommunityResponse>,
                    t: Throwable
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@LeaveCommunityActivity.window.decorView,
                        ""
                    )
                }
            })
    }
}