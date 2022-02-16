package app.gunjan.fragments

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.gunjan.R
import app.gunjan.activities.AddCommunityActivity
import app.gunjan.adapters.CommunityListAdapter
import app.gunjan.entity.CommunityListResponse
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunityListFragment : Fragment() {
    private var communityRecycler:RecyclerView?=null
    private var addCommunity:ImageView?=null
    private var page: Int? = 1
    var isLoading = false
    var isLastPage = false
    private var layoutManager: LinearLayoutManager? = null
    private var blankData: TextView? = null
    private var searchEdt: EditText? = null
    private var search: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var swipeRefresh: SwipeRefreshLayout? = null
    private var communityList: ArrayList<CommunityListResponse.DataBean.CommunityListBean> =
        ArrayList<CommunityListResponse.DataBean.CommunityListBean>()
    private var communityListAdapter: CommunityListAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_community_list, container, false)
        communityRecycler=view.findViewById(R.id.community_recycler)
        addCommunity=view.findViewById(R.id.add_community)
        blankData=view.findViewById(R.id.blank_data)
        progressBar=view.findViewById(R.id.progress_bar)
        swipeRefresh=view.findViewById(R.id.swipe_refresh)
        searchEdt=view.findViewById(R.id.search_edt)
        search=view.findViewById(R.id.search)
        initData()
        return view
    }

    private fun initData() {
        addCommunity!!.setOnClickListener {
            var intent=Intent(context, AddCommunityActivity::class.java)
            startActivity(intent)
        }
        initializeAdapter()
        communityListApi("1")

        swipeRefresh!!.setColorSchemeResources(R.color.pink)
        swipeRefresh!!.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            isLastPage = false
            isLoading = false
            page = 1
            communityList.clear()
            communityListAdapter!!.notifyDataSetChanged()
            communityListSwipeApi("1")
            swipeRefresh!!.isRefreshing = false
        })

        searchEdt!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                if (searchEdt!!.text.toString().trim().isEmpty()) {
                    communityListSearchApi( page.toString(), searchEdt!!.text.toString().trim())
                }
            }
        })

        searchEdt!!.onDone {
            communityListSearchApi( page.toString(), searchEdt!!.text.toString().trim())
        }

        search!!.setOnClickListener {
            communityListSearchApi(page.toString(), searchEdt!!.text.toString().trim())
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
        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let {
            WebServiceRequest.getInstance().getAllCommunityList(
                it, page, "10", value,"0",
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
                                        blankData!!.visibility = View.VISIBLE
                                        communityRecycler!!.visibility = View.GONE
                                    } else {
                                        blankData!!.visibility = View.GONE
                                        communityRecycler!!.visibility = View.VISIBLE
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
                        call: Call<CommunityListResponse>,
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

    private fun communityListApi(page: String) {
        isLoading = true
        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let {
            WebServiceRequest.getInstance().getAllCommunityList(
                it, page, "10", "","0",
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
                                        blankData!!.visibility = View.VISIBLE
                                        communityRecycler!!.visibility = View.GONE
                                    } else {
                                        blankData!!.visibility = View.GONE
                                        communityRecycler!!.visibility = View.VISIBLE
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
                        call: Call<CommunityListResponse>,
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

    private fun communityListSwipeApi(page: String) {
        isLoading = true
        context?.let {
            WebServiceRequest.getInstance().getAllCommunityList(
                it, page, "10", "","0",
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
                                        blankData!!.visibility = View.VISIBLE
                                        communityRecycler!!.visibility = View.GONE
                                    } else {
                                        blankData!!.visibility = View.GONE
                                        communityRecycler!!.visibility = View.VISIBLE
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
                        call: Call<CommunityListResponse>,
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

    private fun communityListPaginationApi(page: String) {
        isLoading = true
        progressBar!!.visibility = View.VISIBLE
        context?.let {
            WebServiceRequest.getInstance().getAllCommunityList(
                it, page, "10", "","0",
                object : Callback<CommunityListResponse> {
                    override fun onResponse(
                        call: Call<CommunityListResponse>,
                        response: Response<CommunityListResponse>,
                    ) {
                        isLoading = false
                        progressBar!!.visibility = View.GONE
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    communityList.addAll(response.body()!!.data.community_list)
                                    val prevSize: Int = response.body()!!.data.community_list.size
                                    if (communityList.size == 0) {
                                        blankData!!.visibility = View.VISIBLE
                                        communityRecycler!!.visibility = View.GONE
                                    } else {
                                        blankData!!.visibility = View.GONE
                                        communityRecycler!!.visibility = View.VISIBLE
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
                        call: Call<CommunityListResponse>,
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
        communityList.clear()
        page = 1
        isLastPage = false
        isLoading = false
        communityListAdapter = CommunityListAdapter(context, communityList)
        layoutManager = LinearLayoutManager(context)
        communityRecycler!!.layoutManager = layoutManager
        communityRecycler!!.adapter = communityListAdapter
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
}