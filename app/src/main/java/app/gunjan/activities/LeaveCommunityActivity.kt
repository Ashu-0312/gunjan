package app.gunjan.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import app.gunjan.R
import kotlinx.android.synthetic.main.activity_leave_community.*

class LeaveCommunityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leave_community)
        initData()
    }

    private fun initData() {
        back.setOnClickListener { finish() }

        Leave.setOnClickListener { leaveCommunityDialog() }
    }

    fun leaveCommunityDialog() {
        var yes: LinearLayout? = null
        var no: LinearLayout? = null
        val dialog = Dialog(this)
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.leave_dialog)
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
        yes.setOnClickListener { dialog.cancel() }

        no.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }
}