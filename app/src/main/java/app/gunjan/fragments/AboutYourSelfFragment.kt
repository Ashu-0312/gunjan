package app.gunjan.fragments

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import app.gunjan.R
import app.gunjan.activities.SetProfileActivity
import app.gunjan.entity.AddAboutResponse
import app.gunjan.entity.UserDetailsResponse
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AboutYourSelfFragment : Fragment() {
    private var Continue: LinearLayout? = null
    private var about: EditText? = null
    private var txtCount: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about_your_self, container, false)
        Continue = view.findViewById(R.id.Continue)
        about = view.findViewById(R.id.about)
        txtCount = view.findViewById(R.id.text_count)
        initData()
        return view
    }

    private fun initData() {
        userDetails()

        about!!.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(
                arg0: CharSequence, arg1: Int, arg2: Int,
                arg3: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
                txtCount!!.text = (500 - s.toString().length).toString() + "/500" + getString(R.string.jf)
            }
        })

        Continue!!.setOnClickListener {
            if (about!!.text.toString().trim() == "") {
                Toast.makeText(context, getString(R.string.about), Toast.LENGTH_LONG).show()
            }else if (about!!.text.toString().length<200){
                Toast.makeText(context, getString(R.string.about_limit), Toast.LENGTH_LONG).show()
            } else {
                val myDialog = ProjectUtill.showProgressDialog(context)
                context?.let { it1 ->
                    WebServiceRequest.getInstance().addAboutYourself(
                        it1, about!!.text.toString().trim(),
                        object : Callback<AddAboutResponse> {
                            override fun onResponse(
                                call: Call<AddAboutResponse>,
                                response: Response<AddAboutResponse>
                            ) {
                                myDialog.dismiss()
                                if (response != null) {
                                    if (response.isSuccessful) {
                                        if (response.body()!!.code == 1) {
                                            (activity as SetProfileActivity).loadCommunityActivity()
                                        } else {
                                            ProjectUtill.printMessage(
                                                (context as Activity).window.decorView,
                                                response.body()?.message
                                            )
                                        }
                                    } else {
                                        ProjectUtill.printErrorMessage(
                                            (context as Activity).window.decorView,
                                            ""
                                        )
                                    }
                                } else {
                                    ProjectUtill.printErrorMessage(
                                        (context as Activity).window.decorView,
                                        ""
                                    )
                                }
                            }

                            override fun onFailure(
                                call: Call<AddAboutResponse>,
                                t: Throwable
                            ) {
                                myDialog.dismiss()
                                ProjectUtill.printErrorMessage(
                                    (context as Activity).window.decorView,
                                    ""
                                )
                            }
                        })
                }
            }
        }
    }

    private fun userDetails(){
        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let { it1 ->
            WebServiceRequest.getInstance().userDetails(
                it1,
                object : Callback<UserDetailsResponse> {
                    override fun onResponse(
                        call: Call<UserDetailsResponse>,
                        response: Response<UserDetailsResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    try {
                                        try {
                                            if (response.body()!!.data.user.about != null || response.body()!!.data.user.about != "") {
                                                about!!.setText(response.body()!!.data.user.about)
                                            }
                                        } catch (e: Exception) {
                                        }
                                    } catch (e: Exception) {
                                    }
                                } else {
                                    ProjectUtill.printMessage(
                                        (context as Activity).window.decorView,
                                        response.body()?.message
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    (context as Activity).window.decorView,
                                    ""
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                (context as Activity).window.decorView,
                                ""
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<UserDetailsResponse>,
                        t: Throwable
                    ) {
                        myDialog.dismiss()
                        ProjectUtill.printErrorMessage(
                            (context as Activity).window.decorView,
                            ""
                        )
                    }
                })
        }
    }
}