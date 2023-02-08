package app.gunjan.fragments

import android.app.Dialog
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.gunjan.R
import app.gunjan.adapters.ActiveMembersAdapter
import app.gunjan.adapters.AdminMembersAdapter
import app.gunjan.entity.CityListResponse
import app.gunjan.entity.MemberListResponse
import app.gunjan.entity.StateListResponse
import app.gunjan.twilio.ClientCreated
import app.gunjan.twilio.Logger
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.twilio.chat.*
import kotlinx.android.synthetic.main.activity_notification.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminMembersFragment : Fragment(), ClientCreated {
    private var page: Int? = 1
    private var cityValue: String? = ""
    private var stateValue: String? = ""
    var swipeRefresh: SwipeRefreshLayout? = null
    var progressBar: ProgressBar? = null
    var chatClient: ChatClient? = null
    var blankData: TextView? = null
    var isLoading = false
    var isLastPage = false
    var citySpinner: Spinner? = null
    private var layoutManager: LinearLayoutManager? = null
    var memberAdapter: AdminMembersAdapter? = null
    private var listRecycler: RecyclerView? = null
    private var list: ArrayList<MemberListResponse.DataBean.MemberListBean> =
        ArrayList<MemberListResponse.DataBean.MemberListBean>()
    private var stateNameList: ArrayList<String> = ArrayList<String>()
    private var cityList: ArrayList<String> = ArrayList<String>()
    private var filter: ImageView? = null
    private var searchEdt: EditText? = null
    private var search: ImageView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_members, container, false)
        listRecycler = view.findViewById(R.id.list_recycler)
        filter = view.findViewById(R.id.filter)
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        progressBar = view.findViewById(R.id.progress_bar)
        blankData = view.findViewById(R.id.blank_data)
        searchEdt = view.findViewById(R.id.search_edt)
        search = view.findViewById(R.id.search)
        initData()
        return view
    }

    private fun initData() {
        createChatClient(FCSharedPreferances.getSharedPreferance(context).chaT_TOKEN)
        initializeAdapter()
        memberListApi("1", "", "")

        cityList.add("Select City")
        cityList.add("city1")
        cityList.add("city2")



        filter!!.setOnClickListener { filterDialog() }

        swipeRefresh!!.setColorSchemeResources(R.color.pink)
        swipeRefresh!!.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            isLastPage = false
            isLoading = false
            page = 1
            list.clear()
            memberAdapter!!.notifyDataSetChanged()
            memberListSwipeApi("1")
            swipe_refresh!!.isRefreshing = false
        })
        searchEdt!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                if (searchEdt!!.text.toString().trim().isEmpty()) {
                    memberListSearchApi(page.toString(), searchEdt!!.text.toString().trim())
                }
            }
        })

        searchEdt!!.onDone {
            memberListSearchApi(page.toString(), searchEdt!!.text.toString().trim())
        }

        search!!.setOnClickListener {
            memberListSearchApi(page.toString(), searchEdt!!.text.toString().trim())
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

    private fun memberListSearchApi(page: String, value: String) {
        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let {
            WebServiceRequest.getInstance().getAllMemberList(
                it, page, "10", "", "", "only admin member", value!!,
                object : Callback<MemberListResponse> {
                    override fun onResponse(
                        call: Call<MemberListResponse>,
                        response: Response<MemberListResponse>,
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    list.clear()
                                    list.addAll(response.body()!!.data.member_list)
                                    val prevSize: Int = response.body()!!.data.member_list.size
                                    if (list.size == 0) {
                                        blankData!!.visibility = View.VISIBLE
                                        listRecycler!!.visibility = View.GONE
                                    } else {
                                        blankData!!.visibility = View.GONE
                                        listRecycler!!.visibility = View.VISIBLE
                                        if (response.body()!!.data.member_list.size < 10) {
                                            isLastPage = true
                                        }
                                        if (list.size == 10) {
                                            memberAdapter!!.notifyDataSetChanged()
                                        } else {
                                            memberAdapter!!.notifyItemRangeChanged(
                                                prevSize,
                                                list.size
                                            )
                                        }
                                    }
                                    setUser()
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
                        call: Call<MemberListResponse>,
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

    private fun memberListApi(page: String, city: String, state: String) {
        isLoading = true
        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let {
            WebServiceRequest.getInstance().getAllMemberList(
                it, page, "10", state, city, "only admin member", "",
                object : Callback<MemberListResponse> {
                    override fun onResponse(
                        call: Call<MemberListResponse>,
                        response: Response<MemberListResponse>,
                    ) {
                        isLoading = false
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    list.clear()
                                    list.addAll(response.body()!!.data.member_list)
                                    val prevSize: Int = response.body()!!.data.member_list.size
                                    if (list.size == 0) {
                                        blankData!!.visibility = View.VISIBLE
                                        listRecycler!!.visibility = View.GONE
                                    } else {
                                        blankData!!.visibility = View.GONE
                                        listRecycler!!.visibility = View.VISIBLE
                                        if (response.body()!!.data.member_list.size < 10) {
                                            isLastPage = true
                                        }
                                        if (list.size == 10) {
                                            memberAdapter!!.notifyDataSetChanged()
                                        } else {
                                            memberAdapter!!.notifyItemRangeChanged(
                                                prevSize,
                                                list.size
                                            )
                                        }
                                    }
                                    setUser()
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
                        call: Call<MemberListResponse>,
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

    private fun memberListSwipeApi(page: String) {
        isLoading = true
        context?.let {
            WebServiceRequest.getInstance().getAllMemberList(
                it, page, "10", "", "", "only admin member", "",
                object : Callback<MemberListResponse> {
                    override fun onResponse(
                        call: Call<MemberListResponse>,
                        response: Response<MemberListResponse>,
                    ) {
                        isLoading = false
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    list.clear()
                                    list.addAll(response.body()!!.data.member_list)
                                    val prevSize: Int = response.body()!!.data.member_list.size
                                    if (list.size == 0) {
                                        blankData!!.visibility = View.VISIBLE
                                        listRecycler!!.visibility = View.GONE
                                    } else {
                                        blankData!!.visibility = View.GONE
                                        listRecycler!!.visibility = View.VISIBLE
                                        if (response.body()!!.data.member_list.size < 10) {
                                            isLastPage = true
                                        }
                                        if (list.size == 10) {
                                            memberAdapter!!.notifyDataSetChanged()
                                        } else {
                                            memberAdapter!!.notifyItemRangeChanged(
                                                prevSize,
                                                list.size
                                            )
                                        }
                                    }
                                    setUser()
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
                        call: Call<MemberListResponse>,
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

    private fun memberListPaginationApi(page: String) {
        isLoading = true
        progress_bar!!.visibility = View.VISIBLE
        context?.let {
            WebServiceRequest.getInstance().getAllMemberList(
                it, page, "10", "", "", "only admin member", "",
                object : Callback<MemberListResponse> {
                    override fun onResponse(
                        call: Call<MemberListResponse>,
                        response: Response<MemberListResponse>,
                    ) {
                        isLoading = false
                        progress_bar!!.visibility = View.GONE
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    list.addAll(response.body()!!.data.member_list)
                                    val prevSize: Int = response.body()!!.data.member_list.size
                                    if (list.size == 0) {
                                        blankData!!.visibility = View.VISIBLE
                                        listRecycler!!.visibility = View.GONE
                                    } else {
                                        blankData!!.visibility = View.GONE
                                        listRecycler!!.visibility = View.VISIBLE
                                        if (response.body()!!.data.member_list.size < 10) {
                                            isLastPage = true
                                        }
                                        if (list.size == 10) {
                                            memberAdapter!!.notifyDataSetChanged()
                                        } else {
                                            memberAdapter!!.notifyItemRangeChanged(
                                                prevSize,
                                                list.size
                                            )
                                        }
                                    }
                                    setUser()
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
                        call: Call<MemberListResponse>,
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
        list.clear()
        page = 1
        isLastPage = false
        isLoading = false
        memberAdapter = AdminMembersAdapter(context, list)
        layoutManager = LinearLayoutManager(context)
        listRecycler!!.layoutManager = layoutManager
        listRecycler!!.adapter = memberAdapter
        listRecycler!!.addOnScrollListener(recyclerViewOnScrollListener)
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
                        memberListPaginationApi(page.toString())
                    }
                }
            }
        }

    fun filterDialog() {
        var reset: TextView? = null
        var apply: LinearLayout? = null
        var close: ImageView? = null
        var stateSpinner: Spinner? = null
        val dialog = context?.let { Dialog(it) }
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.filter_dialog)
        dialog!!.setCancelable(true)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        close = dialog.findViewById(R.id.close)
        apply = dialog.findViewById(R.id.submit)
        reset = dialog.findViewById(R.id.reset)
        stateSpinner = dialog.findViewById(R.id.state_spinner)
        citySpinner = dialog.findViewById(R.id.city_spinner)
        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let {
            WebServiceRequest.getInstance().getStateList(
                it,
                object : Callback<StateListResponse> {
                    override fun onResponse(
                        call: Call<StateListResponse>,
                        response: Response<StateListResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    stateNameList.clear()
                                    stateNameList.add("Select State")
                                    for (i in response.body()!!.data.state_list) {
                                        stateNameList.add(i.stateName)
                                    }
                                    val arrayAdapter1: ArrayAdapter<String> =
                                        object : ArrayAdapter<String>(
                                            context!!,
                                            R.layout.spinner_layout, stateNameList
                                        ) {
                                            override fun isEnabled(position: Int): Boolean {
                                                return position != 0
                                            }

                                            override fun getDropDownView(
                                                position: Int, convertView: View?,
                                                parent: ViewGroup,
                                            ): View {
                                                val view = super.getDropDownView(
                                                    position,
                                                    convertView,
                                                    parent
                                                )
                                                val tv = view as TextView
                                                if (position == 0) { // Set the hint text color gray
                                                    tv.setTextColor(Color.BLACK)
                                                } else {
                                                    tv.setTextColor(resources.getColor(R.color.txt_color))
                                                }
                                                return view
                                            }

                                        }
                                    stateSpinner!!.adapter = arrayAdapter1
                                    stateSpinner!!.onItemSelectedListener = object :
                                        AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(
                                            adapterView: AdapterView<*>?,
                                            view: View,
                                            i: Int,
                                            l: Long,
                                        ) {
                                            stateValue = stateNameList[i].toString()
                                            getCityList(stateNameList[i].toString())
                                        }

                                        override fun onNothingSelected(adapterView: AdapterView<*>?) {}
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
                        call: Call<StateListResponse>,
                        t: Throwable
                    ) {
                        myDialog.dismiss()
                        ProjectUtill.printErrorMessage(
                            activity!!.window.decorView,
                            ""
                        )
                    }
                })
        }

        close.setOnClickListener { dialog.cancel() }

        apply.setOnClickListener {
            if (stateSpinner!!.selectedItem.toString().trim() == getString(R.string.select_state)) {
                Toast.makeText(context, getString(R.string.please_state), Toast.LENGTH_LONG).show()
            } else {
                dialog.cancel()
                initializeAdapter()
                memberListApi(
                    "1",
                    cityValue!!,
                    stateValue!!
                )
            }
        }

        reset.setOnClickListener {
            dialog.cancel()
            dialog.show()
        }

        dialog.show()
    }

    private fun getCityList(code: String) {
        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let {
            WebServiceRequest.getInstance().getCityList(
                it, code,
                object : Callback<CityListResponse> {
                    override fun onResponse(
                        call: Call<CityListResponse>,
                        response: Response<CityListResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    cityList.clear()
                                    cityList.add(getString(R.string.select_city))
                                    for (i in response.body()!!.data.city_list) {
                                        cityList.add(i.district)
                                    }
                                    val arrayAdapter1: ArrayAdapter<String> =
                                        object : ArrayAdapter<String>(
                                            context!!,
                                            R.layout.spinner_layout, cityList
                                        ) {
                                            override fun isEnabled(position: Int): Boolean {
                                                return position != 0
                                            }

                                            override fun getDropDownView(
                                                position: Int, convertView: View?,
                                                parent: ViewGroup,
                                            ): View {
                                                val view = super.getDropDownView(
                                                    position,
                                                    convertView,
                                                    parent
                                                )
                                                val tv = view as TextView
                                                if (position == 0) { // Set the hint text color gray
                                                    tv.setTextColor(Color.BLACK)
                                                } else {
                                                    tv.setTextColor(resources.getColor(R.color.txt_color))
                                                }
                                                return view
                                            }

                                        }
                                    citySpinner!!.adapter = arrayAdapter1
                                    citySpinner!!.onItemSelectedListener = object :
                                        AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(
                                            adapterView: AdapterView<*>?,
                                            view: View,
                                            i: Int,
                                            l: Long,
                                        ) {
                                            if (i > 0) {
                                                cityValue = cityList[i]
                                            }
                                        }

                                        override fun onNothingSelected(adapterView: AdapterView<*>?) {}
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
                        call: Call<CityListResponse>,
                        t: Throwable
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

    private fun setUser() {
        if (chatClient != null) {
            for (p in list.indices) {
                var myId: String? = ""
                if (list[p].userId!!.toInt() > FCSharedPreferances.getSharedPreferance(context).useR_ID.toInt()
                ) myId =
                    FCSharedPreferances.getSharedPreferance(context).useR_ID.toString() + "_" + list[p].userId else myId =
                    "" + list[p].userId + "_" + FCSharedPreferances.getSharedPreferance(
                        context
                    ).useR_ID
                Task1(memberAdapter, chatClient).execute(
                    p.toString(),
                    myId,
                    list[p].userId.toString()
                )
            }
        }
    }

    override fun clientCreated(chatClient: ChatClient?, success: Boolean, exception: Exception?) {
        this.chatClient = chatClient
    }

    class Task1(chatAdapterr: AdminMembersAdapter?, chatClient: ChatClient?) :
        AsyncTask<String?, String?, String?>() {
        var chatAdapter: AdminMembersAdapter? = chatAdapterr
        var chatClient: ChatClient? = chatClient
        override fun doInBackground(vararg params: String?): String? {
            chatClient!!.channels.getChannel(params[1], object : CallbackListener<Channel>() {
                override fun onSuccess(channel: Channel) {
                    Handler().postDelayed({
                        if (channel.messages != null) {
                            channel.messages.getLastMessages(
                                1,
                                object : CallbackListener<List<Message>>() {
                                    override fun onSuccess(messages: List<Message>) {
                                        if (messages != null) {
                                            if (messages.isNotEmpty()) {
                                                chatAdapter!!.setMessage(
                                                    params[0]!!.toInt(),
                                                    messages[0].messageBody
                                                )
                                            }
                                        }
                                    }

                                    override fun onError(errorInfo: ErrorInfo) {
                                        super.onError(errorInfo)
                                        Log.d("error3", errorInfo.message)
                                    }
                                })
                        }
                    }, 2000)
                }

                override fun onError(errorInfo: ErrorInfo) {
                    super.onError(errorInfo)
                    Log.d("error2", errorInfo.message)
                }
            })
            return null
        }
    }


    private fun createChatClient(token: String) {
        val builder = ChatClient.Properties.Builder()
        builder.setRegion("us1")
        val props = builder.createProperties()
        context?.let {
            ChatClient.create(
                it,
                token,
                props,
                object : CallbackListener<ChatClient>() {
                    override fun onSuccess(chatClient: ChatClient) {
                        //Toast.makeText(HomeActivity.this, R.string.success_chat, Toast.LENGTH_LONG).show();
                        Logger.show("success", "chatclient")
                        this@AdminMembersFragment.chatClient = chatClient
                        setUser()
                    }

                    override fun onError(errorInfo: ErrorInfo) {
                        super.onError(errorInfo)
                        //Toast.makeText(HomeActivity.this, R.string.failed_chat, Toast.LENGTH_LONG).show();
                        Logger.show("success: errorInfo", errorInfo.message)
                    }
                })
        }
    }
}