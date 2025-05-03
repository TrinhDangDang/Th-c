package com.example.thuc.ringgg

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.thuc.AlarmRingActivity
import com.example.thuc.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("AlarmReceiver", "🚨 Alarm triggered!")

        showAlarmNotification(context)
    }

    private fun showAlarmNotification(context: Context) {
        val channelId = "alarm_channel"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create the notification channel (Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Alarm Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for Alarm notifications"
            }
            notificationManager.createNotificationChannel(channel)
        }

        // Intent that opens the AlarmRingActivity
        val fullScreenIntent = Intent(context, AlarmRingActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val fullScreenPendingIntent = PendingIntent.getActivity(
            context,
            0,
            fullScreenIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Alarm")
            .setContentText("⏰ Alarm is going off!")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .build()

        notificationManager.notify(1, notification)
    }
}
