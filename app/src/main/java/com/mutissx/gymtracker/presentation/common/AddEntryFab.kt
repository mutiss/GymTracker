package com.mutissx.gymtracker.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mutissx.gymtracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEntryFab(
    onAddExercise: () -> Unit,
    onLogWeight: () -> Unit
) {
    var showChooser by remember { mutableStateOf(false) }

    FloatingActionButton(onClick = { showChooser = true }) {
        Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_entry_content_description))
    }

    if (showChooser) {
        ModalBottomSheet(onDismissRequest = { showChooser = false }) {
            ChooserRow(
                icon = Icons.Default.FitnessCenter,
                label = stringResource(R.string.add_exercise),
                onClick = {
                    showChooser = false
                    onAddExercise()
                }
            )
            ChooserRow(
                icon = Icons.Default.MonitorWeight,
                label = stringResource(R.string.log_weight),
                onClick = {
                    showChooser = false
                    onLogWeight()
                }
            )
        }
    }
}

@Composable
private fun ChooserRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp))
        Text(label, modifier = Modifier.padding(start = 16.dp))
    }
}
