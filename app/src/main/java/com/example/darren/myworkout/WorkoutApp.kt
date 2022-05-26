package com.example.darren.myworkout

import android.app.Application

class WorkoutApp: Application() {
    val db by lazy {
        myWorkoutAppDB.getInstance(this)
    }
}