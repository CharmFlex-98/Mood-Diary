package com.mooddiary.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mooddiary.model.MoodDiary
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodDiaryDao {
    @Query("SELECT * FROM mooddiary")
    fun getAllDiary(): Flow<List<MoodDiary>>

    @Insert
    fun insertDiary(diary: MoodDiary)

    @Delete
    fun deleteDiary(diary: MoodDiary)
}