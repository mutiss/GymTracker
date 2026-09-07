package com.mutissx.gymtracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mutissx.gymtracker.data.local.converter.LocalDateConverter
import com.mutissx.gymtracker.data.local.dao.ExerciseEntryDao
import com.mutissx.gymtracker.data.local.dao.WeightEntryDao
import com.mutissx.gymtracker.data.local.entity.ExerciseEntryEntity
import com.mutissx.gymtracker.data.local.entity.WeightEntryEntity

@Database(
    entities = [ExerciseEntryEntity::class, WeightEntryEntity::class],
    version = 2,
    exportSchema = true
)
@TypeConverters(LocalDateConverter::class)
abstract class GymTrackerDatabase : RoomDatabase() {
    abstract fun exerciseEntryDao(): ExerciseEntryDao
    abstract fun weightEntryDao(): WeightEntryDao
}
