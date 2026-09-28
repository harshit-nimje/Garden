package com.digitalfarmer.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.digitalfarmer.app.data.Plant

// Local offline species presets stand in for a future scan-to-identify model.
// Scanning a plant photo matches against this table instead of calling the network.
private data class SpeciesPreset(
    val species: String,
    val wateringIntervalDays: Int,
    val waterAmountMl: Int,
    val sunHoursNeeded: Float,
    val idealLuxMin: Int,
    val idealLuxMax: Int,
    val fertilizerNote: String
)

private val SPECIES_PRESETS = listOf(
    SpeciesPreset("Monstera Deliciosa", 7, 400, 3f, 2000, 10000, "Balanced liquid feed monthly, spring–summer"),
    SpeciesPreset("Snake Plant", 14, 200, 2f, 1000, 20000, "Cactus feed every 2 months"),
    SpeciesPreset("Basil", 2, 150, 6f, 10000, 30000, "Light nitrogen feed weekly"),
    SpeciesPreset("Succulent (generic)", 12, 100, 6f, 15000, 40000, "Diluted cactus feed monthly"),
    SpeciesPreset("Fiddle Leaf Fig", 7, 500, 4f, 3000, 12000, "Balanced feed every 4 weeks")
)

@Composable
fun AddPlantScreen(onSave: (Plant) -> Unit, onCancel: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var selectedPreset by remember { mutableStateOf<SpeciesPreset?>(null) }
    var scanning by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text("Add a plant", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(20.dp))

        OutlinedButton(
            onClick = {
                // Placeholder for CameraX capture + offline visual-match model.
                // For now it simulates a scan by picking a preset after a beat.
                scanning = true
            },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Filled.CameraAlt, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Scan and add plant")
        }

        if (scanning) {
            Spacer(Modifier.height(12.dp))
            Text("Point the camera at your plant to identify species. Pick a close match below for now:",
                style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(Modifier.height(20.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nickname") },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        )

        Spacer(Modifier.height(16.dp))
        Text("Species preset", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        SPECIES_PRESETS.forEach { preset ->
            ElevatedCard(
                onClick = { selectedPreset = preset; scanning = false },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = if (selectedPreset == preset)
                        MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(Modifier.padding(14.dp)) {
                    Text(preset.species)
                    Text(
                        "Water every ${preset.wateringIntervalDays}d · ${preset.sunHoursNeeded}h sun · ${preset.idealLuxMin}-${preset.idealLuxMax} lux",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick = onCancel, shape = MaterialTheme.shapes.medium) { Text("Cancel") }
            Button(
                enabled = name.isNotBlank() && selectedPreset != null,
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    val p = selectedPreset!!
                    onSave(
                        Plant(
                            name = name,
                            species = p.species,
                            wateringIntervalDays = p.wateringIntervalDays,
                            waterAmountMl = p.waterAmountMl,
                            sunHoursNeeded = p.sunHoursNeeded,
                            idealLuxMin = p.idealLuxMin,
                            idealLuxMax = p.idealLuxMax,
                            fertilizerNote = p.fertilizerNote
                        )
                    )
                }
            ) { Text("Save to library") }
        }
    }
}
