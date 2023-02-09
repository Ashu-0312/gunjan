package app.gunjan.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.gunjan.R
import app.gunjan.adapters.RequestListAdapter
import app.gunjan.entity.InterestListResponse
import app.gunjan.entity.RequestListResponse
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import kotlinx.android.synthetic.main.activity_request_list.*
import kotlinx.android.synthetic.main.activity_request_list.back
import kotlinx.android.synthetic.main.activity_request_list.blank_data
import kotlinx.android.synthetic.main.activity_request_list.progress_bar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestListActivity : BaseActivity() {
    private var page: Int? = 1
    var isLoading = false
    var isLastPage = false
    private var layoutManager: LinearLayoutManager? = null
    var requestListAdapter:RequestListAdapter?=null
    private var requestList: ArrayList<RequestListResponse.DataBean.RequestListBean> = ArrayList<RequestListResponse.DataBean.RequestListBean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_list)
        initData()
    }

    private fun initData() {

        back.setOnClickListener { finish() }

        initializeAdapter()
        requestListApi("1")

        swipe_refresh!!.setColorSchemeResources(R.color.pink)
        swipe_refresh!!.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            isLastPage = false
            isLoading = false
            page = 1
            requestList.clear()
            requestListAdapter!!.notifyDataSetChanged()
            requestListSwipeApi("1")
            swipe_refresh!!.isRefreshing = false
        })
    }

    private fun requestListApi(page: String) {
        isLoading = true
        val myDialog = ProjectUtill.showProgressDialog(this)
        WebServiceRequest.getInstance().getAllCommunityRequest(
            this,intent.getStringExtra("community_id").toString(),page, "10",
            object : Callback<RequestListResponse> {
                override fun onResponse(
                    call: Call<RequestListResponse>,
                    response: Response<RequestListResponse>,
                ) {
                    isLoading = false
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                requestList.clear()
                                requestList.addAll(response.body()!!.data.request_list)
                                val prevSize: Int = response.body()!!.data.request_list.size
                                if (requestList.size == 0) {
                                    blank_data!!.visibility = View.VISIBLE
                                    requestRecycler!!.visibility = View.GONE
                                } else {
                                    blank_data!!.visibility = View.GONE
                                    requestRecycler!!.visibility = View.VISIBLE
                                    if (response.body()!!.data.request_list.size < 10) {
                                        isLastPage = true
                                    }
                                    if (requestList.size == 10) {
                                        requestListAdapter!!.notifyDataSetChanged()
                                    } else {
                                        requestListAdapter!!.notifyItemRangeChanged(
                                            prevSize,
                                            requestList.size
                                        )
                                    }
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@RequestListActivity!!.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@RequestListActivity!!.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@RequestListActivity!!.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<RequestListResponse>,
                    t: Throwable,
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@RequestListActivity!!.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun requestListSwipeApi(page: String) {
        isLoading = true
        WebServiceRequest.getInstance().getAllCommunityRequest(
            this,intent.getStringExtra("community_id").toString(),page, "10",
            object : Callback<RequestListResponse> {
                override fun onResponse(
                    call: Call<RequestListResponse>,
                    response: Response<RequestListResponse>,
                ) {
                    isLoading = false
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                requestList.clear()
                                requestList.addAll(response.body()!!.data.request_list)
                                val prevSize: Int = response.body()!!.data.request_list.size
                                if (requestList.size == 0) {
                                    blank_data!!.visibility = View.VISIBLE
                                    requestRecycler!!.visibility = View.GONE
                                } else {
                                    blank_data!!.visibility = View.GONE
                                    requestRecycler!!.visibility = View.VISIBLE
                                    if (response.body()!!.data.request_list.size < 10) {
                                        isLastPage = true
                                    }
                                    if (requestList.size == 10) {
                                        requestListAdapter!!.notifyDataSetChanged()
                                    } else {
                                        requestListAdapter!!.notifyItemRangeChanged(
                                            prevSize,
                                            requestList.size
                                        )
                                    }
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@RequestListActivity!!.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@RequestListActivity!!.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@RequestListActivity!!.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<RequestListResponse>,
                    t: Throwable,
                ) {
                    ProjectUtill.printErrorMessage(
                        this@RequestListActivity!!.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun requestListPaginationApi(page: String) {
        isLoading = true
        progress_bar!!.visibility = View.VISIBLE
        WebServiceRequest.getInstance().getAllCommunityRequest(
            this,intent.getStringExtra("community_id").toString(),page, "10",
            object : Callback<RequestListResponse> {
                override fun onResponse(
                    call: Call<RequestListResponse>,
                    response: Response<RequestListResponse>,
                ) {
                    isLoading = false
                    progress_bar!!.visibility = View.GONE
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                requestList.addAll(response.body()!!.data.request_list)
                                val prevSize: Int = response.body()!!.data.request_list.size
                                if (requestList.size == 0) {
                                    blank_data!!.visibility = View.VISIBLE
                                    requestRecycler!!.visibility = View.GONE
                                } else {
                                    blank_data!!.visibility = View.GONE
                                    requestRecycler!!.visibility = View.VISIBLE
                                    if (response.body()!!.data.request_list.size < 10) {
                                        isLastPage = true
                                    }
                                    if (requestList.size == 10) {
                                        requestListAdapter!!.notifyDataSetChanged()
                                    } else {
                                        requestListAdapter!!.notifyItemRangeChanged(
                                            prevSize,
                                            requestList.size
                                        )
                                    }
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@RequestListActivity!!.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@RequestListActivity!!.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@RequestListActivity!!.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<RequestListResponse>,
                    t: Throwable,
                ) {
                    progress_bar!!.visibility = View.GONE
                    ProjectUtill.printErrorMessage(
                        this@RequestListActivity!!.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun initializeAdapter() {
        requestList.clear()
        page = 1
        isLastPage = false
        isLoading = false
        requestListAdapter = RequestListAdapter(this, requestList)
        layoutManager = LinearLayoutManager(this)
        requestRecycler!!.layoutManager = layoutManager
        requestRecycler!!.adapter = requestListAdapter
        requestRecycler!!.addOnScrollListener(recyclerViewOnScrollListener)
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
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= requestList.size) {
                        isLoading = true
                        page = page!! + 1
                        requestListPaginationApi(page.toString())
                    }
                }
            }
        }

    fun resetAdapter(){
        isLastPage = false
        isLoading = false
        page = 1
        requestList.clear()
        requestListAdapter!!.notifyDataSetChanged()
        requestListSwipeApi("1")
    }
}