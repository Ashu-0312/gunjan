package app.gunjan.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.display.DisplayManager
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.entity.LikeDislikePostResponse
import app.gunjan.entity.OtherUserDetailsResponse
import app.gunjan.fragments.OthersPostFragment
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


class OtherPostsAdapter(
    var context: Context?,
    data: MutableList<OtherUserDetailsResponse.DataBean.PostListBean>,
    othersPostFragment: OthersPostFragment
) : RecyclerView.Adapter<OtherPostsAdapter.ViewHolder>() {
    private var data: MutableList<OtherUserDetailsResponse.DataBean.PostListBean> = data
    private var  othersPostFragment: OthersPostFragment?=othersPostFragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.otherpost_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.name!!.text=data[position].created_by.first_name+" "+data[position].created_by.last_name
            holder.totalComment!!.text=data[position].total_comment.toString()+" Comments"
            holder.totalLike!!.text=data[position].total_like.toString()
            holder.totaldisLike!!.text=data[position].total_unlike.toString()
            context?.let {
                Glide.with(it).load(data[position].created_by.image).placeholder(R.drawable.user_avatar)
                    .into(holder.profile!!)
            }
            if (data[position].content_type == "image") {
                holder.picLayout!!.visibility = View.VISIBLE
                holder.txtLayout!!.visibility = View.GONE
                holder.videoLayout!!.visibility = View.GONE
                holder.videoView!!.visibility = View.GONE
                context?.let {
                    Glide.with(it).load(data[position].file).placeholder(R.drawable.user_avatar)
                        .into(holder.picLayout!!)
                }
            } else if (data[position].content_type == "video") {
                holder.picLayout!!.visibility = View.GONE
                holder.txtLayout!!.visibility = View.GONE
                holder.videoLayout!!.visibility = View.VISIBLE
                holder.videoView!!.visibility = View.VISIBLE
                val display = context!!.getSystemService<DisplayManager>()
                    ?.getDisplay(Display.DEFAULT_DISPLAY)
                val width = display!!.width
                val height = display!!.height
                holder.videoView!!.layoutParams = FrameLayout.LayoutParams(width, height)
                holder.videoView!!.setVideoPath(data[position].file)
            } else if (data[position].content_type == "text") {
                holder.picLayout!!.visibility = View.GONE
                holder.txtLayout!!.visibility = View.VISIBLE
                holder.videoLayout!!.visibility = View.GONE
                holder.videoView!!.visibility = View.GONE
                holder.description!!.text = data[position].description
            }

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
        }catch (e:Exception){}
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
            val shareBodyText = "Gunjan App"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject here")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText)
            context!!.startActivity(sharingIntent)
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

        holder.videoView!!.setOnCompletionListener(MediaPlayer.OnCompletionListener {
            holder.play!!.visibility = View.VISIBLE
            holder.pause!!.visibility = View.GONE
        })

        holder.videoView!!.setOnPreparedListener(MediaPlayer.OnPreparedListener {
            holder.progressBar!!.visibility = View.GONE
            holder.play!!.visibility = View.VISIBLE
            holder.pause!!.visibility = View.GONE
        })

        holder.like!!.setOnClickListener {
            val myDialog = ProjectUtill.showProgressDialog(context)
            context?.let { it1 ->
                WebServiceRequest.getInstance().likeDislikePost(
                    it1,data[position].id.toString(),"love","1",
                    object : Callback<LikeDislikePostResponse> {
                        override fun onResponse(
                            call: Call<LikeDislikePostResponse>,
                            response: Response<LikeDislikePostResponse>
                        ) {
                            myDialog.dismiss()
                            if (response != null) {
                                if (response.isSuccessful) {
                                    if (response.body()!!.code == 1) {
                                        holder.totalLike!!.text=response.body()!!.data.post.total_like.toString()
                                        holder.totaldisLike!!.text=response.body()!!.data.post.total_unlike.toString()
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
                    it1,data[position].id.toString(),"love","0",
                    object : Callback<LikeDislikePostResponse> {
                        override fun onResponse(
                            call: Call<LikeDislikePostResponse>,
                            response: Response<LikeDislikePostResponse>
                        ) {
                            myDialog.dismiss()
                            if (response != null) {
                                if (response.isSuccessful) {
                                    if (response.body()!!.code == 1) {
                                        holder.totalLike!!.text=response.body()!!.data.post.total_like.toString()
                                        holder.totaldisLike!!.text=response.body()!!.data.post.total_unlike.toString()
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
            othersPostFragment!!.commentsDialog(data[position].id.toString())
        }

        holder.commentLayout2!!.setOnClickListener {
            othersPostFragment!!.commentsDialog(data[position].id.toString())
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var description: TextView? =null
        var showMore: TextView? =null
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
        var postTime: TextView? =null
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
            postTime=itemView.findViewById(R.id.time)
        }
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

}