package com.mooddiary.ui.home.Summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mooddiary.model.MoodDiary
import com.mooddiary.repository.MoodDiaryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class MoodDiaryListState(
    val moodDiaryList: List<MoodDiary> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class MoodDiaryListViewModel(private val _moodDiaryRepository: MoodDiaryRepository) : ViewModel() {

    val uiState: StateFlow<MoodDiaryListState> = _moodDiaryRepository.getAllDiary().map {
        if (it.isEmpty()) {
            return@map MoodDiaryListState(errorMessage = "No data")
        }
        MoodDiaryListState(moodDiaryList = it)
    }.catch {
        emit(MoodDiaryListState(errorMessage = it.toString()))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MoodDiaryListState(isLoading = true)
    )
}