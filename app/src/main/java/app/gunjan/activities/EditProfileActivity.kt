package app.gunjan.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import app.gunjan.R
import app.gunjan.adapters.CommunityListAdapter
import app.gunjan.adapters.ShowInterestAdapter
import kotlinx.android.synthetic.main.activity_edit_profile.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EditProfileActivity : AppCompatActivity() {
    private var mYear = 0
    private  var mMonth:Int = 0
    private  var mDay:Int = 0
    private var dob = ""
    var yourDate: String? = null
    var fromDateValue: String? = null
    private var genderList:ArrayList<String> = ArrayList<String>()
    private var list:ArrayList<String> = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        initData()
    }
    private fun initData() {
        genderList.add("Select Gender")
        genderList.add("Male")
        genderList.add("Female")
        genderList.add("Others")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        var interestAdapter = ShowInterestAdapter(
            this, list
        )
        var layoutManager: GridLayoutManager? = GridLayoutManager(this,3)
        interest_recycler!!.layoutManager = layoutManager
        interest_recycler!!.adapter = interestAdapter

        val arrayAdapter1: ArrayAdapter<String> = object : ArrayAdapter<String>(
            this,
            R.layout.spinner_layout, genderList
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
        genderSpinner!!.adapter = arrayAdapter1
        genderSpinner!!.onItemSelectedListener = object :
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

        edtDob.setOnClickListener { getDate() }

        back.setOnClickListener { finish() }

        viewAll.setOnClickListener {
            startActivity(Intent(this,AddInterestActivity::class.java))
        }
    }

    fun getDate() {
        val c = Calendar.getInstance()
        mYear = c[Calendar.YEAR]
        mMonth = c[Calendar.MONTH]
        mDay = c[Calendar.DAY_OF_MONTH]
        val datePickerDialog =
            DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    var date = dayOfMonth.toString()
                    var month = (monthOfYear + 1).toString()
                    if (date.length == 1) {
                        date = "0$date"
                    }
                    if (month.length == 1) {
                        month = "0$month"
                    }
                    dob = "$year-$month-$date"
                    val today = Calendar.getInstance()
                    val dob = Calendar.getInstance()
                    dob[year, monthOfYear] = dayOfMonth
                    var yourAge = today[Calendar.YEAR] - dob[Calendar.YEAR]
                    dob.add(Calendar.YEAR, yourAge)
                    if (today.before(dob)) {
                        yourAge--
                    }
                    var age = yourAge
                    if (age < 18) {
                        Toast.makeText(
                            this,
                            R.string.valid_age,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                            fromDateValue = "$year-$month-$date"
                            val date = SimpleDateFormat("MM/dd/yyyy").parse("$month/$date/$year")
                            try {
                                var format = SimpleDateFormat("yyyy-MM-dd")
                                val date1 = format.parse(fromDateValue)
                                val date2 = format.format(date1)
                                format =
                                    if (date2.endsWith("01") && !date2.endsWith("11")) SimpleDateFormat(
                                        "d'st' MMM, yyyy"
                                    ) else if (date2.endsWith(
                                            "02"
                                        ) && !date2.endsWith("12")
                                    ) SimpleDateFormat("d'nd' MMM, yyyy") else if (date2.endsWith("03") && !date2.endsWith(
                                            "13"
                                        )
                                    ) SimpleDateFormat("d'rd' MMM, yyyy") else SimpleDateFormat("d'th' MMM, yyyy")
                                yourDate = format.format(date1)
                                edtDob!!.text = yourDate
                            } catch (e: Exception) {
                            }
                    }
                }, mYear, mMonth, mDay
            )
        val c2 = Calendar.getInstance()
        c2[mYear, mMonth] = mDay
        datePickerDialog!!.datePicker.maxDate = System.currentTimeMillis();
        datePickerDialog!!.show()
    }
}