package app.gunjan.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.display.DisplayManager
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaPlayer.OnPreparedListener
import android.os.Build
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.activities.OthersProfileActivity
import app.gunjan.activities.PostListResponse
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
import kotlin.collections.ArrayList


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
        }catch (e:Exception){}
        holder.showMore!!.setOnClickListener(View.OnClickListener {
            if (holder.showMore!!.text.toString() == "Showmore...") {
                holder.description!!.maxLines = Int.MAX_VALUE //your TextView
                holder.showMore!!.text = "Showless"
            } else {
                holder.description!!.maxLines = 4 //your TextView
                holder.showMore!!.text = "Showmore..."
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

        holder.menu!!.setOnClickListener {
            homeFragment.postreportDialog(data[position].created_by.id.toString())
        }

        holder.profile!!.setOnClickListener {
            if(data[position].created_by.id.toString()!=FCSharedPreferances.getSharedPreferance(context).useR_ID) {
                FCSharedPreferances.getSharedPreferance(context).otheR_ID=data[position].created_by.id.toString()
                context!!.startActivity(Intent(context, OthersProfileActivity::class.java))
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
            homeFragment.commentsDialog(data[position].id.toString())
        }

        holder.commentLayout2!!.setOnClickListener {
            homeFragment.commentsDialog(data[position].id.toString())
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
        }
    }

}