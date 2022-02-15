package app.gunjan.fragments

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.adapters.ActiveMembersAdapter
import app.gunjan.adapters.CommunityListAdapter
import app.gunjan.entity.CityListResponse
import app.gunjan.entity.StateListResponse
import app.gunjan.entity.TermsResponse
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_add_community.*
import kotlinx.android.synthetic.main.activity_tc.*
import org.xml.sax.ErrorHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ActiveMembersFragment : Fragment() {
    private var listRecycler: RecyclerView? = null
    private var filter: ImageView? = null
    var citySpinner: Spinner? = null
    private var list: ArrayList<String> = ArrayList<String>()
    private var stateList: ArrayList<String> = ArrayList<String>()
    private var stateNameList: ArrayList<String> = ArrayList<String>()
    private var cityList: ArrayList<String> = ArrayList<String>()
    private var blockList: ArrayList<String> = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_active_members, container, false)
        listRecycler = view.findViewById(R.id.list_recycler)
        filter = view.findViewById(R.id.filter)
        initData()
        return view
    }

    private fun initData() {
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        blockList.add("Select Block")
        blockList.add("block1")
        blockList.add("block2")

        var memberAdapter = ActiveMembersAdapter(
            context, list
        )
        var layoutManager: LinearLayoutManager? = LinearLayoutManager(context)
        listRecycler!!.layoutManager = layoutManager
        listRecycler!!.adapter = memberAdapter

        filter!!.setOnClickListener {
            filterDialog()
        }
    }

    fun filterDialog() {
        var reset: TextView? = null
        var apply: LinearLayout? = null
        var close: ImageView? = null
        var stateSpinner: Spinner? = null
        var blockSpinner: Spinner? = null
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
        blockSpinner = dialog.findViewById(R.id.block_spinner)

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
                                    stateList.clear()
                                    stateNameList.clear()
                                    stateList.add("")
                                    stateNameList.add("Select State")
                                    for (i in response.body()!!.data.state_list) {
                                        stateList.add(i.isoCode)
                                        stateNameList.add(i.name)
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
                                            getCityList(stateList[i].toString())
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

        context?.let {
            val arrayAdapter3: ArrayAdapter<String> = object : ArrayAdapter<String>(
                it,
                R.layout.spinner_layout, blockList
            ) {
                override fun isEnabled(position: Int): Boolean {
                    return position != 0
                }

                override fun getDropDownView(
                    position: Int, convertView: View?,
                    parent: ViewGroup,
                ): View {
                    val view = super.getDropDownView(position, convertView, parent)
                    val tv = view as TextView
                    if (position == 0) { // Set the hint text color gray
                        tv.setTextColor(Color.BLACK)
                    } else {
                        tv.setTextColor(resources.getColor(R.color.grey))
                    }
                    return view
                }
            }
            blockSpinner!!.adapter = arrayAdapter3
            blockSpinner!!.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View,
                    i: Int,
                    l: Long,
                ) {
                    if (i > 0) {
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }
        }

        close.setOnClickListener { dialog.cancel() }

        apply.setOnClickListener {
            dialog.cancel()
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
                                    cityList.add("Select City")
                                    for (i in response.body()!!.data.city_list) {
                                        cityList.add(i.name)
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
}