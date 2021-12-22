package app.gunjan.fragments

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import android.widget.RelativeLayout
import app.gunjan.R
import app.gunjan.activities.EditProfileActivity
import app.gunjan.activities.LeaveCommunityActivity
import app.gunjan.activities.PrivacyPolicyActivity
import app.gunjan.activities.TcActivity

class ProfileFragment : Fragment() {
    private var leaveCommunity: LinearLayout? = null
    private var tc: LinearLayout? = null
    private var privacy: LinearLayout? = null
    private var editProfile: RelativeLayout? = null
    private var deleteAccount: LinearLayout? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        leaveCommunity = view.findViewById(R.id.leave_community)
        tc = view.findViewById(R.id.tc)
        privacy = view.findViewById(R.id.privacy)
        editProfile = view.findViewById(R.id.editProfile)
        deleteAccount = view.findViewById(R.id.delete_account)
        initData()
        return view
    }

    private fun initData() {
        leaveCommunity!!.setOnClickListener {
            startActivity(Intent(context, LeaveCommunityActivity::class.java))
        }

        tc!!.setOnClickListener {
            startActivity(Intent(context, TcActivity::class.java))
        }

        privacy!!.setOnClickListener {
            startActivity(Intent(context, PrivacyPolicyActivity::class.java))
        }

        editProfile!!.setOnClickListener {
            startActivity(Intent(context, EditProfileActivity::class.java))
        }

        deleteAccount!!.setOnClickListener { deleteAccountDialog() }
    }

    fun deleteAccountDialog() {
        var yes: LinearLayout? = null
        var no: LinearLayout? = null
        var close: ImageView? = null
        val dialog = context?.let { Dialog(it) }
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.delete_dialog)
        dialog!!.setCancelable(true)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        yes = dialog.findViewById(R.id.yes)
        no = dialog.findViewById(R.id.no)
        close = dialog.findViewById(R.id.close)
        yes.setOnClickListener { dialog.cancel() }

        no.setOnClickListener {
            dialog.cancel()
        }

        close.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }
}