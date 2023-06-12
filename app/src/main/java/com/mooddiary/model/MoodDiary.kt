package com.mooddiary.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class MoodDiary(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val date: LocalDateTime,
    val content: String,
    val moodIndex: Double, // 0 - 10
)