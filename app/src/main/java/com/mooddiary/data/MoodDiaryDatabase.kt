package com.mooddiary.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import com.mooddiary.model.MoodDiary
import com.mooddiary.model.MoodDiaryConverter

@Database(entities = [MoodDiary::class], version = 3)
@TypeConverters(MoodDiaryConverter::class)
abstract class MoodDiaryDatabase : RoomDatabase() {
    companion object {
        const val databaseName = "Mood Diary Database"

        @Volatile
        var instance: MoodDiaryDatabase? = null
        fun getInstance(context: Context): MoodDiaryDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MoodDiaryDatabase::class.java,
                    databaseName
                ).fallbackToDestructiveMigration()
                    .build()
            }
        }
    }

    abstract fun getDao(): MoodDiaryDao
}