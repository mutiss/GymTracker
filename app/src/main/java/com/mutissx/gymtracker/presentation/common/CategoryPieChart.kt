package com.mutissx.gymtracker.presentation.common

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke

data class PieSlice(val color: Color, val fraction: Float)

@Composable
fun CategoryPieChart(
    slices: List<PieSlice>,
    modifier: Modifier = Modifier,
    strokeWidthFraction: Float = 0.35f
) {
    Canvas(modifier = modifier) {
        val diameter = minOf(size.width, size.height)
        val strokeWidth = diameter * strokeWidthFraction
        val arcSize = Size(diameter - strokeWidth, diameter - strokeWidth)
        val topLeft = Offset(
            (size.width - arcSize.width) / 2f,
            (size.height - arcSize.height) / 2f
        )
        var startAngle = -90f
        slices.forEach { slice ->
            val sweep = slice.fraction * 360f
            drawArc(
                color = slice.color,
                startAngle = startAngle,
                sweepAngle = sweep,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth)
            )
            startAngle += sweep
        }
    }
}
