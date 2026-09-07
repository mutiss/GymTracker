package com.mutissx.gymtracker.presentation.common

import androidx.compose.ui.graphics.Color
import com.mutissx.gymtracker.domain.model.ExerciseCategory

fun ExerciseCategory.color(): Color = when (this) {
    ExerciseCategory.CARDIO -> Color(0xFFEF5350)
    ExerciseCategory.BICEPS -> Color(0xFF42A5F5)
    ExerciseCategory.TRICEPS -> Color(0xFFAB47BC)
    ExerciseCategory.PECTORAL -> Color(0xFF66BB6A)
    ExerciseCategory.SHOULDER -> Color(0xFFFFA726)
    ExerciseCategory.BACK -> Color(0xFF26A69A)
    ExerciseCategory.LEGS -> Color(0xFF5C6BC0)
    ExerciseCategory.ABS -> Color(0xFFEC407A)
}
