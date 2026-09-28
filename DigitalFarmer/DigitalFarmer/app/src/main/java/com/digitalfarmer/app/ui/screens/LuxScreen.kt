package com.digitalfarmer.app.ui.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.digitalfarmer.app.data.Plant

// Reads the device's built-in ambient light sensor — fully offline,
// no camera or network needed to judge whether a spot is bright enough.
@Composable
fun LuxScreen(plants: List<Plant>) {
    val context = LocalContext.current
    var lux by remember { mutableStateOf<Float?>(null) }

    DisposableEffect(Unit) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) { lux = event.values[0] }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
        if (lightSensor != null) sensorManager.registerListener(listener, lightSensor, SensorManager.SENSOR_DELAY_UI)
        onDispose { sensorManager.unregisterListener(listener) }
    }

    Column(
        Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Light meter", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        ElevatedCard(shape = MaterialTheme.shapes.extraLarge, modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    lux?.let { "${it.toInt()} lux" } ?: "No light sensor found",
                    style = MaterialTheme.typography.displayLarge
                )
            }
        }

        Spacer(Modifier.height(24.dp))
        Text("Matches in your library:", style = MaterialTheme.typography.titleMedium)

        val currentLux = lux
        if (currentLux != null) {
            plants.forEach { plant ->
                val fits = currentLux >= plant.idealLuxMin && currentLux <= plant.idealLuxMax
                Spacer(Modifier.height(8.dp))
                ElevatedCard(shape = MaterialTheme.shapes.medium, modifier = Modifier.fillMaxWidth()) {
                    Row(
                        Modifier.padding(14.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(plant.name)
                        Text(
                            if (fits) "Good spot ✅" else "Not ideal (needs ${plant.idealLuxMin}-${plant.idealLuxMax})",
                            color = if (fits) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}
