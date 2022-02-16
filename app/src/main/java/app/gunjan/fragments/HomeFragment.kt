package app.gunjan.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.gunjan.R
import app.gunjan.activities.PostListResponse
import app.gunjan.adapters.*
import app.gunjan.entity.*
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private var page: Int? = 1
    private var postId: String? = ""
    private var commentId: String? = ""
    private var Status: String? = "2"
    private var pic: String? = ""
    private var name: String? = ""
    private var description: String? = ""
    private var type: String? = "discussion"
    var isLoading = false
    var isLastPage = false
    private var layoutManager: LinearLayoutManager? = null
    var commentRecycler: RecyclerView? = null
    var replyRecycler: RecyclerView? = null
    var postsAdapter: HomePostsAdapter? = null
    var swipeRefresh: SwipeRefreshLayout? = null
    var progressBar: ProgressBar? = null
    var blankData: TextView? = null
    var blankData2: TextView? = null
    var blankData3: TextView? = null
    var nestedScroll: NestedScrollView? = null
    private var postList: ArrayList<PostListResponse.DataBean.PostBean> =
        ArrayList<PostListResponse.DataBean.PostBean>()
    private var animShow: Animation? = null
    private var list: ArrayList<String> = ArrayList<String>()
    private var postRecycler: RecyclerView? = null
    private var reasonLayout: LinearLayout? = null
    private var communityPic: CircleImageView? = null
    private var communityName: TextView? = null
    private var invite: LinearLayout? = null
    private var discuss: LinearLayout? = null
    private var trending: LinearLayout? = null
    private var announce: LinearLayout? = null
    private var event: LinearLayout? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        postRecycler = view.findViewById(R.id.post_recycler)
        communityPic = view.findViewById(R.id.community_pic)
        communityName = view.findViewById(R.id.community_name)
        invite = view.findViewById(R.id.invite)
        discuss = view.findViewById(R.id.discussion)
        trending = view.findViewById(R.id.trending)
        announce = view.findViewById(R.id.announce)
        event = view.findViewById(R.id.event)
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        blankData = view.findViewById(R.id.blank_data)
        progressBar = view.findViewById(R.id.progress_bar)
        nestedScroll = view.findViewById(R.id.nested_scroll)
        initData()
        return view
    }

    private fun initData() {
        animShow = AnimationUtils.loadAnimation(context, R.anim.move_right_in_activity)
        userDetails()
        initializeAdapter()
        postListApi("1",type!!)

        communityPic!!.setOnClickListener { communityDescriptionDialog() }

        swipeRefresh!!.setColorSchemeResources(R.color.pink)
        swipeRefresh!!.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            isLastPage = false
            isLoading = false
            page = 1
            postList.clear()
            postsAdapter!!.notifyDataSetChanged()
            postListSwipeApi("1")
            swipeRefresh!!.isRefreshing = false
        })

        invite!!.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBodyText = "Gunjan App"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject here")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText)
            startActivity(sharingIntent)
        }

        discuss!!.setOnClickListener {
            discuss!!.background = resources.getDrawable(R.drawable.pink_border2)
            trending!!.background = resources.getDrawable(R.drawable.edittext_bg)
            announce!!.background = resources.getDrawable(R.drawable.edittext_bg)
            event!!.background = resources.getDrawable(R.drawable.edittext_bg)
            type="discussion"
            initializeAdapter()
            postListApi("1",type!!)
        }

        trending!!.setOnClickListener {
            trending!!.background = resources.getDrawable(R.drawable.pink_border2)
            discuss!!.background = resources.getDrawable(R.drawable.edittext_bg)
            announce!!.background = resources.getDrawable(R.drawable.edittext_bg)
            event!!.background = resources.getDrawable(R.drawable.edittext_bg)
            type="trending"
            initializeAdapter()
            postListApi("1",type!!)
        }

        announce!!.setOnClickListener {
            announce!!.background = resources.getDrawable(R.drawable.pink_border2)
            discuss!!.background = resources.getDrawable(R.drawable.edittext_bg)
            trending!!.background = resources.getDrawable(R.drawable.edittext_bg)
            event!!.background = resources.getDrawable(R.drawable.edittext_bg)
            type="announcement"
            initializeAdapter()
            postListApi("1",type!!)
        }

        event!!.setOnClickListener {
            event!!.background = resources.getDrawable(R.drawable.pink_border2)
            trending!!.background = resources.getDrawable(R.drawable.edittext_bg)
            announce!!.background = resources.getDrawable(R.drawable.edittext_bg)
            discuss!!.background = resources.getDrawable(R.drawable.edittext_bg)
        }

        nestedScroll!!.viewTreeObserver.addOnScrollChangedListener(ViewTreeObserver.OnScrollChangedListener {
            val view = nestedScroll!!.getChildAt(nestedScroll!!.childCount - 1) as View
            val diff: Int = view.bottom - (nestedScroll!!.height + nestedScroll!!
                .scrollY)
            if (diff == 0) {
                val visibleItemCount: Int = layoutManager!!.childCount
                val totalItemCount: Int = layoutManager!!.itemCount
                val firstVisibleItemPosition: Int = layoutManager!!.findFirstVisibleItemPosition()
                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= postList.size) {
                        isLoading = true
                        page = page!! + 1
                        postListPaginationApi(page.toString())
                    }
                }
            }
        })
    }

    private fun postListApi(page: String, type: String) {
        isLoading = true
        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let {
            WebServiceRequest.getInstance().postList(
                it, page, "10",type,
                object : Callback<PostListResponse> {
                    override fun onResponse(
                        call: Call<PostListResponse>,
                        response: Response<PostListResponse>,
                    ) {
                        isLoading = false
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    postList.clear()
                                    postList.addAll(response.body()!!.data.post)
                                    val prevSize: Int = response.body()!!.data.post.size
                                    if (postList.size == 0) {
                                        blankData!!.visibility = View.VISIBLE
                                        postRecycler!!.visibility = View.GONE
                                    } else {
                                        blankData!!.visibility = View.GONE
                                        postRecycler!!.visibility = View.VISIBLE
                                        if (response.body()!!.data.post.size < 10) {
                                            isLastPage = true
                                        }
                                        if (postList.size == 10) {
                                            postsAdapter!!.notifyDataSetChanged()
                                        } else {
                                            postsAdapter!!.notifyItemRangeChanged(
                                                prevSize,
                                                postList.size
                                            )
                                        }
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
                        call: Call<PostListResponse>,
                        t: Throwable,
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

    private fun postListSwipeApi(page: String) {
        isLoading = true
        context?.let {
            WebServiceRequest.getInstance().postList(
                it, page, "10","discussion",
                object : Callback<PostListResponse> {
                    override fun onResponse(
                        call: Call<PostListResponse>,
                        response: Response<PostListResponse>,
                    ) {
                        isLoading = false
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    postList.clear()
                                    postList.addAll(response.body()!!.data.post)
                                    val prevSize: Int = response.body()!!.data.post.size
                                    if (postList.size == 0) {
                                        blankData!!.visibility = View.VISIBLE
                                        postRecycler!!.visibility = View.GONE
                                    } else {
                                        blankData!!.visibility = View.GONE
                                        postRecycler!!.visibility = View.VISIBLE
                                        if (response.body()!!.data.post.size < 10) {
                                            isLastPage = true
                                        }
                                        if (postList.size == 10) {
                                            postsAdapter!!.notifyDataSetChanged()
                                        } else {
                                            postsAdapter!!.notifyItemRangeChanged(
                                                prevSize,
                                                postList.size
                                            )
                                        }
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
                        call: Call<PostListResponse>,
                        t: Throwable,
                    ) {
                        ProjectUtill.printErrorMessage(
                            activity!!.window.decorView,
                            ""
                        )
                    }
                })
        }
    }

    private fun postListPaginationApi(page: String) {
        isLoading = true
        progress_bar!!.visibility = View.VISIBLE
        context?.let {
            WebServiceRequest.getInstance().postList(
                it, page, "10","discussion",
                object : Callback<PostListResponse> {
                    override fun onResponse(
                        call: Call<PostListResponse>,
                        response: Response<PostListResponse>,
                    ) {
                        isLoading = false
                        progressBar!!.visibility = View.GONE
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    postList.addAll(response.body()!!.data.post)
                                    val prevSize: Int = response.body()!!.data.post.size
                                    if (postList.size == 0) {
                                        blankData!!.visibility = View.VISIBLE
                                        postRecycler!!.visibility = View.GONE
                                    } else {
                                        blankData!!.visibility = View.GONE
                                        postRecycler!!.visibility = View.VISIBLE
                                        if (response.body()!!.data.post.size < 10) {
                                            isLastPage = true
                                        }
                                        if (postList.size == 10) {
                                            postsAdapter!!.notifyDataSetChanged()
                                        } else {
                                            postsAdapter!!.notifyItemRangeChanged(
                                                prevSize,
                                                postList.size
                                            )
                                        }
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
                        call: Call<PostListResponse>,
                        t: Throwable,
                    ) {
                        progressBar!!.visibility = View.GONE
                        ProjectUtill.printErrorMessage(
                            activity!!.window.decorView,
                            ""
                        )
                    }
                })
        }
    }

    private fun initializeAdapter() {
        postList.clear()
        page = 1
        isLastPage = false
        isLoading = false
        postsAdapter = HomePostsAdapter(context, postList, this@HomeFragment)
        layoutManager = LinearLayoutManager(context)
        postRecycler!!.layoutManager = layoutManager
        postRecycler!!.adapter = postsAdapter
    }

    fun blockDialog(userId: String) {
        var yes: LinearLayout? = null
        var no: LinearLayout? = null
        var close: ImageView? = null
        val dialog = context?.let { Dialog(it) }
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.block_dialog)
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
            val myDialog = ProjectUtill.showProgressDialog(context)
            context?.let { it1 ->
                WebServiceRequest.getInstance().blockUnblockUser(
                    it1, userId, "0",
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
                                            context,
                                            "" + response.body()!!.message,
                                            Toast.LENGTH_LONG
                                        ).show()
                                        isLastPage = false
                                        isLoading = false
                                        page = 1
                                        postList.clear()
                                        postsAdapter!!.notifyDataSetChanged()
                                        postListSwipeApi("1")
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
                            call: Call<BlockUnblockUserResponse>,
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

        no.setOnClickListener {
            dialog.cancel()
        }

        close.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }

    fun reportDialog(userId: String) {
        var yes: LinearLayout? = null
        var no: LinearLayout? = null
        var close: ImageView? = null
        var edtReason: EditText? = null
        var reasonRecycler: RecyclerView? = null
        val dialog = context?.let { Dialog(it) }
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.report_dialog)
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
        reasonRecycler = dialog.findViewById(R.id.reason_recycler)
        close = dialog.findViewById(R.id.close)
        edtReason = dialog.findViewById(R.id.reason_edt)
        reasonLayout = dialog.findViewById(R.id.reasonLayout)

        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let { it1 ->
            WebServiceRequest.getInstance().reasonList(
                it1,
                object : Callback<ReasonListResponse> {
                    override fun onResponse(
                        call: Call<ReasonListResponse>,
                        response: Response<ReasonListResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    var reasonAdapter = ReasonList2Adapter(
                                        context,
                                        response.body()!!.data.reason_list,
                                        this@HomeFragment
                                    )
                                    var layoutManager: LinearLayoutManager? =
                                        LinearLayoutManager(context)
                                    reasonRecycler!!.layoutManager = layoutManager
                                    reasonRecycler!!.adapter = reasonAdapter
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
                        call: Call<ReasonListResponse>,
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

        yes.setOnClickListener {
            if (Status.equals("1")) {
                if (edtReason.text.toString().trim() == "") {
                    Toast.makeText(context, "Please enter reason", Toast.LENGTH_LONG).show()
                } else {
                    dialog.cancel()
                    val myDialog = ProjectUtill.showProgressDialog(context)
                    context?.let { it1 ->
                        WebServiceRequest.getInstance().reportUser(
                            it1,
                            userId,
                            FCSharedPreferances.getSharedPreferance(context).reasoN_ID,
                            edtReason.text.toString().toString(),
                            object : Callback<ReportReasonResponse> {
                                override fun onResponse(
                                    call: Call<ReportReasonResponse>,
                                    response: Response<ReportReasonResponse>
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
                                                Status = "2"
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
                                    call: Call<ReportReasonResponse>,
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
            } else {
                dialog.cancel()
                val myDialog = ProjectUtill.showProgressDialog(context)
                context?.let { it1 ->
                    WebServiceRequest.getInstance().reportUser(
                        it1, userId, FCSharedPreferances.getSharedPreferance(context).reasoN_ID, "",
                        object : Callback<ReportReasonResponse> {
                            override fun onResponse(
                                call: Call<ReportReasonResponse>,
                                response: Response<ReportReasonResponse>
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
                                            Status = "2"
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
                                call: Call<ReportReasonResponse>,
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

        no.setOnClickListener {
            dialog.cancel()
        }

        close.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }

    fun postreportDialog(userId: String) {
        var close: ImageView? = null
        var report: RelativeLayout? = null
        var copyPost: RelativeLayout? = null
        var block: RelativeLayout? = null
        val dialog = context?.let { Dialog(it) }
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.postreport_dialog)
        dialog!!.setCancelable(true)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        close = dialog.findViewById(R.id.close)
        report = dialog.findViewById(R.id.report)
        copyPost = dialog.findViewById(R.id.copy_post)
        block = dialog.findViewById(R.id.block)

        if (userId == FCSharedPreferances.getSharedPreferance(context).useR_ID) {
            block.visibility = View.GONE
            report.visibility = View.GONE
        } else {
            block.visibility = View.VISIBLE
            report.visibility = View.VISIBLE
        }

        close.setOnClickListener {
            dialog.cancel()
        }

        report.setOnClickListener {
            dialog.cancel()
            reportDialog(userId)
        }

        block.setOnClickListener {
            dialog.cancel()
            blockDialog(userId!!)
        }
        dialog.show()
    }

    fun showReasonLayout(status: String) {
        Status = status
        if (Status.equals("1")) {
            reasonLayout!!.visibility = View.VISIBLE
            reasonLayout!!.startAnimation(animShow)
        } else {
            reasonLayout!!.visibility = View.GONE
        }
    }

    fun communityDescriptionDialog() {
        var close: ImageView? = null
        var cPic: CircleImageView? = null
        var cName: TextView? = null
        var cDescription: TextView? = null
        val dialog = context?.let { Dialog(it) }
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.communitydescription_dialog)
        dialog!!.setCancelable(true)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        close = dialog.findViewById(R.id.close)
        cPic = dialog.findViewById(R.id.community_pic)
        cName = dialog.findViewById(R.id.community_name)
        cDescription = dialog.findViewById(R.id.description)

        context?.let {
            Glide.with(it)
                .load(pic)
                .placeholder(R.drawable.user_avatar)
                .into(cPic)
        }
        cName!!.text = name
        cDescription!!.text = description

        close.setOnClickListener {
            dialog.cancel()
        }

        dialog.show()
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
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
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
                                            edtComment.text.clear()
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

    fun commentsReplyDialog(id: String) {
        var close: ImageView? = null
        var addComment: ImageView? = null
        var edtComment: EditText? = null
        val dialog = context?.let { Dialog(it) }
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.commentreply_dialog)
        dialog!!.setCancelable(true)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        close = dialog.findViewById(R.id.close)
        addComment = dialog.findViewById(R.id.add)
        edtComment = dialog.findViewById(R.id.edt_comment)
        blankData3 = dialog.findViewById(R.id.blank_data2)
        replyRecycler = dialog.findViewById(R.id.reply_recycler)
        commentId = id
        getReplyCommentList(commentId!!)
        addComment!!.setOnClickListener {
            if (edtComment.text.toString().trim() != "") {
                val myDialog = ProjectUtill.showProgressDialog(context)
                context?.let { it1 ->
                    WebServiceRequest.getInstance().addCommentOnReply(
                        it1, commentId!!, "text", edtComment.text.toString().trim(),
                        object : Callback<AddReplyResponse> {
                            override fun onResponse(
                                call: Call<AddReplyResponse>,
                                response: Response<AddReplyResponse>
                            ) {
                                myDialog.dismiss()
                                if (response != null) {
                                    if (response.isSuccessful) {
                                        if (response.body()!!.code == 1) {
                                            edtComment.text.clear()
                                            getReplyCommentList(commentId!!)
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
                                call: Call<AddReplyResponse>,
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
                                        var commentsAdapter = AllCommentsAdapter(
                                            context,
                                            response.body()!!.data.comments, this@HomeFragment
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

    private fun getReplyCommentList(commentId: String) {
        context?.let { it1 ->
            WebServiceRequest.getInstance().replyCommentList(
                it1, commentId!!,
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
                                        var replyAdapter = AllCommentsReplysAdapter(
                                            context,
                                            response.body()!!.data.reply_list, this@HomeFragment
                                        )
                                        var layoutManager = LinearLayoutManager(
                                            context,
                                            LinearLayoutManager.VERTICAL,
                                            true
                                        )
                                        replyRecycler!!.layoutManager = layoutManager
                                        replyRecycler!!.adapter = replyAdapter
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
                        call: Call<ReplyListResponse>,
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

    private fun userDetails() {
        context?.let { it1 ->
            WebServiceRequest.getInstance().userDetails(
                it1,
                object : Callback<UserDetailsResponse> {
                    override fun onResponse(
                        call: Call<UserDetailsResponse>,
                        response: Response<UserDetailsResponse>
                    ) {
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    try {
                                        Glide.with(context!!)
                                            .load(response.body()!!.data.active_community_details.image)
                                            .placeholder(R.drawable.user_avatar)
                                            .into(communityPic!!)
                                        communityName!!.text =
                                            response.body()!!.data.active_community_details.title
                                        pic = response.body()!!.data.active_community_details.image
                                        name = response.body()!!.data.active_community_details.title
                                        description =
                                            response.body()!!.data.active_community_details.about
                                    } catch (e: Exception) {
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
                        call: Call<UserDetailsResponse>,
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
}