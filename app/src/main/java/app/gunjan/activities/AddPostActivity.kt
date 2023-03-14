package app.gunjan.activities

import android.app.*
import android.content.Intent
import android.graphics.Bitmap
import android.hardware.display.DisplayManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import app.gunjan.R
import app.gunjan.entity.AddPostResponse
import app.gunjan.entity.UploadS3FileResponse
import app.gunjan.utill.PermissionUtil
import app.gunjan.utill.ProjectUtill
import app.gunjan.utill.ProjectUtill.getPath
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_add_post.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class AddPostActivity : BaseActivity() {
    private var dob = ""
    private  var status:String? = null
    private var mYear = 0
    private  var mMonth:Int = 0
    private  var mDay:Int = 0
    var fromDateValue = ""
    var yourDate:String? = null
    var toDateValue:String? = ""
    private var pathPic = ""
    var path: String? = null
    private var mHour = 0
    private  var mMinute:Int = 0
    private  var mSecond:Int = 0
    var selectedStartTime: String? = null
    var selectedEndTime: String? = null
    var format:String? = ""
    var timeValue:String? = ""
    var timeValue2:String? = ""
    private var awsPicUrl = ""
    private var awsPicUrl2 = ""
    private var animShow: Animation? = null
    private var type: String = "text"
    private var feedType: String = "disccusion"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)
        initData()
    }

    private fun initData() {
        animShow = AnimationUtils.loadAnimation(this, R.anim.move_right_in_activity)
        back.setOnClickListener { finish() }

        startDate.setOnClickListener {
            status="f"
            get_date()
        }

        endDate.setOnClickListener {
            status="t"
            get_date()
        }

        startTime.setOnClickListener {
            getTime("start")
        }

        endTime.setOnClickListener {
            getTime("end")
        }

        discussType.setOnClickListener {
            feedType = "disccusion"
            discussLayout.visibility = View.VISIBLE
            layout.visibility = View.VISIBLE
            eventLayout.visibility = View.GONE
            discussType.isChecked = true
            eventType.isChecked = false
        }

        eventType.setOnClickListener {
            feedType = "event"
            eventLayout.visibility = View.VISIBLE
            discussLayout.visibility = View.GONE
            layout.visibility = View.GONE
            discussType.isChecked = false
            eventType.isChecked = true
        }

        textPost.setOnClickListener {
            awsPicUrl = ""
            type = "text"
            textPost.background = resources.getDrawable(R.drawable.button_bg)
            mediaPost.background = resources.getDrawable(R.drawable.unselected_bg)
            mediaLayout.visibility = View.GONE
            txtLayout.visibility = View.VISIBLE
            txtLayout!!.startAnimation(animShow)
        }

        mediaPost.setOnClickListener {
            type = "image"
            mediaPost.background = resources.getDrawable(R.drawable.button_bg)
            textPost.background = resources.getDrawable(R.drawable.unselected_bg)
            txtLayout.visibility = View.VISIBLE
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

        emediaLayout.setOnClickListener {
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
            if (feedType == "disccusion") {
                if (type == "text") {
                    if (edtPost.text.toString().trim() == "") {
                        Toast.makeText(this, getString(R.string.write_about_post), Toast.LENGTH_LONG)
                            .show()
                    } else {
                        val myDialog = ProjectUtill.showProgressDialog(this@AddPostActivity)
                        WebServiceRequest.getInstance().addPost(
                            this, edtPost.text.toString().trim(), "", type, feedType,"","","","",
                            object : Callback<AddPostResponse> {
                                override fun onResponse(
                                    call: Call<AddPostResponse>,
                                    response: Response<AddPostResponse>
                                ) {
                                    myDialog.dismiss()
                                    if (response != null) {
                                        if (response.isSuccessful) {
                                            if (response.body()!!.code == 1) {

                                                var intent = Intent(
                                                    this@AddPostActivity,
                                                    HomeActivity::class.java
                                                )
                                                intent.flags =
                                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
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
                } else if (type == "image" || type == "video") {
                    if (awsPicUrl == "") {
                        Toast.makeText(this, getString(R.string.choose_media), Toast.LENGTH_LONG).show()
                    } else if (edtPost.text.toString().trim() == "") {
                        Toast.makeText(this, getString(R.string.write_about_post), Toast.LENGTH_LONG)
                            .show()
                    } else {
                        val myDialog = ProjectUtill.showProgressDialog(this@AddPostActivity)
                        WebServiceRequest.getInstance().addPost(
                            this, edtPost.text.toString().trim(), awsPicUrl, type, feedType,"","","","",
                            object : Callback<AddPostResponse> {
                                override fun onResponse(
                                    call: Call<AddPostResponse>,
                                    response: Response<AddPostResponse>
                                ) {
                                    myDialog.dismiss()
                                    if (response != null) {
                                        if (response.isSuccessful) {
                                            if (response.body()!!.code == 1) {

                                                var intent = Intent(
                                                    this@AddPostActivity,
                                                    HomeActivity::class.java
                                                )
                                                intent.flags =
                                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
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
            } else {
                if (awsPicUrl2 == "") {
                    Toast.makeText(this, getString(R.string.choose_media), Toast.LENGTH_LONG).show()
                } else if (edtEventPost.text.toString().trim() == "") {
                    Toast.makeText(this, getString(R.string.write_about_event), Toast.LENGTH_LONG)
                        .show()
                }else if (fromDateValue == "") {
                    Toast.makeText(this, getString(R.string.please_start_date), Toast.LENGTH_LONG)
                        .show()
                }else if (timeValue == "") {
                    Toast.makeText(this, getString(R.string.please_start_time), Toast.LENGTH_LONG)
                        .show()
                } else if (toDateValue == "") {
                    Toast.makeText(this, getString(R.string.please_end_date), Toast.LENGTH_LONG)
                        .show()
                }else if (timeValue2 == "") {
                    Toast.makeText(this, getString(R.string.please_end_time), Toast.LENGTH_LONG)
                        .show()
                } else {
                    val myDialog = ProjectUtill.showProgressDialog(this@AddPostActivity)
                    WebServiceRequest.getInstance().addPost(
                        this, edtEventPost.text.toString().trim(), awsPicUrl2, type, feedType,fromDateValue,toDateValue!!,timeValue!!,timeValue2!!,
                        object : Callback<AddPostResponse> {
                            override fun onResponse(
                                call: Call<AddPostResponse>,
                                response: Response<AddPostResponse>
                            ) {
                                myDialog.dismiss()
                                if (response != null) {
                                    if (response.isSuccessful) {
                                        if (response.body()!!.code == 1) {

                                            var intent = Intent(
                                                this@AddPostActivity,
                                                HomeActivity::class.java
                                            )
                                            intent.flags =
                                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
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

    fun get_date() {
        val c = Calendar.getInstance()
        mYear = c[Calendar.YEAR]
        mMonth = c[Calendar.MONTH]
        mDay = c[Calendar.DAY_OF_MONTH]
        val datePickerDialog = DatePickerDialog(
            this,
            { view, year, monthOfYear, dayOfMonth ->
                var date = dayOfMonth.toString()
                var month = (monthOfYear + 1).toString()
                if (date.length == 1) {
                    date = "0$date"
                }
                if (month.length == 1) {
                    month = "0$month"
                }
                dob = "$year-$month-$date"
                val today = Calendar.getInstance()
                val dob = Calendar.getInstance()
                dob[year, monthOfYear] = dayOfMonth
                var yourAge = today[Calendar.YEAR] - dob[Calendar.YEAR]
                dob.add(Calendar.YEAR, yourAge)
                if (today.before(dob)) {
                    yourAge--
                }
                val age = yourAge
                if (status.equals("f", ignoreCase = true)) {
                    fromDateValue = "$year-$month-$date"
                    try {
                        var format = SimpleDateFormat("yyyy-MM-dd")
                        val date1 = format.parse(fromDateValue)
                        val date2 = format.format(date1)
                        format =
                            if (date2.endsWith("01") && !date2.endsWith("11")) SimpleDateFormat("d'st' MMM, yyyy") else if (date2.endsWith(
                                    "02"
                                ) && !date2.endsWith("12")
                            ) SimpleDateFormat("d'nd' MMM, yyyy") else if (date2.endsWith("03") && !date2.endsWith(
                                    "13"
                                )
                            ) SimpleDateFormat("d'rd' MMM, yyyy") else SimpleDateFormat("d'th' MMM, yyyy")
                        yourDate = format.format(date1)
                        startDate.text = yourDate
                    } catch (e: java.lang.Exception) {
                    }
                    val isTrue: Boolean = isDateAfter(fromDateValue, toDateValue)
                    if (isTrue) {
                        try {
                            var format = SimpleDateFormat("yyyy-MM-dd")
                            val date1 = format.parse(fromDateValue)
                            val date2 = format.format(date1)
                            format =
                                if (date2.endsWith("01") && !date2.endsWith("11")) SimpleDateFormat(
                                    "d'st' MMM, yyyy"
                                ) else if (date2.endsWith("02") && !date2.endsWith("12")) SimpleDateFormat(
                                    "d'nd' MMM, yyyy"
                                ) else if (date2.endsWith("03") && !date2.endsWith("13")) SimpleDateFormat(
                                    "d'rd' MMM, yyyy"
                                ) else SimpleDateFormat("d'th' MMM, yyyy")
                            yourDate = format.format(date1)
                            startDate.text = yourDate
                        } catch (e: java.lang.Exception) {
                        }
                    } else {
                        if (toDateValue.equals("", ignoreCase = true)) {
                            try {
                                var format = SimpleDateFormat("yyyy-MM-dd")
                                val date1 = format.parse(fromDateValue)
                                val date2 = format.format(date1)
                                format =
                                    if (date2.endsWith("01") && !date2.endsWith("11")) SimpleDateFormat(
                                        "d'st' MMM, yyyy"
                                    ) else if (date2.endsWith("02") && !date2.endsWith("12")) SimpleDateFormat(
                                        "d'nd' MMM, yyyy"
                                    ) else if (date2.endsWith("03") && !date2.endsWith("13")) SimpleDateFormat(
                                        "d'rd' MMM, yyyy"
                                    ) else SimpleDateFormat("d'th' MMM, yyyy")
                                yourDate = format.format(date1)
                                startDate.text = yourDate
                            } catch (e: java.lang.Exception) {
                            }
                            startDate.text = yourDate
                        } else {
                            Toast.makeText(
                                this,
                                getString(R.string.start_end),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    toDateValue = "$year-$month-$date"
                    val isTrue: Boolean = isDateAfter(fromDateValue, toDateValue)
                    if (isTrue) {
                        if (fromDateValue.equals("", ignoreCase = true)) {
                            Toast.makeText(
                                this,
                                getString(R.string.please_start_date),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            try {
                                var format = SimpleDateFormat("yyyy-MM-dd")
                                val date1 = format.parse(toDateValue)
                                val date2 = format.format(date1)
                                format =
                                    if (date2.endsWith("01") && !date2.endsWith("11")) SimpleDateFormat(
                                        "d'st' MMM, yyyy"
                                    ) else if (date2.endsWith("02") && !date2.endsWith("12")) SimpleDateFormat(
                                        "d'nd' MMM, yyyy"
                                    ) else if (date2.endsWith("03") && !date2.endsWith("13")) SimpleDateFormat(
                                        "d'rd' MMM, yyyy"
                                    ) else SimpleDateFormat("d'th' MMM, yyyy")
                                val yourDate = format.format(date1)
                                endDate.setText(yourDate)
                            } catch (e: java.lang.Exception) {
                            }
                        }
                    } else {
                        if (fromDateValue.equals("", ignoreCase = true)) {
                            Toast.makeText(
                                this,
                                getString(R.string.activity_start_date),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this,
                                getString(R.string.end_start),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }, mYear, mMonth, mDay
        )
        val c2 = Calendar.getInstance()
        c2[mYear, mMonth] = mDay
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }

    private fun getTime(statusTime:String) {
        val c = Calendar.getInstance()
        mHour = c[Calendar.HOUR_OF_DAY]
        mMinute = c[Calendar.MINUTE]
        mSecond = c[Calendar.SECOND]
        val timePickerDialog = TimePickerDialog(
            this@AddPostActivity,
            { view, hourOfDay, minute ->
                var hourOfDay = hourOfDay
                if (statusTime == "start") {
                    selectedStartTime = String.format("%02d:%02d", hourOfDay, minute)
                }else{
                    selectedEndTime = String.format("%02d:%02d", hourOfDay, minute)
                }
                if (hourOfDay == 0) {
                    hourOfDay += 12
                    format = "AM"
                } else if (hourOfDay == 12) {
                    format = "PM"
                } else if (hourOfDay > 12) {
                    hourOfDay -= 12
                    format = "PM"
                } else {
                    format = "AM"
                }
                var starthour = hourOfDay.toString()
                if (starthour.length == 1) {
                    starthour = "0$starthour"
                }
                var startMinute = minute.toString()
                if (startMinute.length == 1) {
                    startMinute = "0$startMinute"
                }
                if (statusTime == "start") {
                    timeValue = selectedStartTime
                    startTime.text = "$starthour:$startMinute $format"
                }else{
                    timeValue2 = selectedEndTime
                    endTime.text = "$starthour:$startMinute $format"
                }
            }, mHour, mMinute, true
        )
        timePickerDialog.show()
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
        gallery = dialog.findViewById(R.id.gallery)
        video = dialog.findViewById(R.id.rl_layout1)
        captureVideo = dialog.findViewById(R.id.rl_layout2)
        capturePic = dialog.findViewById(R.id.rl_layout3)
        close = dialog.findViewById(R.id.close)
        gallery.setOnClickListener {
            if (checkPicturePermission()) {
                dialog.cancel()
                type = "image"
                val pickPhoto = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(pickPhoto, 1)
            }
        }
        video.setOnClickListener {
            if (checkPicturePermission()) {
                type = "video"
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 0)
                dialog.cancel()
            }
        }
        captureVideo.setOnClickListener {
            if (checkPicturePermission()) {
                type = "video"
                val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30)
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 3)
                startActivityForResult(intent, 3)
                dialog.cancel()
            }
        }
        capturePic.setOnClickListener {
            if (checkPicturePermission()) {
                type = "image"
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
                    uploadFile()
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
                        uploadFile()
                    }
                }
                3 -> {
                    val vid = data!!.data
                    pathPic = getPath(this, vid)
                    uploadFile()
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
            bip.compress(Bitmap.CompressFormat.PNG, 100, out)
            pathPic = file.absolutePath
            Log.d("BitData", pathPic)
            uploadFile()
            out.flush()
            out.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Exception", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadFile() {
        val myDialog = ProjectUtill.showProgressDialog(this)
        WebServiceRequest.getInstance().uploadFile(
            File(pathPic),
            object : Callback<UploadS3FileResponse> {
                override fun onResponse(
                    call: Call<UploadS3FileResponse>,
                    response: Response<UploadS3FileResponse>
                ) {
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                try {
                                    if (feedType == "disccusion") {
                                        awsPicUrl = response.body()!!.data.path_data.path
                                        if (type == "image") {
                                            addMedia.visibility = View.GONE
                                            videoFrame.visibility = View.GONE
                                            videoView.visibility = View.GONE
                                            play.visibility = View.GONE
                                            pause.visibility = View.GONE
                                            postPic.visibility = View.VISIBLE
                                            Glide.with(this@AddPostActivity).load(awsPicUrl).placeholder(R.drawable.placeholder)
                                                .into(postPic)
                                        } else if (type == "video") {
                                            addMedia.visibility = View.GONE
                                            postPic.visibility = View.GONE
                                            videoFrame.visibility = View.VISIBLE
                                            videoView.visibility = View.VISIBLE
                                            val display =
                                                getSystemService<DisplayManager>()?.getDisplay(Display.DEFAULT_DISPLAY)
                                            val width = display!!.width
                                            val height = display!!.height
                                            videoView.layoutParams = FrameLayout.LayoutParams(width, height)
                                            val video = Uri.parse(awsPicUrl)
                                            videoView.setVideoURI(video)
                                        }
                                    } else {
                                        awsPicUrl2 = response.body()!!.data.path_data.path
                                        if (type == "image") {
                                            eaddMedia.visibility = View.GONE
                                            evideoFrame.visibility = View.GONE
                                            evideoView.visibility = View.GONE
                                            eplay.visibility = View.GONE
                                            epause.visibility = View.GONE
                                            epostPic.visibility = View.VISIBLE
                                            Glide.with(this@AddPostActivity).load(awsPicUrl2).placeholder(R.drawable.placeholder)
                                                .into(epostPic)
                                        } else if (type == "video") {
                                            eaddMedia.visibility = View.GONE
                                            epostPic.visibility = View.GONE
                                            evideoFrame.visibility = View.VISIBLE
                                            evideoView.visibility = View.VISIBLE
                                            val display =
                                                getSystemService<DisplayManager>()?.getDisplay(Display.DEFAULT_DISPLAY)
                                            val width = display!!.width
                                            val height = display!!.height
                                            evideoView.layoutParams = FrameLayout.LayoutParams(width, height)
                                            val video = Uri.parse(awsPicUrl2)
                                            evideoView.setVideoURI(video)
                                        }
                                    }
                                }catch (e: Exception) {}
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
                    call: Call<UploadS3FileResponse>,
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

    fun isDateAfter(startDate: String?, endDate: String?): Boolean {
        return try {
            val myFormatString = "yyyy-M-dd" // for example
            val df = SimpleDateFormat(myFormatString)
            val date1 = df.parse(endDate)
            val startingDate = df.parse(startDate)
            if (date1.after(startingDate)) true else false
        } catch (e: java.lang.Exception) {
            false
        }
    }
}