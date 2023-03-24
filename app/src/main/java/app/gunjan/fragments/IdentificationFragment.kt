package app.gunjan.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import app.gunjan.R
import app.gunjan.activities.SetProfileActivity
import app.gunjan.entity.AddIdentityResponse
import app.gunjan.entity.UploadS3FileResponse
import app.gunjan.entity.UserDetailsResponse
import app.gunjan.utill.PermissionUtil
import app.gunjan.utill.ProjectUtill
import app.gunjan.webservices.WebServiceRequest
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.util.*

class IdentificationFragment : Fragment(){
    private var pathPic = ""
    private var awsPicUrl = ""
    private var Continue: LinearLayout? = null
    private var choosePic: CardView? = null
    private var layout: CardView? = null
    private var layout2: LinearLayout? = null
    private var idPic: ImageView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_identification, container, false)
        Continue = view.findViewById(R.id.AddPhoto)
        idPic = view.findViewById(R.id.identityPic)
        layout = view.findViewById(R.id.layout)
        layout2 = view.findViewById(R.id.layout2)
        choosePic = view.findViewById(R.id.choosePic)
        initData()
        return view
    }

    private fun initData() {
        userDetails()
        Continue!!.setOnClickListener {
            if (awsPicUrl.toString().trim() == "") {
                Toast.makeText(context, getString(R.string.choose_pic), Toast.LENGTH_LONG).show()
            } else {
                val myDialog = ProjectUtill.showProgressDialog(context)
                context?.let { it1 ->
                    WebServiceRequest.getInstance().addIdenificationFile(
                        it1, awsPicUrl,
                        object : Callback<AddIdentityResponse> {
                            override fun onResponse(
                                call: Call<AddIdentityResponse>,
                                response: Response<AddIdentityResponse>
                            ) {
                                myDialog.dismiss()
                                if (response != null) {
                                    if (response.isSuccessful) {
                                        if (response.body()!!.code == 1) {
                                            (activity as SetProfileActivity).loadAboutFragment()
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
                                call: Call<AddIdentityResponse>,
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
        choosePic!!.setOnClickListener {
            if (checkPicturePermission()) {
                val builder2 = AlertDialog.Builder(context)
                builder2.setMessage(getString(R.string.press_gallery))
                builder2.setCancelable(true)
                builder2.setPositiveButton(getString(R.string.gallery)) { _, _ ->
                    val pickPhoto = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(pickPhoto, 1)
                }
                builder2.setNegativeButton(getString(R.string.camera)) { _, _ ->
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
                    uploadFile()
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

    private fun uploadFile() {
        val myDialog = ProjectUtill.showProgressDialog(context)
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
                                    layout2!!.visibility = View.GONE
                                    layout!!.visibility = View.VISIBLE
                                    awsPicUrl = response.body()!!.data.path_data.path
                                    context?.let {
                                        Glide.with(it)
                                            .load(awsPicUrl)
                                            .placeholder(R.drawable.placeholder)
                                            .into(idPic!!)
                                    }
                                }catch (e: Exception) {}
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
                    call: Call<UploadS3FileResponse>,
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
                                        if (response.body()!!.data.user.identification_file!= null){
                                            layout2!!.visibility = View.GONE
                                            layout!!.visibility = View.VISIBLE
                                            awsPicUrl= response.body()!!.data.user.identification_file
                                            Glide.with(context!!).load(response.body()!!.data.user.identification_file).placeholder(R.drawable.placeholder).into(idPic!!)
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