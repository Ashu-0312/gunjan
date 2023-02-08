package app.gunjan.fragments

import android.app.Activity
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.gunjan.R
import app.gunjan.activities.HomeActivity
import app.gunjan.activities.PostListResponse
import app.gunjan.activities.SearchAddCommunityActivity
import app.gunjan.adapters.*
import app.gunjan.entity.*
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.utill.RecyclerItemClickListener
import app.gunjan.webservices.WebServiceRequest
import com.artjimlop.altex.AltexImageDownloader
import com.bumptech.glide.Glide
import com.cashfree.pg.CFPaymentService
import com.ravikoradiya.zoomableimageview.ZoomableImageView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_add_post.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(),RecyclerItemClickListener {
    var coinDialog: Dialog? = null
    var totalCoins: TextView? = null
    var idd: String? = null
    var dialogCommentReply: Dialog? = null
    var dialogComment: Dialog? = null
    private var page: Int? = 1
    var swipeRefresh: SwipeRefreshLayout? = null
    var progressBar: ProgressBar? = null
    var blankData: TextView? = null
    var totalMembers: TextView? = null
    var totalMember: CardView? = null
    var isLoading = false
    var isLastPage = false
    private var layoutManager: LinearLayoutManager? = null
    var postsAdapter: HomePostsAdapter? = null
    private var postId: String? = ""
    private var commentId: String? = ""
    private var Status: String? = "2"
    private var pic: String? = ""
    private var name: String? = ""
    private var userName: String? = ""
    private var description: String? = ""
    private var type: String? = "discussion"
    var commentRecycler: RecyclerView? = null
    var replyRecycler: RecyclerView? = null
    var blankData2: TextView? = null
    var blankData3: TextView? = null
    var nestedScroll: NestedScrollView? = null
    private var postList: ArrayList<PostListResponse.DataBean.PostBean> =
        ArrayList<PostListResponse.DataBean.PostBean>()
    private var animShow: Animation? = null
    private var list: ArrayList<String> = ArrayList<String>()
    private var coinList: ArrayList<String> = ArrayList<String>()
    private var postRecycler: RecyclerView? = null
    private var reasonLayout: LinearLayout? = null
    private var communityPic: CircleImageView? = null
    private var image1: CircleImageView? = null
    private var image2: CircleImageView? = null
    private var image3: CircleImageView? = null
    private var image4: CircleImageView? = null
    private var image5: CircleImageView? = null
    private var memberFrame: FrameLayout? = null
    private var communityName: TextView? = null
    private var invite: LinearLayout? = null
    private var discuss: LinearLayout? = null
    private var trending: LinearLayout? = null
    private var announce: LinearLayout? = null
    private var event: LinearLayout? = null
    private var discusssValue: EditText? = null
    private var submit: LinearLayout? = null
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
        discusssValue = view.findViewById(R.id.discuss_value)
        submit = view.findViewById(R.id.submit)
        totalMembers = view.findViewById(R.id.total_members)
        totalMember = view.findViewById(R.id.totalMember)
        image1 = view.findViewById(R.id.image1)
        image2 = view.findViewById(R.id.image2)
        image3 = view.findViewById(R.id.image3)
        image4 = view.findViewById(R.id.image4)
        image5 = view.findViewById(R.id.image5)
        memberFrame = view.findViewById(R.id.frames_member)
        initData()
        return view
    }

    private fun initData() {
        dialogCommentReply = context?.let { Dialog(it) }
        dialogComment = context?.let { Dialog(it) }
        dialogCommentReply!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogComment!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        animShow = AnimationUtils.loadAnimation(context, R.anim.move_right_in_activity)
        list.add("")
        list.add("")
        userDetails()
        initializeAdapter()
        postListApi("1", type!!)

        communityPic!!.setOnClickListener { communityDescriptionDialog() }

        totalMember!!.setOnClickListener {
            FCSharedPreferances.getSharedPreferance(context).status =
                "members"
            var intent = Intent(
                context,
                HomeActivity::class.java
            )
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        submit!!.setOnClickListener {
            if (FCSharedPreferances.getSharedPreferance(context).iS_ACTIVE.equals("false")) {
                Toast.makeText(context, getString(R.string.blocked_cummunity), Toast.LENGTH_LONG)
                    .show()
            } else {
                if (discusssValue!!.text.toString().trim() == "") {
                    discusssValue!!.requestFocus()
                    discusssValue!!.error = getString(R.string.write_about_post)
                } else {
                    val myDialog = ProjectUtill.showProgressDialog(context)
                    context?.let { it1 ->
                        WebServiceRequest.getInstance().addPost(
                            it1,
                            discusssValue!!.text.toString().trim(),
                            "",
                            "text",
                            "disccusion",
                            "",
                            "",
                            "",
                            "",
                            object : Callback<AddPostResponse> {
                                override fun onResponse(
                                    call: Call<AddPostResponse>,
                                    response: Response<AddPostResponse>
                                ) {
                                    myDialog.dismiss()
                                    if (response != null) {
                                        if (response.isSuccessful) {
                                            if (response.body()!!.code == 1) {
                                                var intent =
                                                    Intent(context, HomeActivity::class.java)
                                                intent.flags =
                                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                startActivity(intent)
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
                                    call: Call<AddPostResponse>,
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
        }

        swipeRefresh!!.setColorSchemeResources(R.color.pink)
        swipeRefresh!!.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            isLastPage = false
            isLoading = false
            page = 1
            postList.clear()
            postsAdapter!!.notifyDataSetChanged()
            postListSwipeApi("1", type!!)
            swipeRefresh!!.isRefreshing = false
        })

        invite!!.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            var shareBodyText =""
            if (FCSharedPreferances.getSharedPreferance(context).savE_LANG.equals("en")){
                shareBodyText = "Gunjan App is now live. Click on the below link to join the community\n\nhttps://play.google.com/store/apps/details?id=app.gunjan"
            }else{
                shareBodyText = "Gunjan App अब लाइव है। कम्युनिटी में जुड़ने के लिए निचे दिए गए लिंक पर क्लिक करे\n\nhttps://play.google.com/store/apps/details?id=app.gunjan"
            }
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject here")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText)
            startActivity(sharingIntent)
        }

        discuss!!.setOnClickListener {
            discuss!!.background = resources.getDrawable(R.drawable.pink_border2)
            trending!!.background = resources.getDrawable(R.drawable.edittext_bg)
            announce!!.background = resources.getDrawable(R.drawable.edittext_bg)
            event!!.background = resources.getDrawable(R.drawable.edittext_bg)
            type = "discussion"
            initializeAdapter()
            postListApi("1", type!!)
        }

        trending!!.setOnClickListener {
            trending!!.background = resources.getDrawable(R.drawable.pink_border2)
            discuss!!.background = resources.getDrawable(R.drawable.edittext_bg)
            announce!!.background = resources.getDrawable(R.drawable.edittext_bg)
            event!!.background = resources.getDrawable(R.drawable.edittext_bg)
            type = "trending"
            initializeAdapter()
            postListApi("1", type!!)
        }

        announce!!.setOnClickListener {
            announce!!.background = resources.getDrawable(R.drawable.pink_border2)
            discuss!!.background = resources.getDrawable(R.drawable.edittext_bg)
            trending!!.background = resources.getDrawable(R.drawable.edittext_bg)
            event!!.background = resources.getDrawable(R.drawable.edittext_bg)
            type = "announcement"
            initializeAdapter()
            postListApi("1", type!!)
        }

        event!!.setOnClickListener {
            event!!.background = resources.getDrawable(R.drawable.pink_border2)
            trending!!.background = resources.getDrawable(R.drawable.edittext_bg)
            announce!!.background = resources.getDrawable(R.drawable.edittext_bg)
            discuss!!.background = resources.getDrawable(R.drawable.edittext_bg)
            type = "event"
            initializeAdapter()
            postListApi("1", type!!)
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
                it, page, "10", type,
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
                                    try {
                                        totalMembers!!.text =
                                            response.body()!!.data.total_members.toString()
                                        when (response.body()!!.data.member_list.size) {
                                            0 -> {
                                                memberFrame!!.visibility = View.GONE
                                            }
                                            1 -> {
                                                memberFrame!!.visibility = View.VISIBLE
                                                Glide.with(context!!)
                                                    .load(response.body()!!.data.member_list[0].image)
                                                    .placeholder(
                                                        R.drawable.user_avatar
                                                    ).into(image1!!)
                                            }
                                            2 -> {
                                                memberFrame!!.visibility = View.VISIBLE
                                                Glide.with(context!!)
                                                    .load(response.body()!!.data.member_list[0].image)
                                                    .placeholder(
                                                        R.drawable.user_avatar
                                                    ).into(image1!!)
                                                Glide.with(context!!)
                                                    .load(response.body()!!.data.member_list[1].image)
                                                    .placeholder(
                                                        R.drawable.user_avatar
                                                    ).into(image2!!)
                                            }
                                            3 -> {
                                                memberFrame!!.visibility = View.VISIBLE
                                                Glide.with(context!!)
                                                    .load(response.body()!!.data.member_list[0].image)
                                                    .placeholder(
                                                        R.drawable.user_avatar
                                                    ).into(image1!!)
                                                Glide.with(context!!)
                                                    .load(response.body()!!.data.member_list[1].image)
                                                    .placeholder(
                                                        R.drawable.user_avatar
                                                    ).into(image2!!)
                                                Glide.with(context!!)
                                                    .load(response.body()!!.data.member_list[2].image)
                                                    .placeholder(
                                                        R.drawable.user_avatar
                                                    ).into(image3!!)
                                            }
                                            4 -> {
                                                memberFrame!!.visibility = View.VISIBLE
                                                Glide.with(context!!)
                                                    .load(response.body()!!.data.member_list[0].image)
                                                    .placeholder(
                                                        R.drawable.user_avatar
                                                    ).into(image1!!)
                                                Glide.with(context!!)
                                                    .load(response.body()!!.data.member_list[1].image)
                                                    .placeholder(
                                                        R.drawable.user_avatar
                                                    ).into(image2!!)
                                                Glide.with(context!!)
                                                    .load(response.body()!!.data.member_list[2].image)
                                                    .placeholder(
                                                        R.drawable.user_avatar
                                                    ).into(image3!!)
                                                Glide.with(context!!)
                                                    .load(response.body()!!.data.member_list[3].image)
                                                    .placeholder(
                                                        R.drawable.user_avatar
                                                    ).into(image4!!)
                                            }
                                            5 -> {
                                                memberFrame!!.visibility = View.VISIBLE
                                                Glide.with(context!!)
                                                    .load(response.body()!!.data.member_list[0].image)
                                                    .placeholder(
                                                        R.drawable.user_avatar
                                                    ).into(image1!!)
                                                Glide.with(context!!)
                                                    .load(response.body()!!.data.member_list[1].image)
                                                    .placeholder(
                                                        R.drawable.user_avatar
                                                    ).into(image2!!)
                                                Glide.with(context!!)
                                                    .load(response.body()!!.data.member_list[2].image)
                                                    .placeholder(
                                                        R.drawable.user_avatar
                                                    ).into(image3!!)
                                                Glide.with(context!!)
                                                    .load(response.body()!!.data.member_list[3].image)
                                                    .placeholder(
                                                        R.drawable.user_avatar
                                                    ).into(image4!!)
                                                Glide.with(context!!)
                                                    .load(response.body()!!.data.member_list[4].image)
                                                    .placeholder(
                                                        R.drawable.user_avatar
                                                    ).into(image5!!)
                                            }
                                            else -> {
                                                memberFrame!!.visibility = View.VISIBLE
                                                Glide.with(context!!)
                                                    .load(response.body()!!.data.member_list[0].image)
                                                    .placeholder(
                                                        R.drawable.user_avatar
                                                    ).into(image1!!)
                                                Glide.with(context!!)
                                                    .load(response.body()!!.data.member_list[1].image)
                                                    .placeholder(
                                                        R.drawable.user_avatar
                                                    ).into(image2!!)
                                                Glide.with(context!!)
                                                    .load(response.body()!!.data.member_list[2].image)
                                                    .placeholder(
                                                        R.drawable.user_avatar
                                                    ).into(image3!!)
                                                Glide.with(context!!)
                                                    .load(response.body()!!.data.member_list[3].image)
                                                    .placeholder(
                                                        R.drawable.user_avatar
                                                    ).into(image4!!)
                                                Glide.with(context!!)
                                                    .load(response.body()!!.data.member_list[4].image)
                                                    .placeholder(
                                                        R.drawable.user_avatar
                                                    ).into(image5!!)
                                            }
                                        }
                                    } catch (e: Exception) {
                                    }
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

    private fun postListSwipeApi(page: String, type: String) {
        isLoading = true
        context?.let {
            WebServiceRequest.getInstance().postList(
                it, page, "10", type,
                object : Callback<PostListResponse> {
                    override fun onResponse(
                        call: Call<PostListResponse>,
                        response: Response<PostListResponse>,
                    ) {
                        isLoading = false
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    try {
                                        totalMembers!!.text =
                                            response.body()!!.data.total_members.toString()
                                        if (response.body()!!.data.member_list.size == 0) {
                                            memberFrame!!.visibility = View.GONE
                                        } else if (response.body()!!.data.member_list.size == 1) {
                                            memberFrame!!.visibility = View.VISIBLE
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[0].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image1!!)
                                        } else if (response.body()!!.data.member_list.size == 2) {
                                            memberFrame!!.visibility = View.VISIBLE
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[0].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image1!!)
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[1].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image2!!)
                                        } else if (response.body()!!.data.member_list.size == 3) {
                                            memberFrame!!.visibility = View.VISIBLE
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[0].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image1!!)
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[1].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image2!!)
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[2].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image3!!)
                                        } else if (response.body()!!.data.member_list.size == 4) {
                                            memberFrame!!.visibility = View.VISIBLE
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[0].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image1!!)
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[1].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image2!!)
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[2].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image3!!)
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[3].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image4!!)
                                        } else if (response.body()!!.data.member_list.size == 5) {
                                            memberFrame!!.visibility = View.VISIBLE
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[0].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image1!!)
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[1].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image2!!)
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[2].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image3!!)
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[3].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image4!!)
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[4].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image5!!)
                                        }
                                    } catch (e: Exception) {
                                    }
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
                it, page, "10", "discussion",
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
                                    try {
                                        totalMembers!!.text =
                                            response.body()!!.data.total_members.toString()
                                        if (response.body()!!.data.member_list.size == 0) {
                                            memberFrame!!.visibility = View.GONE
                                        } else if (response.body()!!.data.member_list.size == 1) {
                                            memberFrame!!.visibility = View.VISIBLE
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[0].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image1!!)
                                        } else if (response.body()!!.data.member_list.size == 2) {
                                            memberFrame!!.visibility = View.VISIBLE
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[0].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image1!!)
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[1].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image2!!)
                                        } else if (response.body()!!.data.member_list.size == 3) {
                                            memberFrame!!.visibility = View.VISIBLE
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[0].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image1!!)
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[1].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image2!!)
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[2].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image3!!)
                                        } else if (response.body()!!.data.member_list.size == 4) {
                                            memberFrame!!.visibility = View.VISIBLE
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[0].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image1!!)
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[1].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image2!!)
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[2].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image3!!)
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[3].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image4!!)
                                        } else if (response.body()!!.data.member_list.size == 5) {
                                            memberFrame!!.visibility = View.VISIBLE
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[0].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image1!!)
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[1].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image2!!)
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[2].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image3!!)
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[3].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image4!!)
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.member_list[4].image)
                                                .placeholder(
                                                    R.drawable.user_avatar
                                                ).into(image5!!)
                                        }
                                    } catch (e: Exception) {
                                    }
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
                                        postListSwipeApi("1", type!!)
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

    fun reportDialog(userId: String,type:String) {
        var title: TextView? = null
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
        title = dialog.findViewById(R.id.title)

        if (type == "user"){
            title?.text = getString(R.string.report_post)
        }else{
            title?.text = getString(R.string.report_comment)
        }

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
            if (type=="user") {
                if (Status.equals("1")) {
                    if (edtReason.text.toString().trim() == "") {
                        Toast.makeText(
                            context,
                            getString(R.string.please_reason),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    } else {
                        dialog.cancel()
                        val myDialog = ProjectUtill.showProgressDialog(context)
                        context?.let { it1 ->
                            WebServiceRequest.getInstance().reportPost(
                                it1,
                                userId,
                                FCSharedPreferances.getSharedPreferance(context).reasoN_ID,
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
                                                    Status = "2"
                                                    isLastPage = false
                                                    isLoading = false
                                                    page = 1
                                                    postList.clear()
                                                    postsAdapter!!.notifyDataSetChanged()
                                                    postListSwipeApi("1", type!!)
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
                                        call: Call<ReportCommentRes>,
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
                        WebServiceRequest.getInstance().reportPost(
                            it1,
                            userId,
                            FCSharedPreferances.getSharedPreferance(context).reasoN_ID,
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
                                                isLastPage = false
                                                isLoading = false
                                                page = 1
                                                postList.clear()
                                                postsAdapter!!.notifyDataSetChanged()
                                                postListSwipeApi("1", type!!)
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
                                    call: Call<ReportCommentRes>,
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
            }else{
                if (Status.equals("1")) {
                    if (edtReason.text.toString().trim() == "") {
                        Toast.makeText(
                            context,
                            getString(R.string.please_reason),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    } else {
                        dialog.cancel()
                        val myDialog = ProjectUtill.showProgressDialog(context)
                        context?.let { it1 ->
                            WebServiceRequest.getInstance().reportComment(
                                it1,
                                userId!!,
                                FCSharedPreferances.getSharedPreferance(context).reasoN_ID,
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
                                                        context,
                                                        "" + response.body()!!.message,
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                    Status = "2"
                                                    getCommentList(postId!!)
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
                                        call: Call<ReportCommentRes>,
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
                        WebServiceRequest.getInstance().reportComment(
                            it1,
                            userId!!,
                            FCSharedPreferances.getSharedPreferance(context).reasoN_ID,
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
                                                    context,
                                                    "" + response.body()!!.message,
                                                    Toast.LENGTH_LONG
                                                ).show()
                                                Status = "2"
                                                getCommentList(postId!!)
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
                                    call: Call<ReportCommentRes>,
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
        }

        no.setOnClickListener {
            dialog.cancel()
        }

        close.setOnClickListener {
            dialog.cancel()
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
        dialog!!.setCanceledOnTouchOutside(true)
        dialog!!.setCancelable(true)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        // dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
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
                                            dialogComment!!.cancel()
                                            isLastPage = false
                                            isLoading = false
                                            page = 1
                                            postList.clear()
                                            postsAdapter!!.notifyDataSetChanged()
                                            postListSwipeApi("1", type!!)
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
            dialogComment!!.cancel()
            isLastPage = false
            isLoading = false
            page = 1
            postList.clear()
            postsAdapter!!.notifyDataSetChanged()
            postListSwipeApi("1", type!!)
        }

        dialogComment!!.show()
    }

    override fun onResume() {
        super.onResume()
        dialogComment!!.setOnKeyListener { dialog, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                // To dismiss the fragment when the back-button is pressed.
                dialog.cancel()
                dialogComment!!.cancel()
                isLastPage = false
                isLoading = false
                page = 1
                postList.clear()
                postsAdapter!!.notifyDataSetChanged()
                postListSwipeApi("1", type!!)
                true
            } else false
        }
    }

    fun commentsReplyDialog(id: String) {
        var close: ImageView? = null
        var addComment: ImageView? = null
        var edtComment: EditText? = null
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
                                            dialogCommentReply!!.cancel()
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
            dialogCommentReply!!.cancel()
        }

        dialogCommentReply!!.show()
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

    fun deleteCommentDialog(commentId: String,userId:String) {
        var delete: RelativeLayout? = null
        var report: RelativeLayout? = null
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
        report = dialog.findViewById(R.id.report_comment)

        if (FCSharedPreferances.getSharedPreferance(context).useR_ID.equals(userId)){
            delete.visibility = View.VISIBLE
            report.visibility = View.GONE
        }else{
            delete.visibility = View.GONE
            report.visibility = View.VISIBLE
        }

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

        report?.setOnClickListener {
            dialog.cancel()
           reportDialog(commentId,"comment")
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
                                    if (response.body()!!.data.active_community_details == null) {
                                        startActivity(
                                            Intent(
                                                context,
                                                SearchAddCommunityActivity::class.java
                                            )
                                        )
                                    }
                                    try {
                                        Glide.with(context!!)
                                            .load(response.body()!!.data.active_community_details.image)
                                            .placeholder(R.drawable.user_avatar)
                                            .into(communityPic!!)
                                        communityName!!.text =
                                            response.body()!!.data.active_community_details.title
                                        pic = response.body()!!.data.active_community_details.image
                                        name = response.body()!!.data.active_community_details.title
                                        userName =
                                            response.body()!!.data.user.first_name + " " + response.body()!!.data.user.last_name
                                        description =
                                            response.body()!!.data.active_community_details.about
                                        FCSharedPreferances.getSharedPreferance(context).communitY_NAME =
                                            response.body()!!.data.active_community_details.title
                                    } catch (e: Exception) {
                                    }
                                } else {
                                    ProjectUtill.printMessage(
                                        (context as Activity).window.decorView,
                                        response.body()?.message
                                    )
                                    FCSharedPreferances.getSharedPreferance(context).tokeN_STATUS = "false"
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

    fun showMedia(
        media: String?,
        type: String
    ) {
        val imageView: ZoomableImageView
        val layout: LinearLayout
        val downloadFile: LinearLayout
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
        downloadFile = dialog.findViewById(R.id.download_file)
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
            context?.let {
                Glide.with(it).load(media).placeholder(R.drawable.user_avatar).into(imageView)
            }
        } else {
            progressBar.visibility = View.VISIBLE
            frameLayout.visibility = View.VISIBLE
            videoView.visibility = View.VISIBLE
            imageView.visibility = View.GONE
            val wm = requireActivity()!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val width = display.width
            val height = display.height
            videoView.layoutParams = FrameLayout.LayoutParams(width, height)
            videoView.setVideoPath(media)
        }
        videoView.setOnCompletionListener {
            videoView.pause()
        }
        videoView.setOnPreparedListener {
            progressBar.visibility = View.GONE
            layout.visibility = View.GONE
            videoView.start()
        }

        downloadFile.setOnClickListener {
            if (media != null) {
                AltexImageDownloader.writeToDisk(context, media, "GUNJAN")
            }
        }
        dialog.show()
    }

    fun coinsDialog(id: String) {
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

        var coinsAdapter = CoinsAdapter(context, coinList, this@HomeFragment)
        coinsRecycler!!.layoutManager = GridLayoutManager(context, 4)
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
            if (edtCoin.text.toString().trim() == "") {
                edtCoin.requestFocus()
                edtCoin.error = getString(R.string.please_coin)
            } else {
                FCSharedPreferances.getSharedPreferance(context).paymenT_TYPE = "home"
                dialog.cancel()
                generateToken(edtCoin.text.toString().trim())
            }
        }

        dialog.show()
    }

    fun donateCoins(coin: String) {
        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let { it1 ->
            WebServiceRequest.getInstance().addPostCoin(
                it1, coin, idd!!,
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
                                    FCSharedPreferances.getSharedPreferance(context).totaL_COINS =
                                        response.body()!!.data.total_available_coins.toString()
                                    isLastPage = false
                                    isLoading = false
                                    page = 1
                                    postList.clear()
                                    postsAdapter!!.notifyDataSetChanged()
                                    postListSwipeApi("1", type!!)
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

    fun generateToken(amount: String) {
        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let {
            WebServiceRequest.getInstance().generateCashFreeToken(
                it, amount, "INR", "Test Transaction",
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
                it1, amount,
                object : Callback<AddCoinResponse> {
                    override fun onResponse(
                        call: Call<AddCoinResponse>,
                        response: Response<AddCoinResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    FCSharedPreferances.getSharedPreferance(context).totaL_COINS =
                                        response.body()!!.data.total_available_coins.toString()
                                    totalCoins!!.text =
                                        response.body()!!.data.total_available_coins.toString()
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

    override fun onItemClick(parentPos: Int, childPos: Int, data: Any, type: String) {
        if (type == "reason_layout"){
            showReasonLayout(childPos.toString())
        }
    }
}