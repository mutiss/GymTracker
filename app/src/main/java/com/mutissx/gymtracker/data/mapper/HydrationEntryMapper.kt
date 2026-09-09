package com.mutissx.gymtracker.data.mapper

import com.mutissx.gymtracker.data.local.entity.HydrationEntryEntity
import com.mutissx.gymtracker.domain.model.HydrationEntry

fun HydrationEntryEntity.toDomain(): HydrationEntry = HydrationEntry(
    id = id,
    date = date,
    hydrated = hydrated,
    updatedAt = updatedAt
)

fun HydrationEntry.toEntity(): HydrationEntryEntity = HydrationEntryEntity(
    id = id,
    date = date,
    hydrated = hydrated,
    updatedAt = updatedAt
)
