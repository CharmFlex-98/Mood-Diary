package com.mooddiary.ui.home.statistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import com.mooddiary.model.MoodDiary
import com.mooddiary.repository.MoodDiaryRepository
import com.mooddiary.ui.home.Summary.MoodDiaryListState
import com.mooddiary.utils.ChartEntry
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class StatisticUIState(
    val chartData: List<ChartEntry> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
class StatisticViewModel(private val repository: MoodDiaryRepository): ViewModel() {
    val uiState: StateFlow<StatisticUIState> = repository.getAllDiary().map {
        StatisticUIState(chartData = createChartData(it), isLoading = false)
    }. catch {
        emit(StatisticUIState(errorMessage = "Cannot load data"))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StatisticUIState(isLoading = true)
    )


    private fun createChartData(data: List<MoodDiary>): List<ChartEntry> {
        return mutableListOf<ChartEntry>().apply {
            for (i in data) {
                add(ChartEntry(i.date.toEpochDay().toFloat(), i.moodIndex.toFloat()))
            }
        }.toList()
    }
}