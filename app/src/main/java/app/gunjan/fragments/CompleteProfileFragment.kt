package app.gunjan.fragments

import android.app.Activity
import android.app.AlertDialog
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
import app.gunjan.entity.*
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
import kotlinx.android.synthetic.main.activity_edit_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.net.UnknownServiceException
import java.util.*

class CompleteProfileFragment : Fragment(), UploadFileListener {
    private var pathPic = ""
    private var statusPin = ""
    private var awsPicUrl = ""
    private var stateValue:String? = null
    private var pincodeValue:String? = null
    private var cityValue:String? = null
    var progressdialog: ProgressDialog? = null
    private var choosePic: RelativeLayout? = null
    private var profilePic: CircleImageView? = null
    private var profileName: EditText? = null
    private var firstName: EditText? = null
    private var lastName: EditText? = null
    private var designation: EditText? = null
    private var edtEmail: EditText? = null
    private var Continue: LinearLayout? = null
    var citySpinner: Spinner? = null
    var stateSpinner: Spinner? = null
    var pincodeSpinner: Spinner? = null
    var pincodeLayout: LinearLayout? = null
    var pincodeLayout2: LinearLayout? = null
    var edtPincode: EditText? = null
    private var stateNameList: ArrayList<String> = ArrayList<String>()
    private var cityList: ArrayList<String> = ArrayList<String>()
    private var pincodeList: ArrayList<String> = ArrayList<String>()
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
        Continue = view.findViewById(R.id.Continue)
        citySpinner = view.findViewById(R.id.city_spinner)
        stateSpinner = view.findViewById(R.id.state_spinner)
        pincodeSpinner = view.findViewById(R.id.pincode_spinner)
        designation = view.findViewById(R.id.edt_designation)
        pincodeLayout = view.findViewById(R.id.pincodeLayout)
        pincodeLayout2 = view.findViewById(R.id.pincodeLayout2)
        edtPincode = view.findViewById(R.id.edtPincode)
        edtEmail = view.findViewById(R.id.edtEmail)
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
                if (statusPin == "1") {
                    if (validate2()) {
                         val myDialog = ProjectUtill.showProgressDialog(context)
                    context?.let { it1 ->
                        WebServiceRequest.getInstance().completeProfile(
                            it1, profileName!!.text.toString().trim(),
                            firstName!!.text.toString().trim(),
                            lastName!!.text.toString().trim(),
                            "android", "en", awsPicUrl,edtPincode!!.text.toString().trim(),
                            stateValue!!,cityValue!!,
                            designation!!.text.toString().trim(),
                            edtEmail!!.text.toString().trim(),
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
                }else if (statusPin=="2") {
                    if (validate()) {
                        val myDialog = ProjectUtill.showProgressDialog(context)
                        context?.let { it1 ->
                            WebServiceRequest.getInstance().completeProfile(
                                it1, profileName!!.text.toString().trim(),
                                firstName!!.text.toString().trim(),
                                lastName!!.text.toString().trim(),
                                "android", "en", awsPicUrl, pincodeValue!!,
                                stateValue!!, cityValue!!,
                                designation!!.text.toString().trim(),
                                edtEmail!!.text.toString().trim(),
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
        }

        profileName!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                var result: String = editable.toString().replace(" ", "");
                if (editable.toString() != result) {
                    profileName!!.setText(result)
                    profileName!!.setSelection(result.length)
                }
            }
        })
        choosePic!!.setOnClickListener {
            if (checkPicturePermission()) {
                val builder2 = AlertDialog.Builder(context)
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
        }else if (edtEmail!!.text.toString().trim().equals("", ignoreCase = true)) {
            edtEmail!!.requestFocus()
            edtEmail!!.error = getString(R.string.enter_mail)
            return false
        } else if (!ProjectUtill.isValidEmailId(edtEmail!!.text.toString().trim())) {
            edtEmail!!.requestFocus()
            edtEmail!!.error = getString(R.string.valid_mail)
            return false
        }else if (designation!!.text.toString().trim().equals("", ignoreCase = true)) {
            designation!!.requestFocus()
            designation!!.error = getString(R.string.please_designation)
            return false
        } else if (stateSpinner!!.selectedItem.equals(getString(R.string.select_state))) {
            Toast.makeText(context, getString(R.string.please_state), Toast.LENGTH_LONG).show()
            return false
        } else if (citySpinner!!.selectedItem.equals(getString(R.string.select_city))) {
            Toast.makeText(context, getString(R.string.please_city), Toast.LENGTH_LONG).show()
            return false
        } else if (pincodeSpinner!!.selectedItem.equals(getString(R.string.select_pincode))) {
            Toast.makeText(context, getString(R.string.enter_pincode), Toast.LENGTH_LONG).show()
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
        }else if (edtEmail!!.text.toString().trim().equals("", ignoreCase = true)) {
            edtEmail!!.requestFocus()
            edtEmail!!.error = getString(R.string.enter_mail)
            return false
        } else if (!ProjectUtill.isValidEmailId(edtEmail!!.text.toString().trim())) {
            edtEmail!!.requestFocus()
            edtEmail!!.error = getString(R.string.valid_mail)
            return false
        }else if (designation!!.text.toString().trim().equals("", ignoreCase = true)) {
            designation!!.requestFocus()
            designation!!.error = getString(R.string.please_designation)
            return false
        } else if (stateSpinner!!.selectedItem.equals(getString(R.string.select_state))) {
            Toast.makeText(context, getString(R.string.please_state), Toast.LENGTH_LONG).show()
            return false
        } else if (citySpinner!!.selectedItem.equals(getString(R.string.select_city))) {
            Toast.makeText(context, getString(R.string.please_city), Toast.LENGTH_LONG).show()
            return false
        }else if (edtPincode!!.text.toString().trim().equals("", ignoreCase = true)) {
            edtPincode!!.requestFocus()
            edtPincode!!.error = getString(R.string.enter_pincode)
            return false
        }
        return true
    }

    private fun userDetails() {
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
                                        if (response.body()!!.data.user.designation != null || response.body()!!.data.user.designation != "") {
                                            designation!!.setText(response.body()!!.data.user.designation)
                                        }
                                        if (response.body()!!.data.user.pincode==null) {
                                            pincodeValue=null
                                        }else{
                                            pincodeValue = response.body()!!.data.user.pincode
                                            edtPincode!!.setText(response.body()!!.data.user.pincode)
                                        }

                                        if (response.body()!!.data.user.state==null) {
                                            stateValue=null
                                        }else{
                                            stateValue = response.body()!!.data.user.state
                                        }
                                        if (response.body()!!.data.user.city==null) {
                                            cityValue=null
                                        }else{
                                            cityValue = response.body()!!.data.user.city
                                        }
                                        getStateList()
                                    } catch (e: Exception) {
                                    }
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

    private fun getStateList() {
        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let {
            WebServiceRequest.getInstance().getStateList(
                it,
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
                                            context!!,
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
                                    if (stateValue == null) {
                                        Log.d("VALUE","fsnngjg")
                                    }else{
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
                                            stateValue=stateNameList[i].toString()
                                            getCityList(stateNameList[i].toString())
                                        }

                                        override fun onNothingSelected(adapterView: AdapterView<*>?) {}
                                    }
                                } else {
                                    ProjectUtill.printMessage(
                                        activity!!.window.decorView,
                                        response.body()?.message
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    activity!!.window.decorView,
                                    ""
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                activity!!.window.decorView,
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
                            activity!!.window.decorView,
                            ""
                        )
                    }
                })
        }
    }

