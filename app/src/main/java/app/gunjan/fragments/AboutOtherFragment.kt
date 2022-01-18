package app.gunjan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import app.gunjan.R
import app.gunjan.entity.OtherUserDetailsResponse
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_others_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AboutOtherFragment : Fragment() {
    private var about:TextView?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about_other, container, false)
        about=view.findViewById(R.id.about)
        initData()
        return view
    }

    private fun initData() {
      userDetails()
    }

    private fun userDetails() {
        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let {
            WebServiceRequest.getInstance().otherUserProfile(
                it, FCSharedPreferances.getSharedPreferance(context).otheR_ID,
                object : Callback<OtherUserDetailsResponse> {
                    override fun onResponse(
                        call: Call<OtherUserDetailsResponse>,
                        response: Response<OtherUserDetailsResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    try {
                                        about!!.text=response.body()!!.data.user.about
                                    }catch (e:Exception){}
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
                        call: Call<OtherUserDetailsResponse>,
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