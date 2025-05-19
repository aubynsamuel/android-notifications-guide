package com.aubynsamuel.notificationguide

import android.Manifest
import android.app.AlarmManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.aubynsamuel.notificationguide.ui.theme.NotificationguideTheme

class MainActivity : ComponentActivity() {
    private lateinit var alarmManager: AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        createNotificationChannel(this)

        setContent {
            NotificationguideTheme {
                MainScreen(this, alarmManager) { requestNotificationPermission() }
            }
        }
    }

    private val requestPermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            //
        } else {
            requestNotificationPermission()
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            ) return
            else requestPermissionsLauncher.launch(permission)
        }
    }
}

object Essentials {
    const val CHANNEL_ID = "schedule_notification_channel"
    const val NOTIFICATION_ID = 1
}