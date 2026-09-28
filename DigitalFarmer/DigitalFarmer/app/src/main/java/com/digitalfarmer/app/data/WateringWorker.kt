package com.digitalfarmer.app.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.flow.first

// Checks every plant's watering schedule and fires a local notification
// for anything due. Runs fully offline via WorkManager's periodic scheduler.
class WateringWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val db = AppDatabase.getInstance(applicationContext)
        val duePlants = db.plantDao().getAll().first().filter { it.isDueForWatering() }

        if (duePlants.isNotEmpty()) {
            notify(duePlants.joinToString(", ") { it.name })
        }
        return Result.success()
    }

    private fun notify(names: String) {
        val channelId = "watering_reminders"
        val nm = applicationContext.getSystemService(NotificationManager::class.java)
        nm.createNotificationChannel(
            NotificationChannel(channelId, "Watering Reminders", NotificationManager.IMPORTANCE_DEFAULT)
        )
        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Time to water 🌱")
            .setContentText("$names need watering today")
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .setAutoCancel(true)
            .build()
        nm.notify(1001, notification)
    }
}
