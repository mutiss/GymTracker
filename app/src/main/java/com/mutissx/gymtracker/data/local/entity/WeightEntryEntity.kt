package com.mutissx.gymtracker.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "weight_entries", indices = [Index(value = ["date"], unique = true)])
data class WeightEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val date: LocalDate,
    val weightKg: Double,
    val updatedAt: LocalDateTime
)
