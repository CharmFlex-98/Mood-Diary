package com.mooddiary.application

import android.app.Activity
import android.app.Application
import com.mooddiary.data.MoodDiaryDao
import com.mooddiary.data.MoodDiaryDatabase
import com.mooddiary.model.MoodDiary
import com.mooddiary.repository.MoodDiaryRepository

class MoodDiaryApplication: Application() {
    val database: MoodDiaryDatabase by lazy { MoodDiaryDatabase.getInstance(this) }
    val moodDiaryRepository: MoodDiaryRepository by lazy { MoodDiaryRepository(database.getDao()) }
}

fun Activity.getMoodDiaryApplication(): MoodDiaryApplication {
    return (application as MoodDiaryApplication)
}