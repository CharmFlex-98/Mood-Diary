package com.mooddiary.ui.home.new_edit_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mooddiary.model.MoodDiary
import com.mooddiary.repository.MoodDiaryRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

data class NewEditUIState(
    val title: String? = null,
    val content: String? = null,
    val date: LocalDate? = null,
    val moodRating: Float? = null
)


class NewEditViewModel(private val _repository: MoodDiaryRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(NewEditUIState(date = LocalDate.now()))
    val uiState: StateFlow<NewEditUIState> = _uiState

    fun setTitle(title: String) {
        _uiState.value = _uiState.value.copy(
            title = title
        )
    }

    fun setContent(content: String) {
        _uiState.value = _uiState.value.copy(
            content = content
        )
    }

    fun setDate(date: LocalDate) {
        _uiState.value = _uiState.value.copy(
            date = date
        )
    }

    fun setMoodRating(rating: Float) {
        _uiState.value = _uiState.value.copy(
            moodRating = rating
        )
    }

    fun createDiary() {
        if (isInputValid()) {
            val data = uiState.value
            viewModelScope.launch(Dispatchers.IO) {
                _repository.insertDiary(
                    MoodDiary(
                        title = data.title!!,
                        content = data.content!!,
                        date = data.date!!,
                        moodIndex = data.moodRating!!.toDouble()
                    )
                )
            }
        }
    }

    fun setDiaryData(diaryId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val diary = _repository.getDiary(diaryId)
            setTitle(diary.title)
            setContent(diary.content)
            setDate(diary.date)
        }
    }

    private fun isInputValid(): Boolean {
        return _uiState.value.date != null && _uiState.value.title != null && _uiState.value.content != null && _uiState.value.moodRating != null
    }
}