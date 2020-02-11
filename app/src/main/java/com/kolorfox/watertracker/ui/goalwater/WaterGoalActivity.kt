package com.kolorfox.watertracker.ui.goalwater

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kolorfox.watertracker.R
import com.kolorfox.watertracker.data.ShareReference
import com.kolorfox.watertracker.waterservices.BedReceiver
import com.kolorfox.watertracker.ui.watertrackermain.MainActivity
import com.kolorfox.watertracker.waterservices.WakeeUpReceiver
import kotlinx.android.synthetic.main.activity_water_goal.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


class WaterGoalActivity : AppCompatActivity() {
    private var alarmManager: AlarmManager? = null
    private lateinit var alarmIntents: PendingIntent

    private var alarmManager2: AlarmManager? = null
    private lateinit var alarmIntents2: PendingIntent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_goal)
        getData()
        alarmWakeBed()
        nextActivity()
    }

    private fun nextActivity() {
        btn_go.setOnClickListener {
            ShareReference.setCheckHistoryLogin(true, this)
            ShareReference.setGoalDrink(tv_water_goal.text.toString(), this)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    fun alarmWakeBed() {
        val sdf2 =
            SimpleDateFormat("HH:mm")// here set the pattern as you date in string was containing like date/month/year
        val bed = ShareReference.getBedTimes(this)
        val wakeUp = ShareReference.getWakeUpTime(this)
        val d = sdf2.parse(bed)
        val d2 = sdf2.parse((wakeUp))
        val cal = Calendar.getInstance()
        cal.time = d
        val hoursBed = cal.get(Calendar.HOUR_OF_DAY)
        val minutesBed = cal.get(Calendar.MINUTE)
        Log.i("bed", "$hoursBed")
        Log.i("bed", "$minutesBed")
        cal.time = d2
        val hoursWake = cal.get(Calendar.HOUR_OF_DAY)
        val minutesWake = cal.get(Calendar.MINUTE)


        alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntents = Intent(this, BedReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(this, 2, intent, 0)
        }
        val calendar3: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hoursBed)
            set(Calendar.MINUTE, minutesBed)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        alarmManager?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar3.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmIntents
        )

        alarmManager2 = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntents2 = Intent(this, WakeeUpReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(this, 3, intent, 0)
        }
        val calendar4: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hoursWake)
            set(Calendar.MINUTE, minutesWake)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        alarmManager2?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar4.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmIntents2
        )
    }

    private fun getData() {

        val weight = ShareReference.getWeights(this)
        val weightFloat = weight!!.replace("[^\\d.]".toRegex(), "").toFloat()
        val WeightInt = weightFloat.toInt()
        val ml = WeightInt / 0.03
        val twoDForm = DecimalFormat("#")
        val waterGoadl = java.lang.Double.valueOf(twoDForm.format(ml)).toInt()
        tv_water_goal.text = "$waterGoadl"
    }
}
