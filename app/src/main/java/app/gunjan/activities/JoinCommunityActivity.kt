package app.gunjan.activities

import android.app.Dialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import app.gunjan.R
import kotlinx.android.synthetic.main.activity_join_community.*

class JoinCommunityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_community)
        initData()
    }

    private fun initData() {
        back.setOnClickListener { finish() }

        Join.setOnClickListener {
           joinDialog()
        }
    }

    fun joinDialog() {
        var apply: LinearLayout?=null
        var close: ImageView?=null
                val dialog = Dialog(this)
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.joincommunity_dialog)
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
        close.setOnClickListener { dialog.cancel() }

        apply.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }

}