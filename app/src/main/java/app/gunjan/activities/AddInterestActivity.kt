package app.gunjan.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.gunjan.R
import app.gunjan.adapters.AddEditInterestAdapter
import app.gunjan.entity.InterestListResponse
import app.gunjan.entity.ShowInterestModel
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import kotlinx.android.synthetic.main.activity_add_interest.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddInterestActivity : BaseActivity() {
    private var page: Int? = 1
    var isLoading = false
    var isLastPage = false
    private var layoutManager: GridLayoutManager? = null
    private var interestAdapter: AddEditInterestAdapter? = null
    private var selectedList: ArrayList<ShowInterestModel> = ArrayList<ShowInterestModel>()
    private var interestList: ArrayList<InterestListResponse.DataBean.InterestBean> =
        ArrayList<InterestListResponse.DataBean.InterestBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_interest)
        initData()
    }

    private fun initData() {
        back.setOnClickListener { finish() }

        Save.setOnClickListener {
            if (interestAdapter!!.getSelectedData()!!.size == 0) {
                Toast.makeText(this, getString(R.string.select_interest), Toast.LENGTH_LONG).show()
            } else if (interestAdapter!!.getSelectedData()!!.size < 2) {
                Toast.makeText(this, getString(R.string.min_interest), Toast.LENGTH_LONG).show()
            } else if (interestAdapter!!.getSelectedData()!!.size > 6) {
                Toast.makeText(this, getString(R.string.max_interest), Toast.LENGTH_LONG).show()
            } else {
                Log.d("SELECTEDDATA", interestAdapter!!.getSelectedData().toString())
                selectedList = interestAdapter!!.getSelectedData()!!
                val intent = Intent()
                intent.putExtra("selected_data", selectedList)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
        initializeAdapter()
        interestListApi("1")

        swipeRefresh!!.setColorSchemeResources(R.color.pink)
        swipeRefresh!!.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            isLastPage = false
            isLoading = false
            page = 1
            interestList.clear()
            interestAdapter!!.notifyDataSetChanged()
            interestListSwipeApi("1")
            swipeRefresh!!.isRefreshing = false
        })
    }

    private fun interestListApi(page: String) {
        isLoading = true
        val myDialog = ProjectUtill.showProgressDialog(this)
        WebServiceRequest.getInstance().getAllInterest(
            this, page, "10",
            object : Callback<InterestListResponse> {
                override fun onResponse(
                    call: Call<InterestListResponse>,
                    response: Response<InterestListResponse>,
                ) {
                    isLoading = false
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                interestList.clear()
                                interestList.addAll(response.body()!!.data.interest)
                                val prevSize: Int = response.body()!!.data.interest.size
                                if (interestList.size == 0) {
                                    blank_data!!.visibility = View.VISIBLE
                                    interestRecycler!!.visibility = View.GONE
                                } else {
                                    blank_data!!.visibility = View.GONE
                                    interestRecycler!!.visibility = View.VISIBLE
                                    if (response.body()!!.data.interest.size < 10) {
                                        isLastPage = true
                                    }
                                    if (interestList.size == 10) {
                                        interestAdapter!!.notifyDataSetChanged()
                                    } else {
                                        interestAdapter!!.notifyItemRangeChanged(
                                            prevSize,
                                            interestList.size
                                        )
                                    }
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@AddInterestActivity!!.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@AddInterestActivity!!.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@AddInterestActivity!!.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<InterestListResponse>,
                    t: Throwable,
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@AddInterestActivity!!.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun interestListSwipeApi(page: String) {
        isLoading = true
        WebServiceRequest.getInstance().getAllInterest(
            this, page, "10",
            object : Callback<InterestListResponse> {
                override fun onResponse(
                    call: Call<InterestListResponse>,
                    response: Response<InterestListResponse>,
                ) {
                    isLoading = false
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                interestList.clear()
                                interestList.addAll(response.body()!!.data.interest)
                                val prevSize: Int = response.body()!!.data.interest.size
                                if (interestList.size == 0) {
                                    blank_data!!.visibility = View.VISIBLE
                                    interestRecycler!!.visibility = View.GONE
                                } else {
                                    blank_data!!.visibility = View.GONE
                                    interestRecycler!!.visibility = View.VISIBLE
                                    if (response.body()!!.data.interest.size < 10) {
                                        isLastPage = true
                                    }
                                    if (interestList.size == 10) {
                                        interestAdapter!!.notifyDataSetChanged()
                                    } else {
                                        interestAdapter!!.notifyItemRangeChanged(
                                            prevSize,
                                            interestList.size
                                        )
                                    }
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@AddInterestActivity!!.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@AddInterestActivity!!.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@AddInterestActivity!!.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<InterestListResponse>,
                    t: Throwable,
                ) {
                    ProjectUtill.printErrorMessage(
                        this@AddInterestActivity!!.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun interestListPaginationApi(page: String) {
        isLoading = true
        progress_bar!!.visibility = View.VISIBLE
        WebServiceRequest.getInstance().getAllInterest(
            this, page, "10",
            object : Callback<InterestListResponse> {
                override fun onResponse(
                    call: Call<InterestListResponse>,
                    response: Response<InterestListResponse>,
                ) {
                    isLoading = false
                    progress_bar!!.visibility = View.GONE
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                interestList.addAll(response.body()!!.data.interest)
                                val prevSize: Int = response.body()!!.data.interest.size
                                if (interestList.size == 0) {
                                    blank_data!!.visibility = View.VISIBLE
                                    interestRecycler!!.visibility = View.GONE
                                } else {
                                    blank_data!!.visibility = View.GONE
                                    interestRecycler!!.visibility = View.VISIBLE
                                    if (response.body()!!.data.interest.size < 10) {
                                        isLastPage = true
                                    }
                                    if (interestList.size == 10) {
                                        interestAdapter!!.notifyDataSetChanged()
                                    } else {
                                        interestAdapter!!.notifyItemRangeChanged(
                                            prevSize,
                                            interestList.size
                                        )
                                    }
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@AddInterestActivity!!.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@AddInterestActivity!!.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@AddInterestActivity!!.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<InterestListResponse>,
                    t: Throwable,
                ) {
                    progress_bar!!.visibility = View.GONE
                    ProjectUtill.printErrorMessage(
                        this@AddInterestActivity!!.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun initializeAdapter() {
        interestList.clear()
        page = 1
        isLastPage = false
        isLoading = false
        interestAdapter = AddEditInterestAdapter(this, interestList)
        layoutManager = GridLayoutManager(this, 3)
        interestRecycler!!.layoutManager = layoutManager
        interestRecycler!!.adapter = interestAdapter
        interestRecycler!!.addOnScrollListener(recyclerViewOnScrollListener)
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
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= interestList.size) {
                        isLoading = true
                        page = page!! + 1
                        interestListPaginationApi(page.toString())
                    }
                }
            }
        }
}