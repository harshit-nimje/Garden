package com.digitalfarmer.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.digitalfarmer.app.data.Plant

@Composable
fun LibraryScreen(
    plants: List<Plant>,
    onWater: (Plant) -> Unit,
    onOpen: (Plant) -> Unit
) {
    if (plants.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Your library is empty", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
                Text("Tap + to scan and add your first plant", style = MaterialTheme.typography.bodyMedium)
            }
        }
        return
    }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(plants, key = { it.id }) { plant ->
            PlantCard(plant = plant, onWater = { onWater(plant) }, onClick = { onOpen(plant) })
        }
    }
}

@Composable
private fun PlantCard(plant: Plant, onWater: () -> Unit, onClick: () -> Unit) {
    ElevatedCard(
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(plant.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(plant.species, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(4.dp))
                val due = plant.isDueForWatering()
                Text(
                    if (due) "Needs water today 💧" else "Water in ${plant.daysUntilWatering()} day(s)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (due) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            FilledIconButton(onClick = onWater, shape = MaterialTheme.shapes.medium) {
                Icon(Icons.Filled.WaterDrop, contentDescription = "Mark watered")
            }
        }
    }
}
