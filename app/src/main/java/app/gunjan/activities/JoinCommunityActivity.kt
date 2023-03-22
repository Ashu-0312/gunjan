package app.gunjan.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import app.gunjan.R
import app.gunjan.entity.SendCommunityRequestResponse
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_join_community.*
import kotlinx.android.synthetic.main.activity_join_community.back
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinCommunityActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_community)
        initData()
    }

    private fun initData() {
        Title.text=intent.getStringExtra("title")
        txt.text=getString(R.string.click_join)+" "+intent.getStringExtra("title")+" "+getString(R.string.co)
        Glide.with(this).load(intent.getStringExtra("pic")).placeholder(R.drawable.user_avatar).into(Pic)
        back.setOnClickListener { finish() }

        Join.setOnClickListener {
            val myDialog = ProjectUtill.showProgressDialog(this@JoinCommunityActivity)
            WebServiceRequest.getInstance().sendCommunityRequest(
                this,intent.getStringExtra("id").toString(),
                object : Callback<SendCommunityRequestResponse> {
                    override fun onResponse(
                        call: Call<SendCommunityRequestResponse>,
                        response: Response<SendCommunityRequestResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    Toast.makeText(this@JoinCommunityActivity,""+ response.body()!!.message,Toast.LENGTH_LONG).show()
                                    joinDialog()
                                } else {
                                    ProjectUtill.printMessage(
                                        this@JoinCommunityActivity.window.decorView,
                                        response.body()?.message
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    this@JoinCommunityActivity.window.decorView,
                                    ""
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@JoinCommunityActivity.window.decorView,
                                ""
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<SendCommunityRequestResponse>,
                        t: Throwable
                    ) {
                        myDialog.dismiss()
                        ProjectUtill.printErrorMessage(
                            this@JoinCommunityActivity.window.decorView,
                            ""
                        )
                    }
                })
        }
    }

    fun joinDialog() {
        val apply: LinearLayout?
        val pic: CircleImageView?
        val title: TextView?
        val close: ImageView?
        val dialog = Dialog(this)
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.joincommunity_dialog)
        dialog.setCancelable(true)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        close = dialog.findViewById(R.id.close)
        apply = dialog.findViewById(R.id.submit)
        title = dialog.findViewById(R.id.title)
        pic = dialog.findViewById(R.id.pic)
        title.text=intent.getStringExtra("title")
        Glide.with(this).load(intent.getStringExtra("pic")).placeholder(R.drawable.user_avatar).into(pic)
        close.setOnClickListener { dialog.cancel() }

        apply.setOnClickListener {
            dialog.cancel()
            FCSharedPreferances.getSharedPreferance(this@JoinCommunityActivity).statuS_LOGIN="true"
            val intent = Intent(this,HomeActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        dialog.show()
    }

}