    private fun getCityList(code: String) {
        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let {
            WebServiceRequest.getInstance().getCityList(
                it, code,
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
                                            context!!,
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
                                    if (cityValue == null) {
                                        Log.d("VALUE","kfsdj")
                                    }else{
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
                                                cityValue=cityList[i].toString()
                                                getPincodeList(stateValue!!,cityList[i].toString())
                                        }

                                        override fun onNothingSelected(adapterView: AdapterView<*>?) {}
                                    }
                                } else {
                                    ProjectUtill.printMessage(
                                        activity!!.window.decorView,
                                        response.body()?.message
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    activity!!.window.decorView,
                                    ""
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                activity!!.window.decorView,
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
                            activity!!.window.decorView,
                            ""
                        )
                    }
                })
        }
    }

    private fun getPincodeList(state:String,city:String) {
        val myDialog = ProjectUtill.showProgressDialog(context)
        context?.let {
            WebServiceRequest.getInstance().pincodeList(
                it,state,city,
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
                                        pincodeLayout!!.visibility = View.GONE
                                        pincodeLayout2!!.visibility = View.VISIBLE
                                    } else {
                                        statusPin = "2"
                                        pincodeLayout!!.visibility = View.VISIBLE
                                        pincodeLayout2!!.visibility = View.GONE
                                    }
                                    val arrayAdapter1: ArrayAdapter<String> =
                                        object : ArrayAdapter<String>(
                                            context!!,
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
                                        Log.d("VALUE","fsnngjg")
                                    }else{
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
                                            pincodeValue=pincodeList[i].toString()
                                        }

                                        override fun onNothingSelected(adapterView: AdapterView<*>?) {}
                                    }
                                } else {
                                    ProjectUtill.printMessage(
                                        activity!!.window.decorView,
                                        response.body()?.message
                                    )
                                }
                            } else {
                                ProjectUtill.printErrorMessage(
                                    activity!!.window.decorView,
                                    ""
                                )
                            }
                        } else {
                            ProjectUtill.printErrorMessage(
                                activity!!.window.decorView,
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
                            activity!!.window.decorView,
                            ""
                        )
                    }
                })
        }
    }
}