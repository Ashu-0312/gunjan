package app.gunjan.activities

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.display.DisplayManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import app.gunjan.R
import app.gunjan.entity.AddPostResponse
import app.gunjan.entity.PrivacyPolicyResponse
import app.gunjan.utill.PermissionUtil
import app.gunjan.utill.ProjectUtill
import app.gunjan.utill.ProjectUtill.getPath
import app.gunjan.utill.UploadFileListener
import app.gunjan.webservices.WebServiceRequest
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.mobileconnectors.s3.transferutility.UploadOptions
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_add_post.*
import kotlinx.android.synthetic.main.activity_add_post.back
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.util.*

class AddPostActivity : AppCompatActivity(),UploadFileListener {
    private var pathPic = ""
    var path: String? = null
    var progressdialog: ProgressDialog?=null
    private var awsPicUrl = ""
    private var animShow: Animation? = null
    private var type:String="text"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)
        initData()
    }

    private fun initData() {
        progressdialog = ProgressDialog(this, R.style.MyAlertDialogStyle)
        progressdialog!!.setCancelable(false)
        animShow = AnimationUtils.loadAnimation(this, R.anim.move_right_in_activity)
        back.setOnClickListener { finish() }

        textPost.setOnClickListener {
            awsPicUrl=""
            type="text"
            textPost.background=resources.getDrawable(R.drawable.button_bg)
            mediaPost.background=resources.getDrawable(R.drawable.unselected_bg)
            mediaLayout.visibility = View.GONE
            txtLayout.visibility = View.VISIBLE
            txtLayout!!.startAnimation(animShow)
        }

        mediaPost.setOnClickListener {
            type="image"
            mediaPost.background=resources.getDrawable(R.drawable.button_bg)
            textPost.background=resources.getDrawable(R.drawable.unselected_bg)
            txtLayout.visibility = View.GONE
            addMedia.visibility = View.VISIBLE
            postPic.visibility = View.GONE
            videoFrame.visibility = View.GONE
            videoView.visibility = View.GONE
            play.visibility = View.GONE
            pause.visibility = View.GONE
            mediaLayout.visibility = View.VISIBLE
            mediaLayout!!.startAnimation(animShow)
        }

        mediaLayout.setOnClickListener {
            chooseMediaDialog()
        }

        play.setOnClickListener {
            play.visibility = View.GONE
            pause.visibility = View.VISIBLE
            videoView.start()
        }

        pause.setOnClickListener {
            play.visibility = View.VISIBLE
            pause.visibility = View.GONE
            videoView.pause()
        }

        videoView.setOnCompletionListener {
            play.visibility = View.VISIBLE
            pause.visibility = View.GONE
        }

        videoView.setOnPreparedListener {
            play.visibility = View.VISIBLE
            pause.visibility = View.GONE
        }

        Post.setOnClickListener {
            if (type=="text"){
                if (edtPost.text.toString().trim() == ""){
                    Toast.makeText(this,"Please write about your post",Toast.LENGTH_LONG).show()
                }else{
                    val myDialog = ProjectUtill.showProgressDialog(this@AddPostActivity)
                    WebServiceRequest.getInstance().addPost(
                        this,edtPost.text.toString().trim(),"",type,
                        object : Callback<AddPostResponse> {
                            override fun onResponse(
                                call: Call<AddPostResponse>,
                                response: Response<AddPostResponse>
                            ) {
                                myDialog.dismiss()
                                if (response != null) {
                                    if (response.isSuccessful) {
                                        if (response.body()!!.code == 1) {
                                            Toast.makeText(this@AddPostActivity,""+response.body()!!.message,Toast.LENGTH_LONG).show()
                                            var intent = Intent(this@AddPostActivity,HomeActivity::class.java)
                                            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            startActivity(intent)
                                        } else {
                                            ProjectUtill.printMessage(
                                                this@AddPostActivity.window.decorView,
                                                response.body()?.message
                                            )
                                        }
                                    } else {
                                        ProjectUtill.printErrorMessage(
                                            this@AddPostActivity.window.decorView,
                                            ""
                                        )
                                    }
                                } else {
                                    ProjectUtill.printErrorMessage(
                                        this@AddPostActivity.window.decorView,
                                        ""
                                    )
                                }
                            }

                            override fun onFailure(
                                call: Call<AddPostResponse>,
                                t: Throwable
                            ) {
                                myDialog.dismiss()
                                ProjectUtill.printErrorMessage(
                                    this@AddPostActivity.window.decorView,
                                    ""
                                )
                            }
                        })
                }
            }else   if (type=="image" || type=="video"){
                if (awsPicUrl == ""){
                    Toast.makeText(this,"Please choose media",Toast.LENGTH_LONG).show()
                }else{
                    val myDialog = ProjectUtill.showProgressDialog(this@AddPostActivity)
                    WebServiceRequest.getInstance().addPost(
                        this,"",awsPicUrl,type,
                        object : Callback<AddPostResponse> {
                            override fun onResponse(
                                call: Call<AddPostResponse>,
                                response: Response<AddPostResponse>
                            ) {
                                myDialog.dismiss()
                                if (response != null) {
                                    if (response.isSuccessful) {
                                        if (response.body()!!.code == 1) {
                                            Toast.makeText(this@AddPostActivity,""+response.body()!!.message,Toast.LENGTH_LONG).show()
                                            var intent = Intent(this@AddPostActivity,HomeActivity::class.java)
                                            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            startActivity(intent)
                                        } else {
                                            ProjectUtill.printMessage(
                                                this@AddPostActivity.window.decorView,
                                                response.body()?.message
                                            )
                                        }
                                    } else {
                                        ProjectUtill.printErrorMessage(
                                            this@AddPostActivity.window.decorView,
                                            ""
                                        )
                                    }
                                } else {
                                    ProjectUtill.printErrorMessage(
                                        this@AddPostActivity.window.decorView,
                                        ""
                                    )
                                }
                            }

                            override fun onFailure(
                                call: Call<AddPostResponse>,
                                t: Throwable
                            ) {
                                myDialog.dismiss()
                                ProjectUtill.printErrorMessage(
                                    this@AddPostActivity.window.decorView,
                                    ""
                                )
                            }
                        })
                }
            }
        }
    }

    private fun chooseMediaDialog() {
        val gallery: RelativeLayout
        val video: RelativeLayout
        val captureVideo: RelativeLayout
        val capturePic: RelativeLayout
        val close: ImageView
        val dialog = Dialog(this)
        // Include dialog.xml file
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.selectfile_dialog)
        dialog.setCancelable(true)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.FILL_PARENT,
            WindowManager.LayoutParams.FILL_PARENT
        )
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        gallery = dialog.findViewById(R.id.rl_layout)
        video = dialog.findViewById(R.id.rl_layout1)
        captureVideo = dialog.findViewById(R.id.rl_layout2)
        capturePic = dialog.findViewById(R.id.rl_layout3)
        close = dialog.findViewById(R.id.close)
        gallery.setOnClickListener {
            if (checkPicturePermission()) {
                dialog.cancel()
                type="image"
                val pickPhoto = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(pickPhoto, 1)
            }
        }
        video.setOnClickListener {
            if (checkPicturePermission()) {
                type="video"
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 0)
                dialog.cancel()
            }
        }
        captureVideo.setOnClickListener {
            if (checkPicturePermission()) {
                type="video"
                val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30)
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 3)
                startActivityForResult(intent, 3)
                dialog.cancel()
            }
        }
        capturePic.setOnClickListener {
            if (checkPicturePermission()) {
                type="image"
                dialog.cancel()
                val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePicture, 4)
            }

        }
        close.setOnClickListener { dialog.cancel() }
        dialog.show()
    }

    private fun checkPicturePermission(): Boolean {
        return if (PermissionUtil.verifyPermissions(this, PermissionUtil.getCameraPermissions())) {
            true
        } else {
            PermissionUtil.requestPermission(
                PermissionUtil.getCameraPermissions(),
                this
            )
            false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                1 -> {
                    val selectedImage = data!!.data
                    pathPic = getPath(this, selectedImage)
                    progressdialog!!.setCancelable(false)
                    progressdialog!!.show()
                    uploadFile(File(pathPic), this, this)
                }
                0 -> {
                    val selectedImage1 = data!!.data
                    pathPic = ProjectUtill.getPath(this, selectedImage1)
                    val mp: MediaPlayer = MediaPlayer.create(this, Uri.parse(pathPic))
                    val duration = mp.duration
                    mp.release()

                    if (duration / 1000 > 20) {
                        Toast.makeText(this, R.string.bigger_video, Toast.LENGTH_SHORT).show()
                    } else {
                        progressdialog!!.setCancelable(false)
                        progressdialog!!.show()
                        uploadVideoFile(File(pathPic), this, this)
                    }
                }
                3 -> {
                    val vid = data!!.data
                    pathPic = getPath(this, vid)
                    progressdialog!!.setCancelable(false)
                    progressdialog!!.show()
                    uploadVideoFile(File(pathPic), this, this)
                }
                4 -> {
                    val bip = data!!.extras!!["data"] as Bitmap?
                    save(bip!!)
                }
            }
        }
    }

    private fun save(bip: Bitmap) {
        val root: String = externalCacheDir!!.absolutePath
        val mkDir = File("$root/saveImage")
        mkDir.mkdirs()
        val generator = Random()
        var n = 10000
        n = generator.nextInt(n)
        val imageName = "Image-$n.jpg"
        val file = File(mkDir, imageName)
        if (file.exists()) file.delete()
        try {
            val out = FileOutputStream(file)
            bip.compress(Bitmap.CompressFormat.JPEG, 90, out)
            pathPic = file.absolutePath
            Log.d("BitData", pathPic)
            progressdialog!!.setCancelable(false)
            progressdialog!!.show()
                uploadFile(File(pathPic), this, this)
            out.flush()
            out.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Exception", Toast.LENGTH_SHORT).show()
        }
    }

    fun uploadFile(file: File, context: Context, listener: UploadFileListener) = Thread {
        val credentials = BasicAWSCredentials(
            "AKIA6LSDBEL3U2HOJWLW",
            "LyHAItB0oo199ff+bEMIuyJk+hmRsmZtJR7arLNV"
        )
        val s3Client = AmazonS3Client(credentials, Region.getRegion(Regions.US_EAST_2))
        s3Client.setObjectAcl(
            "media-appsinvo",
            "AKIA6LSDBEL3U2HOJWLW",
            CannedAccessControlList.PublicRead
        )
        ThreadUtils.runOnUiThread {
            // s3Client.setRegion(Region.getRegion(Regions.fromName("us-east-2")));
            val transferUtility = TransferUtility.builder()
                .context(context)
                .awsConfiguration(AWSMobileClient.getInstance().configuration)
                .s3Client(s3Client)
                .build()

            val uploadObserver = transferUtility.upload(
                file.name, getInputStream(file),
                UploadOptions.builder().bucket("media-appsinvo")
                    .cannedAcl(CannedAccessControlList.PublicRead).build()
            )

            uploadObserver.setTransferListener(object : TransferListener {
                override fun onStateChanged(id: Int, state: TransferState) {
                    if (TransferState.COMPLETED === state) {
                        // Handle a completed download.
                        listener.onSuccess(
                            file.name,
                            "https://s3.us-east-2.amazonaws.com/media-appsinvo/" + file.name
                        )
                    }
                }

                override fun onProgressChanged(
                    id: Int,
                    bytesCurrent: Long,
                    bytesTotal: Long,
                ) {
                    val percentDonef = bytesCurrent.toFloat() / bytesTotal.toFloat() * 100
                    val percentDone = percentDonef.toInt()
                }

                override fun onError(id: Int, ex: Exception) {
                    // Handle errors
                    Log.d("Exception", ex.toString())
                    listener.onFailure(ex.toString())
                }
            })

        }
    }.start()

    override fun onSuccess(localUrl: String?, awsUrl: String?) {
        if (awsUrl != null) {
            Log.d("BitData", awsUrl)
            awsPicUrl=awsUrl
            if (type=="image"){
                addMedia.visibility=View.GONE
                videoFrame.visibility=View.GONE
                videoView.visibility=View.GONE
                play.visibility=View.GONE
                pause.visibility=View.GONE
                postPic.visibility=View.VISIBLE
                Glide.with(this).load(awsPicUrl).placeholder(R.drawable.user_avatar).into(postPic)
            }else if(type=="video"){
                addMedia.visibility=View.GONE
                postPic.visibility=View.GONE
                videoFrame.visibility=View.VISIBLE
                videoView.visibility=View.VISIBLE
                val display = getSystemService<DisplayManager>()?.getDisplay(Display.DEFAULT_DISPLAY)
                val width = display!!.width
                val height = display!!.height
                videoView.layoutParams = FrameLayout.LayoutParams(width, height)
                val video = Uri.parse(awsPicUrl)
                videoView.setVideoURI(video)
            }

            Log.d("BitData", "Success")
        }
        progressdialog!!.dismiss()
    }

    override fun onFailure(error: String?) {
        progressdialog!!.dismiss()
        Log.d("BitData", "Fail" + error)

    }

    private fun getInputStream(file: File): InputStream {
        val bitmap = BitmapFactory.decodeFile(file.absolutePath, BitmapFactory.Options())
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, bos)
        val bitmapdata = bos.toByteArray()
        return ByteArrayInputStream(bitmapdata)
    }

    fun uploadVideoFile(file: File, context: Context, listener: UploadFileListener) = Thread {
        val credentials = BasicAWSCredentials(
            "AKIA6LSDBEL3U2HOJWLW",
            "LyHAItB0oo199ff+bEMIuyJk+hmRsmZtJR7arLNV"
        )
        val s3Client = AmazonS3Client(credentials, Region.getRegion(Regions.US_EAST_2))
        s3Client.setObjectAcl(
            "media-appsinvo",
            "AKIA6LSDBEL3U2HOJWLW",
            CannedAccessControlList.PublicRead
        )
        ThreadUtils.runOnUiThread {
            // s3Client.setRegion(Region.getRegion(Regions.fromName("us-east-2")));
            val transferUtility = TransferUtility.builder()
                .context(context)
                .awsConfiguration(AWSMobileClient.getInstance().configuration)
                .s3Client(s3Client)
                .build()
            val uploadObserver = transferUtility.upload(
                "media-appsinvo",
                file.name,
                file,
                CannedAccessControlList.PublicRead
            )

            uploadObserver.setTransferListener(object : TransferListener {
                override fun onStateChanged(id: Int, state: TransferState) {
                    if (TransferState.COMPLETED === state) {
                        // Handle a completed download.
                        listener.onSuccess(
                            file.name,
                            "https://s3.us-east-2.amazonaws.com/media-appsinvo/" + file.name
                        )
                    }
                }

                override fun onProgressChanged(
                    id: Int,
                    bytesCurrent: Long,
                    bytesTotal: Long,
                ) {
                    val percentDonef = bytesCurrent.toFloat() / bytesTotal.toFloat() * 100
                    val percentDone = percentDonef.toInt()
                }

                override fun onError(id: Int, ex: Exception) {
                    // Handle errors
                    Log.d("Exception", ex.toString())
                    listener.onFailure(ex.toString())
                }
            })

        }
    }.start()

}