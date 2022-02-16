package app.gunjan.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import app.gunjan.R
import app.gunjan.activities.SetProfileActivity
import app.gunjan.entity.CompleteProfileResponse
import app.gunjan.entity.UserDetailsResponse
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
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.net.UnknownServiceException
import java.util.*

class CompleteProfileFragment : Fragment(), UploadFileListener {
    private var pathPic = ""
    private var awsPicUrl = ""
    var progressdialog: ProgressDialog? = null
    private var choosePic: RelativeLayout? = null
    private var profilePic: CircleImageView? = null
    private var profileName: EditText? = null
    private var firstName: EditText? = null
    private var lastName: EditText? = null
    private var pinCode: EditText? = null
    private var Continue: LinearLayout? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_complete_profile, container, false)
        choosePic = view.findViewById(R.id.choosePic)
        profilePic = view.findViewById(R.id.profilePic)
        profileName = view.findViewById(R.id.profileName)
        firstName = view.findViewById(R.id.firstName)
        lastName = view.findViewById(R.id.lastName)
        pinCode = view.findViewById(R.id.pinCode)
        Continue = view.findViewById(R.id.Continue)
        initData()
        return view
    }

    private fun initData() {
        progressdialog = ProgressDialog(context, R.style.MyAlertDialogStyle)
        userDetails()
        Continue!!.setOnClickListener {
            if (awsPicUrl.toString().trim() == "") {
                Toast.makeText(context, getString(R.string.choose_pic), Toast.LENGTH_LONG).show()
            } else {
                if (validate()) {
                    val myDialog = ProjectUtill.showProgressDialog(context)
                    context?.let { it1 ->
                        WebServiceRequest.getInstance().completeProfile(
                            it1, profileName!!.text.toString().trim(),
                            firstName!!.text.toString().trim(),
                            lastName!!.text.toString().trim(),
                            "android", "en", awsPicUrl, pinCode!!.text.toString().trim(),
                            object : Callback<CompleteProfileResponse> {
                                override fun onResponse(
                                    call: Call<CompleteProfileResponse>,
                                    response: Response<CompleteProfileResponse>
                                ) {
                                    myDialog.dismiss()
                                    if (response != null) {
                                        if (response.isSuccessful) {
                                            if (response.body()!!.code == 1) {
                                                (activity as SetProfileActivity).loadIdentificationFragment()
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
                                    call: Call<CompleteProfileResponse>,
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
        }

         profileName!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                var result:String = editable.toString().replace(" ", "");
                if (!editable.toString().equals(result)) {
                    profileName!!.setText(result)
                    profileName!!.setSelection(result.length)
                }
            }
        })
        choosePic!!.setOnClickListener {
            if (checkPicturePermission()) {
                val builder2 = AlertDialog.Builder(context)
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
                        context as Activity,
                        R.color.pink
                    )
                )
                alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(
                    ContextCompat.getColor(
                        context as Activity,
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
                    pathPic = ProjectUtill.getPath(context, selectedImage)
                    progressdialog!!.show()
                    context?.let { uploadFile(File(pathPic), it, this) }
                }
            }
        }
    }

    private fun save(bip: Bitmap) {
        val root = context?.externalCacheDir!!.absolutePath
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
            context?.let { uploadFile(File(pathPic), it, this) }
            out.flush()
            out.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.d("Exception", e.printStackTrace().toString())
        }
    }

    private fun checkPicturePermission(): Boolean {
        return if (PermissionUtil.verifyPermissions(
                context,
                PermissionUtil.getCameraPermissions()
            )
        ) {
            true
        } else {
            PermissionUtil.requestPermission(
                PermissionUtil.getCameraPermissions(),
                context as Activity
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
            context?.let {
                Glide.with(it).load(awsUrl).placeholder(R.drawable.user_avatar)
                    .into(profilePic!!)
            }
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
        if (profileName!!.text.toString().trim().equals("", ignoreCase = true)) {
            profileName!!.requestFocus()
            profileName!!.error = getString(R.string.enter_profilename)
            return false
        } else if (firstName!!.text.toString().trim().equals("", ignoreCase = true)) {
            firstName!!.requestFocus()
            firstName!!.error = getString(R.string.enter_firstname)
            return false
        } else if (lastName!!.text.toString().trim().equals("", ignoreCase = true)) {
            lastName!!.requestFocus()
            lastName!!.error = getString(R.string.enter_lastname)
            return false
        } else if (pinCode!!.text.toString().trim().equals("", ignoreCase = true)) {
            pinCode!!.requestFocus()
            pinCode!!.error = getString(R.string.pincode)
            return false
        }
        return true
    }

    private fun userDetails(){
        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let { it1 ->
            WebServiceRequest.getInstance().userDetails(
                it1,
                object : Callback<UserDetailsResponse> {
                    override fun onResponse(
                        call: Call<UserDetailsResponse>,
                        response: Response<UserDetailsResponse>
                    ) {
                        myDialog.dismiss()
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == 1) {
                                    try {
                                        if (response.body()!!.data.user.image != null) {
                                            awsPicUrl = response.body()!!.data.user.image
                                            Glide.with(context!!)
                                                .load(response.body()!!.data.user.image)
                                                .placeholder(R.drawable.user_avatar)
                                                .into(profilePic!!)
                                        }
                                        if (response.body()!!.data.user.profile_name != null || response.body()!!.data.user.profile_name != "") {
                                            profileName!!.setText(response.body()!!.data.user.profile_name)
                                        }
                                        if (response.body()!!.data.user.first_name != null || response.body()!!.data.user.first_name != "") {
                                            firstName!!.setText(response.body()!!.data.user.first_name)
                                        }
                                        if (response.body()!!.data.user.last_name != null || response.body()!!.data.user.last_name != "") {
                                            lastName!!.setText(response.body()!!.data.user.last_name)
                                        }
                                        if (response.body()!!.data.user.pincode != null || response.body()!!.data.user.pincode != "") {
                                            pinCode!!.setText(response.body()!!.data.user.pincode)
                                        }
                                    }catch (e:Exception){}
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
                        call: Call<UserDetailsResponse>,
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