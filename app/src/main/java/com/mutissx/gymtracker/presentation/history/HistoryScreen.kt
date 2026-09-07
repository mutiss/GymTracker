package com.mutissx.gymtracker.presentation.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mutissx.gymtracker.domain.model.DaySummary
import com.mutissx.gymtracker.domain.model.ExerciseEntry
import com.mutissx.gymtracker.domain.model.ValueUnit
import com.mutissx.gymtracker.presentation.common.CategoryDot
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
            Text("No history yet", textAlign = TextAlign.Center)
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
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = day.date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
                style = MaterialTheme.typography.titleSmall
            )
            if (day.weight != null) {
                Text(
                    text = "Weight: ${day.weight.weightKg} kg",
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
                        text = "${entry.category.displayName} — ${formatValue(entry)}",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

private fun formatValue(entry: ExerciseEntry): String {
    val value = if (entry.value % 1.0 == 0.0) entry.value.toInt().toString() else entry.value.toString()
    return when (entry.unit) {
        ValueUnit.MINUTES -> "$value min"
        ValueUnit.REPS -> "$value reps"
    }
}
