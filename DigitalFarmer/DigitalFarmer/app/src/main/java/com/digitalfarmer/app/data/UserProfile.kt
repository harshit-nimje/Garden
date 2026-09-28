package com.digitalfarmer.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val id: Int = 0,
    val seeds: Int = 0,
    val totalTasksCompleted: Int = 0
) {
    companion object {
        val RANKS = listOf(
            0 to "Seedling",
            50 to "Sprout",
            150 to "Budding Gardener",
            400 to "Green Thumb",
            900 to "Master Grower",
            2000 to "Botanist Legend"
        )
    }

    fun rankName(): String = RANKS.last { seeds >= it.first }.second

    fun seedsToNextRank(): Int? {
        val next = RANKS.firstOrNull { seeds < it.first } ?: return null
        return next.first - seeds
    }
}
