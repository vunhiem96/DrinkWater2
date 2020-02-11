package com.kolorfox.watertracker.waterservices

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
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
import com.kolorfox.watertracker.ui.watertrackermain.MainActivity
import java.text.SimpleDateFormat
import java.util.*

class Alarm : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        Log.i("vkl2", "vkl2")
        val notification: Notification?
        val reminder = ShareReference.getReminder(context)
        val checkbed = ShareReference.getCheckBed(context)
        val checkFull = ShareReference.getCheckfull(context)
        val checkNoti = ShareReference.getCheckNoti(context)
        vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (reminder == true && checkbed == true && checkFull == false && checkNoti == false) {

            Log.i("vkl2", "vkl2")
            val i = Intent(context, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            i.putExtra("drinkwater", 2)
            val uniqueInt = (System.currentTimeMillis() and 0xfffffff).toInt()
            val intents = PendingIntent.getActivity(
                context, uniqueInt, i,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val remoteViews = RemoteViews(context.packageName, R.layout.noti_layout)


            val CHANNEL_ID = "4"
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
                val builder = NotificationCompat.Builder(context)
                    .setSmallIcon(com.kolorfox.watertracker.R.drawable.ic_water)
                    .setContentIntent(intents)
                    .setAutoCancel(true)
                notification = builder.build()
                notification.contentView = remoteViews
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    notification.bigContentView = remoteViews
                }
                val c = Calendar.getInstance()
                val sdf = SimpleDateFormat("HH:mm a")
                val currentDate = sdf.format(c.getTime())
                val time = ShareReference.getTimeNextDrink(context)
                remoteViews.setTextViewText(R.id.tv_time_noti, time)
                val mode = ShareReference.getRemiderMode(context)
                Log.i("chiaa", "$mode")

                if (mode == 2) {

                    notification.defaults = notification.defaults or Notification.DEFAULT_SOUND
                    notification.defaults =
                        notification.defaults or Notification.DEFAULT_VIBRATE


                } else if (mode == 1) {

                    notification.defaults = Notification.DEFAULT_VIBRATE
                    notification.defaults =
                        notification.defaults or Notification.DEFAULT_VIBRATE
                } else if (mode == 0) {
                    notification.defaults = Notification.DEFAULT_LIGHTS
                }



                NotificationManagerCompat.from(context).notify(1000, notification)
            } else {



                val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//                notificationManager.createNotificationChannel(channel)

                val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(com.kolorfox.watertracker.R.drawable.ic_water)
                    .setContentIntent(intents)
                    .setAutoCancel(true)
                    .setSound(null)


                notification = builder.build()
                notification.contentView = remoteViews
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    notification.bigContentView = remoteViews
                }
                val c = Calendar.getInstance()
                val sdf = SimpleDateFormat("HH:mm a")
                val currentDate = sdf.format(c.getTime())
                remoteViews.setTextViewText(R.id.tv_time_noti, currentDate)
                val mode = ShareReference.getRemiderMode(context)
                Log.i("chiaa", "$mode")

                if (mode == 2) {

                    notification.defaults = notification.defaults or Notification.DEFAULT_SOUND
                    notification.defaults =
                        notification.defaults or Notification.DEFAULT_VIBRATE


                } else if (mode == 1) {

                    notification.defaults = Notification.DEFAULT_VIBRATE
                    notification.defaults =
                        notification.defaults or Notification.DEFAULT_VIBRATE
                } else if (mode == 0) {
                    notification.defaults = Notification.DEFAULT_LIGHTS
                }

                val time = ShareReference.getTimeNextDrink(context)
                remoteViews.setTextViewText(R.id.tv_time_noti, time)
//            remoteViews.setTextViewText(R.id.tv_time_noti, currentDate)
                NotificationManagerCompat.from(context).notify(1000, notification)
//                notificationManager.deleteNotificationChannel(CHANNEL_ID)
            }




            fun vibrate() {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(
                            1200,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                } else {
                    vibrator.vibrate(1200)
                }

            }

            val checkpermersion = ShareReference.getNotifiWm(context)
            if (checkpermersion == true) {
                wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
                val view: View
                mView =
                    ViewGroupWM(
                        context
                    )
                view =
                    View.inflate(context, R.layout.dialog_time_drinkwater, mView)
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

                val mode = ShareReference.getRemiderMode(context)


                if (mode == 2) {
                    Log.i("chiaa", "$mode")
                    mediaPlayer = MediaPlayer.create(context, R.raw.drinkwater)
                    mediaPlayer!!.start()
                    vibrate()


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
                ShareReference.setCheckNoti(true, context)

                lnRemove1 = view.findViewById(R.id.ln_remove1)
                lnRemove2 = view.findViewById(R.id.ln_remove2)
                imgRemove = view.findViewById(R.id.img_remove_dialog)


                lnRemove1.setOnClickListener {
                    ShareReference.setCheckNoti(false, context)
                    var alarmMgr: AlarmManager? = null
                    val alarmIntent: PendingIntent
                    alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    alarmIntent = Intent(context, Alarm::class.java).let { intent ->
                        PendingIntent.getBroadcast(context, 0, intent, 0)
                    }


                                wm!!.removeView(mView)

                }
                lnRemove2.setOnClickListener {
                    ShareReference.setCheckNoti(false, context)
                    var alarmMgr: AlarmManager? = null
                    val alarmIntent: PendingIntent
                    alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    alarmIntent = Intent(context, Alarm::class.java).let { intent ->
                        PendingIntent.getBroadcast(context, 0, intent, 0)
                    }


                    wm!!.removeView(mView)
                }
                imgRemove.setOnClickListener {
                    ShareReference.setCheckNoti(false, context)

                                wm!!.removeView(mView)


                }
                imgSetting.setOnClickListener {
                    ShareReference.setCheckNoti(false, context)
                    NotificationManagerCompat.from(context).cancel(1000)

                    val intent3 = Intent(context, MainActivity::class.java)
                    intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent3.putExtra("drinkwater", 3)
                    context.startActivity(intent3)
                    if (wm != null) {
                        wm!!.removeView(mView)
                    }
                }

                btn.setOnClickListener {
                    ShareReference.setCheckNoti(false, context)
                    NotificationManagerCompat.from(context).cancel(1000)

                    val intent2 = Intent(context, MainActivity::class.java)
                    intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent2.putExtra("drinkwater", 2)
                    context.startActivity(intent2)
                    if (wm != null) {
                        wm!!.removeView(mView)
                    }

                }
            }
        }
    }
    var check: Boolean? = false
    private val NOTIFICATION_ID = 144
    val CHANNEL_ID = "1"
    private var notification2: Notification? = null
    private val NOTIFICATION_ID2 = 145
    val CHANNEL_ID2 = "2"
    private var mParams: WindowManager.LayoutParams? = null
    private var mView: ViewGroupWM? = null
    private var wm: WindowManager? = null
    lateinit var btn: Button
    lateinit var lnRemove1: LinearLayout
    lateinit var lnRemove2: LinearLayout
    lateinit var imgRemove: ImageView
    lateinit var vibrator: Vibrator
    private var mediaPlayer: MediaPlayer? = null
    lateinit var imgSetting:ImageView
}