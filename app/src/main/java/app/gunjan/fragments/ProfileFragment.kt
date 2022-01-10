package app.gunjan.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import app.gunjan.R
import app.gunjan.activities.*
import app.gunjan.entity.UserDetailsResponse
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    private var leaveCommunity: LinearLayout? = null
    private var tc: LinearLayout? = null
    private var privacy: LinearLayout? = null
    private var editProfile: RelativeLayout? = null
    private var deleteAccount: LinearLayout? = null
    private var contactUs: LinearLayout? = null
    private var communityHelp: LinearLayout? = null
    private var addMedia: LinearLayout? = null
    private var switchCommunity: LinearLayout? = null
    private var theme: LinearLayout? = null
    private var blockList: LinearLayout? = null
    private var logout: LinearLayout? = null
    private var userPic: CircleImageView? = null
    private var profileName: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        leaveCommunity = view.findViewById(R.id.leave_community)
        tc = view.findViewById(R.id.tc)
        privacy = view.findViewById(R.id.privacy)
        editProfile = view.findViewById(R.id.editProfile)
        deleteAccount = view.findViewById(R.id.delete_account)
        contactUs = view.findViewById(R.id.contact_us)
        communityHelp = view.findViewById(R.id.community_help)
        addMedia = view.findViewById(R.id.addMedia)
        switchCommunity = view.findViewById(R.id.switch_community)
        theme = view.findViewById(R.id.theme)
        blockList = view.findViewById(R.id.block_list)
        logout = view.findViewById(R.id.logout)
        userPic = view.findViewById(R.id.user_pic)
        profileName = view.findViewById(R.id.user_name)
        initData()
        return view
    }

    private fun initData() {
         userDetails()
        logout!!.setOnClickListener {
            FCSharedPreferances.getSharedPreferance(context).statuS_LOGIN="false"
            var intent = Intent(context,LoginActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        theme!!.setOnClickListener {
        }

        leaveCommunity!!.setOnClickListener {
            startActivity(Intent(context, LeaveCommunityActivity::class.java))
        }

        blockList!!.setOnClickListener {
            startActivity(Intent(context, BlockListActivity::class.java))
        }

        tc!!.setOnClickListener {
            startActivity(Intent(context, TcActivity::class.java))
        }

        addMedia!!.setOnClickListener {
            startActivity(Intent(context, AddMediaActivity::class.java))
        }

        contactUs!!.setOnClickListener {
            startActivity(Intent(context, ContactUsActivity::class.java))
        }

        switchCommunity!!.setOnClickListener {
        }

        communityHelp!!.setOnClickListener {
            startActivity(Intent(context, CommunityHelpActivity::class.java))
        }

        privacy!!.setOnClickListener {
            startActivity(Intent(context, PrivacyPolicyActivity::class.java))
        }

        editProfile!!.setOnClickListener {
            startActivity(Intent(context, EditProfileActivity::class.java))
        }

        deleteAccount!!.setOnClickListener { deleteAccountDialog() }
    }

    fun deleteAccountDialog() {
        var yes: LinearLayout? = null
        var no: LinearLayout? = null
        var close: ImageView? = null
        val dialog = context?.let { Dialog(it) }
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
        yes.setOnClickListener { dialog.cancel() }

        no.setOnClickListener {
            dialog.cancel()
        }

        close.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
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
                                        if (response.body()!!.data.user.image != null) {
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.user.image)
                                                .placeholder(R.drawable.user_avatar)
                                                .into(userPic!!)
                                        }
                                        profileName!!.text = response.body()!!.data.user.profile_name
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