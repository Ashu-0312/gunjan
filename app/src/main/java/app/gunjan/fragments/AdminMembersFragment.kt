package app.gunjan.fragments

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.adapters.ActiveMembersAdapter
import app.gunjan.adapters.AdminMembersAdapter
import kotlin.system.exitProcess

class AdminMembersFragment : Fragment() {
    private var listRecycler: RecyclerView?=null
    private var list: ArrayList<String> = ArrayList<String>()
    private var stateList: ArrayList<String> = ArrayList<String>()
    private var cityList: ArrayList<String> = ArrayList<String>()
    private var blockList: ArrayList<String> = ArrayList<String>()
    private var filter:ImageView?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_members, container, false)
        listRecycler=view.findViewById(R.id.list_recycler)
        filter=view.findViewById(R.id.filter)
        initData()
        return view
    }

    private fun initData() {
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        stateList.add("Select State")
        stateList.add("state1")
        stateList.add("state2")
        cityList.add("Select City")
        cityList.add("city1")
        cityList.add("city2")
        blockList.add("Select Block")
        blockList.add("block1")
        blockList.add("block2")

        var memberAdapter = AdminMembersAdapter(
            context, list
        )
        var layoutManager: LinearLayoutManager? = LinearLayoutManager(context)
        listRecycler!!.layoutManager = layoutManager
        listRecycler!!.adapter = memberAdapter

        filter!!.setOnClickListener { filterDialog() }
    }
    fun filterDialog() {
        var reset: TextView?=null
        var apply: LinearLayout?=null
        var close: ImageView?=null
        var stateSpinner: Spinner?=null
        var citySpinner: Spinner?=null
        var blockSpinner: Spinner?=null
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
        context?.let {
            val arrayAdapter1: ArrayAdapter<String> = object : ArrayAdapter<String>(
                it,
                R.layout.spinner_layout, stateList
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
            stateSpinner!!.adapter = arrayAdapter1
            stateSpinner!!.onItemSelectedListener = object :
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

        context?.let {
            val arrayAdapter2: ArrayAdapter<String> = object : ArrayAdapter<String>(
                it,
                R.layout.spinner_layout, cityList
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
            citySpinner!!.adapter = arrayAdapter2
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
}