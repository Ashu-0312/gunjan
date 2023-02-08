package app.gunjan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.gunjan.R
import app.gunjan.adapters.CommunitiesAdapter
import app.gunjan.adapters.JoinedCommunitiesAdapter
import app.gunjan.entity.CommunityListResponse
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import kotlinx.android.synthetic.main.activity_switch_community.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SwitchCommunityActivity : AppCompatActivity() {
    private var page: Int? = 1
    var isLoading = false
    var isLastPage = false
    private var layoutManager: LinearLayoutManager? = null
    var communitiesAdapter: CommunitiesAdapter?=null
    private var communityList: ArrayList<CommunityListResponse.DataBean.CommunityListBean> = ArrayList<CommunityListResponse.DataBean.CommunityListBean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch_community)
        initData()
    }

    private fun initData() {

        back.setOnClickListener { finish() }
        initializeAdapter()
        communityListApi("1")

        swipe_refresh!!.setColorSchemeResources(R.color.pink)
        swipe_refresh!!.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            isLastPage = false
            isLoading = false
            page = 1
            communityList.clear()
            communitiesAdapter!!.notifyDataSetChanged()
            communityListSwipeApi("1")
            swipe_refresh!!.isRefreshing = false
        })
    }

    private fun communityListApi(page: String) {
        isLoading = true
        val myDialog = ProjectUtill.showProgressDialog(this)
        WebServiceRequest.getInstance().getAllCommunityList(
            this,page, "10","","3",
            object : Callback<CommunityListResponse> {
                override fun onResponse(
                    call: Call<CommunityListResponse>,
                    response: Response<CommunityListResponse>,
                ) {
                    isLoading = false
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                communityList.clear()
                                communityList.addAll(response.body()!!.data.community_list)
                                val prevSize: Int = response.body()!!.data.community_list.size
                                if (communityList.size == 0) {
                                    blank_data!!.visibility = View.VISIBLE
                                    communityRecycler!!.visibility = View.GONE
                                } else {
                                    blank_data!!.visibility = View.GONE
                                    communityRecycler!!.visibility = View.VISIBLE
                                    if (response.body()!!.data.community_list.size < 10) {
                                        isLastPage = true
                                    }
                                    if (communityList.size == 10) {
                                        communitiesAdapter!!.notifyDataSetChanged()
                                    } else {
                                        communitiesAdapter!!.notifyItemRangeChanged(
                                            prevSize,
                                            communityList.size
                                        )
                                    }
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@SwitchCommunityActivity!!.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@SwitchCommunityActivity!!.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@SwitchCommunityActivity!!.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<CommunityListResponse>,
                    t: Throwable,
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@SwitchCommunityActivity!!.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun communityListSwipeApi(page: String) {
        isLoading = true
        WebServiceRequest.getInstance().getAllCommunityList(
            this,page, "10","","3",
            object : Callback<CommunityListResponse> {
                override fun onResponse(
                    call: Call<CommunityListResponse>,
                    response: Response<CommunityListResponse>,
                ) {
                    isLoading = false
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                communityList.clear()
                                communityList.addAll(response.body()!!.data.community_list)
                                val prevSize: Int = response.body()!!.data.community_list.size
                                if (communityList.size == 0) {
                                    blank_data!!.visibility = View.VISIBLE
                                    communityRecycler!!.visibility = View.GONE
                                } else {
                                    blank_data!!.visibility = View.GONE
                                    communityRecycler!!.visibility = View.VISIBLE
                                    if (response.body()!!.data.community_list.size < 10) {
                                        isLastPage = true
                                    }
                                    if (communityList.size == 10) {
                                        communitiesAdapter!!.notifyDataSetChanged()
                                    } else {
                                        communitiesAdapter!!.notifyItemRangeChanged(
                                            prevSize,
                                            communityList.size
                                        )
                                    }
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@SwitchCommunityActivity!!.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@SwitchCommunityActivity!!.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@SwitchCommunityActivity!!.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<CommunityListResponse>,
                    t: Throwable,
                ) {
                    ProjectUtill.printErrorMessage(
                        this@SwitchCommunityActivity!!.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun communityListPaginationApi(page: String) {
        isLoading = true
        progress_bar!!.visibility = View.VISIBLE
        WebServiceRequest.getInstance().getAllCommunityList(
            this,page, "10","","3",
            object : Callback<CommunityListResponse> {
                override fun onResponse(
                    call: Call<CommunityListResponse>,
                    response: Response<CommunityListResponse>,
                ) {
                    isLoading = false
                    progress_bar!!.visibility = View.GONE
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                communityList.addAll(response.body()!!.data.community_list)
                                val prevSize: Int = response.body()!!.data.community_list.size
                                if (communityList.size == 0) {
                                    blank_data!!.visibility = View.VISIBLE
                                    communityRecycler!!.visibility = View.GONE
                                } else {
                                    blank_data!!.visibility = View.GONE
                                    communityRecycler!!.visibility = View.VISIBLE
                                    if (response.body()!!.data.community_list.size < 10) {
                                        isLastPage = true
                                    }
                                    if (communityList.size == 10) {
                                        communitiesAdapter!!.notifyDataSetChanged()
                                    } else {
                                        communitiesAdapter!!.notifyItemRangeChanged(
                                            prevSize,
                                            communityList.size
                                        )
                                    }
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@SwitchCommunityActivity!!.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@SwitchCommunityActivity!!.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@SwitchCommunityActivity!!.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<CommunityListResponse>,
                    t: Throwable,
                ) {
                    progress_bar!!.visibility = View.GONE
                    ProjectUtill.printErrorMessage(
                        this@SwitchCommunityActivity!!.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun initializeAdapter() {
        communityList.clear()
        page = 1
        isLastPage = false
        isLoading = false
        communitiesAdapter = CommunitiesAdapter(this, communityList)
        layoutManager = LinearLayoutManager(this)
        communityRecycler!!.layoutManager = layoutManager
        communityRecycler!!.adapter = communitiesAdapter
        communityRecycler!!.addOnScrollListener(recyclerViewOnScrollListener)
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
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= communityList.size) {
                        isLoading = true
                        page = page!! + 1
                        communityListPaginationApi(page.toString())
                    }
                }
            }
        }

    fun resetAdapter(){
        isLastPage = false
        isLoading = false
        page = 1
        communityList.clear()
        communitiesAdapter!!.notifyDataSetChanged()
        communityListSwipeApi("1")
        val intent = Intent(this@SwitchCommunityActivity, HomeActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}