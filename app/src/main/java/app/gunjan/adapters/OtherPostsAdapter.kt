package app.gunjan.adapters

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.activities.OthersProfileActivity
import java.util.*


class OtherPostsAdapter(
    var context: Context?,
    data: ArrayList<String>
) : RecyclerView.Adapter<OtherPostsAdapter.ViewHolder>() {
    private var data: ArrayList<String> = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.otherpost_item, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position==0){
            holder.picLayout!!.visibility=View.VISIBLE
            holder.txtLayout!!.visibility=View.GONE
            holder.videoLayout!!.visibility=View.GONE
            holder.videoView!!.visibility=View.GONE
        }else if (position==3){
            holder.picLayout!!.visibility=View.GONE
            holder.txtLayout!!.visibility=View.GONE
            holder.videoLayout!!.visibility=View.VISIBLE
            holder.videoView!!.visibility=View.VISIBLE
            val wm = context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val width = display.width
            val height = display.height
            holder.videoView!!.layoutParams = FrameLayout.LayoutParams(width, height)
            holder.videoView!!.setVideoPath("http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4")
        }else{
            holder.picLayout!!.visibility=View.GONE
            holder.txtLayout!!.visibility=View.VISIBLE
            holder.videoLayout!!.visibility=View.GONE
            holder.videoView!!.visibility=View.GONE
        }
        holder.showMore!!.setOnClickListener(View.OnClickListener {
            if (holder.showMore!!.getText().toString().equals("Showmore...")) {
                holder.description!!.setMaxLines(Int.MAX_VALUE) //your TextView
                holder.showMore!!.text = "Showless"
            } else {
                holder.description!!.setMaxLines(3) //your TextView
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
            (context as OthersProfileActivity).postreportDialog()
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
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var description: TextView? =null
        var showMore: TextView? =null
        var share: LinearLayout? =null
        var menu: ImageView? =null
        var txtLayout: LinearLayout? =null
        var picLayout: ImageView? =null
        var videoLayout: FrameLayout? =null
        var videoView: VideoView? =null
        var play: ImageView? =null
        var pause: ImageView? =null
        var progressBar: ProgressBar? =null
        init {
            description=itemView.findViewById(R.id.description)
            showMore=itemView.findViewById(R.id.show_more)
            share=itemView.findViewById(R.id.share)
            menu=itemView.findViewById(R.id.menu)
            txtLayout=itemView.findViewById(R.id.txt_layout)
            picLayout=itemView.findViewById(R.id.pic_layout)
            videoLayout=itemView.findViewById(R.id.video_layout)
            videoView=itemView.findViewById(R.id.media_video)
            play=itemView.findViewById(R.id.play)
            pause=itemView.findViewById(R.id.pause)
            progressBar=itemView.findViewById(R.id.progress_bar)
        }
    }

}