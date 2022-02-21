package app.gunjan.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import app.gunjan.R
import app.gunjan.twilio.Logger
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.ProjectUtill
import com.bumptech.glide.Glide
import com.twilio.chat.Message
import com.twilio.chat.ProgressListener
import com.twilio.chat.StatusListener
import java.io.*
import java.util.*

class ChatAdapter(
    var context: Context?,
    data: List<Message>?
) :RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    private var data: List<Message>? = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.chat_item, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            if (!FCSharedPreferances.getSharedPreferance(context).useR_ID.equals(data!![position].author)) {
                holder.rightFrame!!.visibility = View.GONE
                holder.leftvideoCard!!.visibility = View.GONE
                holder.rightvideoCard!!.visibility = View.GONE
                holder.rightVideo!!.visibility = View.GONE
                holder.rightProgress!!.visibility = View.GONE
                holder.rightPlay!!.visibility = View.GONE
                holder.rightPause!!.visibility = View.GONE
                holder.rightLayout!!.visibility = View.GONE
                holder.rightMessage!!.visibility = View.GONE
                holder.rightTime!!.visibility = View.GONE
                holder.rightCard!!.visibility = View.GONE
                holder.rightImage!!.visibility = View.GONE

                holder.leftFrame!!.visibility = View.GONE
                holder.leftVideo!!.visibility = View.GONE
                holder.leftProgress!!.visibility = View.GONE
                holder.leftPlay!!.visibility = View.GONE
                holder.leftPause!!.visibility = View.GONE
                holder.leftLayout!!.visibility = View.GONE
                holder.leftMessage!!.visibility = View.GONE
                holder.leftTime!!.visibility = View.GONE
                holder.leftCard!!.visibility = View.GONE
                holder.leftImage!!.visibility = View.GONE

                if (data!![position].hasMedia()) {
                    if (data!![position].media.type.equals("video/mp4", ignoreCase = true)) {
                        holder.rightFrame!!.visibility = View.VISIBLE
                        holder.rightvideoCard!!.visibility = View.VISIBLE
                        holder.rightVideo!!.visibility = View.VISIBLE
                        holder.rightProgress!!.visibility = View.VISIBLE
                        val out = ByteArrayOutputStream()
                        data!![position].media.download(out, object : StatusListener() {
                            override fun onSuccess() {
                                holder.rightProgress!!.visibility = View.GONE
                                val byteArray = out.toByteArray()
                                val someFile = File(
                                    context!!.getExternalFilesDir(null),
                                    data!![position].media.fileName
                                )
                                try {
                                    var fos: FileOutputStream? = null
                                    fos = FileOutputStream(someFile)
                                    fos.write(byteArray)
                                    fos.flush()
                                    fos.close()
                                    // Toast.makeText(context, "success"+someFile.getPath(), Toast.LENGTH_SHORT).show();
                                    val wm =
                                        context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                                    val display = wm.defaultDisplay
                                    val width = display.width
                                    val height = display.height
                                    holder.rightVideo!!.layoutParams =
                                        FrameLayout.LayoutParams(width, height)
                                    holder.rightVideo!!.setVideoPath(someFile.path)
                                    holder.rightPlay!!.setOnClickListener {
                                        holder.rightPlay!!.visibility = View.GONE
                                        holder.rightPause!!.visibility = View.VISIBLE
                                        holder.rightVideo!!.start()
                                    }
                                    holder.rightPause!!.setOnClickListener {
                                        holder.rightPlay!!.visibility = View.VISIBLE
                                        holder.rightPause!!.visibility = View.GONE
                                        holder.rightVideo!!.pause()
                                    }
                                    holder.rightVideo!!.setOnCompletionListener {
                                        holder.rightPlay!!.visibility = View.VISIBLE
                                        holder.rightPause!!.visibility = View.GONE
                                    }
                                    holder.rightVideo!!.setOnPreparedListener {
                                        holder.rightProgress!!.visibility = View.GONE
                                        holder.rightPlay!!.visibility = View.VISIBLE
                                        holder.rightPause!!.visibility = View.GONE
                                    }
                                    holder.rightVideo!!.setOnErrorListener { mediaPlayer, i, i1 -> false }
                                } catch (fileNotFoundException: FileNotFoundException) {
                                    fileNotFoundException.printStackTrace()
                                } catch (ioException: IOException) {
                                    ioException.printStackTrace()
                                }
                            }
                        }, object : ProgressListener() {
                            override fun onStarted() {
                                holder.rightProgress!!.visibility = View.VISIBLE
                            }

                            override fun onProgress(l: Long) {
                                holder.rightProgress!!.visibility = View.VISIBLE
                            }

                            override fun onCompleted(s: String) {
                                holder.rightProgress!!.visibility = View.GONE
                                Logger.show("sssssssss", s)
                            }
                        })
                    } else {
                        holder.rightProgress!!.visibility = View.VISIBLE
                        holder.rightCard!!.visibility = View.VISIBLE
                        holder.rightImage!!.visibility = View.VISIBLE
                        val out = ByteArrayOutputStream()
                        data!![position].media.download(out, object : StatusListener() {
                            override fun onSuccess() {
                                holder.rightProgress!!.visibility = View.GONE
                                val byteArray = out.toByteArray()
                                val someFile = File(
                                    context!!.getExternalFilesDir(null),
                                    data!![position].media.fileName
                                )
                                try {
                                    var fos: FileOutputStream? = null
                                    fos = FileOutputStream(someFile)
                                    fos!!.write(byteArray)
                                    fos!!.flush()
                                    fos!!.close()
                                    //  Toast.makeText(context, "success"+someFile.getPath(), Toast.LENGTH_SHORT).show();
                                    Glide.with(context!!).load(someFile.path)
                                        .placeholder(R.drawable.user_avatar).into(holder.rightImage!!)
                                } catch (fileNotFoundException: FileNotFoundException) {
                                    fileNotFoundException.printStackTrace()
                                } catch (ioException: IOException) {
                                    ioException.printStackTrace()
                                }
                            }
                        }, object : ProgressListener() {
                            override fun onStarted() {
                                holder.rightProgress!!.visibility = View.VISIBLE
                            }

                            override fun onProgress(l: Long) {
                                holder.rightProgress!!.visibility = View.VISIBLE
                            }

                            override fun onCompleted(s: String) {
                                holder.rightProgress!!.visibility = View.GONE
                                Logger.show("sssssssss", s)
                            }
                        })
                    }
                } else {
                    holder.rightLayout!!.visibility = View.VISIBLE
                    holder.rightMessage!!.visibility = View.VISIBLE
                    holder.rightTime!!.visibility = View.VISIBLE
                    holder.rightMessage!!.text = data!![position].messageBody
                    holder.rightTime!!.text = ProjectUtill.DateFormate(data!![position].dateCreatedAsDate.toString())
                }
            } else {
                holder.rightFrame!!.visibility = View.GONE
                holder.rightVideo!!.visibility = View.GONE
                holder.leftvideoCard!!.visibility = View.GONE
                holder.rightvideoCard!!.visibility = View.GONE
                holder.rightProgress!!.visibility = View.GONE
                holder.rightPlay!!.visibility = View.GONE
                holder.rightPause!!.visibility = View.GONE
                holder.rightLayout!!.visibility = View.GONE
                holder.rightMessage!!.visibility = View.GONE
                holder.rightTime!!.visibility = View.GONE
                holder.rightCard!!.visibility = View.GONE
                holder.rightImage!!.visibility = View.GONE
                holder.leftFrame!!.visibility = View.GONE
                holder.leftVideo!!.visibility = View.GONE
                holder.leftProgress!!.visibility = View.GONE
                holder.leftPlay!!.visibility = View.GONE
                holder.leftPause!!.visibility = View.GONE
                holder.leftLayout!!.visibility = View.GONE
                holder.leftMessage!!.visibility = View.GONE
                holder.leftTime!!.visibility = View.GONE
                holder.leftCard!!.visibility = View.GONE
                holder.leftImage!!.visibility = View.GONE
                if (data!![position].hasMedia()) {
                    if (data!![position].media.type.equals("video/mp4", ignoreCase = true)) {
                        holder.leftFrame!!.visibility = View.VISIBLE
                        holder.leftvideoCard!!.visibility = View.VISIBLE
                        holder.leftVideo!!.visibility = View.VISIBLE
                        holder.leftProgress!!.visibility = View.VISIBLE
                        val out = ByteArrayOutputStream()
                        data!![position].media.download(out, object : StatusListener() {
                            override fun onSuccess() {
                                holder.leftProgress!!.visibility = View.GONE
                                val byteArray = out.toByteArray()
                                val someFile = File(
                                    context!!.getExternalFilesDir(null),
                                    data!![position].media.fileName
                                )
                                try {
                                    var fos: FileOutputStream? = null
                                    fos = FileOutputStream(someFile)
                                    fos!!.write(byteArray)
                                    fos!!.flush()
                                    fos!!.close()
                                    // Toast.makeText(context, "success"+someFile.getPath(), Toast.LENGTH_SHORT).show();
                                    val wm =
                                        context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                                    val display = wm.defaultDisplay
                                    val width = display.width
                                    val height = display.height
                                    holder.leftVideo!!.layoutParams =
                                        FrameLayout.LayoutParams(width, height)
                                    holder.leftVideo!!.setVideoPath(someFile.path)
                                    holder.leftPlay!!.setOnClickListener {
                                        holder.leftPlay!!.visibility = View.GONE
                                        holder.leftPause!!.visibility = View.VISIBLE
                                        holder.leftVideo!!.start()
                                    }
                                    holder.leftPause!!.setOnClickListener {
                                        holder.leftPlay!!.visibility = View.VISIBLE
                                        holder.leftPause!!.visibility = View.GONE
                                        holder.leftVideo!!.pause()
                                    }
                                    holder.leftVideo!!.setOnCompletionListener {
                                        holder.leftPlay!!.visibility = View.VISIBLE
                                        holder.leftPause!!.visibility = View.GONE
                                    }
                                    holder.leftVideo!!.setOnPreparedListener {
                                        holder.leftProgress!!.visibility = View.GONE
                                        holder.leftPlay!!.visibility = View.VISIBLE
                                        holder.leftPause!!.visibility = View.GONE
                                    }
                                    holder.leftVideo!!.setOnErrorListener { mediaPlayer, i, i1 -> false }
                                } catch (fileNotFoundException: FileNotFoundException) {
                                    fileNotFoundException.printStackTrace()
                                } catch (ioException: IOException) {
                                    ioException.printStackTrace()
                                }
                            }
                        }, object : ProgressListener() {
                            override fun onStarted() {
                                holder.leftProgress!!.visibility = View.VISIBLE
                            }

                            override fun onProgress(l: Long) {
                                holder.leftProgress!!.visibility = View.VISIBLE
                            }

                            override fun onCompleted(s: String) {
                                holder.leftProgress!!.visibility = View.GONE
                                Logger.show("sssssssss", s)
                            }
                        })
                    } else {
                        holder.leftProgress!!.visibility = View.VISIBLE
                        holder.leftCard!!.visibility = View.VISIBLE
                        holder.leftImage!!.visibility = View.VISIBLE
                        val out = ByteArrayOutputStream()
                        data!![position].media.download(out, object : StatusListener() {
                            override fun onSuccess() {
                                holder.leftProgress!!.visibility = View.GONE
                                val byteArray = out.toByteArray()
                                val someFile = File(
                                    context!!.getExternalFilesDir(null),
                                    data!![position].media.fileName
                                )
                                try {
                                    var fos: FileOutputStream? = null
                                    fos = FileOutputStream(someFile)
                                    fos!!.write(byteArray)
                                    fos!!.flush()
                                    fos!!.close()
                                    //Toast.makeText(context, "success"+someFile.getPath(), Toast.LENGTH_SHORT).show();
                                    Glide.with(context!!).load(someFile.path)
                                        .placeholder(R.drawable.user_avatar).into(holder.leftImage!!)
                                } catch (fileNotFoundException: FileNotFoundException) {
                                    fileNotFoundException.printStackTrace()
                                } catch (ioException: IOException) {
                                    ioException.printStackTrace()
                                }
                            }
                        }, object : ProgressListener() {
                            override fun onStarted() {
                                holder.leftProgress!!.visibility = View.VISIBLE
                            }

                            override fun onProgress(l: Long) {
                                holder.leftProgress!!.visibility = View.VISIBLE
                            }

                            override fun onCompleted(s: String) {
                                holder.leftProgress!!.visibility = View.GONE
                                Logger.show("sssssssss", s)
                            }
                        })
                    }
                } else {
                    holder.leftLayout!!.visibility = View.VISIBLE
                    holder.leftMessage!!.visibility = View.VISIBLE
                    holder.leftTime!!.visibility = View.VISIBLE
                    holder.leftMessage!!.text = data!![position].messageBody
                    holder.leftTime!!.text = ProjectUtill.DateFormate(data!![position].dateCreatedAsDate.toString())
                }
            }
        }catch (e: Exception){}
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var leftLayout: LinearLayout? = null
        var rightLayout: RelativeLayout? = null
        var leftFrame: FrameLayout? = null
        var rightFrame: FrameLayout? = null
        var leftImage: ImageView? = null
        var leftPlay: ImageView? = null
        var rightPlay: ImageView? = null
        var rightPause: ImageView? = null
        var leftPause: ImageView? = null
        var rightImage: ImageView? = null
        var leftVideo: VideoView? = null
        var rightVideo: VideoView? = null
        var leftMessage: TextView? = null
        var leftTime: TextView? = null
        var rightMessage: TextView? = null
        var rightTime: TextView? = null
        var leftProgress: ProgressBar? = null
        var rightProgress: ProgressBar? = null
        var leftCard: CardView? = null
        var rightCard: CardView? = null
        var leftvideoCard: CardView? = null
        var rightvideoCard: CardView? = null
        init {
            leftLayout = itemView.findViewById(R.id.leftLayout)
            leftProgress = itemView.findViewById(R.id.left_progress_bar)
            rightProgress = itemView.findViewById(R.id.right_progress_bar)
            rightLayout = itemView.findViewById(R.id.rightLayout)
            leftMessage = itemView.findViewById(R.id.left_message)
            leftTime = itemView.findViewById(R.id.leftTime)
            rightMessage = itemView.findViewById(R.id.right_message)
            rightTime = itemView.findViewById(R.id.rightTime)
            leftVideo = itemView.findViewById(R.id.left_media_video)
            rightVideo = itemView.findViewById(R.id.right_media_video)
            leftImage = itemView.findViewById(R.id.left_media_pic)
            rightImage = itemView.findViewById(R.id.right_media_pic)
            leftFrame = itemView.findViewById(R.id.left_video_frame)
            rightFrame = itemView.findViewById(R.id.right_video_frame)
            rightPause = itemView.findViewById(R.id.right_pause)
            leftPause = itemView.findViewById(R.id.left_pause)
            rightPlay = itemView.findViewById(R.id.right_play)
            leftPlay = itemView.findViewById(R.id.left_play)
            leftCard = itemView.findViewById(R.id.left_card)
            rightCard = itemView.findViewById(R.id.right_card)
            leftvideoCard = itemView.findViewById(R.id.left_video_card)
            rightvideoCard = itemView.findViewById(R.id.right_video_card)
        }
    }

}