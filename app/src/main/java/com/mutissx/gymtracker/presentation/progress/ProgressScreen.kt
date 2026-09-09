package com.mutissx.gymtracker.presentation.progress

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mutissx.gymtracker.R
import com.mutissx.gymtracker.domain.model.ChartGranularity
import com.mutissx.gymtracker.domain.model.ChartPoint
import com.mutissx.gymtracker.presentation.common.SetGoalWeightDialog
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.CartesianMeasuringContext
import com.patrykandpatrick.vico.compose.cartesian.axis.Axis
import com.patrykandpatrick.vico.compose.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.compose.cartesian.data.lineModel
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.ProvideVicoTheme
import com.patrykandpatrick.vico.compose.common.data.ExtraStore
import com.patrykandpatrick.vico.compose.m3.common.rememberM3VicoTheme
import java.time.LocalDate
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor
import org.koin.androidx.compose.koinViewModel

private val LabelListKey = ExtraStore.Key<List<String>>()

private val WeightRangeProvider = object : CartesianLayerRangeProvider {
    override fun getMinY(minY: Double, maxY: Double, extraStore: ExtraStore): Double = floor(minY) - 1.0
    override fun getMaxY(minY: Double, maxY: Double, extraStore: ExtraStore): Double = ceil(maxY) + 1.0
}

private val BottomAxisValueFormatter = object : CartesianValueFormatter {
    override fun format(
        context: CartesianMeasuringContext,
        value: Double,
        verticalAxisPosition: Axis.Position.Vertical?
    ): String {
        val labels = context.model.extraStore[LabelListKey]
        return labels.getOrNull(value.toInt()).orEmpty()
    }
}

@Composable
fun ProgressScreen(viewModel: ProgressViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val modelProducer = remember { CartesianChartModelProducer() }
    var showGoalDialog by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.points) {
        if (uiState.points.isNotEmpty()) {
            modelProducer.runTransaction {
                lineModel {
                    series(
                        uiState.points.indices.map { it.toDouble() },
                        uiState.points.map { it.weightKg }
                    )
                }
                extras { it[LabelListKey] = uiState.points.map(ChartPoint::label) }
            }
        }
    }

    ProvideVicoTheme(rememberM3VicoTheme()) {
        Column(modifier = Modifier.fillMaxSize()) {
            GoalWeightSection(
                latestWeightKg = uiState.latestWeightKg,
                goalWeightKg = uiState.goalWeightKg,
                onEditGoal = { showGoalDialog = true }
            )

            SingleChoiceSegmentedButtonRow(modifier = Modifier.padding(16.dp)) {
                ChartGranularity.entries.forEachIndexed { index, granularity ->
                    SegmentedButton(
                        selected = uiState.granularity == granularity,
                        onClick = { viewModel.onGranularitySelected(granularity) },
                        shape = SegmentedButtonDefaults.itemShape(index, ChartGranularity.entries.size)
                    ) {
                        Text(
                            stringResource(
                                when (granularity) {
                                    ChartGranularity.DAY -> R.string.chart_granularity_day
                                    ChartGranularity.WEEK -> R.string.chart_granularity_week
                                    ChartGranularity.MONTH -> R.string.chart_granularity_month
                                }
                            )
                        )
                    }
                }
            }

            if (uiState.points.size < 2) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        stringResource(R.string.progress_empty_state_message),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                CartesianChartHost(
                    chart = rememberCartesianChart(
                        rememberLineCartesianLayer(rangeProvider = WeightRangeProvider),
                        startAxis = VerticalAxis.rememberStart(),
                        bottomAxis = HorizontalAxis.rememberBottom(valueFormatter = BottomAxisValueFormatter)
                    ),
                    modelProducer = modelProducer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .padding(16.dp)
                )
            }
        }
    }

    if (showGoalDialog) {
        SetGoalWeightDialog(
            initialGoalWeightKg = uiState.goalWeightKg,
            onDismiss = { showGoalDialog = false },
            onConfirm = { weightKg ->
                viewModel.onSetGoalWeight(weightKg)
                showGoalDialog = false
            }
        )
    }
}

@Composable
private fun GoalWeightSection(
    latestWeightKg: Double?,
    goalWeightKg: Double?,
    onEditGoal: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (goalWeightKg == null) {
                OutlinedButton(onClick = onEditGoal, modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(R.string.set_goal_weight_action))
                }
            } else {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(
                            R.string.goal_weight_summary_format,
                            latestWeightKg?.let { formatKg(it) } ?: "—",
                            formatKg(goalWeightKg)
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onEditGoal) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = stringResource(R.string.edit_goal_weight_content_description)
                        )
                    }
                }
                val messages = stringArrayResource(R.array.motivational_messages)
                val statusMessage = when {
                    latestWeightKg == null -> stringResource(R.string.log_weight_prompt)
                    abs(latestWeightKg - goalWeightKg) <= 0.1 -> stringResource(R.string.goal_reached_message)
                    else -> messages[(LocalDate.now().toEpochDay() % messages.size).toInt()]
                }
                Text(
                    text = statusMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

private fun formatKg(value: Double): String =
    if (value % 1.0 == 0.0) value.toInt().toString() else value.toString()
