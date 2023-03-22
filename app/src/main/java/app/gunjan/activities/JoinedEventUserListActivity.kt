package app.gunjan.activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.gunjan.R
import app.gunjan.adapters.JoinedEventUsersAdapter
import app.gunjan.entity.EventJoinedUsersResponse
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import kotlinx.android.synthetic.main.activity_joined_event_user_list.*
import kotlinx.android.synthetic.main.activity_joined_event_user_list.back
import kotlinx.android.synthetic.main.activity_joined_event_user_list.swipe_refresh
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinedEventUserListActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_joined_event_user_list)
        initData()
    }

    private fun initData() {
        back.setOnClickListener { finish() }
        userList()

        swipe_refresh!!.setColorSchemeResources(R.color.pink)
        swipe_refresh!!.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            userListSwipe()
            swipe_refresh!!.isRefreshing = false
        })
    }

    private fun userList(){
        val myDialog = ProjectUtill.showProgressDialog(this@JoinedEventUserListActivity)
        WebServiceRequest.getInstance().joinedUserList(
            this,intent.getStringExtra("id").toString(),
            object : Callback<EventJoinedUsersResponse> {
                override fun onResponse(
                    call: Call<EventJoinedUsersResponse>,
                    response: Response<EventJoinedUsersResponse>
                ) {
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                if(response.body()!!.data.member_list.size==0){
                                    userRecycler.visibility=View.GONE
                                    blankData.visibility=View.VISIBLE
                                }else {
                                    userRecycler.visibility=View.VISIBLE
                                    blankData.visibility=View.GONE
                                    val listAdapter = JoinedEventUsersAdapter(
                                        this@JoinedEventUserListActivity,
                                        response.body()!!.data.member_list
                                    )
                                    userRecycler.layoutManager =
                                        LinearLayoutManager(this@JoinedEventUserListActivity)
                                    userRecycler.adapter = listAdapter
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@JoinedEventUserListActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@JoinedEventUserListActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@JoinedEventUserListActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<EventJoinedUsersResponse>,
                    t: Throwable
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@JoinedEventUserListActivity.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun userListSwipe(){
        WebServiceRequest.getInstance().joinedUserList(
            this,intent.getStringExtra("id").toString(),
            object : Callback<EventJoinedUsersResponse> {
                override fun onResponse(
                    call: Call<EventJoinedUsersResponse>,
                    response: Response<EventJoinedUsersResponse>
                ) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                if(response.body()!!.data.member_list.size==0){
                                    userRecycler.visibility=View.GONE
                                    blankData.visibility=View.VISIBLE
                                }else {
                                    userRecycler.visibility=View.VISIBLE
                                    blankData.visibility=View.GONE
                                    val listAdapter = JoinedEventUsersAdapter(
                                        this@JoinedEventUserListActivity,
                                        response.body()!!.data.member_list
                                    )
                                    userRecycler.layoutManager =
                                        LinearLayoutManager(this@JoinedEventUserListActivity)
                                    userRecycler.adapter = listAdapter
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@JoinedEventUserListActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@JoinedEventUserListActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@JoinedEventUserListActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<EventJoinedUsersResponse>,
                    t: Throwable
                ) {
                    ProjectUtill.printErrorMessage(
                        this@JoinedEventUserListActivity.window.decorView,
                        ""
                    )
                }
            })
    }
}