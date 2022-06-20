package app.gunjan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import app.gunjan.R
import app.gunjan.utill.FCSharedPreferances
import java.util.*

class SplashActivity : AppCompatActivity() {
    var myLocale: Locale? = null
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
        FCSharedPreferances.getSharedPreferance(this).totaL_COINS = ""
        handler = Handler(Looper.getMainLooper())
        handler!!.postDelayed(Runnable {
            if (FCSharedPreferances.getSharedPreferance(this@SplashActivity).savE_LANG.equals("")){
                setLocale("en")
            }else if (FCSharedPreferances.getSharedPreferance(this@SplashActivity).savE_LANG.equals("en")){
                setLocale("en")
            }else{
                setLocale("hi")
            }
            FCSharedPreferances.getSharedPreferance(this@SplashActivity).status =
                ""
            if (FCSharedPreferances.getSharedPreferance(this@SplashActivity).statuS_LOGIN.equals("true")) {
                val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                if (FCSharedPreferances.getSharedPreferance(this).firsT_TIME.equals("true")) {
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }else{
                    FCSharedPreferances.getSharedPreferance(this).firsT_TIME = "true"
                    val intent = Intent(this@SplashActivity, ChooseLanguageActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
        }, 3000)
    }

    override fun onPause() {
        super.onPause()
        handler!!.removeCallbacksAndMessages(null)
    }

    fun setLocale(localeName: String) {
        myLocale = Locale(localeName)
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
        FCSharedPreferances.getSharedPreferance(this@SplashActivity).savE_LANG = localeName
    }
}