package app.gunjan.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import app.gunjan.R
import kotlinx.android.synthetic.main.activity_add_community.*

class AddCommunityActivity : AppCompatActivity() {
    private var list: ArrayList<String> = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_community)
        initData()
    }

    private fun initData() {
        list.add("Select Category")
        list.add("category1")
        list.add("category2")
        list.add("category3")
        list.add("category4")
        back.setOnClickListener { finish() }

        val arrayAdapter1: ArrayAdapter<String> = object : ArrayAdapter<String>(
            this,
            R.layout.spinner_layout, list
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
        categorySpinner!!.adapter = arrayAdapter1
        categorySpinner!!.onItemSelectedListener = object :
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
}