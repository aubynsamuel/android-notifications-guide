package com.aubynsamuel.notificationguide

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput

class ReplyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        remoteInput?.let {
            val replyText = it.getCharSequence(NotificationReceiver.KEY_TEXT_REPLY)
            replyText?.let { text ->
                // Show toast with reply text
                Toast.makeText(context, "Reply: $text", Toast.LENGTH_LONG).show()

                // Update notification to show the reply was received
                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                val repliedNotification = NotificationCompat.Builder(
                    context,
                    Essentials.CHANNEL_ID
                )
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentText("Reply sent: $text")
                    .build()

                notificationManager.notify(Essentials.NOTIFICATION_ID, repliedNotification)
            }
        }
    }
}