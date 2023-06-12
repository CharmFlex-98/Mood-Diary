package com.mooddiary.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class MoodDiaryDatabase: RoomDatabase() {
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
                ).build()
            }
        }
    }
    abstract fun getDao(): MoodDiaryDao
}