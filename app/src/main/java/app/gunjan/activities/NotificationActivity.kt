package app.gunjan.activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.gunjan.R
import app.gunjan.adapters.NotificationListAdapter
import app.gunjan.entity.NotificationListResponse
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.activity_notification.back
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationActivity : BaseActivity() {
    private var page: Int? = 1
    var isLoading = false
    var isLastPage = false
    private var layoutManager: LinearLayoutManager? = null
    var notificationAdapter: NotificationListAdapter? = null
    private var notificationList: ArrayList<NotificationListResponse.DataBean.NotificationBean> =
        ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        initData()
    }

    private fun initData() {

        back.setOnClickListener { finish() }
        initializeAdapter()
        notificationListApi("1")

        swipe_refresh!!.setColorSchemeResources(R.color.pink)
        swipe_refresh!!.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            isLastPage = false
            isLoading = false
            page = 1
            notificationList.clear()
            notificationAdapter!!.notifyDataSetChanged()
            notificationListSwipeApi("1")
            swipe_refresh!!.isRefreshing = false
        })
    }

    private fun notificationListApi(page: String) {
        isLoading = true
        val myDialog = ProjectUtill.showProgressDialog(this)
        WebServiceRequest.getInstance().getNotificationList(
            this, page, "10",
            object : Callback<NotificationListResponse> {
                override fun onResponse(
                    call: Call<NotificationListResponse>,
                    response: Response<NotificationListResponse>,
                ) {
                    isLoading = false
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                notificationList.clear()
                                notificationList.addAll(response.body()!!.data.notification)
                                val prevSize: Int = response.body()!!.data.notification.size
                                if (notificationList.size == 0) {
                                    blank_data!!.visibility = View.VISIBLE
                                    notificationRecycler!!.visibility = View.GONE
                                } else {
                                    blank_data!!.visibility = View.GONE
                                    notificationRecycler!!.visibility = View.VISIBLE
                                    if (response.body()!!.data.notification.size < 10) {
                                        isLastPage = true
                                    }
                                    if (notificationList.size == 10) {
                                        notificationAdapter!!.notifyDataSetChanged()
                                    } else {
                                        notificationAdapter!!.notifyItemRangeChanged(
                                            prevSize,
                                            notificationList.size
                                        )
                                    }
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@NotificationActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@NotificationActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@NotificationActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<NotificationListResponse>,
                    t: Throwable,
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@NotificationActivity.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun notificationListSwipeApi(page: String) {
        isLoading = true
        WebServiceRequest.getInstance().getNotificationList(
            this, page, "10",
            object : Callback<NotificationListResponse> {
                override fun onResponse(
                    call: Call<NotificationListResponse>,
                    response: Response<NotificationListResponse>,
                ) {
                    isLoading = false
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                notificationList.clear()
                                notificationList.addAll(response.body()!!.data.notification)
                                val prevSize: Int = response.body()!!.data.notification.size
                                if (notificationList.size == 0) {
                                    blank_data!!.visibility = View.VISIBLE
                                    notificationRecycler!!.visibility = View.GONE
                                } else {
                                    blank_data!!.visibility = View.GONE
                                    notificationRecycler!!.visibility = View.VISIBLE
                                    if (response.body()!!.data.notification.size < 10) {
                                        isLastPage = true
                                    }
                                    if (notificationList.size == 10) {
                                        notificationAdapter!!.notifyDataSetChanged()
                                    } else {
                                        notificationAdapter!!.notifyItemRangeChanged(
                                            prevSize,
                                            notificationList.size
                                        )
                                    }
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@NotificationActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@NotificationActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@NotificationActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<NotificationListResponse>,
                    t: Throwable,
                ) {
                    ProjectUtill.printErrorMessage(
                        this@NotificationActivity.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun notificationListPaginationApi(page: String) {
        isLoading = true
        progress_bar!!.visibility = View.VISIBLE
        WebServiceRequest.getInstance().getNotificationList(
            this, page, "10",
            object : Callback<NotificationListResponse> {
                override fun onResponse(
                    call: Call<NotificationListResponse>,
                    response: Response<NotificationListResponse>,
                ) {
                    isLoading = false
                    progress_bar!!.visibility = View.GONE
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                notificationList.addAll(response.body()!!.data.notification)
                                val prevSize: Int = response.body()!!.data.notification.size
                                if (notificationList.size == 0) {
                                    blank_data!!.visibility = View.VISIBLE
                                    notificationRecycler!!.visibility = View.GONE
                                } else {
                                    blank_data!!.visibility = View.GONE
                                    notificationRecycler!!.visibility = View.VISIBLE
                                    if (response.body()!!.data.notification.size < 10) {
                                        isLastPage = true
                                    }
                                    if (notificationList.size == 10) {
                                        notificationAdapter!!.notifyDataSetChanged()
                                    } else {
                                        notificationAdapter!!.notifyItemRangeChanged(
                                            prevSize,
                                            notificationList.size
                                        )
                                    }
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@NotificationActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@NotificationActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@NotificationActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<NotificationListResponse>,
                    t: Throwable,
                ) {
                    progress_bar!!.visibility = View.GONE
                    ProjectUtill.printErrorMessage(
                        this@NotificationActivity.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun initializeAdapter() {
        notificationList.clear()
        page = 1
        isLastPage = false
        isLoading = false
        notificationAdapter = NotificationListAdapter(this, notificationList)
        layoutManager = LinearLayoutManager(this)
        notificationRecycler!!.layoutManager = layoutManager
        notificationRecycler!!.adapter = notificationAdapter
        notificationRecycler!!.addOnScrollListener(recyclerViewOnScrollListener)
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
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= notificationList.size) {
                        isLoading = true
                        page = page!! + 1
                        notificationListPaginationApi(page.toString())
                    }
                }
            }
        }
}