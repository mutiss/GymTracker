package com.mutissx.gymtracker.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
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
import com.mutissx.gymtracker.R

@Composable
fun SetGoalWeightDialog(
    initialGoalWeightKg: Double?,
    onDismiss: () -> Unit,
    onConfirm: (weightKg: Double) -> Unit
) {
    var weightText by remember { mutableStateOf(initialGoalWeightKg?.toString().orEmpty()) }
    val parsedWeight = weightText.toDoubleOrNull()
    val canConfirm = parsedWeight != null && parsedWeight > 0

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.goal_weight_title)) },
        text = {
            OutlinedTextField(
                value = weightText,
                onValueChange = { weightText = it },
                label = { Text(stringResource(R.string.goal_weight_label)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                enabled = canConfirm,
                onClick = { onConfirm(parsedWeight!!) }
            ) { Text(stringResource(R.string.action_save)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.action_cancel)) }
        }
    )
}
