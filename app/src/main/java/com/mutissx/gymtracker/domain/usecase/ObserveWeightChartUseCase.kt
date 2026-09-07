package com.mutissx.gymtracker.domain.usecase

import com.mutissx.gymtracker.domain.model.ChartGranularity
import com.mutissx.gymtracker.domain.model.ChartPoint
import com.mutissx.gymtracker.domain.model.WeightEntry
import com.mutissx.gymtracker.domain.repository.WeightRepository
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveWeightChartUseCase(
    private val weightRepository: WeightRepository
) {
    operator fun invoke(
        granularity: ChartGranularity,
        today: LocalDate = LocalDate.now()
    ): Flow<List<ChartPoint>> {
        val start = when (granularity) {
            ChartGranularity.DAY -> today.minusDays(29)
            ChartGranularity.WEEK -> today.minusWeeks(25)
            ChartGranularity.MONTH -> today.minusMonths(11)
        }
        return weightRepository.observeWeightsInRange(start, today).map { entries ->
            when (granularity) {
                ChartGranularity.DAY -> entries
                    .sortedBy { it.date }
                    .map { entry ->
                        ChartPoint(
                            date = entry.date,
                            label = entry.date.format(DAY_FORMATTER),
                            weightKg = entry.weightKg
                        )
                    }

                ChartGranularity.WEEK -> aggregateByWeek(entries)
                ChartGranularity.MONTH -> aggregateByMonth(entries)
            }
        }
    }

    private fun aggregateByWeek(entries: List<WeightEntry>): List<ChartPoint> {
        val weekFields = WeekFields.ISO
        return entries
            .groupBy { it.date.with(weekFields.dayOfWeek(), 1L) }
            .toSortedMap()
            .map { (weekStart, entriesInWeek) ->
                ChartPoint(
                    date = weekStart,
                    label = "Wk " + weekStart.get(weekFields.weekOfWeekBasedYear()),
                    weightKg = entriesInWeek.map { it.weightKg }.average()
                )
            }
    }

    private fun aggregateByMonth(entries: List<WeightEntry>): List<ChartPoint> {
        return entries
            .groupBy { YearMonth.from(it.date) }
            .toSortedMap()
            .map { (yearMonth, entriesInMonth) ->
                ChartPoint(
                    date = yearMonth.atDay(1),
                    label = yearMonth.month.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                    weightKg = entriesInMonth.map { it.weightKg }.average()
                )
            }
    }

    private companion object {
        val DAY_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("M/d")
    }
}
