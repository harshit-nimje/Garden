package com.digitalfarmer.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val species: String,
    val addedDate: Long = System.currentTimeMillis(),
    val wateringIntervalDays: Int,
    val lastWateredDate: Long = System.currentTimeMillis(),
    val waterAmountMl: Int,
    val sunHoursNeeded: Float,
    val idealLuxMin: Int,
    val idealLuxMax: Int,
    val fertilizerNote: String,
    val notes: String = "",
    val imagePath: String? = null
) {
    fun nextWateringDate(): Long =
        lastWateredDate + wateringIntervalDays * 24L * 60 * 60 * 1000

    fun isDueForWatering(): Boolean = System.currentTimeMillis() >= nextWateringDate()

    fun daysUntilWatering(): Long {
        val diff = nextWateringDate() - System.currentTimeMillis()
        return diff / (24 * 60 * 60 * 1000)
    }
}
