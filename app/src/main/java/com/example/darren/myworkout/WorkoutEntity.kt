package com.example.darren.myworkout

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout-table")
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val id: Int = 0,
    @ColumnInfo(name = "rest_Duration")
    val restDuration: Long,
    @ColumnInfo(name = "exercise_Duration")
    val exerciseDuration: Long
)

