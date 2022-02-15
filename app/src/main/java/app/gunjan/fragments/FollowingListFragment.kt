package app.gunjan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.gunjan.R
import app.gunjan.adapters.FollowingAdapter
import app.gunjan.entity.FollowingListResponse
import app.gunjan.entity.NotificationListResponse
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import kotlinx.android.synthetic.main.activity_notification.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingListFragment : Fragment() {
    private var page: Int? = 1
    var isLoading = false
    var isLastPage = false
    private var layoutManager: LinearLayoutManager? = null
    var userAdapter: FollowingAdapter? = null
    private var list: ArrayList<FollowingListResponse.DataBean.UserListBean> =
        ArrayList<FollowingListResponse.DataBean.UserListBean>()
    private var followingRecycler: RecyclerView? = null
    private var swipeRefresh: SwipeRefreshLayout? = null
    private var progressBar: ProgressBar? = null
    private var emptyData: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_following_list, container, false)
        followingRecycler=view.findViewById(R.id.following_recycler)
        swipeRefresh=view.findViewById(R.id.swipe_refresh)
        progressBar=view.findViewById(R.id.progress_bar)
        emptyData=view.findViewById(R.id.blank_data)
        initData()
        return view
    }

    private fun initData() {
        initializeAdapter()
        userListApi("1")

        swipeRefresh!!.setColorSchemeResources(R.color.pink)
        swipeRefresh!!.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            isLastPage = false
            isLoading = false
            page = 1
            list.clear()
            userAdapter!!.notifyDataSetChanged()
            userListSwipeApi("1")
            swipe_refresh!!.isRefreshing = false
        })
    }

    private fun userListApi(page: String) {
        isLoading = true
        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let {
            WebServiceRequest.getInstance().followingUserList(
                it, page, "10", FCSharedPreferances.getSharedPreferance(context).otheR_ID,
                object : Callback<FollowingListResponse> {
                    override fun onResponse(
                        call: Call<FollowingListResponse>,
                        response: Response<FollowingListResponse>,
                    ) {
                        isLoading = false
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    list.clear()
                                    list.addAll(response.body()!!.data.user_list)
                                    val prevSize: Int = response.body()!!.data.user_list.size
                                    if (list.size == 0) {
                                        emptyData!!.visibility = View.VISIBLE
                                        followingRecycler!!.visibility = View.GONE
                                    } else {
                                        emptyData!!.visibility = View.GONE
                                        followingRecycler!!.visibility = View.VISIBLE
                                        if (response.body()!!.data.user_list.size < 10) {
                                            isLastPage = true
                                        }
                                        if (list.size == 10) {
                                            userAdapter!!.notifyDataSetChanged()
                                        } else {
                                            userAdapter!!.notifyItemRangeChanged(
                                                prevSize,
                                                list.size
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
                        call: Call<FollowingListResponse>,
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

    private fun userListSwipeApi(page: String) {
        isLoading = true
        context?.let {
            WebServiceRequest.getInstance().followingUserList(
                it, page, "10",FCSharedPreferances.getSharedPreferance(context).otheR_ID,
                object : Callback<FollowingListResponse> {
                    override fun onResponse(
                        call: Call<FollowingListResponse>,
                        response: Response<FollowingListResponse>,
                    ) {
                        isLoading = false
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    list.clear()
                                    list.addAll(response.body()!!.data.user_list)
                                    val prevSize: Int = response.body()!!.data.user_list.size
                                    if (list.size == 0) {
                                        emptyData!!.visibility = View.VISIBLE
                                        followingRecycler!!.visibility = View.GONE
                                    } else {
                                        emptyData!!.visibility = View.GONE
                                        followingRecycler!!.visibility = View.VISIBLE
                                        if (response.body()!!.data.user_list.size < 10) {
                                            isLastPage = true
                                        }
                                        if (list.size == 10) {
                                            userAdapter!!.notifyDataSetChanged()
                                        } else {
                                            userAdapter!!.notifyItemRangeChanged(
                                                prevSize,
                                                list.size
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
                        call: Call<FollowingListResponse>,
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

    private fun userListPaginationApi(page: String) {
        isLoading = true
        progress_bar!!.visibility = View.VISIBLE
        context?.let {
            WebServiceRequest.getInstance().followingUserList(
                it, page, "10",FCSharedPreferances.getSharedPreferance(context).otheR_ID,
                object : Callback<FollowingListResponse> {
                    override fun onResponse(
                        call: Call<FollowingListResponse>,
                        response: Response<FollowingListResponse>,
                    ) {
                        isLoading = false
                        progressBar!!.visibility = View.GONE
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    list.addAll(response.body()!!.data.user_list)
                                    val prevSize: Int = response.body()!!.data.user_list.size
                                    if (list.size == 0) {
                                        emptyData!!.visibility = View.VISIBLE
                                        followingRecycler!!.visibility = View.GONE
                                    } else {
                                        emptyData!!.visibility = View.GONE
                                        followingRecycler!!.visibility = View.VISIBLE
                                        if (response.body()!!.data.user_list.size < 10) {
                                            isLastPage = true
                                        }
                                        if (list.size == 10) {
                                            userAdapter!!.notifyDataSetChanged()
                                        } else {
                                            userAdapter!!.notifyItemRangeChanged(
                                                prevSize,
                                                list.size
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
                        call: Call<FollowingListResponse>,
                        t: Throwable,
                    ) {
                        progress_bar!!.visibility = View.GONE
                        ProjectUtill.printErrorMessage(
                            activity!!.window.decorView,
                            ""
                        )
                    }
                })
        }
    }

    private fun initializeAdapter() {
        list.clear()
        page = 1
        isLastPage = false
        isLoading = false
        userAdapter = FollowingAdapter(context, list)
        layoutManager = LinearLayoutManager(context)
        followingRecycler!!.layoutManager = layoutManager
        followingRecycler!!.adapter = userAdapter
        followingRecycler!!.addOnScrollListener(recyclerViewOnScrollListener)
    }

    private val recyclerViewOnScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount: Int = layoutManager!!.childCount
                val totalItemCount: Int = layoutManager!!.itemCount
                val firstVisibleItemPosition: Int = layoutManager!!.findFirstVisibleItemPosition()
                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= list.size) {
                        isLoading = true
                        page = page!! + 1
                        userListPaginationApi(page.toString())
                    }
                }
            }
        }
}