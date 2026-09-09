package com.mutissx.gymtracker.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mutissx.gymtracker.R
import com.mutissx.gymtracker.domain.model.ExerciseCategory
import com.mutissx.gymtracker.domain.model.ValueUnit

@Composable
fun AddExerciseDialog(
    onDismiss: () -> Unit,
    onConfirm: (category: ExerciseCategory, value: Double, unit: ValueUnit) -> Unit
) {
    var selectedCategory by remember { mutableStateOf<ExerciseCategory?>(null) }
    var valueText by remember { mutableStateOf("") }
    var unit by remember { mutableStateOf(ValueUnit.MINUTES) }

    val parsedValue = valueText.toDoubleOrNull()
    val canConfirm = selectedCategory != null && parsedValue != null && parsedValue > 0

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.add_exercise)) },
        text = {
            Column {
                Text(stringResource(R.string.exercise_label))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    ExerciseCategory.entries.forEach { category ->
                        val selected = category == selectedCategory
                        FilterChip(
                            selected = selected,
                            onClick = { selectedCategory = category },
                            label = { Text(category.displayName) },
                            leadingIcon = { CategoryDot(category.color()) }
                        )
                    }
                }
                OutlinedTextField(
                    value = valueText,
                    onValueChange = { valueText = it },
                    label = { Text(stringResource(R.string.value_label)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
                SingleChoiceSegmentedButtonRow(modifier = Modifier.padding(top = 8.dp)) {
                    ValueUnit.entries.forEachIndexed { index, entry ->
                        SegmentedButton(
                            selected = unit == entry,
                            onClick = { unit = entry },
                            shape = SegmentedButtonDefaults.itemShape(index, ValueUnit.entries.size)
                        ) {
                            Text(
                                stringResource(
                                    if (entry == ValueUnit.MINUTES) R.string.unit_minutes_label else R.string.unit_reps_label
                                )
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                enabled = canConfirm,
                onClick = { onConfirm(selectedCategory!!, parsedValue!!, unit) }
            ) { Text(stringResource(R.string.action_add)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.action_cancel)) }
        }
    )
}
