package com.aubynsamuel.notificationguide

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            "ACCEPT_ACTION" -> {
                Toast.makeText(context, "Accepted!", Toast.LENGTH_SHORT).show()
            }

            "DECLINE_ACTION" -> {
                Toast.makeText(context, "Declined!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}