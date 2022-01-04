package app.gunjan.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import app.gunjan.R
import app.gunjan.activities.SetProfileActivity
import app.gunjan.entity.AddAboutResponse
import app.gunjan.entity.CompleteProfileResponse
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AboutYourSelfFragment : Fragment() {
    private var Continue: LinearLayout? = null
    private var about: EditText? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about_your_self, container, false)
        Continue = view.findViewById(R.id.Continue)
        about = view.findViewById(R.id.about)
        initData()
        return view
    }

    private fun initData() {
        Continue!!.setOnClickListener {
            if (about!!.text.toString().trim() == "") {
                Toast.makeText(context, getString(R.string.about), Toast.LENGTH_LONG).show()
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
}