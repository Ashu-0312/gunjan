package app.gunjan.activities

import android.app.Dialog
import android.content.*
import android.hardware.display.DisplayManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.adapters.AllCommentsAdapter
import app.gunjan.adapters.AllCommentsReplysAdapter
import app.gunjan.adapters.CoinsAdapter
import app.gunjan.adapters.ReasonList2Adapter
import app.gunjan.entity.*
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.utill.RecyclerItemClickListener
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import com.cashfree.pg.CFPaymentService
import kotlinx.android.synthetic.main.activity_post_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class PostDetailsActivity : AppCompatActivity(), RecyclerItemClickListener {
    var dialogComment: Dialog? = null
    var dialogCommentReply: Dialog? = null
    var blankData2: TextView? = null
    private var postId: String? = ""
    private var commentId: String? = ""
    var blankData3: TextView? = null
    var commentRecycler: RecyclerView? = null
    var replyRecycler: RecyclerView? = null
    private var reasonLayout: LinearLayout? = null
    var data: PostListResponse.DataBean.PostBean? = null
    private var Status: String? = "2"
    private var id: String? = ""
    var coinDialog: Dialog? = null
    var totalCoins: TextView? = null
    private var coinList: ArrayList<String> = ArrayList()

    var idd: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)
        initData()
    }

    private fun initData() {
        dialogComment = Dialog(this)
        dialogCommentReply = Dialog(this)

        if (intent.hasExtra("id")) {
            id = intent.getStringExtra("id").toString()
            getDetails()
        } else {
            if (FCSharedPreferances.getSharedPreferance(this).statuS_LOGIN.equals("true")) {
                val appLinkAction: String? = intent?.action
                val appLinkData: Uri? = intent?.data
                if (Intent.ACTION_VIEW == appLinkAction && appLinkData != null) {
                    id = appLinkData.getQueryParameter("cid")
                    getDetails()
                }
            } else {
                val intent = Intent(this@PostDetailsActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }
        show_more.setOnClickListener {
            if (show_more.text.toString() == getString(R.string.showmore)) {
                description.maxLines = Int.MAX_VALUE //your TextView
                show_more.text = getString(R.string.showless)
            } else {
                description.maxLines = 4 //your TextView
                show_more.text = getString(R.string.showmore)
            }
        }

        menu.setOnClickListener {

            if (data!!.created_by.id.toString() == FCSharedPreferances.getSharedPreferance(
                    this
                ).useR_ID
            ) {
                val popup = PopupMenu(this, menu)

                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu2)
                popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

                    when (item!!.itemId) {
                        R.id.copy -> {
                            copyText(description.text.toString().trim())
                        }
                    }

                    true
                })

                popup.show()
            } else {
                val popup = PopupMenu(this, menu)
                //inflating menu from xml resource
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu)
                popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

                    when (item!!.itemId) {
                        R.id.block -> {
                            blockDialog(data!!.created_by.id.toString())
                        }
                        R.id.copy -> {
                            copyText(description.text.toString().trim())
                        }
                        R.id.report -> {
                            // reportDialog(data.id.toString(), "user")
                        }
                    }

                    true
                })

                popup.show()
            }
        }

        play.setOnClickListener {
            play.visibility = View.GONE
            pause.visibility = View.GONE
            media_video.start()
        }

        pause.setOnClickListener {
            play.visibility = View.VISIBLE
            pause.visibility = View.GONE
            media_video.pause()
        }

        media_video.setOnCompletionListener(MediaPlayer.OnCompletionListener {
            play.visibility = View.VISIBLE
            pause.visibility = View.GONE
        })

        media_video.setOnPreparedListener(MediaPlayer.OnPreparedListener {
            progress_bar.visibility = View.GONE
            play.visibility = View.VISIBLE
            pause.visibility = View.GONE
        })

        like.setOnClickListener {
            val myDialog = ProjectUtill.showProgressDialog(this)
            WebServiceRequest.getInstance().likeDislikePost(
                this, data!!.id.toString(), "love", "1",
                object : Callback<LikeDislikePostResponse> {
                    override fun onResponse(
                        call: Call<LikeDislikePostResponse>,
                        response: Response<LikeDislikePostResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    total_like.text =
                                        response.body()!!.data.post.total_like.toString()
                                    total_dislike.text =
                                        response.body()!!.data.post.total_unlike.toString()
                                } else {
                                    ProjectUtill.printMessage(
                                        this@PostDetailsActivity.window.decorView,
                                        response.body()?.message
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    this@PostDetailsActivity.window.decorView,
                                    ""
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@PostDetailsActivity.window.decorView,
                                ""
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<LikeDislikePostResponse>,
                        t: Throwable
                    ) {
                        myDialog.dismiss()
                        ProjectUtill.printErrorMessage(
                            this@PostDetailsActivity.window.decorView,
                            ""
                        )
                    }
                })
        }

        dislike.setOnClickListener {
            val myDialog = ProjectUtill.showProgressDialog(this)
            WebServiceRequest.getInstance().likeDislikePost(
                this, data!!.id.toString(), "love", "0",
                object : Callback<LikeDislikePostResponse> {
                    override fun onResponse(
                        call: Call<LikeDislikePostResponse>,
                        response: Response<LikeDislikePostResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    total_like.text =
                                        response.body()!!.data.post.total_like.toString()
                                    total_dislike.text =
                                        response.body()!!.data.post.total_unlike.toString()
                                } else {
                                    ProjectUtill.printMessage(
                                        this@PostDetailsActivity.window.decorView,
                                        response.body()?.message
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    this@PostDetailsActivity.window.decorView,
                                    ""
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@PostDetailsActivity.window.decorView,
                                ""
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<LikeDislikePostResponse>,
                        t: Throwable
                    ) {
                        myDialog.dismiss()
                        ProjectUtill.printErrorMessage(
                            this@PostDetailsActivity.window.decorView,
                            ""
                        )
                    }
                })
        }

        comment_layout.setOnClickListener {
            commentsDialog(data!!.id.toString())
        }

        comment_layout2.setOnClickListener {
            commentsDialog(data!!.id.toString())
        }

        reward.setOnClickListener {
            coinsDialog(data!!.id.toString())
        }

        joined_event.setOnClickListener {
            val intent = Intent(this, JoinedEventUserListActivity::class.java)
            intent.putExtra("id", data!!.id.toString())
            startActivity(intent)
        }

        join_event.setOnClickListener {
            if (!data!!.isJoinedThisEvent) {
                val myDialog = ProjectUtill.showProgressDialog(this)
                WebServiceRequest.getInstance().joinEvent(
                    this, data!!.id.toString(),
                    object : Callback<JoinEventResponse> {
                        override fun onResponse(
                            call: Call<JoinEventResponse>,
                            response: Response<JoinEventResponse>
                        ) {
                            myDialog.dismiss()
                            if (response != null) {
                                if (response.isSuccessful) {
                                    if (response.body()!!.code == 1) {
                                        join_txt.text =
                                            getString(R.string.joined)
                                        data!!.isJoinedThisEvent = true
                                        total_users.text =
                                            response.body()!!.data.total_member + " " + getString(
                                                R.string._0_users_joined
                                            )
                                    } else {
                                        ProjectUtill.printMessage(
                                            this@PostDetailsActivity.window.decorView,
                                            response.body()?.message
                                        )
                                    }
                                } else {
                                    ProjectUtill.printErrorMessage(
                                        this@PostDetailsActivity.window.decorView,
                                        ""
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    this@PostDetailsActivity.window.decorView,
                                    ""
                                )
                            }
                        }

                        override fun onFailure(
                            call: Call<JoinEventResponse>,
                            t: Throwable
                        ) {
                            myDialog.dismiss()
                            ProjectUtill.printErrorMessage(
                                this@PostDetailsActivity.window.decorView,
                                ""
                            )
                        }
                    })
            }
        }

        video_layout.setOnClickListener {
            if (data!!.content_type == "video") {
                play.visibility = View.VISIBLE
                pause.visibility = View.GONE
                media_video.pause()
            } else {
                Log.d("", "")
            }
        }
    }

    fun coinsDialog(id: String) {
        val coinsRecycler: RecyclerView?
        val addCoins: CardView?
        val close: LinearLayout?
        coinDialog = Dialog(this)
        // Include dialog.xml file
        coinDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        coinDialog!!.setContentView(R.layout.reward_dialog)
        coinDialog!!.setCancelable(true)
        val window = coinDialog!!.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        coinDialog!!.window!!.attributes.windowAnimations = R.style.DialogAnimation2
        coinDialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        coinsRecycler = coinDialog!!.findViewById(R.id.coins_recycler)
        addCoins = coinDialog!!.findViewById(R.id.add_coins)
        totalCoins = coinDialog!!.findViewById(R.id.total_coins)
        close = coinDialog!!.findViewById(R.id.close)

        idd = id
        totalCoins!!.text = FCSharedPreferances.getSharedPreferance(this).totaL_COINS

        coinList.clear()
        coinList.add("5")
        coinList.add("10")
        coinList.add("15")
        coinList.add("20")
        coinList.add("25")
        coinList.add("30")
        coinList.add("35")
        coinList.add("40")
        coinList.add("45")
        coinList.add("50")
        coinList.add("55")
        coinList.add("60")
        coinList.add("65")
        coinList.add("70")
        coinList.add("75")
        coinList.add("80")
        coinList.add("85")
        coinList.add("90")
        coinList.add("95")
        coinList.add("100")

        val coinsAdapter = CoinsAdapter(this, coinList, this@PostDetailsActivity)
        coinsRecycler.layoutManager = GridLayoutManager(this, 4)
        coinsRecycler.adapter = coinsAdapter

        close!!.setOnClickListener { coinDialog!!.cancel() }

        addCoins!!.setOnClickListener { addCoinsDialog() }

        coinDialog!!.show()
    }

    private fun addCoinsDialog() {
        val done: LinearLayout?
        val edtCoin: EditText?
        val dialog = Dialog(this)
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.addcoin_dialog)
        dialog.setCancelable(true)
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
            if (edtCoin.text.toString().trim() == "") {
                edtCoin.requestFocus()
                edtCoin.error = getString(R.string.please_coin)
            } else {
                FCSharedPreferances.getSharedPreferance(this).paymenT_TYPE = "home"
                dialog.cancel()
                generateToken(edtCoin.text.toString().trim())
            }
        }

        dialog.show()
    }

    fun generateToken(amount: String) {
        val myDialog = ProjectUtill.showProgressDialog(this)
        WebServiceRequest.getInstance().generateCashFreeToken(
            this, amount, "INR", "Test Transaction",
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
                                    this@PostDetailsActivity,
                                    "" + response.body()!!.message,
                                    Toast.LENGTH_LONG
                                ).show()
                                val params: HashMap<String, String> = HashMap()
                                params[CFPaymentService.PARAM_APP_ID] =
                                    "22061307922ac43c73853febd0316022"
                                params[CFPaymentService.PARAM_ORDER_ID] =
                                    response.body()!!.data.data.orderId
                                params[CFPaymentService.PARAM_ORDER_AMOUNT] =
                                    response.body()!!.data.data.orderAmount
                                params[CFPaymentService.PARAM_ORDER_NOTE] = "Gunjan"
                                params[CFPaymentService.PARAM_CUSTOMER_NAME] =
                                    response.body()!!.data.data.customerName
                                params[CFPaymentService.PARAM_CUSTOMER_PHONE] =
                                    response.body()!!.data.data.customerPhone
                                params[CFPaymentService.PARAM_CUSTOMER_EMAIL] =
                                    response.body()!!.data.data.customerEmail
                                params[CFPaymentService.PARAM_ORDER_CURRENCY] =
                                    response.body()!!.data.data.orderCurrency
                                CFPaymentService.getCFPaymentServiceInstance().doPayment(
                                    this@PostDetailsActivity,
                                    params,
                                    response.body()!!.data.data.tokenData,
                                    "PROD"
                                )
                            } else {
                                ProjectUtill.printMessage(
                                    this@PostDetailsActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@PostDetailsActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@PostDetailsActivity.window.decorView,
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
                        this@PostDetailsActivity.window.decorView,
                        ""
                    )
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Same request code for all payment APIs.
        Log.d(ContentValues.TAG, "ReqCode : " + CFPaymentService.REQ_CODE)
        Log.d(ContentValues.TAG, "API Response : ")
        //Prints all extras. Replace with app logic.
        if (data != null) {
            val bundle = data.extras
            if (bundle != null) {
                if (bundle.getString("txStatus")
                        .toString() == "CANCELLED" || bundle.getString("txStatus")
                        .toString() == "FAILED"
                ) {
                    Toast.makeText(this, getString(R.string.failed), Toast.LENGTH_LONG).show()
                } else {
                    addCoins(bundle.getString("orderAmount").toString())
                }
            }
        }
    }

    fun addCoins(amount: String) {
        val myDialog = ProjectUtill.showProgressDialog(this)
        WebServiceRequest.getInstance().addCoin(
            this, amount,
            object : Callback<AddCoinResponse> {
                override fun onResponse(
                    call: Call<AddCoinResponse>,
                    response: Response<AddCoinResponse>
                ) {
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                FCSharedPreferances.getSharedPreferance(this@PostDetailsActivity).totaL_COINS =
                                    response.body()!!.data.total_available_coins.toString()
                                totalCoins!!.text =
                                    response.body()!!.data.total_available_coins.toString()
                            } else {
                                ProjectUtill.printMessage(
                                    this@PostDetailsActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@PostDetailsActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@PostDetailsActivity.window.decorView,
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
                        this@PostDetailsActivity.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun toastDialog() {
        val errorTxt: TextView?
        val close: ImageView?
        val dialog = Dialog(this)
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.toast_dialog)
        dialog.setCancelable(true)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        close = dialog.findViewById(R.id.close)
        errorTxt = dialog.findViewById(R.id.error_txt)

        close.setOnClickListener {
            dialog.cancel()
        }

        errorTxt.setOnClickListener {
            dialog.cancel()
            addCoinsDialog()
        }

        dialog.show()
    }

    private fun donateCoins(coin: String) {
        val myDialog = ProjectUtill.showProgressDialog(this)
        WebServiceRequest.getInstance().addPostCoin(
            this, coin, idd!!,
            object : Callback<DonateCoinResponse> {
                override fun onResponse(
                    call: Call<DonateCoinResponse>,
                    response: Response<DonateCoinResponse>
                ) {
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                coinDialog!!.cancel()
                                FCSharedPreferances.getSharedPreferance(this@PostDetailsActivity).totaL_COINS =
                                    response.body()!!.data.total_available_coins.toString()
                                getDetails()
                            } else {
                                ProjectUtill.printMessage(
                                    this@PostDetailsActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@PostDetailsActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@PostDetailsActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<DonateCoinResponse>,
                    t: Throwable
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@PostDetailsActivity.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun getDetails() {

        val myDialog = ProjectUtill.showProgressDialog(this)
        WebServiceRequest.getInstance().postDetails(
            this,
            id!!,
            object : Callback<PostDetailsRes> {
                override fun onResponse(
                    call: Call<PostDetailsRes>,
                    response: Response<PostDetailsRes>
                ) {
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                if (response.body()!!.data.post == null) {
                                    if (FCSharedPreferances.getSharedPreferance(this@PostDetailsActivity).statuS_LOGIN.equals(
                                            "true"
                                        )
                                    ) {
                                        val intent = Intent(
                                            this@PostDetailsActivity,
                                            HomeActivity::class.java
                                        )
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        val intent = Intent(
                                            this@PostDetailsActivity,
                                            LoginActivity::class.java
                                        )
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        startActivity(intent)
                                        finish()
                                    }
                                } else {
                                    name.text =
                                        response.body()!!.data.post.created_by.first_name + " " + response.body()!!.data.post.created_by.last_name
                                    total_comment.text =
                                        response.body()!!.data.post.total_comment.toString()
                                    total_like.text =
                                        response.body()!!.data.post.total_like.toString()
                                    total_dislike.text =
                                        response.body()!!.data.post.total_unlike.toString()

                                    if (response.body()!!.data.post.isJoinedThisEvent) {
                                        join_txt.text = getString(R.string.joined)
                                    } else {
                                        join_txt.text = getString(R.string.join_event)
                                    }

                                    total_users.text =
                                        response.body()!!.data.post.total_joined_member + " " + getString(
                                            R.string._0_users_joined
                                        )


                                    val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                                    val output = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

                                    var d: Date? = null
                                    try {
                                        d = input.parse(response.body()!!.data.post.createdAt)
                                    } catch (e: ParseException) {
                                        e.printStackTrace()
                                    }
                                    val formatted = output.format(d)
                                    Log.i("DATE", "" + formatted)

                                    time.text = convertTimeToText(formatted)
                                    Glide.with(this@PostDetailsActivity)
                                        .load(response.body()!!.data.post.created_by.image)
                                        .placeholder(R.drawable.user_avatar)
                                        .into(user_profile)

                                    if (response.body()!!.data.post.feed_type == "event") {
                                        event_layout.visibility = View.VISIBLE
                                        joined_event.visibility = View.VISIBLE
                                        join_event.visibility = View.VISIBLE
                                        var format = SimpleDateFormat("yyyy-MM-dd")
                                        val date1 =
                                            format.parse(response.body()!!.data.post.start_date)
                                        val date2 = format.format(date1)

                                        format =
                                            if (date2.endsWith("01") && !date2.endsWith("11")) SimpleDateFormat(
                                                "d'st'"
                                            ) else if (date2.endsWith(
                                                    "02"
                                                ) && !date2.endsWith("12")
                                            ) SimpleDateFormat("d'nd'") else if (date2.endsWith("03") && !date2.endsWith(
                                                    "13"
                                                )
                                            ) SimpleDateFormat(
                                                "d'rd'"
                                            ) else SimpleDateFormat("d'th'")

                                        val yourDate = format.format(date1)
                                        activity_day.text = yourDate

                                        var format2 = SimpleDateFormat("yyyy-MM-dd")
                                        val date3 =
                                            format2.parse(response.body()!!.data.post.start_date)
                                        val date4 = format2.format(date3)

                                        format2 =
                                            if (date4.endsWith("01") && !date4.endsWith("11")) SimpleDateFormat(
                                                "MMM"
                                            ) else if (date4.endsWith(
                                                    "02"
                                                ) && !date4.endsWith("12")
                                            ) SimpleDateFormat("MMM") else if (date4.endsWith("03") && !date4.endsWith(
                                                    "13"
                                                )
                                            ) SimpleDateFormat(
                                                "MMM"
                                            ) else SimpleDateFormat("MMM")

                                        val yourMonth = format2.format(date3)
                                        activity_month.text = yourMonth

                                        val tk =
                                            StringTokenizer(response.body()!!.data.post.start_date.toString() + " " + response.body()!!.data.post.start_time)
                                        val date = tk.nextToken()
                                        val time = tk.nextToken()

                                        val sdf = SimpleDateFormat("hh:mm:ss")
                                        val sdfs = SimpleDateFormat("hh:mmaa")
                                        val dt: Date
                                        try {
                                            dt = sdf.parse(time)
                                            activity_time.text = sdfs.format(dt)
                                        } catch (e: ParseException) {
                                            e.printStackTrace()
                                        }
                                    } else {
                                        event_layout.visibility = View.GONE
                                        joined_event.visibility = View.GONE
                                        join_event.visibility = View.GONE
                                    }

                                    when (response.body()!!.data.post.content_type) {
                                        "image" -> {
                                            pic_layout.visibility = View.VISIBLE
                                            txt_layout.visibility = View.VISIBLE
                                            video_layout.visibility = View.GONE
                                            media_video.visibility = View.GONE
                                            Glide.with(this@PostDetailsActivity)
                                                .load(response.body()!!.data.post.file)
                                                .placeholder(R.drawable.user_avatar)
                                                .into(pic_layout)
                                            description.text =
                                                response.body()!!.data.post.description
                                        }
                                        "video" -> {
                                            pic_layout.visibility = View.GONE
                                            txt_layout.visibility = View.VISIBLE
                                            video_layout.visibility = View.VISIBLE
                                            media_video.visibility = View.VISIBLE
                                            val display = getSystemService<DisplayManager>()
                                                ?.getDisplay(Display.DEFAULT_DISPLAY)
                                            val width = display!!.width
                                            val height = display.height
                                            media_video.layoutParams =
                                                FrameLayout.LayoutParams(width, height)
                                            media_video.setVideoPath(response.body()!!.data.post.file)
                                            description.text =
                                                response.body()!!.data.post.description
                                        }
                                        "text" -> {
                                            pic_layout.visibility = View.GONE
                                            txt_layout.visibility = View.VISIBLE
                                            video_layout.visibility = View.GONE
                                            media_video.visibility = View.GONE
                                            description.text =
                                                response.body()!!.data.post.description
                                        }
                                    }
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@PostDetailsActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@PostDetailsActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@PostDetailsActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<PostDetailsRes>,
                    t: Throwable
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@PostDetailsActivity.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun convertTimeToText(dataDate: String?): String? {
        var convTime: String? = null
        val suffix = getString(R.string.ago)
        try {
            val dateFormat = SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss"
            )
            val oldDate: Date = dateFormat.parse(dataDate)
            val nowTime = Date()
            val dateDiff = nowTime.time - oldDate.time - 19800000
            val second: Long = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
            val minute: Long = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
            val hour: Long = TimeUnit.MILLISECONDS.toHours(dateDiff)
            val day: Long = TimeUnit.MILLISECONDS.toDays(dateDiff)
            if (second < 60) {
                convTime =
                    second.toString() + " " + getString(R.string.seconds) + " " + suffix
            } else if (minute < 60) {
                convTime =
                    minute.toString() + " " + getString(R.string.minutes) + " " + suffix
            } else if (hour < 24) {
                convTime =
                    hour.toString() + " " + getString(R.string.hours) + " " + suffix
            } else if (day >= 7) {
                convTime = if (day > 360) {
                    (day / 360).toString() + " " + getString(R.string.years) + " " + suffix
                } else if (day > 30) {
                    (day / 30).toString() + " " + getString(R.string.months) + " " + suffix
                } else {
                    (day / 7).toString() + " " + getString(R.string.weeks) + " " + suffix
                }
            } else if (day < 7) {
                convTime = day.toString() + " " + getString(R.string.days) + " " + suffix
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            Log.e("ConvTimeE", e.message!!)
        }
        return convTime
    }

    private fun copyText(text: String) {
        val myClipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val myClip: ClipData = ClipData.newPlainText("Label", text)
        myClipboard.setPrimaryClip(myClip)
    }

    private fun blockDialog(userId: String) {
        val yes: LinearLayout?
        val no: LinearLayout?
        val close: ImageView?
        val dialog = Dialog(this)
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.block_dialog)
        dialog.setCancelable(true)
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
            val myDialog = ProjectUtill.showProgressDialog(this)
            WebServiceRequest.getInstance().blockUnblockUser(
                this, userId, "0",
                object : Callback<BlockUnblockUserResponse> {
                    override fun onResponse(
                        call: Call<BlockUnblockUserResponse>,
                        response: Response<BlockUnblockUserResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    Toast.makeText(
                                        this@PostDetailsActivity,
                                        "" + response.body()!!.message,
                                        Toast.LENGTH_LONG
                                    ).show()

                                } else {
                                    ProjectUtill.printMessage(
                                        this@PostDetailsActivity.window.decorView,
                                        response.body()?.message
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    this@PostDetailsActivity.window.decorView,
                                    ""
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@PostDetailsActivity.window.decorView,
                                ""
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<BlockUnblockUserResponse>,
                        t: Throwable
                    ) {
                        myDialog.dismiss()
                        ProjectUtill.printErrorMessage(
                            this@PostDetailsActivity.window.decorView,
                            ""
                        )
                    }
                })
        }
        no.setOnClickListener {
            dialog.cancel()
        }

        close.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }

    private fun commentsDialog(id: String) {
        val close: ImageView?
        val addComment: ImageView?
        val edtComment: EditText?
        // Include dialog.xml file
        dialogComment!!.setContentView(R.layout.comment_dialog)
        dialogComment!!.setCancelable(true)
        val window = dialogComment!!.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialogComment!!.window!!.attributes.windowAnimations = R.style.DialogAnimation2
        dialogComment!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        close = dialogComment!!.findViewById(R.id.close)
        addComment = dialogComment!!.findViewById(R.id.add)
        edtComment = dialogComment!!.findViewById(R.id.edt_comment)
        blankData2 = dialogComment!!.findViewById(R.id.blank_data)
        commentRecycler = dialogComment!!.findViewById(R.id.comment_recycler)
        postId = id
        getCommentList(postId!!)
        addComment!!.setOnClickListener {
            if (edtComment.text.toString().trim() != "") {
                val myDialog = ProjectUtill.showProgressDialog(this)
                WebServiceRequest.getInstance().addComment(
                    this, postId!!, "text", edtComment.text.toString().trim(),
                    object : Callback<AddCommentResponse> {
                        override fun onResponse(
                            call: Call<AddCommentResponse>,
                            response: Response<AddCommentResponse>
                        ) {
                            myDialog.dismiss()
                            if (response != null) {
                                if (response.isSuccessful) {
                                    if (response.body()!!.code == 1) {
                                        dialogComment!!.cancel()
                                        getDetails()
                                    } else {
                                        ProjectUtill.printMessage(
                                            this@PostDetailsActivity.window.decorView,
                                            response.body()?.message
                                        )
                                    }
                                } else {
                                    ProjectUtill.printErrorMessage(
                                        this@PostDetailsActivity.window.decorView,
                                        ""
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    this@PostDetailsActivity.window.decorView,
                                    ""
                                )
                            }
                        }

                        override fun onFailure(
                            call: Call<AddCommentResponse>,
                            t: Throwable
                        ) {
                            myDialog.dismiss()
                            ProjectUtill.printErrorMessage(
                                this@PostDetailsActivity.window.decorView,
                                ""
                            )
                        }
                    })
            }
        }

        close.setOnClickListener {
            dialogComment!!.cancel()
            getDetails()
        }

        dialogComment!!.show()
    }

    private fun getCommentList(postId: String) {
        WebServiceRequest.getInstance().commentList(
            this, postId,
            object : Callback<CommentListResponse> {
                override fun onResponse(
                    call: Call<CommentListResponse>,
                    response: Response<CommentListResponse>
                ) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                if (response.body()!!.data.comments.size == 0) {
                                    commentRecycler!!.visibility = View.GONE
                                    blankData2!!.visibility = View.VISIBLE
                                } else {
                                    commentRecycler!!.visibility = View.VISIBLE
                                    blankData2!!.visibility = View.GONE
                                    val commentsAdapter = AllCommentsAdapter(
                                        this@PostDetailsActivity,
                                        response.body()!!.data.comments, this@PostDetailsActivity
                                    )
                                    val layoutManager = LinearLayoutManager(
                                        this@PostDetailsActivity,
                                        LinearLayoutManager.VERTICAL,
                                        true
                                    )
                                    commentRecycler!!.layoutManager = layoutManager
                                    commentRecycler!!.adapter = commentsAdapter
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@PostDetailsActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@PostDetailsActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@PostDetailsActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<CommentListResponse>,
                    t: Throwable
                ) {
                    ProjectUtill.printErrorMessage(
                        this@PostDetailsActivity.window.decorView,
                        ""
                    )
                }
            })
    }

    override fun onItemClick(parentPos: Int, childPos: Int, data: Any, type: String) {
        if (type == "delete") {
            deleteCommentDialog(
                (data as CommentListResponse.DataBean.CommentsBean).id.toString(),
                (data).commented_by.id.toString()
            )
        } else if (type == "reply") {
            commentsReplyDialog((data as CommentListResponse.DataBean.CommentsBean).id.toString())
        } else if (type == "toast") {
            toastDialog()
        } else if (type == "donate") {
            donateCoins((data as String).toString())
        }
    }

    private fun commentsReplyDialog(id: String) {
        val close: ImageView?
        val addComment: ImageView?
        val edtComment: EditText?
        // Include dialog.xml file
        dialogCommentReply!!.setContentView(R.layout.commentreply_dialog)
        dialogCommentReply!!.setCancelable(true)
        val window = dialogCommentReply!!.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialogCommentReply!!.window!!.attributes.windowAnimations = R.style.DialogAnimation2
        dialogCommentReply!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        close = dialogCommentReply!!.findViewById(R.id.close)
        addComment = dialogCommentReply!!.findViewById(R.id.add)
        edtComment = dialogCommentReply!!.findViewById(R.id.edt_comment)
        blankData3 = dialogCommentReply!!.findViewById(R.id.blank_data2)
        replyRecycler = dialogCommentReply!!.findViewById(R.id.reply_recycler)
        commentId = id
        getReplyCommentList(commentId!!)
        addComment!!.setOnClickListener {
            if (edtComment.text.toString().trim() != "") {
                val myDialog = ProjectUtill.showProgressDialog(this)
                WebServiceRequest.getInstance().addCommentOnReply(
                    this, commentId!!, "text", edtComment.text.toString().trim(),
                    object : Callback<AddReplyResponse> {
                        override fun onResponse(
                            call: Call<AddReplyResponse>,
                            response: Response<AddReplyResponse>
                        ) {
                            myDialog.dismiss()
                            if (response != null) {
                                if (response.isSuccessful) {
                                    if (response.body()!!.code == 1) {
                                        dialogCommentReply!!.cancel()
                                    } else {
                                        ProjectUtill.printMessage(
                                            this@PostDetailsActivity.window.decorView,
                                            response.body()?.message
                                        )
                                    }
                                } else {
                                    ProjectUtill.printErrorMessage(
                                        this@PostDetailsActivity.window.decorView,
                                        ""
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    this@PostDetailsActivity.window.decorView,
                                    ""
                                )
                            }
                        }

                        override fun onFailure(
                            call: Call<AddReplyResponse>,
                            t: Throwable
                        ) {
                            myDialog.dismiss()
                            ProjectUtill.printErrorMessage(
                                this@PostDetailsActivity.window.decorView,
                                ""
                            )
                        }
                    })
            }
        }

        close.setOnClickListener {
            dialogCommentReply!!.cancel()
        }

        dialogCommentReply!!.show()
    }

    private fun deleteCommentDialog(commentId: String, userId: String) {
        val delete: RelativeLayout?
        val report: RelativeLayout?
        val dialog = Dialog(this)
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.deletecomment_dialog)
        dialog.setCancelable(true)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        delete = dialog.findViewById(R.id.delete_comment)
        report = dialog.findViewById(R.id.report_comment)

        if (FCSharedPreferances.getSharedPreferance(this).useR_ID.equals(userId)) {
            delete.visibility = View.VISIBLE
            report.visibility = View.GONE
        } else {
            delete.visibility = View.GONE
            report.visibility = View.VISIBLE
        }

        delete!!.setOnClickListener {
            dialog.cancel()
            val myDialog = ProjectUtill.showProgressDialog(this)
            WebServiceRequest.getInstance().deleteComment(
                this, commentId,
                object : Callback<DeleteCommentResponse> {
                    override fun onResponse(
                        call: Call<DeleteCommentResponse>,
                        response: Response<DeleteCommentResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    getCommentList(postId!!)
                                } else {
                                    ProjectUtill.printMessage(
                                        this@PostDetailsActivity.window.decorView,
                                        response.body()?.message
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    this@PostDetailsActivity.window.decorView,
                                    ""
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@PostDetailsActivity.window.decorView,
                                ""
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<DeleteCommentResponse>,
                        t: Throwable
                    ) {
                        myDialog.dismiss()
                        ProjectUtill.printErrorMessage(
                            this@PostDetailsActivity.window.decorView,
                            ""
                        )
                    }
                })
        }

        report?.setOnClickListener {
            dialog.cancel()
            reportDialog(commentId, "comment")
        }
        dialog.show()
    }

    private fun reportDialog(userId: String, type: String) {
        val title: TextView?
        val yes: LinearLayout?
        val no: LinearLayout?
        val close: ImageView?
        val edtReason: EditText?
        val reasonRecycler: RecyclerView?
        val dialog = Dialog(this)
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.report_dialog)
        dialog.setCancelable(true)
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
        reasonRecycler = dialog.findViewById(R.id.reason_recycler)
        close = dialog.findViewById(R.id.close)
        edtReason = dialog.findViewById(R.id.reason_edt)
        reasonLayout = dialog.findViewById(R.id.reasonLayout)
        title = dialog.findViewById(R.id.title)

        if (type == "user") {
            title?.text = getString(R.string.report_post)
        } else {
            title?.text = getString(R.string.report_comment)
        }

        val myDialog = ProjectUtill.showProgressDialog(this)
        WebServiceRequest.getInstance().reasonList(
            this,
            object : Callback<ReasonListResponse> {
                override fun onResponse(
                    call: Call<ReasonListResponse>,
                    response: Response<ReasonListResponse>
                ) {
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                val reasonAdapter = ReasonList2Adapter(
                                    this@PostDetailsActivity,
                                    response.body()!!.data.reason_list,
                                    this@PostDetailsActivity
                                )
                                val layoutManager =
                                    LinearLayoutManager(this@PostDetailsActivity)
                                reasonRecycler.layoutManager = layoutManager
                                reasonRecycler.adapter = reasonAdapter
                            } else {
                                ProjectUtill.printMessage(
                                    this@PostDetailsActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@PostDetailsActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@PostDetailsActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<ReasonListResponse>,
                    t: Throwable
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@PostDetailsActivity.window.decorView,
                        ""
                    )
                }
            })

        yes.setOnClickListener {
            if (type == "user") {
                if (Status.equals("1")) {
                    if (edtReason.text.toString().trim() == "") {
                        Toast.makeText(
                            this,
                            getString(R.string.please_reason),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    } else {
                        dialog.cancel()
                        val myDialog = ProjectUtill.showProgressDialog(this)
                        WebServiceRequest.getInstance().reportPost(
                            this,
                            userId,
                            FCSharedPreferances.getSharedPreferance(this).reasoN_ID,
                            edtReason.text.toString(),
                            object : Callback<ReportCommentRes> {
                                override fun onResponse(
                                    call: Call<ReportCommentRes>,
                                    response: Response<ReportCommentRes>
                                ) {
                                    myDialog.dismiss()
                                    if (response != null) {
                                        if (response.isSuccessful) {
                                            if (response.body()!!.code == 1) {
                                                Status = "2"
                                            } else {
                                                ProjectUtill.printMessage(
                                                    this@PostDetailsActivity.window.decorView,
                                                    response.body()?.message
                                                )
                                            }
                                        } else {
                                            ProjectUtill.printErrorMessage(
                                                this@PostDetailsActivity.window.decorView,
                                                ""
                                            )
                                        }
                                    } else {
                                        ProjectUtill.printErrorMessage(
                                            this@PostDetailsActivity.window.decorView,
                                            ""
                                        )
                                    }
                                }

                                override fun onFailure(
                                    call: Call<ReportCommentRes>,
                                    t: Throwable
                                ) {
                                    myDialog.dismiss()
                                    ProjectUtill.printErrorMessage(
                                        this@PostDetailsActivity.window.decorView,
                                        ""
                                    )
                                }
                            })
                    }
                } else {
                    dialog.cancel()
                    val myDialog = ProjectUtill.showProgressDialog(this)
                    WebServiceRequest.getInstance().reportPost(
                        this,
                        userId,
                        FCSharedPreferances.getSharedPreferance(this).reasoN_ID,
                        "",
                        object : Callback<ReportCommentRes> {
                            override fun onResponse(
                                call: Call<ReportCommentRes>,
                                response: Response<ReportCommentRes>
                            ) {
                                myDialog.dismiss()
                                if (response != null) {
                                    if (response.isSuccessful) {
                                        if (response.body()!!.code == 1) {
                                            Status = "2"
                                        } else {
                                            ProjectUtill.printMessage(
                                                this@PostDetailsActivity.window.decorView,
                                                response.body()?.message
                                            )
                                        }
                                    } else {
                                        ProjectUtill.printErrorMessage(
                                            this@PostDetailsActivity.window.decorView,
                                            ""
                                        )
                                    }
                                } else {
                                    ProjectUtill.printErrorMessage(
                                        this@PostDetailsActivity.window.decorView,
                                        ""
                                    )
                                }
                            }

                            override fun onFailure(
                                call: Call<ReportCommentRes>,
                                t: Throwable
                            ) {
                                myDialog.dismiss()
                                ProjectUtill.printErrorMessage(
                                    this@PostDetailsActivity.window.decorView,
                                    ""
                                )
                            }
                        })
                }
            } else {
                if (Status.equals("1")) {
                    if (edtReason.text.toString().trim() == "") {
                        Toast.makeText(
                            this,
                            getString(R.string.please_reason),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    } else {
                        dialog.cancel()
                        val myDialog = ProjectUtill.showProgressDialog(this)
                        WebServiceRequest.getInstance().reportComment(
                            this,
                            userId,
                            FCSharedPreferances.getSharedPreferance(this).reasoN_ID,
                            edtReason.text.toString().toString(),
                            object : Callback<ReportCommentRes> {
                                override fun onResponse(
                                    call: Call<ReportCommentRes>,
                                    response: Response<ReportCommentRes>
                                ) {
                                    myDialog.dismiss()
                                    if (response != null) {
                                        if (response.isSuccessful) {
                                            if (response.body()!!.code == 1) {
                                                Toast.makeText(
                                                    this@PostDetailsActivity,
                                                    "" + response.body()!!.message,
                                                    Toast.LENGTH_LONG
                                                ).show()
                                                Status = "2"
                                                getCommentList(postId!!)
                                            } else {
                                                ProjectUtill.printMessage(
                                                    this@PostDetailsActivity.window.decorView,
                                                    response.body()?.message
                                                )
                                            }
                                        } else {
                                            ProjectUtill.printErrorMessage(
                                                this@PostDetailsActivity.window.decorView,
                                                ""
                                            )
                                        }
                                    } else {
                                        ProjectUtill.printErrorMessage(
                                            this@PostDetailsActivity.window.decorView,
                                            ""
                                        )
                                    }
                                }

                                override fun onFailure(
                                    call: Call<ReportCommentRes>,
                                    t: Throwable
                                ) {
                                    myDialog.dismiss()
                                    ProjectUtill.printErrorMessage(
                                        this@PostDetailsActivity.window.decorView,
                                        ""
                                    )
                                }
                            })
                    }
                } else {
                    dialog.cancel()
                    val myDialog = ProjectUtill.showProgressDialog(this)
                    WebServiceRequest.getInstance().reportComment(
                        this,
                        userId,
                        FCSharedPreferances.getSharedPreferance(this).reasoN_ID,
                        "",
                        object : Callback<ReportCommentRes> {
                            override fun onResponse(
                                call: Call<ReportCommentRes>,
                                response: Response<ReportCommentRes>
                            ) {
                                myDialog.dismiss()
                                if (response != null) {
                                    if (response.isSuccessful) {
                                        if (response.body()!!.code == 1) {
                                            Toast.makeText(
                                                this@PostDetailsActivity,
                                                "" + response.body()!!.message,
                                                Toast.LENGTH_LONG
                                            ).show()
                                            Status = "2"
                                            getCommentList(postId!!)
                                        } else {
                                            ProjectUtill.printMessage(
                                                this@PostDetailsActivity.window.decorView,
                                                response.body()?.message
                                            )
                                        }
                                    } else {
                                        ProjectUtill.printErrorMessage(
                                            this@PostDetailsActivity.window.decorView,
                                            ""
                                        )
                                    }
                                } else {
                                    ProjectUtill.printErrorMessage(
                                        this@PostDetailsActivity.window.decorView,
                                        ""
                                    )
                                }
                            }

                            override fun onFailure(
                                call: Call<ReportCommentRes>,
                                t: Throwable
                            ) {
                                myDialog.dismiss()
                                ProjectUtill.printErrorMessage(
                                    this@PostDetailsActivity.window.decorView,
                                    ""
                                )
                            }
                        })
                }
            }
        }

        no.setOnClickListener {
            dialog.cancel()
        }

        close.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }

    private fun getReplyCommentList(commentId: String) {
        WebServiceRequest.getInstance().replyCommentList(
            this, commentId,
            object : Callback<ReplyListResponse> {
                override fun onResponse(
                    call: Call<ReplyListResponse>,
                    response: Response<ReplyListResponse>
                ) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                if (response.body()!!.data.reply_list.size == 0) {
                                    replyRecycler!!.visibility = View.GONE
                                    blankData3!!.visibility = View.VISIBLE
                                } else {
                                    replyRecycler!!.visibility = View.VISIBLE
                                    blankData3!!.visibility = View.GONE
                                    val replyAdapter = AllCommentsReplysAdapter(
                                        this@PostDetailsActivity,
                                        response.body()!!.data.reply_list
                                    )
                                    val layoutManager = LinearLayoutManager(
                                        this@PostDetailsActivity,
                                        LinearLayoutManager.VERTICAL,
                                        true
                                    )
                                    replyRecycler!!.layoutManager = layoutManager
                                    replyRecycler!!.adapter = replyAdapter
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@PostDetailsActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@PostDetailsActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@PostDetailsActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<ReplyListResponse>,
                    t: Throwable
                ) {
                    ProjectUtill.printErrorMessage(
                        this@PostDetailsActivity.window.decorView,
                        ""
                    )
                }
            })
    }
}