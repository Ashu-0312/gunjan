package app.gunjan.activities

import android.content.Intent
import android.os.Bundle
import app.gunjan.R
import app.gunjan.utill.FCSharedPreferances
import kotlinx.android.synthetic.main.activity_choose_language.*

class ChooseLanguageActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_language)
        initData()
    }

    private fun initData() {
        back.setOnClickListener { finish() }

        rl_layout.setOnClickListener {
            FCSharedPreferances.getSharedPreferance(this@ChooseLanguageActivity).savE_LANG =
                "en"
            val intent = Intent(this@ChooseLanguageActivity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        rl_layout1.setOnClickListener {
            FCSharedPreferances.getSharedPreferance(this@ChooseLanguageActivity).savE_LANG =
                "hi"
            val intent = Intent(this@ChooseLanguageActivity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }
}