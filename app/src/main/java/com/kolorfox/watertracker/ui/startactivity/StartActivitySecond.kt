package com.kolorfox.watertracker.ui.startactivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kolorfox.watertracker.R
import com.kolorfox.watertracker.ui.userdata.UsersDataActivity
import kotlinx.android.synthetic.main.activity_start_second.*

class StartActivitySecond : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_second)
        startActivity()
    }

    private fun startActivity() {
        btn_start2.setOnClickListener {
            val intent = Intent(this, UsersDataActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
