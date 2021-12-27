package app.gunjan.fragments

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.adapters.HomePostsAdapter
import app.gunjan.adapters.ReasonList2Adapter
import de.hdodenhof.circleimageview.CircleImageView

class HomeFragment : Fragment() {
    private var animShow: Animation? = null
    private var list:ArrayList<String> = ArrayList<String>()
    private var reasonList:ArrayList<String> = ArrayList<String>()
    private var postRecycler:RecyclerView?=null
    private var reasonLayout:LinearLayout?=null
    private var showDescription:CircleImageView?=null
    private var share:ImageView?=null
    private var invite:LinearLayout?=null
    private var discuss:LinearLayout?=null
    private var trending:LinearLayout?=null
    private var announce:LinearLayout?=null
    private var event:LinearLayout?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        postRecycler=view.findViewById(R.id.post_recycler)
        showDescription=view.findViewById(R.id.show_description)
        share=view.findViewById(R.id.whatsapp)
        invite=view.findViewById(R.id.invite)
        discuss=view.findViewById(R.id.discussion)
        trending=view.findViewById(R.id.trending)
        announce=view.findViewById(R.id.announce)
        event=view.findViewById(R.id.event)
        initData()
        return view
    }

    private fun initData() {
        animShow = AnimationUtils.loadAnimation(context, R.anim.move_right_in_activity)
        reasonList.add("Spam")
        reasonList.add("Abusive Language")
        reasonList.add("Fake Post")
        reasonList.add("Hate Speech")
        reasonList.add("Obscene Post")
        reasonList.add("Other")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        var postAdapter = HomePostsAdapter(
            context, list,this@HomeFragment
        )
        var layoutManager: LinearLayoutManager? = LinearLayoutManager(context)
        postRecycler!!.layoutManager = layoutManager
        postRecycler!!.adapter = postAdapter

        showDescription!!.setOnClickListener { communityDescriptionDialog() }

        share!!.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBodyText = "Gunjan App"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject here")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText)
            startActivity(sharingIntent)
        }

        invite!!.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBodyText = "Gunjan App"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject here")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText)
            startActivity(sharingIntent)
        }

        discuss!!.setOnClickListener {
            discuss!!.background=resources.getDrawable(R.drawable.pink_border2)
            trending!!.background=resources.getDrawable(R.drawable.edittext_bg)
            announce!!.background=resources.getDrawable(R.drawable.edittext_bg)
            event!!.background=resources.getDrawable(R.drawable.edittext_bg)
        }

        trending!!.setOnClickListener {
            trending!!.background=resources.getDrawable(R.drawable.pink_border2)
            discuss!!.background=resources.getDrawable(R.drawable.edittext_bg)
            announce!!.background=resources.getDrawable(R.drawable.edittext_bg)
            event!!.background=resources.getDrawable(R.drawable.edittext_bg)
        }

        announce!!.setOnClickListener {
            announce!!.background=resources.getDrawable(R.drawable.pink_border2)
            discuss!!.background=resources.getDrawable(R.drawable.edittext_bg)
            trending!!.background=resources.getDrawable(R.drawable.edittext_bg)
            event!!.background=resources.getDrawable(R.drawable.edittext_bg)
        }

        event!!.setOnClickListener {
            event!!.background=resources.getDrawable(R.drawable.pink_border2)
            trending!!.background=resources.getDrawable(R.drawable.edittext_bg)
            announce!!.background=resources.getDrawable(R.drawable.edittext_bg)
            discuss!!.background=resources.getDrawable(R.drawable.edittext_bg)
        }
    }
    fun blockDialog() {
        var yes: LinearLayout? = null
        var no: LinearLayout? = null
        var close: ImageView? = null
        val dialog = context?.let { Dialog(it) }
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.block_dialog)
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

    fun reportDialog() {
        var yes: LinearLayout? = null
        var no: LinearLayout? = null
        var close: ImageView? = null
        var reasonRecycler: RecyclerView? = null
        val dialog = context?.let { Dialog(it) }
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.report_dialog)
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
        reasonRecycler = dialog.findViewById(R.id.reason_recycler)
        close = dialog.findViewById(R.id.close)
        reasonLayout = dialog.findViewById(R.id.reasonLayout)
        var reasonAdapter = ReasonList2Adapter(
            context, reasonList,this@HomeFragment
        )
        var layoutManager: LinearLayoutManager? = LinearLayoutManager(context)
        reasonRecycler!!.layoutManager = layoutManager
        reasonRecycler!!.adapter = reasonAdapter
        yes.setOnClickListener { dialog.cancel() }

        no.setOnClickListener {
            dialog.cancel()
        }

        close.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }

    fun postreportDialog() {
        var close: ImageView? = null
        var report: RelativeLayout? = null
        var copyPost: RelativeLayout? = null
        var block: RelativeLayout? = null
        val dialog = context?.let { Dialog(it) }
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.postreport_dialog)
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
        report = dialog.findViewById(R.id.report)
        copyPost = dialog.findViewById(R.id.copy_post)
        block = dialog.findViewById(R.id.block)

        close.setOnClickListener {
            dialog.cancel()
        }

        report.setOnClickListener { reportDialog() }

        block.setOnClickListener {
            blockDialog()
        }
        dialog.show()
    }

    fun showReasonLayout(status:String){
        if (status.equals("1")) {
            reasonLayout!!.visibility = View.VISIBLE
            reasonLayout!!.startAnimation(animShow)
        }else{
            reasonLayout!!.visibility = View.GONE
        }
    }

    fun communityDescriptionDialog() {
        var close: ImageView? = null
        val dialog = context?.let { Dialog(it) }
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.communitydescription_dialog)
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

        close.setOnClickListener {
            dialog.cancel()
        }

        dialog.show()
    }
}