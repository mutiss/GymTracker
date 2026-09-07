package com.mutissx.gymtracker.data.mapper

import com.mutissx.gymtracker.data.local.entity.WeightEntryEntity
import com.mutissx.gymtracker.domain.model.WeightEntry

fun WeightEntryEntity.toDomain(): WeightEntry = WeightEntry(
    id = id,
    date = date,
    weightKg = weightKg,
    updatedAt = updatedAt
)

fun WeightEntry.toEntity(): WeightEntryEntity = WeightEntryEntity(
    id = id,
    date = date,
    weightKg = weightKg,
    updatedAt = updatedAt
)
