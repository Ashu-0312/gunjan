package app.gunjan.activities

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import app.gunjan.R
import app.gunjan.adapters.ShowInterestAdapter
import app.gunjan.entity.*
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
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.back
import kotlinx.android.synthetic.main.activity_edit_profile.ccp
import kotlinx.android.synthetic.main.activity_edit_profile.edtMobile
import kotlinx.android.synthetic.main.activity_edit_profile.iv_flag
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_mobile_register.*
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EditProfileActivity : AppCompatActivity(), UploadFileListener {
    private var pathPic = ""
    private var statusPin = ""
    private var awsPicUrl = ""
    private var cityValue = ""
    private var stateValue = ""
    private var pincodeValue = ""
    private var mYear = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private var dob = ""
    private val codee = "+91"
    var yourDate: String? = null
    var fromDateValue: String? = null
    private var progressdialog: ProgressDialog? = null
    private var genderList: ArrayList<String> = ArrayList<String>()
    private var selectedInterestList: ArrayList<String> = ArrayList<String>()
    private var interestList: ArrayList<ShowInterestModel> = ArrayList<ShowInterestModel>()
    private var interestAdapter: ShowInterestAdapter? = null
    private var stateNameList: java.util.ArrayList<String> = java.util.ArrayList<String>()
    private var cityList: java.util.ArrayList<String> = java.util.ArrayList<String>()
    private var pincodeList: java.util.ArrayList<String> = java.util.ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        initData()
    }

    private fun initData() {
        progressdialog = ProgressDialog(this, R.style.MyAlertDialogStyle)
        edtMobile.keyListener = DigitsKeyListener.getInstance("0123456789")
        edtMobile.inputType = InputType.TYPE_CLASS_NUMBER
        userDetails()
        genderList.add(getString(R.string.gender_select))
        genderList.add(getString(R.string.male))
        genderList.add(getString(R.string.female))
        genderList.add(getString(R.string.others))

        about.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(
                arg0: CharSequence, arg1: Int, arg2: Int,
                arg3: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
                text_count.text =
                    (500 - s.toString().length).toString() + "/500" + getString(R.string.jf)
            }
        })

        edtDob.setOnClickListener { getDate() }

        back.setOnClickListener { finish() }

        viewAll.setOnClickListener {
            val intent = Intent(this@EditProfileActivity, AddInterestActivity::class.java)
            startActivityForResult(intent, 100)
        }
        choosePic.setOnClickListener {
            if (checkPicturePermission()) {
                val builder2 = AlertDialog.Builder(this)
                builder2.setMessage(getString(R.string.press_gallery))
                builder2.setCancelable(true)
                builder2.setPositiveButton(R.string.gallery) { _, _ ->
                    val pickPhoto = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(pickPhoto, 1)
                }
                builder2.setNegativeButton(R.string.camera) { _, _ ->
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

        Save.setOnClickListener {
            selectedInterestList.clear()
            for (i in interestList.indices) {
                selectedInterestList.add(interestList[i].id)
            }
            if (awsPicUrl.toString().trim() == "") {
                Toast.makeText(this, getString(R.string.choose_pic), Toast.LENGTH_LONG).show()
            } else {

                if (statusPin == "1") {
                    if (validate2()) {
                        val myDialog = ProjectUtill.showProgressDialog(this@EditProfileActivity)
                        WebServiceRequest.getInstance().editProfile(
                            this,
                            profileName.text.toString().trim(),
                            firstName.text.toString().trim(),
                            lastName.text.toString().trim(),
                            "android",
                            "en",
                            awsPicUrl,
                            edtPincode.text.toString().trim(),
                            edtEmail.text.toString().trim(),
                            edtDob.text.toString().trim(),
                            Gson().toJson(selectedInterestList),
                            edtMobile.text.toString().trim(),
                            "+91",
                            genderSpinner.selectedItem.toString(),
                            about.text.toString().trim(),
                            stateValue,
                            cityValue,
                            designation.text.toString().trim(),
                            object : Callback<EditProfileResponse> {
                                override fun onResponse(
                                    call: Call<EditProfileResponse>,
                                    response: Response<EditProfileResponse>
                                ) {
                                    myDialog.dismiss()
                                    if (response != null) {
                                        if (response.isSuccessful) {
                                            if (response.body()!!.code == 1) {
                                                FCSharedPreferances.getSharedPreferance(this@EditProfileActivity).status =
                                                    "edit"
                                                if (response.body()!!.message.equals("OTP sent on given number")) {
                                                    var intent = Intent(
                                                        this@EditProfileActivity,
                                                        OtpActivity::class.java
                                                    )
                                                    intent.putExtra(
                                                        "mobile",
                                                        edtMobile.text.toString().trim()
                                                    )
                                                    intent.putExtra(
                                                        "code",
                                                        ccp.selectedCountryCodeWithPlus.toString()
                                                    )
                                                    intent.putExtra(
                                                        "type",
                                                        "edit"
                                                    )
                                                    startActivity(intent)
                                                } else {
                                                    var intent = Intent(
                                                        this@EditProfileActivity,
                                                        HomeActivity::class.java
                                                    )
                                                    intent.flags =
                                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                    startActivity(intent)
                                                }
                                            } else {
                                                ProjectUtill.printMessage(
                                                    this@EditProfileActivity.window.decorView,
                                                    response.body()?.message
                                                )
                                            }
                                        } else {
                                            ProjectUtill.printErrorMessage(
                                                this@EditProfileActivity.window.decorView,
                                                ""
                                            )
                                        }
                                    } else {
                                        ProjectUtill.printErrorMessage(
                                            this@EditProfileActivity.window.decorView,
                                            ""
                                        )
                                    }
                                }

                                override fun onFailure(
                                    call: Call<EditProfileResponse>,
                                    t: Throwable
                                ) {
                                    myDialog.dismiss()
                                    ProjectUtill.printErrorMessage(
                                        this@EditProfileActivity.window.decorView,
                                        ""
                                    )
                                }
                            })
                    }
                } else if (statusPin == "2") {
                    if (validate()) {
                        val myDialog = ProjectUtill.showProgressDialog(this@EditProfileActivity)
                        WebServiceRequest.getInstance().editProfile(
                            this,
                            profileName.text.toString().trim(),
                            firstName.text.toString().trim(),
                            lastName.text.toString().trim(),
                            "android",
                            "en",
                            awsPicUrl,
                            pincodeValue,
                            edtEmail.text.toString().trim(),
                            edtDob.text.toString().trim(),
                            Gson().toJson(selectedInterestList),
                            edtMobile.text.toString().trim(),
                            "+91",
                            genderSpinner.selectedItem.toString(),
                            about.text.toString().trim(),
                            stateValue,
                            cityValue,
                            designation.text.toString().trim(),
                            object : Callback<EditProfileResponse> {
                                override fun onResponse(
                                    call: Call<EditProfileResponse>,
                                    response: Response<EditProfileResponse>
                                ) {
                                    myDialog.dismiss()
                                    if (response != null) {
                                        if (response.isSuccessful) {
                                            if (response.body()!!.code == 1) {
                                                FCSharedPreferances.getSharedPreferance(this@EditProfileActivity).status =
                                                    "edit"
                                                if (response.body()!!.message.equals("OTP sent on given number")) {
                                                    var intent = Intent(
                                                        this@EditProfileActivity,
                                                        OtpActivity::class.java
                                                    )
                                                    intent.putExtra(
                                                        "mobile",
                                                        edtMobile.text.toString().trim()
                                                    )
                                                    intent.putExtra(
                                                        "code",
                                                        ccp.selectedCountryCodeWithPlus.toString()
                                                    )
                                                    intent.putExtra(
                                                        "type",
                                                        "edit"
                                                    )
                                                    startActivity(intent)
                                                } else {
                                                    var intent = Intent(
                                                        this@EditProfileActivity,
                                                        HomeActivity::class.java
                                                    )
                                                    intent.flags =
                                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                    startActivity(intent)
                                                }
                                            } else {
                                                ProjectUtill.printMessage(
                                                    this@EditProfileActivity.window.decorView,
                                                    response.body()?.message
                                                )
                                            }
                                        } else {
                                            ProjectUtill.printErrorMessage(
                                                this@EditProfileActivity.window.decorView,
                                                ""
                                            )
                                        }
                                    } else {
                                        ProjectUtill.printErrorMessage(
                                            this@EditProfileActivity.window.decorView,
                                            ""
                                        )
                                    }
                                }

                                override fun onFailure(
                                    call: Call<EditProfileResponse>,
                                    t: Throwable
                                ) {
                                    myDialog.dismiss()
                                    ProjectUtill.printErrorMessage(
                                        this@EditProfileActivity.window.decorView,
                                        ""
                                    )
                                }
                            })
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                100 -> {
                    val list = data!!.getSerializableExtra("selected_data")
                    interestList.clear()
                    interestList = list as ArrayList<ShowInterestModel>
                    interestAdapter = ShowInterestAdapter(
                        this@EditProfileActivity, interestList
                    )
                    var layoutManager: GridLayoutManager? =
                        GridLayoutManager(this@EditProfileActivity, 3)
                    interest_recycler!!.layoutManager = layoutManager
                    interest_recycler!!.adapter = interestAdapter
                }
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
                .into(profilePic)
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

    fun getDate() {
        val c = Calendar.getInstance()
        mYear = c[Calendar.YEAR]
        mMonth = c[Calendar.MONTH]
        mDay = c[Calendar.DAY_OF_MONTH]
        val datePickerDialog =
            DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
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
                    var age = yourAge
                    if (age < 18) {
                        Toast.makeText(
                            this,
                            R.string.valid_age,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        fromDateValue = "$year-$month-$date"
                        try {

                            edtDob!!.text = fromDateValue
                        } catch (e: Exception) {
                        }
                    }
                }, mYear, mMonth, mDay
            )
        val c2 = Calendar.getInstance()
        c2[mYear, mMonth] = mDay
        datePickerDialog!!.datePicker.maxDate = System.currentTimeMillis();
        datePickerDialog!!.show()
    }

    private fun userDetails() {
        val myDialog = ProjectUtill.showProgressDialog(this)
        WebServiceRequest.getInstance().userDetails(
            this,
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
                                        Glide.with(this@EditProfileActivity)
                                            .load(response.body()!!.data.user.image)
                                            .placeholder(R.drawable.user_avatar)
                                            .into(profilePic!!)
                                    }
                                    profileName!!.setText(response.body()!!.data.user.profile_name)
                                    designation!!.setText(response.body()!!.data.user.designation)
                                    firstName!!.setText(response.body()!!.data.user.first_name)
                                    lastName!!.setText(response.body()!!.data.user.last_name)
                                    edtMobile!!.setText(response.body()!!.data.user.mobile)
                                    about!!.setText(response.body()!!.data.user.about)
                                    edtPincode!!.setText(response.body()!!.data.user.pincode)
                                    if (response.body()!!.data.user.dob != null || response.body()!!.data.user.dob != "") {
                                        edtDob!!.text = response.body()!!.data.user.dob
                                    }
                                    if (response.body()!!.data.user.email != null || response.body()!!.data.user.email != "") {
                                        edtEmail!!.setText(response.body()!!.data.user.email)
                                    }
                                    if (response.body()!!.data.user.countryCode.equals("")) {
                                        ccp!!.setDefaultCountryUsingPhoneCode(codee.toInt())
                                    } else {
                                        ccp!!.setDefaultCountryUsingPhoneCode(response.body()!!.data.user.countryCode.toInt())
                                    }
                                    ccp!!.setOnCountryChangeListener {
                                        ccp!!.imageViewFlag = iv_flag
                                    }
                                    ccp!!.resetToDefaultCountry()
                                    ccp!!.setDefaultCountryUsingNameCode(ccp!!.defaultCountryNameCode)

                                    val arrayAdapter1: ArrayAdapter<String> =
                                        object : ArrayAdapter<String>(
                                            this@EditProfileActivity,
                                            R.layout.spinner_layout, genderList
                                        ) {
                                            override fun isEnabled(position: Int): Boolean {
                                                return position != 0
                                            }

                                            override fun getDropDownView(
                                                position: Int, convertView: View?,
                                                parent: ViewGroup,
                                            ): View {
                                                val view = super.getDropDownView(
                                                    position,
                                                    convertView,
                                                    parent
                                                )
                                                val tv = view as TextView
                                                if (position == 0) { // Set the hint text color gray
                                                    tv.setTextColor(Color.BLACK)
                                                } else {
                                                    tv.setTextColor(resources.getColor(R.color.grey))
                                                }
                                                return view
                                            }
                                        }
                                    genderSpinner!!.adapter = arrayAdapter1
                                    if (response.body()!!.data.user.gender != null || response.body()!!.data.user.gender != "") {
                                        val spinnerPosition =
                                            arrayAdapter1.getPosition(response.body()!!.data.user.gender)
                                        genderSpinner!!.setSelection(spinnerPosition)
                                    }
                                    genderSpinner!!.onItemSelectedListener = object :
                                        AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(
                                            adapterView: AdapterView<*>?,
                                            view: View,
                                            i: Int,
                                            l: Long,
                                        ) {
                                            if (i > 0) {
                                            }
                                        }

                                        override fun onNothingSelected(adapterView: AdapterView<*>?) {}
                                    }
                                    if (response.body()!!.data.user.interest_list.size != 0) {
                                        for (i in response.body()!!.data.user.interest_list.indices) {
                                            interestList.add(
                                                ShowInterestModel(
                                                    response.body()!!.data.user.interest_list[i].interestDetails.name,
                                                    response.body()!!.data.user.interest_list[i].interestDetails.id.toString()
                                                )
                                            )
                                        }
                                        interestAdapter = ShowInterestAdapter(
                                            this@EditProfileActivity, interestList
                                        )
                                        var layoutManager: GridLayoutManager? =
                                            GridLayoutManager(this@EditProfileActivity, 3)
                                        interest_recycler!!.layoutManager = layoutManager
                                        interest_recycler!!.adapter = interestAdapter
                                    }
                                    stateValue = response.body()!!.data.user.state
                                    cityValue = response.body()!!.data.user.city
                                    pincodeValue = response.body()!!.data.user.pincode
                                    getStateList()
                                } catch (e: Exception) {
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@EditProfileActivity.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@EditProfileActivity.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@EditProfileActivity.window.decorView,
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
                        this@EditProfileActivity.window.decorView,
                        ""
                    )
                }
            })
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
        } else if (designation!!.text.toString().trim().equals("", ignoreCase = true)) {
            designation!!.requestFocus()
            designation!!.error = getString(R.string.please_designation)
            return false
        } else if (edtMobile.text.toString().trim().equals("", ignoreCase = true)) {
            edtMobile.requestFocus()
            edtMobile.error = getString(R.string.enter_mobile)
            return false
        } else if (edtMobile.text.toString().trim().length < 10) {
            edtMobile.requestFocus()
            edtMobile.error = getString(R.string.valid_mobile)
            return false
        } else if (edtEmail.text.toString().trim().equals("", ignoreCase = true)) {
            edtEmail.requestFocus()
            edtEmail.error = getString(R.string.enter_mail)
            return false
        } else if (!ProjectUtill.isValidEmailId(edtEmail!!.text.toString().trim())) {
            edtEmail!!.requestFocus()
            edtEmail!!.error = getString(R.string.valid_mail)
            return false
        } else if (edtDob.text.toString().trim().equals("", ignoreCase = true)) {
            Toast.makeText(this, getString(R.string.enter_dob), Toast.LENGTH_LONG).show()
            return false
        } else if (about.text.toString().trim().equals("", ignoreCase = true)) {
            about.requestFocus()
            about.error = getString(R.string.about)
            return false
        } else if (genderSpinner.selectedItem.toString()
                .trim() == getString(R.string.gender_select)
        ) {
            Toast.makeText(this, getString(R.string.select_gender), Toast.LENGTH_LONG).show()
            return false
        } else if (stateSpinner!!.selectedItem.equals(getString(R.string.select_state))) {
            Toast.makeText(this, getString(R.string.please_state), Toast.LENGTH_LONG).show()
            return false
        } else if (citySpinner!!.selectedItem.equals(getString(R.string.select_city))) {
            Toast.makeText(this, getString(R.string.please_city), Toast.LENGTH_LONG).show()
            return false
        } else if (pincodeSpinner!!.selectedItem.equals(getString(R.string.select_pincode))) {
            Toast.makeText(this, getString(R.string.enter_pincode), Toast.LENGTH_LONG).show()
            return false
        } else if (selectedInterestList.size == 0) {
            Toast.makeText(this, getString(R.string.select_interest), Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun validate2(): Boolean {
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
        } else if (designation!!.text.toString().trim().equals("", ignoreCase = true)) {
            designation!!.requestFocus()
            designation!!.error = getString(R.string.please_designation)
            return false
        } else if (edtMobile.text.toString().trim().equals("", ignoreCase = true)) {
            edtMobile.requestFocus()
            edtMobile.error = getString(R.string.enter_mobile)
            return false
        } else if (edtMobile.text.toString().trim().length < 10) {
            edtMobile.requestFocus()
            edtMobile.error = getString(R.string.valid_mobile)
            return false
        } else if (edtEmail.text.toString().trim().equals("", ignoreCase = true)) {
            edtEmail.requestFocus()
            edtEmail.error = getString(R.string.enter_mail)
            return false
        } else if (!ProjectUtill.isValidEmailId(edtEmail!!.text.toString().trim())) {
            edtEmail!!.requestFocus()
            edtEmail!!.error = getString(R.string.valid_mail)
            return false
        } else if (edtDob.text.toString().trim().equals("", ignoreCase = true)) {
            Toast.makeText(this, getString(R.string.enter_dob), Toast.LENGTH_LONG).show()
            return false
        } else if (about.text.toString().trim().equals("", ignoreCase = true)) {
            about.requestFocus()
            about.error = getString(R.string.about)
            return false
        } else if (genderSpinner.selectedItem.toString()
                .trim() == getString(R.string.gender_select)
        ) {
            Toast.makeText(this, getString(R.string.select_gender), Toast.LENGTH_LONG).show()
            return false
        } else if (stateSpinner!!.selectedItem.equals(getString(R.string.select_state))) {
            Toast.makeText(this, getString(R.string.please_state), Toast.LENGTH_LONG).show()
            return false
        } else if (citySpinner!!.selectedItem.equals(getString(R.string.select_city))) {
            Toast.makeText(this, getString(R.string.please_city), Toast.LENGTH_LONG).show()
            return false
        } else if (edtPincode.text.toString().trim().equals("", ignoreCase = true)) {
            edtPincode.requestFocus()
            edtPincode.error = getString(R.string.enter_pincode)
            return false
        } else if (selectedInterestList.size == 0) {
            Toast.makeText(this, getString(R.string.select_interest), Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun getStateList() {
        val myDialog = ProjectUtill.showProgressDialog(this)
        WebServiceRequest.getInstance().getStateList(
            this,
            object : Callback<StateListResponse> {
                override fun onResponse(
                    call: Call<StateListResponse>,
                    response: Response<StateListResponse>
                ) {
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                stateNameList.clear()
                                stateNameList.add(getString(R.string.select_state))
                                for (i in response.body()!!.data.state_list) {
                                    stateNameList.add(i.stateName)
                                }
                                val arrayAdapter1: ArrayAdapter<String> =
                                    object : ArrayAdapter<String>(
                                        this@EditProfileActivity,
                                        R.layout.spinner_layout, stateNameList
                                    ) {
                                        override fun isEnabled(position: Int): Boolean {
                                            return position != 0
                                        }

                                        override fun getDropDownView(
                                            position: Int, convertView: View?,
                                            parent: ViewGroup,
                                        ): View {
                                            val view = super.getDropDownView(
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
                                stateSpinner!!.adapter = arrayAdapter1
                                if (stateValue != null || stateValue != "") {
                                    val spinnerPosition =
                                        arrayAdapter1.getPosition(stateValue)
                                    stateSpinner!!.setSelection(spinnerPosition)
                                }
                                stateSpinner!!.onItemSelectedListener = object :
                                    AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(
                                        adapterView: AdapterView<*>?,
                                        view: View,
                                        i: Int,
                                        l: Long,
                                    ) {
                                        stateValue = stateNameList[i].toString()
                                        getCityList(stateNameList[i].toString())
                                    }

                                    override fun onNothingSelected(adapterView: AdapterView<*>?) {}
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@EditProfileActivity!!.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@EditProfileActivity!!.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@EditProfileActivity!!.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<StateListResponse>,
                    t: Throwable
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@EditProfileActivity!!.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun getCityList(code: String) {
        val myDialog = ProjectUtill.showProgressDialog(this)
        WebServiceRequest.getInstance().getCityList(
            this, code,
            object : Callback<CityListResponse> {
                override fun onResponse(
                    call: Call<CityListResponse>,
                    response: Response<CityListResponse>
                ) {
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                cityList.clear()
                                cityList.add(getString(R.string.select_city))
                                for (i in response.body()!!.data.city_list) {
                                    cityList.add(i.district)
                                }
                                val arrayAdapter1: ArrayAdapter<String> =
                                    object : ArrayAdapter<String>(
                                        this@EditProfileActivity,
                                        R.layout.spinner_layout, cityList
                                    ) {
                                        override fun isEnabled(position: Int): Boolean {
                                            return position != 0
                                        }

                                        override fun getDropDownView(
                                            position: Int, convertView: View?,
                                            parent: ViewGroup,
                                        ): View {
                                            val view = super.getDropDownView(
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
                                citySpinner!!.adapter = arrayAdapter1
                                if (cityValue != null || cityValue != "") {
                                    val spinnerPosition =
                                        arrayAdapter1.getPosition(cityValue)
                                    citySpinner!!.setSelection(spinnerPosition)
                                }
                                citySpinner!!.onItemSelectedListener = object :
                                    AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(
                                        adapterView: AdapterView<*>?,
                                        view: View,
                                        i: Int,
                                        l: Long,
                                    ) {
                                        if (i > 0) {
                                            cityValue = cityList[i].toString()
                                            getPincodeList(stateValue!!, cityList[i].toString())
                                        }
                                    }

                                    override fun onNothingSelected(adapterView: AdapterView<*>?) {}
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@EditProfileActivity!!.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@EditProfileActivity!!.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@EditProfileActivity!!.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<CityListResponse>,
                    t: Throwable
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@EditProfileActivity!!.window.decorView,
                        ""
                    )
                }
            })
    }

    private fun getPincodeList(state: String, city: String) {
        val myDialog = ProjectUtill.showProgressDialog(this)
        WebServiceRequest.getInstance().pincodeList(
            this, state, city,
            object : Callback<PincodeListResponse> {
                override fun onResponse(
                    call: Call<PincodeListResponse>,
                    response: Response<PincodeListResponse>
                ) {
                    myDialog.dismiss()
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == 1) {
                                pincodeList.clear()
                                pincodeList.add(getString(R.string.select_pincode))
                                for (i in response.body()!!.data.pincodes) {
                                    pincodeList.add(i.pincode.toString())
                                }
                                if (pincodeList.size == 1) {
                                    statusPin = "1"
                                    pincodeLayout.visibility = View.GONE
                                    pincodeLayout2.visibility = View.VISIBLE
                                } else {
                                    statusPin = "2"
                                    pincodeLayout.visibility = View.VISIBLE
                                    pincodeLayout2.visibility = View.GONE
                                }
                                val arrayAdapter1: ArrayAdapter<String> =
                                    object : ArrayAdapter<String>(
                                        this@EditProfileActivity!!,
                                        R.layout.spinner_layout, pincodeList
                                    ) {
                                        override fun isEnabled(position: Int): Boolean {
                                            return position != 0
                                        }

                                        override fun getDropDownView(
                                            position: Int, convertView: View?,
                                            parent: ViewGroup,
                                        ): View {
                                            val view = super.getDropDownView(
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
                                pincodeSpinner!!.adapter = arrayAdapter1
                                if (pincodeValue == null) {
                                    Log.d("VALUE", "fsnngjg")
                                } else {
                                    val spinnerPosition =
                                        arrayAdapter1.getPosition(pincodeValue)
                                    pincodeSpinner!!.setSelection(spinnerPosition)
                                }
                                pincodeSpinner!!.onItemSelectedListener = object :
                                    AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(
                                        adapterView: AdapterView<*>?,
                                        view: View,
                                        i: Int,
                                        l: Long,
                                    ) {
                                        pincodeValue = pincodeList[i].toString()
                                    }

                                    override fun onNothingSelected(adapterView: AdapterView<*>?) {}
                                }
                            } else {
                                ProjectUtill.printMessage(
                                    this@EditProfileActivity!!.window.decorView,
                                    response.body()?.message
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                this@EditProfileActivity!!.window.decorView,
                                ""
                            )
                        }
                    } else {
                        ProjectUtill.printErrorMessage(
                            this@EditProfileActivity!!.window.decorView,
                            ""
                        )
                    }
                }

                override fun onFailure(
                    call: Call<PincodeListResponse>,
                    t: Throwable
                ) {
                    myDialog.dismiss()
                    ProjectUtill.printErrorMessage(
                        this@EditProfileActivity!!.window.decorView,
                        ""
                    )
                }
            })
    }
}