package app.gunjan.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import app.gunjan.R
import app.gunjan.adapters.AddEditInterestAdapter
import kotlinx.android.synthetic.main.activity_add_interest.*

class AddInterestActivity : AppCompatActivity() {
    private var list: ArrayList<Model> = ArrayList<Model>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_interest)
        initData()
    }

    private fun initData() {
        list.add(Model("", false))
        list.add(Model("", false))
        list.add(Model("", false))
        list.add(Model("", false))
        list.add(Model("", false))
        list.add(Model("", false))
        list.add(Model("", false))
        list.add(Model("", false))
        list.add(Model("", false))
        list.add(Model("", false))
        var interestAdapter = AddEditInterestAdapter(
            this, list
        )
        var layoutManager: GridLayoutManager? = GridLayoutManager(this, 3)
        interestRecycler!!.layoutManager = layoutManager
        interestRecycler!!.adapter = interestAdapter

        back.setOnClickListener { finish() }

        Save.setOnClickListener { finish() }
    }
}