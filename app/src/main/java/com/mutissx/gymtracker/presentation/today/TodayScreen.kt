package com.mutissx.gymtracker.presentation.today

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mutissx.gymtracker.R
import com.mutissx.gymtracker.domain.model.ExerciseEntry
import com.mutissx.gymtracker.domain.model.ValueUnit
import com.mutissx.gymtracker.presentation.common.AddEntryFab
import com.mutissx.gymtracker.presentation.common.AddExerciseDialog
import com.mutissx.gymtracker.presentation.common.CategoryDot
import com.mutissx.gymtracker.presentation.common.LogWeightDialog
import com.mutissx.gymtracker.presentation.common.color
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import org.koin.androidx.compose.koinViewModel

@Composable
fun TodayScreen(viewModel: TodayViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var showAddExerciseDialog by remember { mutableStateOf(false) }
    var showLogWeightDialog by remember { mutableStateOf(false) }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        floatingActionButton = {
            AddEntryFab(
                onAddExercise = { showAddExerciseDialog = true },
                onLogWeight = { showLogWeightDialog = true }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(
                text = LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )

            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)) {
                Text(
                    text = uiState.weight?.let { stringResource(R.string.weight_kg_format, it.weightKg) }
                        ?: stringResource(R.string.no_weight_logged_today),
                    modifier = Modifier.padding(16.dp)
                )
            }

            if (uiState.exercises.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(stringResource(R.string.no_exercises_yet), textAlign = TextAlign.Center)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.exercises, key = { it.id }) { entry ->
                        ExerciseRow(entry, onDelete = { viewModel.onDeleteExercise(entry.id) })
                    }
                }
            }
        }
    }

    if (showAddExerciseDialog) {
        AddExerciseDialog(
            onDismiss = { showAddExerciseDialog = false },
            onConfirm = { category, value, unit ->
                viewModel.onAddExercise(category, value, unit)
                showAddExerciseDialog = false
            }
        )
    }

    if (showLogWeightDialog) {
        LogWeightDialog(
            initialWeightKg = uiState.weight?.weightKg,
            onDismiss = { showLogWeightDialog = false },
            onConfirm = { weightKg ->
                viewModel.onLogWeight(weightKg)
                showLogWeightDialog = false
            }
        )
    }
}

@Composable
private fun ExerciseRow(entry: ExerciseEntry, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryDot(entry.category.color())
            Text(
                text = stringResource(R.string.exercise_row_format, entry.category.displayName, formatValue(entry)),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            )
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete_content_description))
            }
        }
    }
}

@Composable
private fun formatValue(entry: ExerciseEntry): String {
    val value = if (entry.value % 1.0 == 0.0) entry.value.toInt().toString() else entry.value.toString()
    return when (entry.unit) {
        ValueUnit.MINUTES -> stringResource(R.string.unit_minutes_format, value)
        ValueUnit.REPS -> stringResource(R.string.unit_reps_format, value)
    }
}
