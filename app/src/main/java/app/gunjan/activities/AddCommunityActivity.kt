package app.gunjan.activities

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import app.gunjan.R
import app.gunjan.entity.AddCommunityResponse
import app.gunjan.entity.CategoryListResponse
import app.gunjan.entity.PrivacyPolicyResponse
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.PermissionUtil
import app.gunjan.utill.ProjectUtill
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
import kotlinx.android.synthetic.main.activity_add_community.*
import kotlinx.android.synthetic.main.activity_add_community.back
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.util.*
import kotlin.collections.ArrayList

class AddCommunityActivity : AppCompatActivity(), UploadFileListener {
    private var pathPic = ""
    private var awsPicUrl = ""
    private var categoryId = ""
    private var nameList: ArrayList<String> = ArrayList<String>()
    private val idList: ArrayList<String> = ArrayList<String>()
    private var progressdialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_community)
        initData()
    }

    private fun initData() {
        progressdialog = ProgressDialog(this, R.style.MyAlertDialogStyle)
        getCategoryList()
        back.setOnClickListener { finish() }

        Submit.setOnClickListener {
            if (awsPicUrl.toString().trim() == "") {
                Toast.makeText(this, getString(R.string.choose_pic), Toast.LENGTH_LONG).show()
            } else {
                if (validate()) {
                    val myDialog = ProjectUtill.showProgressDialog(this@AddCommunityActivity)
                    WebServiceRequest.getInstance().addCommunity(
                        this,
                        community.text.toString().trim(),
                        about.text.toString().trim(),
                        categoryId,
                        awsPicUrl,
                        object : Callback<AddCommunityResponse> {
                            override fun onResponse(
                                call: Call<AddCommunityResponse>,
                                response: Response<AddCommunityResponse>
                            ) {
                                myDialog.dismiss()
                                if (response != null) {
                                    if (response.isSuccessful) {
                                        if (response.body()!!.code == 1) {
                                            finish()
                                        } else {
                                            ProjectUtill.printMessage(
                                                this@AddCommunityActivity.window.decorView,
                                                response.body()?.message
                                            )
                                        }
                                    } else {
                                        ProjectUtill.printErrorMessage(
                                            this@AddCommunityActivity.window.decorView,
                                            ""
                                        )
                                    }
                                } else {
                                    ProjectUtill.printErrorMessage(
                                        this@AddCommunityActivity.window.decorView,
                                        ""
                                    )
                                }
                            }

                            override fun onFailure(
                                call: Call<AddCommunityResponse>,
                                t: Throwable
                            ) {
                                myDialog.dismiss()
                                ProjectUtill.printErrorMessage(
                                    this@AddCommunityActivity.window.decorView,
                                    ""
                                )
                            }
                        })
                }
            }
        }

        choosePic.setOnClickListener {
            if (checkPicturePermission()) {
                val builder2 = AlertDialog.Builder(this)
                builder2.setMessage("Press Gallery or Camera")
                builder2.setCancelable(true)
                builder2.setPositiveButton("Gallery") { _, _ ->
                    val pickPhoto = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(pickPhoto, 1)
                }
                builder2.setNegativeButton("Camera") { _, _ ->
                    val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(takePicture, 0)
                }
                val alert = builder2.create()
                alert.show()
                alert.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.pink
                    )
                )
                alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.pink
                    )
                )
            }
        }
    }
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (resultCode == Activity.RESULT_OK) {
                when (requestCode) {
                    0 -> {
                        val bip = data!!.extras!!["data"] as Bitmap?
                        Log.d("BitData", data!!.extras!!["data"].toString())
                        save(bip!!)
                    }
                    1 -> {
                        val selectedImage = data!!.data
                        pathPic = ProjectUtill.getPath(this, selectedImage)
                        progressdialog!!.show()
                        uploadFile(File(pathPic), this, this)
                    }
                }
            }
        }

        private fun save(bip: Bitmap) {
            val root = externalCacheDir!!.absolutePath
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
                progressdialog!!.show()
                uploadFile(File(pathPic), this, this)
                out.flush()
                out.close()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Log.d("Exception", e.printStackTrace().toString())
            }
        }

        private fun checkPicturePermission(): Boolean {
            return if (PermissionUtil.verifyPermissions(
                    this,
                    PermissionUtil.getCameraPermissions()
                )
            ) {
                true
            } else {
                PermissionUtil.requestPermission(
                    PermissionUtil.getCameraPermissions(),
                    this
                )
                false
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
                Glide.with(this).load(awsUrl).placeholder(R.drawable.user_avatar)
                    .into(pic)
                awsPicUrl = awsUrl
            }
            progressdialog!!.dismiss()
        }

        override fun onFailure(error: String?) {
            progressdialog!!.dismiss()
        }

        private fun getInputStream(file: File): InputStream {
            val bitmap = BitmapFactory.decodeFile(file.absolutePath, BitmapFactory.Options())
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, bos)
            val bitmapdata = bos.toByteArray()
            return ByteArrayInputStream(bitmapdata)
        }

        private fun validate(): Boolean {
            if (community!!.text.toString().trim().equals("", ignoreCase = true)) {
                community!!.requestFocus()
                community!!.error = "Please Enter Community Name"
                return false
            } else if (about!!.text.toString().trim().equals("", ignoreCase = true)) {
                about!!.requestFocus()
                about!!.error = "Please Enter About Community"
                return false
            } else if (categorySpinner!!.selectedItem.toString().trim().equals("Select Category")) {
                Toast.makeText(this, "Please select category", Toast.LENGTH_LONG).show()
                return false
            }
            return true
        }

        fun getCategoryList() {
            val myDialog = ProjectUtill.showProgressDialog(this@AddCommunityActivity)
            WebServiceRequest.getInstance().categoryList(
                this,
                object : Callback<CategoryListResponse> {
                    override fun onResponse(
                        call: Call<CategoryListResponse>,
                        response: Response<CategoryListResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    idList.clear()
                                    idList.add("")
                                    nameList.add("Select Category")
                                    for (i in response.body()!!.data.category_list) {
                                        idList.add(i.id.toString())
                                        nameList.add(i.name)
                                    }
                                    val arrayAdapter1: ArrayAdapter<String> =
                                        object : ArrayAdapter<String>(
                                            this@AddCommunityActivity,
                                            R.layout.spinner_layout, nameList
                                        ) {
                                            override fun isEnabled(position: Int): Boolean {
                                                return position != 0
                                            }

                                            override fun getDropDownView(
                                                position: Int, convertView: View?,
                                                parent: ViewGroup,
                                            ): View {
                                                val view =
                                                    super.getDropDownView(
                                                        position,
                                                        convertView,
                                                        parent
                                                    )
                                                val tv = view as TextView
                                                if (position == 0) { // Set the hint text color gray
                                                    tv.setTextColor(Color.BLACK)
                                                } else {
                                                    tv.setTextColor(resources.getColor(R.color.txt_color))
                                                }
                                                return view
                                            }

                                        }
                                    categorySpinner!!.adapter = arrayAdapter1
                                    categorySpinner!!.onItemSelectedListener = object :
                                        AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(
                                            adapterView: AdapterView<*>?,
                                            view: View,
                                            i: Int,
                                            l: Long,
                                        ) {
                                            if (i > 0) {
                                               categoryId=idList[i].toString()
                                            }
                                        }

                                        override fun onNothingSelected(adapterView: AdapterView<*>?) {}
                                    }
                                } else {
                                    ProjectUtill.printMessage(
                                        this@AddCommunityActivity.window.decorView,
                                        response.body()?.message
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    this@AddCommunityActivity.window.decorView,
                                    ""
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@AddCommunityActivity.window.decorView,
                                ""
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<CategoryListResponse>,
                        t: Throwable
                    ) {
                        myDialog.dismiss()
                        ProjectUtill.printErrorMessage(
                            this@AddCommunityActivity.window.decorView,
                            ""
                        )
                    }
                })
        }
    }
