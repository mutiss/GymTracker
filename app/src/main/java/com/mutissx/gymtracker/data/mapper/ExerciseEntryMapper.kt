package com.mutissx.gymtracker.data.mapper

import com.mutissx.gymtracker.data.local.entity.ExerciseEntryEntity
import com.mutissx.gymtracker.domain.model.ExerciseCategory
import com.mutissx.gymtracker.domain.model.ExerciseEntry
import com.mutissx.gymtracker.domain.model.ValueUnit

fun ExerciseEntryEntity.toDomain(): ExerciseEntry = ExerciseEntry(
    id = id,
    date = date,
    category = ExerciseCategory.valueOf(category),
    value = value,
    unit = ValueUnit.valueOf(unit),
    createdAt = createdAt
)

fun ExerciseEntry.toEntity(): ExerciseEntryEntity = ExerciseEntryEntity(
    id = id,
    date = date,
    category = category.name,
    value = value,
    unit = unit.name,
    createdAt = createdAt
)
