package com.example.darren.myworkout

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Insert
    suspend fun insertWorkout(workoutEntity: WorkoutEntity)

    @Insert
    suspend fun insertExercise(exerciseEntity: ExerciseEntity)

    @Delete
    suspend fun deleteWorkout(workoutEntity: WorkoutEntity)

    @Query(value = "SELECT * FROM `workout-table`")
    fun getAllWorkout(): Flow<List<WorkoutEntity>>


    @Query(value = "SELECT * FROM `exercise-table` WHERE workout_id=:workoutId")
    fun getExercise(workoutId: Int) : Flow<List<ExerciseEntity>>
}