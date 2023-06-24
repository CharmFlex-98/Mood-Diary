package com.mooddiary.repository

import com.mooddiary.model.MoodDiary
import kotlinx.coroutines.flow.Flow

abstract class MoodDiaryBaseRepository {
    abstract fun getAllDiary(): Flow<List<MoodDiary>>
    abstract suspend fun insertDiary(diary: MoodDiary)
    abstract suspend fun deleteDiary(diary: MoodDiary)

    abstract suspend fun getDiary(diaryId: Long): MoodDiary
}