package app.gunjan.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.gunjan.R
import app.gunjan.adapters.AllOtherCommentsAdapter
import app.gunjan.adapters.OtherCoinsAdapter
import app.gunjan.adapters.OtherPostsAdapter
import app.gunjan.entity.*
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.artjimlop.altex.AltexImageDownloader
import com.bumptech.glide.Glide
import com.cashfree.pg.CFPaymentService
import com.ravikoradiya.zoomableimageview.ZoomableImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OthersPostFragment : Fragment() {
    var coinDialog:Dialog?=null
    var totalCoins: TextView? = null
    var idd: String? = null
    private var coinList: ArrayList<String> = ArrayList<String>()
    private var postId: String? = ""
    var commentRecycler: RecyclerView? = null
    var swipeRefresh: SwipeRefreshLayout? = null
    var blankData: TextView? = null
    var blankData2: TextView? = null
    private var postRecycler: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_others_post, container, false)
        postRecycler = view.findViewById(R.id.post_recycler)
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        blankData = view.findViewById(R.id.blank_data)
        initData()
        return view
    }

    private fun initData() {
        userDetails()

        swipeRefresh!!.setColorSchemeResources(R.color.pink)
        swipeRefresh!!.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
           userDetails()
            swipeRefresh!!.isRefreshing = false
        })
    }

    private fun userDetails() {
        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let {
            WebServiceRequest.getInstance().otherUserProfile(
                it, FCSharedPreferances.getSharedPreferance(context).otheR_ID,
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
                                        if (response.body()!!.data.post_list.size == 0) {
                                            postRecycler!!.visibility = View.GONE
                                            blankData!!.visibility = View.VISIBLE
                                        } else {
                                            postRecycler!!.visibility=View.VISIBLE
                                            blankData!!.visibility=View.GONE
                                        var postAdapter = OtherPostsAdapter(
                                            context,
                                            response.body()!!.data.post_list,
                                            this@OthersPostFragment
                                        )
                                        var layoutManager: LinearLayoutManager? =
                                            LinearLayoutManager(context)
                                        postRecycler!!.layoutManager = layoutManager
                                        postRecycler!!.adapter = postAdapter
                                    }
                                    } catch (e: Exception) {
                                    }
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
                        call: Call<OtherUserDetailsResponse>,
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

    private fun postListRefresh() {
        context?.let {
            WebServiceRequest.getInstance().otherUserProfile(
                it, FCSharedPreferances.getSharedPreferance(context).otheR_ID,
                object : Callback<OtherUserDetailsResponse> {
                    override fun onResponse(
                        call: Call<OtherUserDetailsResponse>,
                        response: Response<OtherUserDetailsResponse>
                    ) {
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    try {
                                        if (response.body()!!.data.post_list.size == 0) {
                                            postRecycler!!.visibility = View.GONE
                                            blankData!!.visibility = View.VISIBLE
                                        } else {
                                            postRecycler!!.visibility=View.VISIBLE
                                            blankData!!.visibility=View.GONE
                                            var postAdapter = OtherPostsAdapter(
                                                context,
                                                response.body()!!.data.post_list,
                                                this@OthersPostFragment
                                            )
                                            var layoutManager: LinearLayoutManager? =
                                                LinearLayoutManager(context)
                                            postRecycler!!.layoutManager = layoutManager
                                            postRecycler!!.adapter = postAdapter
                                        }
                                    } catch (e: Exception) {
                                    }
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
                        call: Call<OtherUserDetailsResponse>,
                        t: Throwable
                    ) {
                        ProjectUtill.printErrorMessage(
                            activity!!.window.decorView,
                            ""
                        )
                    }
                })
        }
    }

    fun commentsDialog(id: String) {
        var close: ImageView? = null
        var addComment: ImageView? = null
        var edtComment: EditText? = null
        val dialog = context?.let { Dialog(it) }
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.comment_dialog)
        dialog!!.setCancelable(true)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation2
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        close = dialog.findViewById(R.id.close)
        addComment = dialog.findViewById(R.id.add)
        edtComment = dialog.findViewById(R.id.edt_comment)
        blankData2 = dialog.findViewById(R.id.blank_data)
        commentRecycler = dialog.findViewById(R.id.comment_recycler)
        postId = id
        getCommentList(postId!!)
        addComment!!.setOnClickListener {
            if (edtComment.text.toString().trim() != "") {
                val myDialog = ProjectUtill.showProgressDialog(context)
                context?.let { it1 ->
                    WebServiceRequest.getInstance().addComment(
                        it1, postId!!, "text", edtComment.text.toString().trim(),
                        object : Callback<AddCommentResponse> {
                            override fun onResponse(
                                call: Call<AddCommentResponse>,
                                response: Response<AddCommentResponse>
                            ) {
                                myDialog.dismiss()
                                if (response != null) {
                                    if (response.isSuccessful) {
                                        if (response.body()!!.code == 1) {
                                            dialog.cancel()
                                            postListRefresh()
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
                                call: Call<AddCommentResponse>,
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

        close.setOnClickListener {
            dialog.cancel()
        }

        dialog.show()
    }

    private fun getCommentList(postId: String) {
        context?.let { it1 ->
            WebServiceRequest.getInstance().commentList(
                it1, postId!!,
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
                                        var commentsAdapter = AllOtherCommentsAdapter(
                                            context,
                                            response.body()!!.data.comments, this@OthersPostFragment
                                        )
                                        var layoutManager = LinearLayoutManager(
                                            context,
                                            LinearLayoutManager.VERTICAL,
                                            true
                                        )
                                        commentRecycler!!.layoutManager = layoutManager
                                        commentRecycler!!.adapter = commentsAdapter
                                    }
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
                        call: Call<CommentListResponse>,
                        t: Throwable
                    ) {
                        ProjectUtill.printErrorMessage(
                            (context as Activity).window.decorView,
                            ""
                        )
                    }
                })
        }
    }

    fun deleteCommentDialog(commentId: String) {
        var close: ImageView? = null
        var delete: RelativeLayout? = null
        val dialog = context?.let { Dialog(it) }
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.deletecomment_dialog)
        dialog!!.setCancelable(true)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        delete = dialog.findViewById(R.id.delete_comment)

        delete!!.setOnClickListener {
            dialog.cancel()
            context?.let { it1 ->
                val myDialog = ProjectUtill.showProgressDialog(context)
                WebServiceRequest.getInstance().deleteComment(
                    it1, commentId!!,
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
                            call: Call<DeleteCommentResponse>,
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
        dialog.show()
    }

    fun showMedia(
        media: String?,
        type: String
    ) {
        val imageView: ZoomableImageView
        val play: ImageView
        val layout: LinearLayout
        val downloadFile: LinearLayout
        val pause: ImageView
        val progressBar: ProgressBar
        val frameLayout: FrameLayout
        val videoView: VideoView
        val dialog = context?.let { Dialog(it) }
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.show_media_dialog)
        dialog.setCancelable(true)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation2
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        imageView = dialog.findViewById(R.id.image)
        play = dialog.findViewById(R.id.play)
        downloadFile = dialog.findViewById(R.id.download_file)
        pause = dialog.findViewById(R.id.pause)
        progressBar = dialog.findViewById(R.id.progress_bar)
        frameLayout = dialog.findViewById(R.id.frame)
        videoView = dialog.findViewById(R.id.video_view_chat)
        layout = dialog.findViewById(R.id.layout)
        layout.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE

        if (type.equals("image", ignoreCase = true)) {
            progressBar.visibility = View.GONE
            layout.visibility = View.GONE
            imageView.visibility = View.VISIBLE
            context?.let { Glide.with(it).load(media).placeholder(R.drawable.user_avatar).into(imageView) }
        } else {
            progressBar.visibility = View.VISIBLE
            frameLayout.visibility = View.VISIBLE
            videoView.visibility = View.VISIBLE
            imageView.visibility = View.GONE
            val wm =requireActivity()!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val width = display.width
            val height = display.height
            videoView.layoutParams = FrameLayout.LayoutParams(width, height)
            videoView.setVideoPath(media)
        }
        videoView.setOnCompletionListener {
            play.visibility = View.VISIBLE
            pause.visibility = View.GONE
        }
        videoView.setOnPreparedListener {
            progressBar.visibility = View.GONE
            layout.visibility = View.GONE
            play.visibility = View.VISIBLE
            pause.visibility = View.GONE
        }
        play.setOnClickListener {
            play.visibility = View.GONE
            pause.visibility = View.VISIBLE
            videoView.start()
        }
        pause.setOnClickListener {
            play.visibility = View.VISIBLE
            pause.visibility = View.GONE
            videoView.pause()
        }

        downloadFile.setOnClickListener {
            if (media != null) {
                AltexImageDownloader.writeToDisk(context, media, "GUNJAN")
            }
        }
        dialog.show()
    }

    fun coinsDialog(id:String) {
        var coinsRecycler: RecyclerView? = null
        var addCoins: CardView? = null
        var close: LinearLayout? = null
        coinDialog = context?.let { Dialog(it) }
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
        totalCoins!!.text = FCSharedPreferances.getSharedPreferance(context).totaL_COINS

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

        var coinsAdapter = OtherCoinsAdapter(context,coinList,this@OthersPostFragment)
        coinsRecycler!!.layoutManager = GridLayoutManager(context,4)
        coinsRecycler!!.adapter = coinsAdapter

        close!!.setOnClickListener { coinDialog!!.cancel() }

        addCoins!!.setOnClickListener { addCoinsDialog() }

        coinDialog!!.show()
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
                FCSharedPreferances.getSharedPreferance(context).paymenT_TYPE ="other"
                dialog.cancel()
                generateToken(edtCoin.text.toString().trim())
            }
        }

        dialog.show()
    }
    fun donateCoins(coin:String){
        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let { it1 ->
            WebServiceRequest.getInstance().addPostCoin(
                it1,coin,idd!!,
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
                                    FCSharedPreferances.getSharedPreferance(context).totaL_COINS = response.body()!!.data.total_available_coins.toString()
                                     postListRefresh()
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
                        call: Call<DonateCoinResponse>,
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

}