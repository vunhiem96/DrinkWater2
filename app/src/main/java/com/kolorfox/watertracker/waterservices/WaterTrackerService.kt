package com.kolorfox.watertracker.waterservices

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.os.*
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kolorfox.watertracker.R
import com.kolorfox.watertracker.data.ShareReference
import com.kolorfox.watertracker.data.WaterDatabase
import com.kolorfox.watertracker.data.model.WaterMonth
import com.kolorfox.watertracker.data.model.WaterWeek
import com.kolorfox.watertracker.data.model.Weight
import com.kolorfox.watertracker.ui.watertrackermain.MainActivity
import java.text.SimpleDateFormat
import java.util.*


class WaterTrackerService : Service() {
    override fun onCreate() {
        super.onCreate()


        vibrator = applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        CreateChanel()
        reSet()
        CreateNotification()
        dataBase()
        registersReceiver()
    }

    private fun registersReceiver() {


        applicationContext!!.registerReceiver(receiverService, IntentFilter("resetWater"))
        applicationContext!!.registerReceiver(receiverService, IntentFilter("notificationWater"))
        applicationContext!!.registerReceiver(receiverService, IntentFilter("resetall"))
        applicationContext!!.registerReceiver(receiverService, IntentFilter("addWaterMon"))
        applicationContext!!.registerReceiver(receiverService, IntentFilter("addWaterTue"))
        applicationContext!!.registerReceiver(receiverService, IntentFilter("addWaterWed"))
        applicationContext!!.registerReceiver(receiverService, IntentFilter("addWaterFri"))
        applicationContext!!.registerReceiver(receiverService, IntentFilter("addWaterSar"))
        applicationContext!!.registerReceiver(receiverService, IntentFilter("addWaterSun"))
        applicationContext!!.registerReceiver(receiverService, IntentFilter("addWaterThu"))
        applicationContext!!.registerReceiver(receiverService, IntentFilter("resetallMonth"))
    }

