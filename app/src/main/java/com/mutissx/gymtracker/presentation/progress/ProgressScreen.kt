package com.mutissx.gymtracker.presentation.progress

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mutissx.gymtracker.domain.model.ChartGranularity
import com.mutissx.gymtracker.domain.model.ChartPoint
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
            SingleChoiceSegmentedButtonRow(modifier = Modifier.padding(16.dp)) {
                ChartGranularity.entries.forEachIndexed { index, granularity ->
                    SegmentedButton(
                        selected = uiState.granularity == granularity,
                        onClick = { viewModel.onGranularitySelected(granularity) },
                        shape = SegmentedButtonDefaults.itemShape(index, ChartGranularity.entries.size)
                    ) {
                        Text(
                            when (granularity) {
                                ChartGranularity.DAY -> "Day"
                                ChartGranularity.WEEK -> "Week"
                                ChartGranularity.MONTH -> "Month"
                            }
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
                        "Log your weight on a few different days to see your progress chart.",
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
}
