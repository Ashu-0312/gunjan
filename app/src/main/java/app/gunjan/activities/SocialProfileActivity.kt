package app.gunjan.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import app.gunjan.R
import app.gunjan.entity.OtherUserDetailsResponse
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_social_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SocialProfileActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social_profile)
        initData()
    }

    private fun initData() {
        back.setOnClickListener { finish() }

        youtubeLayout.setOnClickListener {
            try {
                if (youtubeName.text.toString().contains("http")) {
                    val url: String = youtubeName.text.toString()
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }else{
                    val url: String = "https://www.youtube.com/"+youtubeName.text.toString()
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
            }catch (e: Exception){
                Log.d("ERROR",e.printStackTrace().toString())
            }
        }

        youtubeName.setOnClickListener {
            try {
                if (youtubeName.text.toString().contains("http")) {
                    val url: String = youtubeName.text.toString()
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }else{
                    val url: String = "https://www.youtube.com/"+youtubeName.text.toString()
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
            }catch (e: Exception){
                Log.d("ERROR",e.printStackTrace().toString())
            }
        }

        fbLayout.setOnClickListener {
            try {
                if (fbName.text.toString().contains("http")) {
                    val url: String = fbName.text.toString()
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }else{
                    val url: String = "https://www.facebook.com/"+fbName.text.toString()
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
            }catch (e: Exception){
                Log.d("ERROR",e.printStackTrace().toString())
            }
        }

        fbName.setOnClickListener {
            try {
                if (youtubeName.text.toString().contains("http")) {
                    val url: String = fbName.text.toString()
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }else{
                    val url: String = "https://www.facebook.com/"+fbName.text.toString()
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
            }catch (e: Exception){
                Log.d("ERROR",e.printStackTrace().toString())
            }
        }

        instaLayout.setOnClickListener {
            try {
                if (instaName.text.toString().contains("http")) {
                    val url: String = instaName.text.toString()
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }else{
                    val url: String = "https://www.instagram.com/"+instaName.text.toString()
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
            }catch (e: Exception){
                Log.d("ERROR",e.printStackTrace().toString())
            }
        }

        instaName.setOnClickListener {
            try {
                if (instaName.text.toString().contains("http")) {
                    val url: String = instaName.text.toString()
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }else{
                    val url: String = "https://www.instagram.com/"+instaName.text.toString()
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
            }catch (e: Exception){
                Log.d("ERROR",e.printStackTrace().toString())
            }
        }

        linkedInLayout.setOnClickListener {
            try {
                if (linkedInName.text.toString().contains("http")) {
                    val url: String = linkedInName.text.toString()
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }else{
                    val url: String = "https://www.linkedin.com/"+linkedInName.text.toString()
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
            }catch (e: Exception){
                Log.d("ERROR",e.printStackTrace().toString())
            }
        }

        linkedInName.setOnClickListener {
            try {
                if (linkedInName.text.toString().contains("http")) {
                    val url: String = linkedInName.text.toString()
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }else{
                    val url: String = "https://www.linkedin.com/"+linkedInName.text.toString()
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
            }catch (e: Exception){
                Log.d("ERROR",e.printStackTrace().toString())
            }
        }

        userDetails()
    }
    private fun userDetails(){
        val myDialog = ProjectUtill.showProgressDialog(this)
        WebServiceRequest.getInstance().otherUserProfile(
            this, FCSharedPreferances.getSharedPreferance(this).otheR_ID,
            object : Callback<OtherUserDetailsResponse> {
                override fun onResponse(
                    call: Call<OtherUserDetailsResponse>,
                    response: Response<OtherUserDetailsResponse>
                ) {
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                try {
                                    if (response.body()!!.data.user.image != null) {
                                        Glide.with(this@SocialProfileActivity)
                                            .load(response.body()!!.data.user.image)
                                            .placeholder(R.drawable.user_avatar)
                                            .into(userPic)
                                    }
                                    if (response.body()!!.data.user.social_media_details.facebook.equals("")){
                                        fbLayout.visibility = View.GONE
                                    }else{
                                        fbLayout.visibility = View.VISIBLE
                                    }
                                    if (response.body()!!.data.user.social_media_details.instagram.equals("")){
                                        instaLayout.visibility = View.GONE
                                    }else{
                                        instaLayout.visibility = View.VISIBLE
                                    }
                                    if (response.body()!!.data.user.social_media_details.youtube.equals("")){
                                        youtubeLayout.visibility = View.GONE
                                    }else{
                                        youtubeLayout.visibility = View.VISIBLE
                                    }
                                    if (response.body()!!.data.user.social_media_details.linkedin.equals("")){
                                        linkedInLayout.visibility = View.GONE
                                    }else{
                                        linkedInLayout.visibility = View.VISIBLE
                                    }
                                    userName!!.text =
                                        response.body()!!.data.user.first_name + " " + response.body()!!.data.user.last_name
                                    userDesignation!!.text = response.body()!!.data.user.designation
                                    fbName.text =
                                        response.body()!!.data.user.social_media_details.facebook
                                    youtubeName.text =
                                        response.body()!!.data.user.social_media_details.youtube
                                    instaName.text =
                                        response.body()!!.data.user.social_media_details.instagram
                                    linkedInName.text =
                                        response.body()!!.data.user.social_media_details.linkedin
                                } catch (e: Exception) {
                                    Log.d("ERROR",e.printStackTrace().toString())
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@SocialProfileActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@SocialProfileActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@SocialProfileActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<OtherUserDetailsResponse>,
                    t: Throwable
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@SocialProfileActivity.window.decorView,
                        ""
                    )
                }
            })
    }
}