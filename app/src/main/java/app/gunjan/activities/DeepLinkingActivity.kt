package app.gunjan.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import app.gunjan.utill.ProjectUtill

class DeepLinkingActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appLinkAction: String? = intent?.action
        val appLinkData: Uri? = intent?.data
        Log.e("Gunjan", "${appLinkData}")
        Log.e("Gunjan", "${appLinkAction}")
        if (Intent.ACTION_VIEW == appLinkAction && appLinkData != null) {
            //http://gunjanapp.com/post/747/
            val url = appLinkData.toString().split("/")
            if (appLinkData.toString().contains("gunjanapp.com/post")) {
                //Post
                startActivity(
                    Intent(
                        this@DeepLinkingActivity,
                        PostDetailsActivity::class.java
                    ).apply {
                        putExtra("id", ProjectUtill.deCodeId(url.get(url.size - 1)))
                        putExtra("type", "deep_link")
                    })
            } else {
                //Community
                startActivity(
                    Intent(
                        this@DeepLinkingActivity,
                        CommunityDetailsActivity::class.java
                    ).apply {
                        putExtra("id", ProjectUtill.deCodeId("${url.get(url.size - 1)}"))
                        putExtra("type", "deep_link")
                    })
            }
            finish()
        }
    }
}