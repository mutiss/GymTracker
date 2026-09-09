package com.mutissx.gymtracker.presentation.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mutissx.gymtracker.R
import com.mutissx.gymtracker.domain.model.DaySummary
import com.mutissx.gymtracker.domain.model.ExerciseEntry
import com.mutissx.gymtracker.domain.model.ValueUnit
import com.mutissx.gymtracker.presentation.common.CategoryDot
import com.mutissx.gymtracker.presentation.common.CategoryPieChart
import com.mutissx.gymtracker.presentation.common.PieSlice
import com.mutissx.gymtracker.presentation.common.color
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryScreen(viewModel: HistoryViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.days.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(stringResource(R.string.no_history_yet), textAlign = TextAlign.Center)
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(uiState.days, key = { it.date }) { day ->
                DayCard(day)
            }
        }
    }
}

@Composable
private fun DayCard(day: DaySummary) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = day.date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
                    style = MaterialTheme.typography.titleSmall
                )
                if (day.weight != null) {
                    Text(
                        text = stringResource(R.string.weight_kg_format, day.weight.weightKg),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                day.exercises.forEach { entry ->
                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CategoryDot(entry.category.color())
                        Text(
                            text = stringResource(
                                R.string.exercise_row_format,
                                entry.category.displayName,
                                formatValue(entry)
                            ),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
            val slices = daySlices(day)
            if (slices.isNotEmpty()) {
                CategoryPieChart(
                    slices = slices,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .heightIn(max = 92.dp)
                        .fillMaxHeight()
                        .aspectRatio(1f, matchHeightConstraintsFirst = true)
                )
            }
        }
    }
}

private fun daySlices(day: DaySummary): List<PieSlice> {
    if (day.exercises.isEmpty()) return emptyList()
    val total = day.exercises.size
    return day.exercises
        .groupingBy { it.category }
        .eachCount()
        .map { (category, count) -> PieSlice(color = category.color(), fraction = count.toFloat() / total) }
}

@Composable
private fun formatValue(entry: ExerciseEntry): String {
    val value = if (entry.value % 1.0 == 0.0) entry.value.toInt().toString() else entry.value.toString()
    return when (entry.unit) {
        ValueUnit.MINUTES -> stringResource(R.string.unit_minutes_format, value)
        ValueUnit.REPS -> stringResource(R.string.unit_reps_format, value)
    }
}
