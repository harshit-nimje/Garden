package com.digitalfarmer.app

import android.app.Application
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.digitalfarmer.app.data.WateringWorker
import java.util.concurrent.TimeUnit

class DigitalFarmerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        scheduleWateringChecks()
    }

    private fun scheduleWateringChecks() {
        val request = PeriodicWorkRequestBuilder<WateringWorker>(8, TimeUnit.HOURS).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "watering_check",
            androidx.work.ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}