    fun vibrate() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(1200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(1200)
        }

    }


    private fun CreateChanel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNotificationChannel()
            val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID2)
                .setSmallIcon(R.drawable.ic_app)
                .setContentTitle("WaterTraker active")
                .setOnlyAlertOnce(true)
                .setSound(null)
                .setAutoCancel(true)
            notification2 = builder.build()
            with(NotificationManagerCompat.from(applicationContext)) {
                notify(
                    NOTIFICATION_ID2,
                    notification2!!
                )
            }
            startForeground(NOTIFICATION_ID2, notification2)

        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Water Tracker_channel"
            val descriptionText = "A best channel"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(CHANNEL_ID2, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            channel.setSound(null, null)
            channel.importance = NotificationManager.IMPORTANCE_LOW
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun dataBase() {
        db = WaterDatabase(applicationContext)
        db.createDefaultNotesIfNeed()
        db.createDefaultNotesIfNeed2()
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY


    }

    fun reSet() {
        val h = Handler()
        h.post(
            object : Runnable {
                override fun run() {
                    val c = Calendar.getInstance()
                    val sdf = SimpleDateFormat("HH:mm")
                    val currentDate = sdf.format(c.getTime())
                    val sdf2 = SimpleDateFormat("dd")
                    val currentDate2 = sdf2.format(c.getTime()).toInt()
                    System.out.println(" C DATE is  " + currentDate)
                    val y = "$currentDate"
                    if (y == "00:00") {
                        ShareReference.setCheckFull(false, applicationContext)
                        val intent = Intent()
                        intent.action = "resetWater"
                        applicationContext!!.sendBroadcast(intent)
                    }
                    val calendar = Calendar.getInstance()
                    val day = calendar.get(Calendar.DAY_OF_WEEK)

                    if (y == "23:58" && day == Calendar.MONDAY) {
                        val intent1 = Intent()
                        intent1.action = "addWaterMon"
                        applicationContext!!.sendBroadcast(intent1)
                    }
                    if (y == "00:00" && day == Calendar.MONDAY) {
                        val intent3 = Intent()
                        intent3.action = "resetall"
                        applicationContext!!.sendBroadcast(intent3)
                    }
                    if (y == "23:58" && day == Calendar.TUESDAY) {
                        val intent1 = Intent()
                        intent1.action = "addWaterTue"
                        applicationContext!!.sendBroadcast(intent1)
                    }
                    if (y == "23:58" && day == Calendar.WEDNESDAY) {
                        val intent1 = Intent()
                        intent1.action = "addWaterWed"
                        applicationContext!!.sendBroadcast(intent1)
                    }
                    if (y == "23:58" && day == Calendar.THURSDAY) {
                        val intent1 = Intent()
                        intent1.action = "addWaterThu"
                        applicationContext!!.sendBroadcast(intent1)
                    }
                    if (y == "23:58" && day == Calendar.FRIDAY) {
                        val intent1 = Intent()
                        intent1.action = "addWaterFri"
                        applicationContext!!.sendBroadcast(intent1)
                    }
                    if (y == "23:58" && day == Calendar.SATURDAY) {
                        val intent1 = Intent()
                        intent1.action = "addWaterSar"
                        applicationContext!!.sendBroadcast(intent1)
                    }
                    if (y == "23:58" && day == Calendar.SUNDAY) {
                        val intent1 = Intent()
                        intent1.action = "addWaterSun"
                        applicationContext!!.sendBroadcast(intent1)
                    }
                    if (currentDate2 == 1 && y == "00:00") {
                        val intent1 = Intent()
                        intent1.action = "resetallMonth"
                        applicationContext!!.sendBroadcast(intent1)
                    }

                    h.postDelayed(this, 20000)
                }
            })
    }

    fun createdNotificationWindownanager() {
        val checkpermersion = ShareReference.getNotifiWm(applicationContext)
        val checkNoti = ShareReference.getCheckNoti(applicationContext)
        if (checkpermersion == true && checkNoti == false) {
            wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager?
            val view: View
            mView = ViewGroupWM(
                applicationContext
            )
            view =
                View.inflate(applicationContext, R.layout.dialog_time_drinkwater, mView)
            btn = view.findViewById(R.id.btn_accept_service)
            imgSetting = view.findViewById(R.id.seitting_wm)


            mParams = WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                } else {
                    WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
                },
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )


            val mode = ShareReference.getRemiderMode(applicationContext)
            Log.i("chiaa", "$mode")

            if (mode == 2) {
                mMediaPlayer = MediaPlayer.create(applicationContext, R.raw.drinkwater)
                mMediaPlayer!!.start()
                vibrate()
                mMediaPlayer = MediaPlayer()


            } else if (mode == 1) {

                vibrate()
            }

            mParams!!.x = 0
            mParams!!.y = 0


            if (wm != null) {
                try {
                    wm!!.removeView(mView)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            wm!!.addView(mView, mParams)
            ShareReference.setCheckNoti(true,applicationContext)

            lnRemove1 = view.findViewById(R.id.ln_remove1)
            lnRemove2 = view.findViewById(R.id.ln_remove2)
            imgRemove = view.findViewById(R.id.img_remove_dialog)


            lnRemove1.setOnClickListener {
                var alarmMgr: AlarmManager? = null
                val alarmIntent: PendingIntent
                alarmMgr =
                    applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmIntent = Intent(applicationContext, Alarm::class.java).let { intent ->
                    PendingIntent.getBroadcast(applicationContext, 0, intent, 0)
                }
                ShareReference.setCheckNoti(false,applicationContext)
                wm!!.removeView(mView)


            }
            lnRemove2.setOnClickListener {
                ShareReference.setCheckNoti(false,applicationContext)
                var alarmMgr: AlarmManager? = null
                val alarmIntent: PendingIntent
                alarmMgr =
                    applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmIntent = Intent(applicationContext, Alarm::class.java).let { intent ->
                    PendingIntent.getBroadcast(applicationContext, 0, intent, 0)
                }

                wm!!.removeView(mView)
            }
            imgRemove.setOnClickListener {
                ShareReference.setCheckNoti(false,applicationContext)
                if (wm != null) {
                    wm!!.removeView(mView)
                }
            }
            btn.setOnClickListener {
                ShareReference.setCheckNoti(false,applicationContext)
                NotificationManagerCompat.from(applicationContext).cancel(1000)
                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("drinkwater", 2)
                startActivity(intent)
                wm!!.removeView(mView)
            }
            imgSetting.setOnClickListener {
                ShareReference.setCheckNoti(false,applicationContext)
                NotificationManagerCompat.from(applicationContext).cancel(1000)

                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("drinkwater", 3)
                applicationContext.startActivity(intent)
                if (wm != null) {
                    wm!!.removeView(mView)
                }
            }
        }
    }

    internal var receiverService: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent) {
            if (intent.action == "resetWater") {
                db = WaterDatabase(p0!!)
                ShareReference.setWaterlevel(0, applicationContext)
                ShareReference.setCount(0, applicationContext)
                ShareReference.setCheckFull(false, applicationContext)
                db.deleteHistory()
//                CreateChanel()
            }
            if (intent.action == "notificationWater") {

                createdNotificationWindownanager()
                val checkNoti = ShareReference.getCheckNoti(applicationContext)
                if(checkNoti== false) {
                    val mode = ShareReference.getRemiderMode(applicationContext)


                    if (mode == 2) {
                        mMediaPlayer = MediaPlayer.create(applicationContext, R.raw.drinkwater)
                        mMediaPlayer!!.start()
                        vibrate()
                        mMediaPlayer = MediaPlayer()


                    } else if (mode == 1) {

                        vibrate()
                    }


                    var check3 = ShareReference.getCheckBed(applicationContext)
                    var check2 = ShareReference.getCheckfull(applicationContext)
                    val remoteViews = RemoteViews(packageName, R.layout.noti_layout)
                    if (check == true) {
                        remoteViews.setTextViewText(
                            R.id.tv_title,
                            "Drink water to start a fresh day"
                        )
                        check = false
                    } else {
                        remoteViews.setTextViewText(R.id.tv_title, "It's time to drink water")
                    }
                    val i = Intent(applicationContext, MainActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                    i.putExtra("drinkwater", 2)
                    val uniqueInt = (System.currentTimeMillis() and 0xfffffff).toInt()
                    val intent = PendingIntent.getActivity(
                        applicationContext, uniqueInt, i,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )

                    val CHANNEL_ID = "4"
                    if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
                        val builder = NotificationCompat.Builder(applicationContext)
                            .setSmallIcon(com.kolorfox.watertracker.R.drawable.ic_water)
                            .setContentIntent(intent)
                            .setAutoCancel(true)
                            .setOngoing(true)
                            .setSound(null)
                        val notification = builder.build()
                        notification.contentView = remoteViews
                        val mode2 = ShareReference.getRemiderMode(applicationContext)
                        Log.i("chiaa", "$mode")

                        if (mode2 == 2) {

                            notification.defaults =
                                notification.defaults or Notification.DEFAULT_SOUND
                            notification.defaults =
                                notification.defaults or Notification.DEFAULT_VIBRATE


                        } else if (mode2 == 1) {

                            notification.defaults = Notification.DEFAULT_VIBRATE
                            notification.defaults =
                                notification.defaults or Notification.DEFAULT_VIBRATE
                        } else if (mode2 == 0) {
                            notification.defaults = Notification.DEFAULT_LIGHTS
                        }



                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            notification.bigContentView = remoteViews
                        }
                        val c = Calendar.getInstance()
                        val sdf = SimpleDateFormat("HH:mm a")
                        val currentDate = sdf.format(c.getTime())
                        val time = ShareReference.getTimeNextDrink(applicationContext)
                        remoteViews.setTextViewText(R.id.tv_time_noti, time)
                        NotificationManagerCompat.from(applicationContext)
                            .notify(1000, notification)

                    } else {

                        val notificationManager: NotificationManager =
                            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//                    notificationManager.createNotificationChannel(channel)
                        val CHANNEL_ID = "4"
                        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                            .setSmallIcon(com.kolorfox.watertracker.R.drawable.ic_water)
                            .setContentIntent(intent)
                            .setAutoCancel(true)
                            .setOngoing(true)
                            .setSound(null)
//                        .setVibrate(longArrayOf(2000))

                        val notification = builder.build()


                        notification.contentView = remoteViews
                        val mode3 = ShareReference.getRemiderMode(applicationContext)
                        Log.i("chiaa", "$mode")

                        if (mode3 == 2) {
                            notification.defaults =
                                notification.defaults or Notification.DEFAULT_SOUND
                            notification.defaults =
                                notification.defaults or Notification.DEFAULT_VIBRATE
                        } else if (mode3 == 1) {
                            notification.defaults = Notification.DEFAULT_VIBRATE
                            notification.defaults =
                                notification.defaults or Notification.DEFAULT_VIBRATE
                        } else if (mode3 == 0) {
                            notification.defaults = Notification.DEFAULT_LIGHTS
                        }



                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            notification.bigContentView = remoteViews
                        }
                        val c = Calendar.getInstance()
                        val sdf = SimpleDateFormat("HH:mm a")
                        val currentDate = sdf.format(c.getTime())
                        val time = ShareReference.getTimeNextDrink(applicationContext)
                        remoteViews.setTextViewText(R.id.tv_time_noti, time)
                        NotificationManagerCompat.from(applicationContext)
                            .notify(1000, notification)

//                    notificationManager.deleteNotificationChannel(CHANNEL_ID)
                    }
                }


            }

            if (intent.action == "resetall") {

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
            if (intent.action == "resetallMonth") {
                for (i in 1..31) {
                    db.updateWaterMonth(WaterMonth(i, 0, 0, 0))
                    db.updateWeightMonth(Weight(i, 0f))
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        applicationContext.unregisterReceiver(receiverService)
    }

    fun CreateNotification() {
        val checkbed = ShareReference.getCheckBed(applicationContext)
        val h = Handler()
        h.post(
            object : Runnable {
                override fun run() {
                    val timeNextDrink = ShareReference.getTimeNextDrink(applicationContext)
                    val c = Calendar.getInstance()
                    val sdf = SimpleDateFormat("HH:mm")
                    val currentDate = sdf.format(c.getTime())
                    val y = "$currentDate"
                    val timeWakeup = ShareReference.getWakeUpTime(applicationContext)
                    val reminder = ShareReference.getReminder(applicationContext)
                    val bedTime = ShareReference.getBedTimes(applicationContext)
                    if (y == bedTime) {
                        ShareReference.setCheckBedTime(false, applicationContext)
                    }
                    val check3 = ShareReference.getCheckBed(applicationContext)
                    val checkFull = ShareReference.getCheckfull(applicationContext)
                    if (reminder == true && checkFull == false) {
                        if (checkbed == true) {
                            if (y == timeNextDrink && check3 == true) {
                                val intent = Intent()
                                intent.action = "notificationWater"
                                applicationContext!!.sendBroadcast(intent)
                            } else if (y == timeWakeup) {
                                Log.i("hjhjhj", "wakeup")
                                ShareReference.setCheckBedTime(true, applicationContext)
                                check = true
                                val intent = Intent()
                                intent.action = "notificationWater"
                                applicationContext!!.sendBroadcast(intent)

                            }
                        }
                    }
                    h.postDelayed(this, 58000)
                }
            })
    }

    private var mParams: WindowManager.LayoutParams? = null
    private var mView: ViewGroupWM? = null
    private var wm: WindowManager? = null
    lateinit var btn: Button
    lateinit var lnRemove1: LinearLayout
    lateinit var lnRemove2: LinearLayout
    lateinit var imgRemove: ImageView
    var check: Boolean? = false
    internal lateinit var db: WaterDatabase
    private var notification2: Notification? = null
    private val NOTIFICATION_ID2 = 145
    val CHANNEL_ID2 = "4"
    lateinit var vibrator: Vibrator
    private var mMediaPlayer: MediaPlayer? = null
    lateinit var imgSetting: ImageView

}





