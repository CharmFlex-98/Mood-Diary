package com.mooddiary.repository

import com.mooddiary.model.MoodDiary
import kotlinx.coroutines.flow.Flow

abstract class MoodDiaryBaseRepository {
    abstract fun getAllDiary(): Flow<List<MoodDiary>>
    abstract fun insertDiary(diary: MoodDiary)
    abstract fun deleteDiary(diary: MoodDiary)
}