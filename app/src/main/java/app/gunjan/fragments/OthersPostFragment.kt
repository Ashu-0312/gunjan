package app.gunjan.fragments

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.gunjan.R
import app.gunjan.adapters.AllOtherCommentsAdapter
import app.gunjan.adapters.OtherPostsAdapter
import app.gunjan.entity.AddCommentResponse
import app.gunjan.entity.CommentListResponse
import app.gunjan.entity.DeleteCommentResponse
import app.gunjan.entity.OtherUserDetailsResponse
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OthersPostFragment : Fragment() {
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
}