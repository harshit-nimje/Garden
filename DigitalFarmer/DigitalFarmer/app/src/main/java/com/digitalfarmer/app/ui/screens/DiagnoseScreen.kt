package com.digitalfarmer.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Offline rule-based diagnosis stub: pairs a symptom pick with fixed advice.
// Swap resultFor() for an on-device vision model later without touching the UI.
private data class Symptom(val label: String, val diagnosis: String, val fix: String)

private val SYMPTOMS = listOf(
    Symptom("Yellow leaves", "Likely overwatering", "Let soil dry out fully between waterings"),
    Symptom("Brown crispy tips", "Likely underwatering or low humidity", "Water more consistently, mist leaves"),
    Symptom("Drooping leaves", "Under- or over-watering stress", "Check soil moisture before next watering"),
    Symptom("White spots/fuzz", "Possible pest or mildew", "Isolate plant, wipe leaves, improve airflow"),
    Symptom("Leggy, sparse growth", "Not enough light", "Move closer to a window or brighter spot")
)

@Composable
fun DiagnoseScreen() {
    var selected by remember { mutableStateOf<Symptom?>(null) }

    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text("Diagnose a plant", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        Text("Scan a photo, or pick what you see:", style = MaterialTheme.typography.bodyMedium)

        Spacer(Modifier.height(16.dp))
        OutlinedButton(
            onClick = { /* TODO: CameraX capture + on-device model */ },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Filled.CameraAlt, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Scan and diagnose")
        }

        Spacer(Modifier.height(20.dp))
        SYMPTOMS.forEach { symptom ->
            ElevatedCard(
                onClick = { selected = symptom },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) { Text(symptom.label, modifier = Modifier.padding(14.dp)) }
        }

        selected?.let { s ->
            Spacer(Modifier.height(20.dp))
            ElevatedCard(
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(Modifier.padding(18.dp)) {
                    Text(s.diagnosis, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(6.dp))
                    Text(s.fix, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
