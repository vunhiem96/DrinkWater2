package com.kolorfox.watertracker.ui.startactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.android.gms.ads.MobileAds
import com.kolorfox.watertracker.R
import com.kolorfox.watertracker.data.ShareReference
import com.kolorfox.watertracker.ui.watertrackermain.MainActivity

class StartActivityOne : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_one)
        loadGoogleAds()
        startScreen()
    }

    private fun loadGoogleAds() {
        val idApp = getString(R.string.app_id)
        var dialog = ShareReference.getFirstTimeLogin(this)
        if (dialog == false) {
            MobileAds.initialize(
                this,
                idApp
            )

        }
    }

    private fun startScreen() {
        val check = ShareReference.getHistoryLogin(this)
        if(check == false){
        val handler = Handler()
        handler.postDelayed({ screenSwith() }, 2100)}
        else
        {
            val handler = Handler()
            handler.postDelayed({ screenSwith() }, 0)}
        }




    private fun screenSwith() {
        val check = ShareReference.getHistoryLogin(this)
        if (check == false) {
            val intent = Intent(this, StartActivitySecond::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

