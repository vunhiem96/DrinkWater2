package com.kolorfox.watertracker.waterservices

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import com.kolorfox.watertracker.data.model.WaterMonth
import com.kolorfox.watertracker.data.model.WaterWeek
import com.kolorfox.watertracker.data.model.Weight
import com.kolorfox.watertracker.data.WaterDatabase
import com.kolorfox.watertracker.data.ShareReference
import java.text.SimpleDateFormat
import java.util.*

class NewsDaysReceiver : BroadcastReceiver() {
    lateinit var db: WaterDatabase
    var check: Boolean? = false
    private val NOTIFICATION_ID = 144
    val CHANNEL_ID = "1"
    private var notification2: Notification? = null
    private val NOTIFICATION_ID2 = 145
    val CHANNEL_ID2 = "2"
    override fun onReceive(context: Context, intent: Intent) {
        db = WaterDatabase(context)
        ShareReference.setWaterlevel(0, context)
        ShareReference.setCount(0, context)
        ShareReference.setCheckFull(false, context)
        db.deleteHistory()

        val handler3 = Handler()
        handler3.postDelayed({
            val calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_WEEK)
            if (day == Calendar.MONDAY) {
                val mon = WaterWeek(2, 0, 0, 0)
                val tue = WaterWeek(3, 0, 0, 0)
                val wed = WaterWeek(4, 0, 0, 0)
                val thu = WaterWeek(5, 0, 0, 0)
                val fri = WaterWeek(6, 0, 0, 0)
                val sar = WaterWeek(7, 0, 0, 0)
                val sun = WaterWeek(8, 0, 0, 0)
                db.updateWaterWeek(mon)
                db.updateWaterWeek(tue)
                db.updateWaterWeek(thu)
                db.updateWaterWeek(fri)
                db.updateWaterWeek(wed)
                db.updateWaterWeek(sar)
                db.updateWaterWeek(sun)

                val monw = Weight(2, 0f)
                val tuew = Weight(3, 0f)
                val wedw = Weight(4, 0f)
                val thuw = Weight(5, 0f)
                val friw = Weight(6, 0f)
                val sarw = Weight(7, 0f)
                val sunw = Weight(8, 0f)
                db.updateWeightWeek(monw)
                db.updateWeightWeek(tuew)
                db.updateWeightWeek(wedw)
                db.updateWeightWeek(thuw)
                db.updateWeightWeek(friw)
                db.updateWeightWeek(sarw)
                db.updateWeightWeek(sunw)
            }
            val c = Calendar.getInstance()
            val sdf2 = SimpleDateFormat("dd")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            if (currentDate2 == 1) {
                for (i in 1..31) {
                    db.updateWaterMonth(WaterMonth(i, 0, 0, 0))
                    db.updateWeightMonth(Weight(i, 0f))
                }
            }
            Log.i("0h00", "resetall")

        }, 62000)




    }

}