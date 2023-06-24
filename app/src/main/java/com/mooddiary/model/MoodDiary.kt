package com.mooddiary.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.mooddiary.utils.toFormattedString
import com.mooddiary.utils.toLocalDateTime
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
data class MoodDiary(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val title: String,
    val date: LocalDate,
    val content: String,
    val moodIndex: Double, // 0 - 10
)

class MoodDiaryConverter {
    @TypeConverter
    fun fromDateToString(date: LocalDate?): String? {
        return date?.let { date.toFormattedString() }
    }

    @TypeConverter
    fun fromStringToDate(date: String?): LocalDate? {
        return date?.let { date.toLocalDateTime() }
    }
}