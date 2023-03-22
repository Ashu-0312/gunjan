package app.gunjan.activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.gunjan.R
import app.gunjan.adapters.BlockedListAdapter
import app.gunjan.entity.BlockedUserListResponse
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import kotlinx.android.synthetic.main.activity_block_list.*
import kotlinx.android.synthetic.main.activity_block_list.back
import kotlinx.android.synthetic.main.activity_block_list.blank_data
import kotlinx.android.synthetic.main.activity_block_list.progress_bar
import kotlinx.android.synthetic.main.activity_block_list.swipe_refresh
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BlockListActivity : BaseActivity() {
    private var page: Int? = 1
    var isLoading = false
    var isLastPage = false
    private var layoutManager: LinearLayoutManager? = null
    var blockedListAdapter: BlockedListAdapter?=null
    private var blockList: ArrayList<BlockedUserListResponse.DataBean.MemberListBean> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block_list)
        initData()
    }

    private fun initData() {

        back.setOnClickListener { finish() }
        initializeAdapter()
        blockedListApi("1")

        swipe_refresh!!.setColorSchemeResources(R.color.pink)
        swipe_refresh!!.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            isLastPage = false
            isLoading = false
            page = 1
            blockList.clear()
            blockedListAdapter!!.notifyDataSetChanged()
            blockedListSwipeApi("1")
            swipe_refresh!!.isRefreshing = false
        })
    }

    private fun blockedListApi(page: String) {
        isLoading = true
        val myDialog = ProjectUtill.showProgressDialog(this)
        WebServiceRequest.getInstance().getAllBlockedMemberList(
            this,page, "10",FCSharedPreferances.getSharedPreferance(this).activE_COMMUNITY,
            object : Callback<BlockedUserListResponse> {
                override fun onResponse(
                    call: Call<BlockedUserListResponse>,
                    response: Response<BlockedUserListResponse>,
                ) {
                    isLoading = false
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                blockList.clear()
                                blockList.addAll(response.body()!!.data.member_list)
                                val prevSize: Int = response.body()!!.data.member_list.size
                                if (blockList.size == 0) {
                                    blank_data!!.visibility = View.VISIBLE
                                    blockRecycler!!.visibility = View.GONE
                                } else {
                                    blank_data!!.visibility = View.GONE
                                    blockRecycler!!.visibility = View.VISIBLE
                                    if (response.body()!!.data.member_list.size < 10) {
                                        isLastPage = true
                                    }
                                    if (blockList.size == 10) {
                                        blockedListAdapter!!.notifyDataSetChanged()
                                    } else {
                                        blockedListAdapter!!.notifyItemRangeChanged(
                                            prevSize,
                                            blockList.size
                                        )
                                    }
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@BlockListActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@BlockListActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@BlockListActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<BlockedUserListResponse>,
                    t: Throwable,
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@BlockListActivity.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun blockedListSwipeApi(page: String) {
        isLoading = true
        WebServiceRequest.getInstance().getAllBlockedMemberList(
            this,page, "10",FCSharedPreferances.getSharedPreferance(this).activE_COMMUNITY,
            object : Callback<BlockedUserListResponse> {
                override fun onResponse(
                    call: Call<BlockedUserListResponse>,
                    response: Response<BlockedUserListResponse>,
                ) {
                    isLoading = false
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                blockList.clear()
                                blockList.addAll(response.body()!!.data.member_list)
                                val prevSize: Int = response.body()!!.data.member_list.size
                                if (blockList.size == 0) {
                                    blank_data!!.visibility = View.VISIBLE
                                    blockRecycler!!.visibility = View.GONE
                                } else {
                                    blank_data!!.visibility = View.GONE
                                    blockRecycler!!.visibility = View.VISIBLE
                                    if (response.body()!!.data.member_list.size < 10) {
                                        isLastPage = true
                                    }
                                    if (blockList.size == 10) {
                                        blockedListAdapter!!.notifyDataSetChanged()
                                    } else {
                                        blockedListAdapter!!.notifyItemRangeChanged(
                                            prevSize,
                                            blockList.size
                                        )
                                    }
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@BlockListActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@BlockListActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@BlockListActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<BlockedUserListResponse>,
                    t: Throwable,
                ) {
                    ProjectUtill.printErrorMessage(
                        this@BlockListActivity.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun blockedListPaginationApi(page: String) {
        isLoading = true
        progress_bar!!.visibility = View.VISIBLE
        WebServiceRequest.getInstance().getAllBlockedMemberList(
            this,page, "10",FCSharedPreferances.getSharedPreferance(this).activE_COMMUNITY,
            object : Callback<BlockedUserListResponse> {
                override fun onResponse(
                    call: Call<BlockedUserListResponse>,
                    response: Response<BlockedUserListResponse>,
                ) {
                    isLoading = false
                    progress_bar!!.visibility = View.GONE
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                blockList.addAll(response.body()!!.data.member_list)
                                val prevSize: Int = response.body()!!.data.member_list.size
                                if (blockList.size == 0) {
                                    blank_data!!.visibility = View.VISIBLE
                                    blockRecycler!!.visibility = View.GONE
                                } else {
                                    blank_data!!.visibility = View.GONE
                                    blockRecycler!!.visibility = View.VISIBLE
                                    if (response.body()!!.data.member_list.size < 10) {
                                        isLastPage = true
                                    }
                                    if (blockList.size == 10) {
                                        blockedListAdapter!!.notifyDataSetChanged()
                                    } else {
                                        blockedListAdapter!!.notifyItemRangeChanged(
                                            prevSize,
                                            blockList.size
                                        )
                                    }
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@BlockListActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@BlockListActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@BlockListActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<BlockedUserListResponse>,
                    t: Throwable,
                ) {
                    progress_bar.visibility=View.GONE
                    ProjectUtill.printErrorMessage(
                        this@BlockListActivity.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun initializeAdapter() {
        blockList.clear()
        page = 1
        isLastPage = false
        isLoading = false
        blockedListAdapter = BlockedListAdapter(this, blockList)
        layoutManager = LinearLayoutManager(this)
        blockRecycler!!.layoutManager = layoutManager
        blockRecycler!!.adapter = blockedListAdapter
        blockRecycler!!.addOnScrollListener(recyclerViewOnScrollListener)
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
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= blockList.size) {
                        isLoading = true
                        page = page!! + 1
                        blockedListPaginationApi(page.toString())
                    }
                }
            }
        }

    fun resetAdapter(){
        isLastPage = false
        isLoading = false
        page = 1
        blockList.clear()
        blockedListAdapter!!.notifyDataSetChanged()
        blockedListSwipeApi("1")
    }
}