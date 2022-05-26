package com.example.darren.myworkout

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercise-table",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutEntity::class,
            parentColumns = arrayOf("_id"),
            childColumns = arrayOf("workout_id"),
            onDelete = ForeignKey.CASCADE)]
)
data class ExerciseEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val id: Int = 0,
    @ColumnInfo(name = "workout_id")
    val workoutId: Int,
    @ColumnInfo(name = "exercise_name")
    val exerciseName: String,
    @ColumnInfo(name="isCompleted")
    val isCompleted: Boolean = false,
    @ColumnInfo(name="isSelected")
    val isSelected: Boolean = false
)