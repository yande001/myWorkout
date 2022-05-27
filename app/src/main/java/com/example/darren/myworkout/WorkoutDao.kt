package com.example.darren.myworkout

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Insert
    suspend fun insertWorkout(workoutEntity: WorkoutEntity)

    @Insert
    suspend fun insertExercise(exerciseEntity: ExerciseEntity)

    @Delete
    suspend fun deleteWorkout(workoutEntity: WorkoutEntity)

    @Delete
    suspend fun deleteExercise(exerciseEntity: ExerciseEntity)

    @Query(value = "DELETE FROM `exercise-table` WHERE _id = :id")
    suspend fun deleteExerciseById(id: Int)

    @Query(value =
            "UPDATE `exercise-table`" +
            "SET exercise_name =:exerciseName " +
            "WHERE _id = :id ")
    suspend fun updateExerciseName(id: Int,
                                   exerciseName: String)

    @Query(value =
            "SELECT exercise_name " +
            "FROM `exercise-table`" +
            "WHERE _id = :id")
    suspend fun getExerciseNameById(id: Int): String

    @Query(value = "SELECT * FROM `workout-table`")
    fun getAllWorkout(): Flow<List<WorkoutEntity>>


    @Query(value = "SELECT * FROM `exercise-table` WHERE workout_id=:workoutId")
    fun getExercise(workoutId: Int) : Flow<List<ExerciseEntity>>

    @Query(value = "SELECT rest_Duration FROM `workout-table` WHERE _id = :id ")
    suspend fun getWorkoutRestDuration(id: Int): Long

    @Query(value = "SELECT exercise_Duration FROM `workout-table` WHERE _id = :id ")
    suspend fun getWorkoutExerciseDuration(id: Int): Long

    @Query(value = "UPDATE `workout-table` SET rest_duration = :restDuration WHERE _id = :id")
    suspend fun updateWorkoutRestDuration(id: Int, restDuration: Long)

    @Query(value = "UPDATE `workout-table` SET exercise_duration = :exerciseDuration WHERE _id = :id")
    suspend fun updateWorkoutExerciseDuration(id: Int, exerciseDuration: Long)


}