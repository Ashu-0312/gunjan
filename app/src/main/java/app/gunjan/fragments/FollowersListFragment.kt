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
import app.gunjan.adapters.FollowersAdapter
import app.gunjan.entity.FollowerListResponse
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import kotlinx.android.synthetic.main.activity_notification.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersListFragment : Fragment() {
    private var followerRecycler: RecyclerView? = null
    private var page: Int? = 1
    var isLoading = false
    var isLastPage = false
    private var layoutManager: LinearLayoutManager? = null
    var userAdapter: FollowersAdapter? = null
    private var list: ArrayList<FollowerListResponse.DataBean.UserListBean> =
        ArrayList<FollowerListResponse.DataBean.UserListBean>()
    private var swipeRefresh: SwipeRefreshLayout? = null
    private var progressBar: ProgressBar? = null
    private var emptyData: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_followers_list, container, false)
        followerRecycler=view.findViewById(R.id.follower_recycler)
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
            WebServiceRequest.getInstance().followerUserList(
                it, page, "10", FCSharedPreferances.getSharedPreferance(context).otheR_ID,
                object : Callback<FollowerListResponse> {
                    override fun onResponse(
                        call: Call<FollowerListResponse>,
                        response: Response<FollowerListResponse>,
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
                                        followerRecycler!!.visibility = View.GONE
                                    } else {
                                        emptyData!!.visibility = View.GONE
                                        followerRecycler!!.visibility = View.VISIBLE
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
                        call: Call<FollowerListResponse>,
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
            WebServiceRequest.getInstance().followerUserList(
                it, page, "10", FCSharedPreferances.getSharedPreferance(context).otheR_ID,
                object : Callback<FollowerListResponse> {
                    override fun onResponse(
                        call: Call<FollowerListResponse>,
                        response: Response<FollowerListResponse>,
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
                                        followerRecycler!!.visibility = View.GONE
                                    } else {
                                        emptyData!!.visibility = View.GONE
                                        followerRecycler!!.visibility = View.VISIBLE
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
                        call: Call<FollowerListResponse>,
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
            WebServiceRequest.getInstance().followerUserList(
                it, page, "10", FCSharedPreferances.getSharedPreferance(context).otheR_ID,
                object : Callback<FollowerListResponse> {
                    override fun onResponse(
                        call: Call<FollowerListResponse>,
                        response: Response<FollowerListResponse>,
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
                                        followerRecycler!!.visibility = View.GONE
                                    } else {
                                        emptyData!!.visibility = View.GONE
                                        followerRecycler!!.visibility = View.VISIBLE
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
                        call: Call<FollowerListResponse>,
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
        userAdapter = FollowersAdapter(context, list)
        layoutManager = LinearLayoutManager(context)
        followerRecycler!!.layoutManager = layoutManager
        followerRecycler!!.adapter = userAdapter
        followerRecycler!!.addOnScrollListener(recyclerViewOnScrollListener)
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