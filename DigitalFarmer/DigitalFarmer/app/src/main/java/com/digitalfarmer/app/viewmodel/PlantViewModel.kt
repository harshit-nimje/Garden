package com.digitalfarmer.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.digitalfarmer.app.data.AppDatabase
import com.digitalfarmer.app.data.Plant
import com.digitalfarmer.app.data.UserProfile
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// Reward given for completing a care task (watering, feeding, etc).
const val SEEDS_PER_TASK = 5

class PlantViewModel(app: Application) : AndroidViewModel(app) {
    private val db = AppDatabase.getInstance(app)
    private val plantDao = db.plantDao()
    private val profileDao = db.userProfileDao()

    val plants: StateFlow<List<Plant>> =
        plantDao.getAll().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val profile: StateFlow<UserProfile> =
        profileDao.observe().map { it ?: UserProfile() }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserProfile())

    init {
        viewModelScope.launch {
            if (profileDao.get() == null) profileDao.upsert(UserProfile())
        }
    }

    fun addPlant(plant: Plant) = viewModelScope.launch {
        plantDao.insert(plant)
    }

    fun deletePlant(plant: Plant) = viewModelScope.launch {
        plantDao.delete(plant)
    }

    // Marking a task done both resets the schedule and pays out seeds —
    // the core gamification loop, Duolingo-style, for real plant care.
    fun markWatered(plant: Plant) = viewModelScope.launch {
        plantDao.update(plant.copy(lastWateredDate = System.currentTimeMillis()))
        rewardSeeds(SEEDS_PER_TASK)
    }

    private suspend fun rewardSeeds(amount: Int) {
        val current = profileDao.get() ?: UserProfile()
        profileDao.upsert(
            current.copy(
                seeds = current.seeds + amount,
                totalTasksCompleted = current.totalTasksCompleted + 1
            )
        )
    }
}
