package com.kolorfox.watertracker.waterservices

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.kolorfox.watertracker.data.ShareReference

class BedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        ShareReference.setCheckBedTime(false, context)


    }
}
