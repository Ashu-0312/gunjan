package app.gunjan.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import app.gunjan.R
import app.gunjan.activities.*
import app.gunjan.adapters.OthersTabAdapter
import app.gunjan.entity.DeleteAccountResponse
import app.gunjan.entity.LogoutResponse
import app.gunjan.entity.PrivacyPolicyResponse
import app.gunjan.entity.UserDetailsResponse
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_others_profile.*
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import kotlinx.android.synthetic.main.activity_privacy_policy.back
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    private var userPic:CircleImageView?=null
    private var settings:ImageView?=null
    private var tab_layout:TabLayout?=null
    private var view_pager:ViewPager?=null
    private var followers:LinearLayout?=null
    private var followings:LinearLayout?=null
    private var userName:TextView?=null
    private var About:TextView?=null
    private var followerCount:TextView?=null
    private var followingCount:TextView?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        userPic = view.findViewById(R.id.userPic)
        tab_layout = view.findViewById(R.id.tab_layout)
        view_pager = view.findViewById(R.id.view_pager)
        followers = view.findViewById(R.id.follower)
        followings = view.findViewById(R.id.following)
        userName = view.findViewById(R.id.userName)
        About = view.findViewById(R.id.About)
        settings = view.findViewById(R.id.settings)
        followerCount = view.findViewById(R.id.follower_count)
        followingCount = view.findViewById(R.id.following_count)
        initData()
        return view
    }

    private fun initData() {
        userDetails()

        followers!!.setOnClickListener {
            startActivity(Intent(context, FollowFollowerActivity::class.java))
        }

        followings!!.setOnClickListener {
            startActivity(Intent(context, FollowFollowerActivity::class.java))
        }

        settings!!.setOnClickListener {
            startActivity(Intent(context, SettingsActivity::class.java))
        }
    }

    private fun userDetails(){
        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let { it1 ->
            WebServiceRequest.getInstance().userDetails(
                it1,
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
                                        FCSharedPreferances.getSharedPreferance(context).otheR_ID=response.body()!!.data.user.id.toString()
                                        Glide.with(context!!)
                                            .load(response.body()!!.data.user.image)
                                            .placeholder(R.drawable.user_avatar)
                                            .into(userPic!!)
                                    userName!!.text = response.body()!!.data.user.profile_name
                                    About!!.text=response.body()!!.data.user.about
                                        tab_layout!!.addTab(tab_layout!!.newTab().setText("About"))
                                        tab_layout!!.addTab(tab_layout!!.newTab().setText("Post"))
                                        val tabsAdapter =
                                            OthersTabAdapter(
                                                childFragmentManager,
                                                tab_layout!!.tabCount
                                            )
                                        view_pager!!.adapter = tabsAdapter
                                        view_pager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))

                                        followerCount!!.text = response.body()!!.data.follower_count.toString()
                                        followingCount!!.text = response.body()!!.data.following_count.toString()
                                    }catch (e:Exception){}
                                } else {
                                    ProjectUtill.printMessage(
                                        (context as Activity).window.decorView,
                                        response.body()?.message
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    (context as Activity).window.decorView,
                                    ""
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                (context as Activity).window.decorView,
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
                            (context as Activity).window.decorView,
                            ""
                        )
                    }
                })
        }
    }

}