package com.mutissx.gymtracker.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "hydration_entries", indices = [Index(value = ["date"], unique = true)])
data class HydrationEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val date: LocalDate,
    val hydrated: Boolean,
    val updatedAt: LocalDateTime
)
