package com.kolorfox.watertracker.waterservices

import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.os.Handler
import android.util.Log

import androidx.annotation.RequiresApi
import com.kolorfox.watertracker.data.model.WaterMonth
import com.kolorfox.watertracker.data.model.WaterWeek
import com.kolorfox.watertracker.data.model.Weight
import com.kolorfox.watertracker.data.WaterDatabase
import com.kolorfox.watertracker.data.ShareReference
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
internal class MyJobService : JobService() {
    lateinit var db: WaterDatabase
    override fun onStartJob(params: JobParameters): Boolean {

        val calendar2: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 0)


        }
        db = WaterDatabase(this)
        val handler3 = Handler()
        handler3.postDelayed({
            db.deleteHistory()
            ShareReference.setWaterlevel(0, this)
            Log.i("test26", "0h00 JobService")
            ShareReference.setCount(0, this)
            ShareReference.setCheckFull(false, this)
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

        }, calendar2.timeInMillis)


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
        Log.i("bed", "$hoursWake")
        Log.i("bed", "$minutesWake")
        val calendar3: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hoursBed)
            set(Calendar.MINUTE, minutesBed)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val calendar4: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hoursWake)
            set(Calendar.MINUTE, minutesWake)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val handler = Handler()
        handler.postDelayed({

            ShareReference.setCheckBedTime(false, this)

        }, calendar3.timeInMillis)

        val handler2 = Handler()
        handler2.postDelayed({

            ShareReference.setCheckBedTime(true, this)

        }, calendar4.timeInMillis)




        return false
    }

    override fun onStopJob(params: JobParameters): Boolean {
        return false
    }

    companion object {
        private val JOB_ID = 1
        private val ONE_DAY_INTERVAL = 24 * 60 * 60 * 1000L // 1 Day
        private val ONE_WEEK_INTERVAL = 7 * 24 * 60 * 60 * 1000L // 1 Week

        fun schedule(context: Context, intervalMillis: Long) {
            val jobScheduler =
                context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            val componentName = ComponentName(context, MyJobService::class.java)
            val builder = JobInfo.Builder(JOB_ID, componentName)
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            builder.setPeriodic(intervalMillis)
            jobScheduler.schedule(builder.build())
        }


    }
}
