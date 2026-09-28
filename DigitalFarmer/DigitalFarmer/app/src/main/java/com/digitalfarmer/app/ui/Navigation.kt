package com.digitalfarmer.app.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.collectAsState
import com.digitalfarmer.app.ui.screens.*
import com.digitalfarmer.app.viewmodel.PlantViewModel

private sealed class Dest(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Library : Dest("library", "Library", Icons.Filled.Yard)
    object Lux : Dest("lux", "Light", Icons.Filled.WbSunny)
    object Diagnose : Dest("diagnose", "Diagnose", Icons.Filled.HealthAndSafety)
    object Marketplace : Dest("marketplace", "Rank", Icons.Filled.EmojiEvents)
}

private val bottomDestinations = listOf(Dest.Library, Dest.Lux, Dest.Diagnose, Dest.Marketplace)

@Composable
fun DigitalFarmerNav() {
    val navController = rememberNavController()
    val viewModel: PlantViewModel = viewModel()
    val plants by viewModel.plants.collectAsState()
    val profile by viewModel.profile.collectAsState()

    Scaffold(
        bottomBar = {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination
            NavigationBar {
                bottomDestinations.forEach { dest ->
                    NavigationBarItem(
                        selected = currentRoute?.hierarchy?.any { it.route == dest.route } == true,
                        onClick = {
                            navController.navigate(dest.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(dest.icon, contentDescription = dest.label) },
                        label = { Text(dest.label) }
                    )
                }
            }
        },
        floatingActionButton = {
            val backStackEntry by navController.currentBackStackEntryAsState()
            if (backStackEntry?.destination?.route == Dest.Library.route) {
                FloatingActionButton(
                    onClick = { navController.navigate("add_plant") },
                    shape = MaterialTheme.shapes.large
                ) { Icon(Icons.Filled.Add, contentDescription = "Add plant") }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Dest.Library.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Dest.Library.route) {
                LibraryScreen(
                    plants = plants,
                    onWater = { viewModel.markWatered(it) },
                    onOpen = { /* detail view: future work */ }
                )
            }
            composable(Dest.Lux.route) { LuxScreen(plants = plants) }
            composable(Dest.Diagnose.route) { DiagnoseScreen() }
            composable(Dest.Marketplace.route) { MarketplaceScreen(profile = profile) }
            composable("add_plant") {
                AddPlantScreen(
                    onSave = { plant -> viewModel.addPlant(plant); navController.popBackStack() },
                    onCancel = { navController.popBackStack() }
                )
            }
        }
    }
}
