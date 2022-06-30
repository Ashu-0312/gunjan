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
import app.gunjan.entity.*
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import com.cashfree.pg.CFPaymentService
import com.google.android.material.tabs.TabLayout
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_others_profile.*
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import kotlinx.android.synthetic.main.activity_privacy_policy.back
import kotlinx.android.synthetic.main.activity_settings.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    private var userPic:CircleImageView?=null
    private var settings:ImageView?=null
    private var addCoin:ImageView?=null
    private var tab_layout:TabLayout?=null
    private var view_pager:ViewPager?=null
    private var followers:LinearLayout?=null
    private var followings:LinearLayout?=null
    private var userName:TextView?=null
    private var About:TextView?=null
    private var followerCount:TextView?=null
    private var followingCount:TextView?=null
    private var communityHelp:LinearLayout?=null
    private var coins:TextView?=null
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
        coins = view.findViewById(R.id.coins)
        communityHelp = view.findViewById(R.id.community_help)
        addCoin = view.findViewById(R.id.add_coin)
        initData()
        return view
    }

    private fun initData() {
        userDetails()
        tab_layout!!.addTab(tab_layout!!.newTab().setText(getString(R.string.about_tab)))
        tab_layout!!.addTab(tab_layout!!.newTab().setText(getString(R.string.post_tab)))
        val tabsAdapter =
            OthersTabAdapter(
                childFragmentManager,
                tab_layout!!.tabCount
            )
        view_pager!!.adapter = tabsAdapter
        view_pager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        tab_layout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                view_pager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        followers!!.setOnClickListener {
            startActivity(Intent(context, FollowFollowerActivity::class.java))
        }

        followings!!.setOnClickListener {
            startActivity(Intent(context, FollowFollowerActivity::class.java))
        }

        settings!!.setOnClickListener {
            startActivity(Intent(context, SettingsActivity::class.java))
        }

        communityHelp!!.setOnClickListener {
            startActivity(Intent(context, CommunityHelpActivity::class.java))
        }

        addCoin!!.setOnClickListener { addCoinsDialog() }
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


                                        followerCount!!.text = response.body()!!.data.follower_count.toString()
                                        followingCount!!.text = response.body()!!.data.following_count.toString()
                                        coins!!.text = response.body()!!.data.user.total_available_coins.toString()
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

    fun addCoinsDialog() {
        var done: LinearLayout? = null
        var edtCoin: EditText? = null
        val dialog = context?.let { Dialog(it) }
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.addcoin_dialog)
        dialog!!.setCancelable(true)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        done = dialog.findViewById(R.id.done)
        edtCoin = dialog.findViewById(R.id.edt_coin)

        done!!.setOnClickListener {
            if (edtCoin.text.toString().trim() == ""){
                edtCoin.requestFocus()
                edtCoin.error = getString(R.string.please_coin)
            }else{
                FCSharedPreferances.getSharedPreferance(context).paymenT_TYPE ="profile"
                dialog.cancel()
                generateToken(edtCoin.text.toString().trim())
            }
        }

        dialog.show()
    }

    fun generateToken(amount:String){
        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let {
            WebServiceRequest.getInstance().generateCashFreeToken(
                it,amount, "INR", "Test Transaction",
                object : Callback<PaymentTokenGenerateResponse> {
                    override fun onResponse(
                        call: Call<PaymentTokenGenerateResponse>,
                        response: Response<PaymentTokenGenerateResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    Toast.makeText(
                                        context,
                                        "" + response.body()!!.message,
                                        Toast.LENGTH_LONG
                                    ).show()
                                    var params: HashMap<String, String> = HashMap()
                                    params[CFPaymentService.PARAM_APP_ID] = "22061307922ac43c73853febd0316022"
                                    params[CFPaymentService.PARAM_ORDER_ID] = response.body()!!.data.data.orderId
                                    params[CFPaymentService.PARAM_ORDER_AMOUNT] = response.body()!!.data.data.orderAmount
                                    params[CFPaymentService.PARAM_ORDER_NOTE] = "Gunjan"
                                    params[CFPaymentService.PARAM_CUSTOMER_NAME] = response.body()!!.data.data.customerName
                                    params[CFPaymentService.PARAM_CUSTOMER_PHONE] = response.body()!!.data.data.customerPhone
                                    params[CFPaymentService.PARAM_CUSTOMER_EMAIL] = response.body()!!.data.data.customerEmail
                                    params[CFPaymentService.PARAM_ORDER_CURRENCY] = response.body()!!.data.data.orderCurrency
                                    CFPaymentService.getCFPaymentServiceInstance().doPayment(
                                        context as Activity,
                                        params,
                                        response.body()!!.data.data.tokenData,
                                        "PROD"
                                    )
                                } else {
                                    ProjectUtill.printMessage(
                                        activity!!.window.decorView,
                                        response.body()?.message
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    activity!!.window.decorView,
                                    ""
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                activity!!.window.decorView,
                                ""
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<PaymentTokenGenerateResponse>,
                        t: Throwable
                    ) {
                        myDialog.dismiss()
                        ProjectUtill.printErrorMessage(
                            activity!!.window.decorView,
                            ""
                        )
                    }
                })
        }
    }

    fun addCoins(amount: String) {
        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let { it1 ->
            WebServiceRequest.getInstance().addCoin(
                it1,amount,
                object : Callback<AddCoinResponse> {
                    override fun onResponse(
                        call: Call<AddCoinResponse>,
                        response: Response<AddCoinResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    FCSharedPreferances.getSharedPreferance(context).totaL_COINS = response.body()!!.data.total_available_coins.toString()
                                    coins!!.text = response.body()!!.data.total_available_coins.toString()
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
                        call: Call<AddCoinResponse>,
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