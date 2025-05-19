package com.aubynsamuel.notificationguide

import android.app.AlarmManager
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MainScreen(
    context: Context,
    alarmManager: AlarmManager,
    requestNotificationPermission: () -> Unit,
) {
    LaunchedEffect(Unit) {
        requestNotificationPermission()
    }
    Scaffold { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Button(
                onClick = {
                    checkAndScheduleNotification(
                        context = context,
                        alarmManager = alarmManager
                    )
                },
            ) { Text("Schedule Notification") }

        }
    }
}
