package com.example.a7minutes

import android.app.Application

class WorkoutApp: Application() {
    val db by lazy{
        HistoryDatabase.getInstance(this)
    }
}