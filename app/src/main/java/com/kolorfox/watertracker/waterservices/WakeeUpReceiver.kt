package com.kolorfox.watertracker.waterservices

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.kolorfox.watertracker.R
import com.kolorfox.watertracker.data.ShareReference
import com.kolorfox.watertracker.ui.watertrackermain.MainActivity
import java.text.SimpleDateFormat
import java.util.*

class WakeeUpReceiver : BroadcastReceiver() {
    var check: Boolean? = false
    private val NOTIFICATION_ID = 144
    val CHANNEL_ID = "1"
    private var notification2: Notification? = null
    private val NOTIFICATION_ID2 = 145
    val CHANNEL_ID2 = "2"
    override fun onReceive(context: Context, intent: Intent) {
        var reminder = ShareReference.getReminder(context)
        ShareReference.setCheckBedTime(true, context)
        if (reminder == true) {
            Log.i("vkl2", "vkl2")
            val i = Intent(context, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            i.putExtra("hihi", true)
            val uniqueInt = (System.currentTimeMillis() and 0xfffffff).toInt()
            val intent = PendingIntent.getActivity(
                context, uniqueInt, i,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val remoteViews = RemoteViews(context.packageName, R.layout.noti_layout)
            remoteViews.setTextViewText(R.id.tv_title, "Drink water to start a fresh day")

            val CHANNEL_ID = "1"
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
                val builder = NotificationCompat.Builder(context)
                    .setSmallIcon(com.kolorfox.watertracker.R.drawable.ic_water)
                    .setContentIntent(intent)
                    .setAutoCancel(true)
                val notification = builder.build()
                notification.contentView = remoteViews
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    notification.bigContentView = remoteViews
                }
                val c = Calendar.getInstance()
                val sdf = SimpleDateFormat("HH:mm a")
                val currentDate = sdf.format(c.getTime())
                remoteViews.setTextViewText(R.id.tv_time_noti, currentDate)
//                notification.defaults = notification.defaults or Notification.DEFAULT_SOUND
//                notification.defaults =
//                    notification.defaults or Notification.DEFAULT_VIBRATE


//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                notification.bigContentView = remoteViews
//            }

//            remoteViews.setTextViewText(R.id.tv_time_noti, currentDate)
//                NotificationManagerCompat.from(context).notify(1000, notification)
            } else {
                val name = "Drink Water_channel"
                val descriptionText = "A cool channel"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }

                val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)

                val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(com.kolorfox.watertracker.R.drawable.ic_water)
                    .setContentIntent(intent)
                    .setAutoCancel(true)
                    .setSound(null)


                val notification = builder.build()
                notification.contentView = remoteViews
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    notification.bigContentView = remoteViews
                }
                val c = Calendar.getInstance()
                val sdf = SimpleDateFormat("HH:mm a")
                val currentDate = sdf.format(c.getTime())
                remoteViews.setTextViewText(R.id.tv_time_noti, currentDate)
                notification.defaults = Notification.DEFAULT_LIGHTS

//            remoteViews.setTextViewText(R.id.tv_time_noti, currentDate)
//                NotificationManagerCompat.from(context).notify(1000, notification)
            }


        }
    }
}