package com.mooddiary.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mooddiary.model.MoodDiary
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodDiaryDao {
    @Query("SELECT * FROM mooddiary")
    fun getAllDiary(): Flow<List<MoodDiary>>

    @Insert
    suspend fun insertDiary(diary: MoodDiary)

    @Query("SELECT * FROM mooddiary WHERE id=:id")
    suspend fun getDiary(id: Long): MoodDiary

    @Delete
    suspend fun deleteDiary(diary: MoodDiary)

    @Update
    suspend fun updateDiary(diary: MoodDiary)
}