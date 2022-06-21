package app.gunjan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.adapters.DonationReceiveAdapter
import app.gunjan.adapters.FaqListAdapter
import app.gunjan.entity.CoinFaqListResponse
import app.gunjan.entity.ReceivedCoinListResponse
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DonationReceivedFragment : Fragment() {
    private var totalCoins:Int?=0
    private var totalCoin:TextView?=null
    private var donationRecycler: RecyclerView? = null
    private var faqRecycler: RecyclerView? = null
    private var faqLayout: LinearLayout? = null
    private var claimReward: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_donation_received, container, false)
        donationRecycler=view.findViewById(R.id.donate_recycler)
        totalCoin=view.findViewById(R.id.total_coins)
        faqRecycler=view.findViewById(R.id.faqRecycler)
        faqLayout=view.findViewById(R.id.faqLayout)
        claimReward=view.findViewById(R.id.claim_reward)
        initData()
        return view
    }

    private fun initData() {

        claimReward!!.setOnClickListener {
            faqLayout!!.visibility = View.VISIBLE
            faqList()
        }

        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let {
            WebServiceRequest.getInstance().getAllReceivedDonationList(
                it,"2",
                object : Callback<ReceivedCoinListResponse> {
                    override fun onResponse(
                        call: Call<ReceivedCoinListResponse>,
                        response: Response<ReceivedCoinListResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    for (i in response.body()!!.data.donation_list.indices){
                                        totalCoins = totalCoins!! +response.body()!!.data.donation_list[i].total_coins
                                    }
                                    totalCoin!!.text = totalCoins.toString()+" "+getString(R.string.coins)
                                    var donationAdapter = DonationReceiveAdapter(
                                        context, response.body()!!.data.donation_list
                                    )
                                    var layoutManager: LinearLayoutManager? = LinearLayoutManager(context)
                                    donationRecycler!!.layoutManager = layoutManager
                                    donationRecycler!!.adapter = donationAdapter
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
                        call: Call<ReceivedCoinListResponse>,
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

    private fun faqList(){
        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let {
            WebServiceRequest.getInstance().getFAQs(
                it,FCSharedPreferances.getSharedPreferance(context).savE_LANG,
                object : Callback<CoinFaqListResponse> {
                    override fun onResponse(
                        call: Call<CoinFaqListResponse>,
                        response: Response<CoinFaqListResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    var faqListAdapter = FaqListAdapter(context,response.body()!!.data.question)
                                    faqRecycler!!.layoutManager = LinearLayoutManager(context)
                                    faqRecycler!!.adapter = faqListAdapter
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
                        call: Call<CoinFaqListResponse>,
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