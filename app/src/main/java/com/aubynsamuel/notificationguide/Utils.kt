package com.aubynsamuel.notificationguide

import android.Manifest
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.aubynsamuel.notificationguide.Essentials.CHANNEL_ID
import com.aubynsamuel.notificationguide.Essentials.NOTIFICATION_ID
import java.util.Calendar

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Schedule Notification Channel"
        val descriptionText = "Channel for schedule notifications"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }
}

@RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
fun checkAndScheduleNotification(alarmManager: AlarmManager, context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        // For Android 12 (API 31) and above
        if (alarmManager.canScheduleExactAlarms()) {
            scheduleNotification(alarmManager, context)
        } else {
            showAlarmPermissionDialog(context)
        }
    } else {
        // For Android 11 and below
        scheduleNotification(alarmManager, context)
    }
}

fun showAlarmPermissionDialog(context: Context) {
    AlertDialog.Builder(context)
        .setTitle("Permission Required")
        .setMessage("To schedule exact notifications, this app needs permission to schedule exact alarms. Please enable this in Settings.")
        .setPositiveButton("Open Settings") { _, _ ->
            openAlarmSettings(context)
        }
        .setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        .show()
}

fun openAlarmSettings(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
        intent.data = context.packageName.toUri()
        context.startActivity(intent)
    }
}

@RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
fun scheduleNotification(alarmManager: AlarmManager, context: Context) {
    try {
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            add(Calendar.SECOND, 5) // Schedule notification 5 seconds from now
        }

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )

        Toast.makeText(context, "Notification scheduled!", Toast.LENGTH_SHORT).show()
    } catch (_: SecurityException) {
        Toast.makeText(
            context,
            "Permission denied: Cannot schedule exact alarms",
            Toast.LENGTH_LONG
        ).show()
    }
}