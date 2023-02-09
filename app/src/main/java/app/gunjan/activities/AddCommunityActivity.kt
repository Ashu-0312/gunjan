package app.gunjan.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
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
import app.gunjan.entity.UploadS3FileResponse
import app.gunjan.utill.FCSharedPreferances
import app.gunjan.utill.PermissionUtil
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_add_community.*
import kotlinx.android.synthetic.main.activity_add_community.back
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.util.*
import kotlin.collections.ArrayList

class AddCommunityActivity : BaseActivity(){
    private var pathPic = ""
    private var awsPicUrl = ""
    private var categoryId = ""
    private var nameList: ArrayList<String> = ArrayList<String>()
    private val idList: ArrayList<String> = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_community)
        initData()
    }

    private fun initData() {
        getCategoryList()
        back.setOnClickListener { finish() }

        community.addTextChangedListener(object : TextWatcher {
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
                nameCount.text =
                    (50 - s.toString().length).toString() + "/50" + getString(R.string.fifty_char)
            }
        })

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
                descCount.text =
                    (250 - s.toString().length).toString() + "/250" + getString(R.string.twofifty_char)
            }
        })

        Submit.setOnClickListener {
            if (awsPicUrl.toString().trim() == "") {
                Toast.makeText(this, getString(R.string.logo_coomunity), Toast.LENGTH_LONG).show()
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
                                            FCSharedPreferances.getSharedPreferance(this@AddCommunityActivity).statuS_LOGIN =
                                                "true"
                                            var intent =
                                                Intent(
                                                    this@AddCommunityActivity,
                                                    HomeActivity::class.java
                                                )
                                            intent.flags =
                                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            startActivity(intent)
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
                builder2.setMessage(R.string.press_gallery)
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
                    uploadFile()
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
            uploadFile()
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
                                    awsPicUrl = response.body()!!.data.path_data.path
                                    Glide.with(this@AddCommunityActivity)
                                        .load(awsPicUrl)
                                        .placeholder(R.drawable.user_avatar)
                                        .into(pic!!)
                                }catch (e: Exception) {}
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
                    call: Call<UploadS3FileResponse>,
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

    private fun validate(): Boolean {
        if (community!!.text.toString().trim().equals("", ignoreCase = true)) {
            community!!.requestFocus()
            community!!.error = getString(R.string.please_community_name)
            return false
        } else if (about!!.text.toString().trim().equals("", ignoreCase = true)) {
            about!!.requestFocus()
            about!!.error = getString(R.string.about_community)
            return false
        } else if (categorySpinner!!.selectedItem.toString().trim()
                .equals(getString(R.string.select_category))
        ) {
            Toast.makeText(this, getString(R.string.please_category), Toast.LENGTH_LONG).show()
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
                                nameList.add(getString(R.string.select_category))
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
                                            categoryId = idList[i].toString()
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
