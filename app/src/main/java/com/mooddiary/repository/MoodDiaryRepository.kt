package com.mooddiary.repository

import com.mooddiary.data.MoodDiaryDao
import com.mooddiary.model.MoodDiary
import kotlinx.coroutines.flow.Flow

class MoodDiaryRepository(private val _dao: MoodDiaryDao): MoodDiaryBaseRepository() {
    override fun getAllDiary(): Flow<List<MoodDiary>> {
        return _dao.getAllDiary()
    }

    override fun insertDiary(diary: MoodDiary) {
        _dao.insertDiary(diary)
    }

    override fun deleteDiary(diary: MoodDiary) {
        _dao.deleteDiary(diary)
    }
}