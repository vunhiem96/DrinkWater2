package com.kolorfox.watertracker.ui.privacypolice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kolorfox.watertracker.R
import kotlinx.android.synthetic.main.activity_privacy_police.*

class PrivacyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_police)
        webView.loadUrl("https://kolorfox.com/drinkwater/privacy_policy.html")
        img_back.setOnClickListener {
            finish()
        }
    }
}
