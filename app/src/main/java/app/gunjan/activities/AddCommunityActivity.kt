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
import app.gunjan.entity.CategoryListResponse
import app.gunjan.entity.PrivacyPolicyResponse
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import kotlinx.android.synthetic.main.activity_add_community.*
import kotlinx.android.synthetic.main.activity_add_community.back
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCommunityActivity : AppCompatActivity() {
    private var nameList: ArrayList<String> = ArrayList<String>()
    private val idList: ArrayList<String> = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_community)
        initData()
    }

    private fun initData() {

        back.setOnClickListener { finish() }
        getCategoryList()
    }

    fun getCategoryList() {
        val myDialog = ProjectUtill.showProgressDialog(this@AddCommunityActivity)
        WebServiceRequest.getInstance().categoryList(
            this,
            object : Callback<CategoryListResponse> {
                override fun onResponse(
                    call: Call<CategoryListResponse>,
                    response: Response<CategoryListResponse>
                ) {
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                idList.clear()
                                idList.add("")
                                nameList.add("Select Category")
                                for (i in response.body()!!.data.category_list) {
                                    idList.add(i.id.toString())
                                    nameList.add(i.name)
                                }
                                val arrayAdapter1: ArrayAdapter<String> =
                                    object : ArrayAdapter<String>(
                                        this@AddCommunityActivity,
                                        R.layout.spinner_layout, nameList
                                    ) {
                                        override fun isEnabled(position: Int): Boolean {
                                            return position != 0
                                        }

                                        override fun getDropDownView(
                                            position: Int, convertView: View?,
                                            parent: ViewGroup,
                                        ): View {
                                            val view =
                                                super.getDropDownView(position, convertView, parent)
                                            val tv = view as TextView
                                            if (position == 0) { // Set the hint text color gray
                                                tv.setTextColor(Color.BLACK)
                                            } else {
                                                tv.setTextColor(resources.getColor(R.color.txt_color))
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
                            } else {
                                ProjectUtill.printMessage(
                                    this@AddCommunityActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@AddCommunityActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@AddCommunityActivity.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<CategoryListResponse>,
                    t: Throwable
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@AddCommunityActivity.window.decorView,
                        ""
                    )
                }
            })
    }
}