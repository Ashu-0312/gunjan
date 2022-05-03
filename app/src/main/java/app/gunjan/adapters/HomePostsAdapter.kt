package app.gunjan.adapters

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.hardware.display.DisplayManager
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaPlayer.OnPreparedListener
import android.os.Build
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.activities.HomeActivity
import app.gunjan.activities.JoinedEventUserListActivity
import app.gunjan.activities.OthersProfileActivity
import app.gunjan.activities.PostListResponse
import app.gunjan.entity.JoinEventResponse
import app.gunjan.entity.LikeDislikePostResponse
import app.gunjan.fragments.HomeFragment
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class HomePostsAdapter(
    var context: Context?,
    data: ArrayList<PostListResponse.DataBean.PostBean>,
    homeFragment: HomeFragment
) : RecyclerView.Adapter<HomePostsAdapter.ViewHolder>() {
    private var data: ArrayList<PostListResponse.DataBean.PostBean> = data
    private var  homeFragment: HomeFragment=homeFragment
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.homepost_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.name!!.text=data[position].created_by.first_name+" "+data[position].created_by.last_name
            holder.totalComment!!.text=data[position].total_comment.toString()+context!!.getString(R.string.commentss)
            holder.totalLike!!.text=data[position].total_like.toString()
            holder.totaldisLike!!.text=data[position].total_unlike.toString()

            if (data[position].isJoinedThisEvent){
                holder.joinTxt!!.text = context!!.getString(R.string.joined)
            }else{
                holder.joinTxt!!.text = context!!.getString(R.string.join_event)
            }

            holder.totalUsers!!.text = data[position].total_joined_member+" "+context!!.getString(R.string._0_users_joined)


            val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val output = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

            var d: Date? = null
            try {
                d = input.parse(data[position].createdAt)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val formatted = output.format(d)
            Log.i("DATE", "" + formatted)

            holder.postTime!!.text=convertTimeToText(formatted)
            context?.let {
                Glide.with(it).load(data[position].created_by.image).placeholder(R.drawable.user_avatar)
                    .into(holder.profile!!)
            }

            if (data[position].feed_type=="event"){
                holder.eventLayout!!.visibility=View.VISIBLE
                holder.joinedLayout!!.visibility=View.VISIBLE
                holder.joinLayout!!.visibility=View.VISIBLE
                var format = SimpleDateFormat("yyyy-MM-dd")
                val date1 = format.parse(data[position].start_date)
                val date2 = format.format(date1)

                format =
                    if (date2.endsWith("01") && !date2.endsWith("11")) SimpleDateFormat("d'st'") else if (date2.endsWith(
                            "02"
                        ) && !date2.endsWith("12")
                    ) SimpleDateFormat("d'nd'") else if (date2.endsWith("03") && !date2.endsWith("13")) SimpleDateFormat(
                        "d'rd'"
                    ) else SimpleDateFormat("d'th'")

                val yourDate = format.format(date1)
                holder.day!!.text = yourDate

                var format2 = SimpleDateFormat("yyyy-MM-dd")
                val date3 = format2.parse(data[position].start_date)
                val date4 = format2.format(date3)

                format2 =
                    if (date4.endsWith("01") && !date4.endsWith("11")) SimpleDateFormat("MMM") else if (date4.endsWith(
                            "02"
                        ) && !date4.endsWith("12")
                    ) SimpleDateFormat("MMM") else if (date4.endsWith("03") && !date4.endsWith("13")) SimpleDateFormat(
                        "MMM"
                    ) else SimpleDateFormat("MMM")

                val yourMonth = format2.format(date3)
                holder.month!!.text = yourMonth

                val tk =
                    StringTokenizer(data[position].start_date.toString() + " " + data[position].start_time)
                val date = tk.nextToken()
                val time = tk.nextToken()

                val sdf = SimpleDateFormat("hh:mm:ss")
                val sdfs = SimpleDateFormat("hh:mmaa")
                val dt: Date
                try {
                    dt = sdf.parse(time)
                    holder.time!!.text = sdfs.format(dt)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }else{
                holder.eventLayout!!.visibility=View.GONE
                holder.joinedLayout!!.visibility=View.GONE
                holder.joinLayout!!.visibility=View.GONE
            }

            if (data[position].content_type == "image") {
                holder.picLayout!!.visibility = View.VISIBLE
                holder.txtLayout!!.visibility = View.VISIBLE
                holder.videoLayout!!.visibility = View.GONE
                holder.videoView!!.visibility = View.GONE
                context?.let {
                    Glide.with(it).load(data[position].file).placeholder(R.drawable.user_avatar)
                        .into(holder.picLayout!!)
                }
                holder.description!!.text = data[position].description
            } else if (data[position].content_type == "video") {
                holder.picLayout!!.visibility = View.GONE
                holder.txtLayout!!.visibility = View.VISIBLE
                holder.videoLayout!!.visibility = View.VISIBLE
                holder.videoView!!.visibility = View.VISIBLE
                val display = context!!.getSystemService<DisplayManager>()
                    ?.getDisplay(Display.DEFAULT_DISPLAY)
                val width = display!!.width
                val height = display!!.height
                holder.videoView!!.layoutParams = FrameLayout.LayoutParams(width, height)
                holder.videoView!!.setVideoPath(data[position].file)
                holder.description!!.text = data[position].description
            } else if (data[position].content_type == "text") {
                holder.picLayout!!.visibility = View.GONE
                holder.txtLayout!!.visibility = View.VISIBLE
                holder.videoLayout!!.visibility = View.GONE
                holder.videoView!!.visibility = View.GONE
                holder.description!!.text = data[position].description
            }
        }catch (e: Exception){}
        holder.showMore!!.setOnClickListener(View.OnClickListener {
            if (holder.showMore!!.text.toString() == context!!.getString(R.string.showmore)) {
                holder.description!!.maxLines = Int.MAX_VALUE //your TextView
                holder.showMore!!.text = context!!.getString(R.string.showless)
            } else {
                holder.description!!.maxLines = 4 //your TextView
                holder.showMore!!.text = context!!.getString(R.string.showmore)
            }
        })

        holder.share!!.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBodyText = "Download this App"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject here")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText)
            context!!.startActivity(sharingIntent)
        }

        holder.menu!!.setOnClickListener {

            if (data[position].created_by.id.toString() == FCSharedPreferances.getSharedPreferance(
                    context
                ).useR_ID) {
                val popup = PopupMenu(context, holder.menu)
                //inflating menu from xml resource
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu2)
                popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

                    when (item!!.itemId) {
                        R.id.copy -> {
                            copyText(holder.description!!.text.toString().trim())
                        }
                    }

                    true
                })

                popup.show()
            } else {
                val popup = PopupMenu(context, holder.menu)
                //inflating menu from xml resource
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu)
                popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

                    when (item!!.itemId) {
                        R.id.block -> {
                            homeFragment!!.blockDialog(data[position].created_by.id.toString())
                        }
                        R.id.copy -> {
                            copyText(holder.description!!.text.toString().trim())
                        }
                        R.id.report -> {
                            homeFragment!!.reportDialog(data[position].created_by.id.toString())
                        }
                    }

                    true
                })

                popup.show()
            }
        }

        holder.profile!!.setOnClickListener {
            if(data[position].created_by.id.toString()!=FCSharedPreferances.getSharedPreferance(
                    context
                ).useR_ID) {
                FCSharedPreferances.getSharedPreferance(context).otheR_ID=data[position].created_by.id.toString()
                context!!.startActivity(Intent(context, OthersProfileActivity::class.java))
            }else{
                FCSharedPreferances.getSharedPreferance(context).status =
                    "edit"
                FCSharedPreferances.getSharedPreferance(context).otheR_ID=data[position].created_by.id.toString()
                var intent = Intent(
                    context,
                    HomeActivity::class.java
                )
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context!!.startActivity(intent)
            }
        }

        holder.play!!.setOnClickListener {
            holder.play!!.visibility = View.GONE
            holder.pause!!.visibility = View.VISIBLE
            holder.videoView!!.start()
        }

        holder.pause!!.setOnClickListener {
            holder.play!!.visibility = View.VISIBLE
            holder.pause!!.visibility = View.GONE
            holder.videoView!!.pause()
        }

        holder.videoView!!.setOnCompletionListener(OnCompletionListener {
            holder.play!!.visibility = View.VISIBLE
            holder.pause!!.visibility = View.GONE
        })

        holder.videoView!!.setOnPreparedListener(OnPreparedListener {
            holder.progressBar!!.visibility = View.GONE
            holder.play!!.visibility = View.VISIBLE
            holder.pause!!.visibility = View.GONE
        })

        holder.like!!.setOnClickListener {
            val myDialog = ProjectUtill.showProgressDialog(context)
            context?.let { it1 ->
                WebServiceRequest.getInstance().likeDislikePost(
                    it1, data[position].id.toString(), "love", "1",
                    object : Callback<LikeDislikePostResponse> {
                        override fun onResponse(
                            call: Call<LikeDislikePostResponse>,
                            response: Response<LikeDislikePostResponse>
                        ) {
                            myDialog.dismiss()
                            if (response != null) {
                                if (response.isSuccessful) {
                                    if (response.body()!!.code == 1) {
                                        holder.totalLike!!.text =
                                            response.body()!!.data.post.total_like.toString()
                                        holder.totaldisLike!!.text =
                                            response.body()!!.data.post.total_unlike.toString()
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
                            call: Call<LikeDislikePostResponse>,
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

        holder.dislike!!.setOnClickListener {
            val myDialog = ProjectUtill.showProgressDialog(context)
            context?.let { it1 ->
                WebServiceRequest.getInstance().likeDislikePost(
                    it1, data[position].id.toString(), "love", "0",
                    object : Callback<LikeDislikePostResponse> {
                        override fun onResponse(
                            call: Call<LikeDislikePostResponse>,
                            response: Response<LikeDislikePostResponse>
                        ) {
                            myDialog.dismiss()
                            if (response != null) {
                                if (response.isSuccessful) {
                                    if (response.body()!!.code == 1) {
                                        holder.totalLike!!.text =
                                            response.body()!!.data.post.total_like.toString()
                                        holder.totaldisLike!!.text =
                                            response.body()!!.data.post.total_unlike.toString()
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
                            call: Call<LikeDislikePostResponse>,
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

        holder.commentLayout!!.setOnClickListener {
            homeFragment.commentsDialog(data[position].id.toString())
        }

        holder.commentLayout2!!.setOnClickListener {
            homeFragment.commentsDialog(data[position].id.toString())
        }

        holder.joinedLayout!!.setOnClickListener {
            var intent = Intent(context,JoinedEventUserListActivity::class.java)
            intent.putExtra("id",data[position].id.toString())
            context!!.startActivity(intent)
        }

        holder.joinLayout!!.setOnClickListener {
            if (!data[position].isJoinedThisEvent) {
                val myDialog = ProjectUtill.showProgressDialog(context)
                context?.let { it1 ->
                    WebServiceRequest.getInstance().joinEvent(
                        it1, data[position].id.toString(),
                        object : Callback<JoinEventResponse> {
                            override fun onResponse(
                                call: Call<JoinEventResponse>,
                                response: Response<JoinEventResponse>
                            ) {
                                myDialog.dismiss()
                                if (response != null) {
                                    if (response.isSuccessful) {
                                        if (response.body()!!.code == 1) {
                                            holder.joinTxt!!.text = context!!.getString(R.string.joined)
                                            data[position].isJoinedThisEvent=true
                                            holder.totalUsers!!.text = response.body()!!.data.total_member+" "+context!!.getString(R.string._0_users_joined)
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
                                call: Call<JoinEventResponse>,
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

        holder.picLayout!!.setOnClickListener {
            if (data[position].content_type=="image"){
                homeFragment!!.showMedia(
                    data[position].file,
                    data[position].content_type,
                )
            }else{
                Log.d("","")
            }
        }

        holder.videoLayout!!.setOnClickListener {
            if (data[position].content_type=="video"){
                homeFragment!!.showMedia(
                    data[position].file,
                    data[position].content_type,
                )
            }else{
                Log.d("","")
            }
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    fun convertTimeToText(dataDate: String?): String? {
        var convTime: String? = null
        val prefix = ""
        val suffix = context!!.getString(R.string.ago)
        try {
            val dateFormat = SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss"
            )
            val oldDate: Date = dateFormat.parse(dataDate)
            val nowTime = Date()
            val dateDiff = nowTime.time - oldDate.time-19800000
            val second: Long = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
            val minute: Long = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
            val hour: Long = TimeUnit.MILLISECONDS.toHours(dateDiff)
            val day: Long = TimeUnit.MILLISECONDS.toDays(dateDiff)
            if (second < 60) {
                convTime = second.toString()+ " "+context!!.getString(R.string.seconds)+" "+suffix
            } else if (minute < 60) {
                convTime = minute.toString()+ " "+context!!.getString(R.string.minutes)+" "+suffix
            } else if (hour < 24) {
                convTime = hour.toString()+ " "+context!!.getString(R.string.hours)+" "+suffix
            } else if (day >= 7) {
                convTime = if (day > 360) {
                    (day / 360).toString() + " "+context!!.getString(R.string.years)+" "+ suffix
                } else if (day > 30) {
                    (day / 30).toString() + " "+context!!.getString(R.string.months)+" "+ suffix
                } else {
                    (day / 7).toString() + " "+context!!.getString(R.string.weeks)+" "+ suffix
                }
            } else if (day < 7) {
                convTime = day.toString() + " "+context!!.getString(R.string.days)+" "+ suffix
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            Log.e("ConvTimeE", e.message!!)
        }
        return convTime
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var description: TextView? =null
        var showMore: TextView? =null
        var postTime: TextView? =null
        var name: TextView? =null
        var totalComment: TextView? =null
        var totalLike: TextView? =null
        var totaldisLike: TextView? =null
        var like: ImageView? =null
        var dislike: ImageView? =null
        var share: LinearLayout? =null
        var menu: ImageView? =null
        var profile: CircleImageView? =null
        var txtLayout: LinearLayout? =null
        var picLayout: ImageView? =null
        var videoLayout: FrameLayout? =null
        var videoView: VideoView? =null
        var play: ImageView? =null
        var pause: ImageView? =null
        var progressBar: ProgressBar? =null
        var commentLayout: LinearLayout? =null
        var commentLayout2: LinearLayout? =null
        var eventLayout: LinearLayout? =null
        var joinLayout: LinearLayout? =null
        var joinedLayout: LinearLayout? =null
        var day: TextView? = null
        var month: TextView? = null
        var time: TextView? = null
        var totalUsers: TextView? = null
        var joinTxt: TextView? = null
        init {
            description=itemView.findViewById(R.id.description)
            showMore=itemView.findViewById(R.id.show_more)
            share=itemView.findViewById(R.id.share)
            menu=itemView.findViewById(R.id.menu)
            profile=itemView.findViewById(R.id.user_profile)
            txtLayout=itemView.findViewById(R.id.txt_layout)
            picLayout=itemView.findViewById(R.id.pic_layout)
            videoLayout=itemView.findViewById(R.id.video_layout)
            videoView=itemView.findViewById(R.id.media_video)
            play=itemView.findViewById(R.id.play)
            pause=itemView.findViewById(R.id.pause)
            progressBar=itemView.findViewById(R.id.progress_bar)
            name=itemView.findViewById(R.id.name)
            totalComment=itemView.findViewById(R.id.total_comment)
            totalLike=itemView.findViewById(R.id.total_like)
            totaldisLike=itemView.findViewById(R.id.total_dislike)
            like=itemView.findViewById(R.id.like)
            dislike=itemView.findViewById(R.id.dislike)
            commentLayout=itemView.findViewById(R.id.comment_layout)
            commentLayout2=itemView.findViewById(R.id.comment_layout2)
            eventLayout=itemView.findViewById(R.id.event_layout)
            postTime=itemView.findViewById(R.id.time)
            day = itemView.findViewById(R.id.activity_day)
            month = itemView.findViewById(R.id.activity_month)
            time = itemView.findViewById(R.id.activity_time)
            joinedLayout = itemView.findViewById(R.id.joined_event)
            joinLayout = itemView.findViewById(R.id.join_event)
            totalUsers = itemView.findViewById(R.id.total_users)
            joinTxt = itemView.findViewById(R.id.join_txt)
        }
    }

    fun copyText(text:String){
        val myClipboard = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val myClip: ClipData = ClipData.newPlainText("Label", text)
        myClipboard.setPrimaryClip(myClip)
    }
}