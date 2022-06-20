package app.gunjan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.gunjan.R
import app.gunjan.utill.FCSharedPreferances
import kotlinx.android.synthetic.main.activity_choose_language.*
import java.util.*

class ChooseLanguageActivity : AppCompatActivity() {
    private var myLocale:Locale?=null
    private var currentLang:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_language)
        initData()
    }

    private fun initData() {
        back.setOnClickListener { finish() }

        rl_layout.setOnClickListener {
            setLocale("en")
        }

        rl_layout1.setOnClickListener {
            setLocale("hi")
        }
    }
    fun setLocale(localeName: String?) {
        myLocale = Locale(localeName)
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
        FCSharedPreferances.getSharedPreferance(this@ChooseLanguageActivity).savE_LANG = localeName
        val refresh = Intent(this, LoginActivity::class.java)
        refresh.putExtra(currentLang, localeName)
        startActivity(refresh)
        finish()
    }
}