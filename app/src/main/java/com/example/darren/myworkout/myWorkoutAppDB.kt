package com.example.darren.myworkout

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [WorkoutEntity::class, ExerciseEntity::class], version = 1)
abstract class myWorkoutAppDB: RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao

    companion object{
        @Volatile
        private var INSTANCE: myWorkoutAppDB? = null

        fun getInstance(context: Context): myWorkoutAppDB{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        myWorkoutAppDB::class.java,
                        "employee_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }

        }

    }
}