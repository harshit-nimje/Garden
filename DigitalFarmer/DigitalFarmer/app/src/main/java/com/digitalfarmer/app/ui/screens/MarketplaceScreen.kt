package com.digitalfarmer.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.digitalfarmer.app.data.UserProfile

// Duolingo-style progression: seeds earned from real care tasks unlock rank.
// Marketplace redemption (real seed packets, discounts) is the online-phase hook.
@Composable
fun MarketplaceScreen(profile: UserProfile) {
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text("Gardener rank", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        ElevatedCard(
            shape = MaterialTheme.shapes.extraLarge,
            colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(profile.rankName(), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Black)
                Spacer(Modifier.height(6.dp))
                Text("🌱 ${profile.seeds} seeds", style = MaterialTheme.typography.titleMedium)
                profile.seedsToNextRank()?.let {
                    Spacer(Modifier.height(4.dp))
                    Text("$it seeds to next rank", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        Spacer(Modifier.height(24.dp))
        Text("How to earn seeds", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        listOf(
            "Mark a plant watered on time" to "+5 seeds",
            "Feed a plant on schedule" to "+5 seeds",
            "Diagnose and treat an issue" to "+5 seeds"
        ).forEach { (task, reward) ->
            ElevatedCard(shape = MaterialTheme.shapes.medium, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Row(
                    Modifier.padding(14.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) { Text(task); Text(reward, fontWeight = FontWeight.Bold) }
            }
        }

        Spacer(Modifier.height(24.dp))
        Text(
            "Marketplace (seed redemption for real seed packets & discounts) unlocks once online sync is added.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
