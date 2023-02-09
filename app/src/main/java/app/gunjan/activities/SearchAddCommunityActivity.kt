package app.gunjan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.gunjan.R
import app.gunjan.adapters.CommunityListAdapter
import app.gunjan.entity.CommunityListResponse
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import kotlinx.android.synthetic.main.activity_search_add_community.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchAddCommunityActivity : BaseActivity() {
    private var page: Int? = 1
    var isLoading = false
    var isLastPage = false
    private var layoutManager: LinearLayoutManager? = null
    private var communityList: ArrayList<CommunityListResponse.DataBean.CommunityListBean> =
        ArrayList<CommunityListResponse.DataBean.CommunityListBean>()
    private var communityListAdapter: CommunityListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_add_community)
        initData()
    }

    private fun initData() {
        back.setOnClickListener { finish() }
        add_community!!.setOnClickListener {
            var intent= Intent(this, AddCommunityActivity::class.java)
            startActivity(intent)
        }
        initializeAdapter()
        communityListApi("1")

        swipe_refresh!!.setColorSchemeResources(R.color.pink)
        swipe_refresh!!.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            isLastPage = false
            isLoading = false
            page = 1
            communityList.clear()
            communityListAdapter!!.notifyDataSetChanged()
            communityListSwipeApi("1")
            swipe_refresh!!.isRefreshing = false
        })

        search_edt!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                if (search_edt!!.text.toString().trim().isEmpty()) {
                    communityListSearchApi( page.toString(), search_edt!!.text.toString().trim())
                }
            }
        })

        search_edt!!.onDone {
            communityListSearchApi( page.toString(), search_edt!!.text.toString().trim())
        }

        search!!.setOnClickListener {
            communityListSearchApi(page.toString(), search_edt!!.text.toString().trim())
        }
    }

    fun EditText.onDone(callback: () -> Unit) {
        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                callback.invoke()
                true
            }
            false
        }
    }

    private fun communityListSearchApi(page: String, value: String) {
        initializeAdapter()
        val myDialog = ProjectUtill.showProgressDialog(this)
            WebServiceRequest.getInstance().getAllCommunityList(
                this, page, "10", value,"0",
                object : Callback<CommunityListResponse> {
                    override fun onResponse(
                        call: Call<CommunityListResponse>,
                        response: Response<CommunityListResponse>,
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    communityList.clear()
                                    communityList.addAll(response.body()!!.data.community_list)
                                    val prevSize: Int = response.body()!!.data.community_list.size
                                    if (communityList.size == 0) {
                                        blank_data!!.visibility = View.VISIBLE
                                        community_recycler!!.visibility = View.GONE
                                    } else {
                                        blank_data!!.visibility = View.GONE
                                        community_recycler!!.visibility = View.VISIBLE
                                        if (response.body()!!.data.community_list.size < 10) {
                                            isLastPage = true
                                        }
                                        if (communityList.size == 10) {
                                            communityListAdapter!!.notifyDataSetChanged()
                                        } else {
                                            communityListAdapter!!.notifyItemRangeChanged(
                                                prevSize,
                                                communityList.size
                                            )
                                        }
                                    }
                                } else {
                                    ProjectUtill.printMessage(
                                        this@SearchAddCommunityActivity!!.window.decorView,
                                        response.body()?.message
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    this@SearchAddCommunityActivity!!.window.decorView,
                                    ""
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@SearchAddCommunityActivity!!.window.decorView,
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
                            this@SearchAddCommunityActivity!!.window.decorView,
                            ""
                        )
                    }
                })
    }

    private fun communityListApi(page: String) {
        isLoading = true
        val myDialog = ProjectUtill.showProgressDialog(this)
            WebServiceRequest.getInstance().getAllCommunityList(
                this, page, "10", "","0",
                object : Callback<CommunityListResponse> {
                    override fun onResponse(
                        capll: Call<CommunityListResponse>,
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
                                        community_recycler!!.visibility = View.GONE
                                    } else {
                                        blank_data!!.visibility = View.GONE
                                        community_recycler!!.visibility = View.VISIBLE
                                        if (response.body()!!.data.community_list.size < 10) {
                                            isLastPage = true
                                        }
                                        if (communityList.size == 10) {
                                            communityListAdapter!!.notifyDataSetChanged()
                                        } else {
                                            communityListAdapter!!.notifyItemRangeChanged(
                                                prevSize,
                                                communityList.size
                                            )
                                        }
                                    }
                                } else {
                                    ProjectUtill.printMessage(
                                        this@SearchAddCommunityActivity!!.window.decorView,
                                        response.body()?.message
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    this@SearchAddCommunityActivity!!.window.decorView,
                                    ""
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@SearchAddCommunityActivity!!.window.decorView,
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
                            this@SearchAddCommunityActivity!!.window.decorView,
                            ""
                        )
                    }
                })
    }

    private fun communityListSwipeApi(page: String) {
        isLoading = true
            WebServiceRequest.getInstance().getAllCommunityList(
                this, page, "10", "","0",
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
                                        community_recycler!!.visibility = View.GONE
                                    } else {
                                        blank_data!!.visibility = View.GONE
                                        community_recycler!!.visibility = View.VISIBLE
                                        if (response.body()!!.data.community_list.size < 10) {
                                            isLastPage = true
                                        }
                                        if (communityList.size == 10) {
                                            communityListAdapter!!.notifyDataSetChanged()
                                        } else {
                                            communityListAdapter!!.notifyItemRangeChanged(
                                                prevSize,
                                                communityList.size
                                            )
                                        }
                                    }
                                } else {
                                    ProjectUtill.printMessage(
                                        this@SearchAddCommunityActivity!!.window.decorView,
                                        response.body()?.message
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    this@SearchAddCommunityActivity!!.window.decorView,
                                    ""
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@SearchAddCommunityActivity!!.window.decorView,
                                ""
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<CommunityListResponse>,
                        t: Throwable,
                    ) {
                        ProjectUtill.printErrorMessage(
                            this@SearchAddCommunityActivity!!.window.decorView,
                            ""
                        )
                    }
                })
    }

    private fun communityListPaginationApi(page: String) {
        isLoading = true
        progress_bar!!.visibility = View.VISIBLE
            WebServiceRequest.getInstance().getAllCommunityList(
                this, page, "10", "","0",
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
                                        community_recycler!!.visibility = View.GONE
                                    } else {
                                        blank_data!!.visibility = View.GONE
                                        community_recycler!!.visibility = View.VISIBLE
                                        if (response.body()!!.data.community_list.size < 10) {
                                            isLastPage = true
                                        }
                                        if (communityList.size == 10) {
                                            communityListAdapter!!.notifyDataSetChanged()
                                        } else {
                                            communityListAdapter!!.notifyItemRangeChanged(
                                                prevSize,
                                                communityList.size
                                            )
                                        }
                                    }
                                } else {
                                    ProjectUtill.printMessage(
                                        this@SearchAddCommunityActivity!!.window.decorView,
                                        response.body()?.message
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    this@SearchAddCommunityActivity!!.window.decorView,
                                    ""
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@SearchAddCommunityActivity!!.window.decorView,
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
                            this@SearchAddCommunityActivity!!.window.decorView,
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
        communityListAdapter = CommunityListAdapter(this, communityList)
        layoutManager = LinearLayoutManager(this)
        community_recycler!!.layoutManager = layoutManager
        community_recycler!!.adapter = communityListAdapter
        community_recycler!!.addOnScrollListener(recyclerViewOnScrollListener)
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
}