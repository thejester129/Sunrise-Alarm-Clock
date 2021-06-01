package com.example.sunrisealarmclock.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.sunrisealarmclock.R

class NotificationSender(private val context: Context) {
    private val channelId = "alarm_notification_channel"

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_name)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(channelId, name, importance)
            mChannel.description = descriptionText
            val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    fun sendAlarmNotification(){
        createNotification()
    }

    private fun createNotification(){
//        val fullScreenIntent = Intent(context, ImportantActivity::class.java)
//        val fullScreenPendingIntent = PendingIntent.getActivity(context, 0,
//            fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.alarm_icon)
            .setContentTitle("Sun is rising, time to get out of bed yo")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .addAction(R.drawable.alarm_icon, "Snooze", null)
            .addAction(R.drawable.alarm_icon, "Okay", null)
            //.setFullScreenIntent(fullScreenPendingIntent, true)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1, builder.build())

    }
}