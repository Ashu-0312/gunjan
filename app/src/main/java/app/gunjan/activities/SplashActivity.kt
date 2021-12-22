package app.gunjan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import app.gunjan.R

class SplashActivity : AppCompatActivity() {
    private var handler: Handler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        initHandler()
    }

    private fun initHandler() {
        handler = Handler()
        handler!!.postDelayed(Runnable {

            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }, 3000)
    }

    override fun onPause() {
        super.onPause()
        handler!!.removeCallbacksAndMessages(null)
    }
}