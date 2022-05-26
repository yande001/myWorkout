package com.example.darren.myworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.darren.myworkout.databinding.ActivitySettingBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SettingActivity : AppCompatActivity() {
    private var binding: ActivitySettingBinding? = null
    private var mWorkoutKey: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val dao = (application as WorkoutApp).db.workoutDao()

        setDefaultWorkout(dao)

        lifecycleScope.launch {
            dao.getExercise(1).collect {
                val list = ArrayList(it)
                setUpListOfDataIntoRecyclerView(list, dao)
            }
        }

        binding?.buttonAdd?.setOnClickListener {
            if (binding?.editExerciseName?.text.toString().isNotEmpty()){
                addExercise(dao, binding?.editExerciseName?.text.toString())
                binding?.editExerciseName?.text?.clear()
            } else{
                Toast.makeText(this, "Please enter an exercise name.", Toast.LENGTH_LONG).show()
            }

        }

    }

    private fun addExercise(workoutDao: WorkoutDao, exName: String){
        lifecycleScope.launch {
            workoutDao.insertExercise(ExerciseEntity(workoutId = 1, exerciseName = exName))

        }
    }

    private fun setDefaultWorkout(workoutDao: WorkoutDao){
        lifecycleScope.launch {
            workoutDao.getAllWorkout().collect {
                val list = ArrayList(it)
                if (list.isEmpty()) {
                    val workoutEntity = WorkoutEntity(restDuration = 10, exerciseDuration = 30)
                    workoutDao.insertWorkout(workoutEntity)
                    workoutDao.insertExercise(ExerciseEntity(workoutId = 1, exerciseName = "Jumping Jacks"))
                    workoutDao.insertExercise(ExerciseEntity(workoutId = 1, exerciseName = "Wall Sit"))
                    workoutDao.insertExercise(ExerciseEntity(workoutId = 1, exerciseName = "Push Up"))
                    workoutDao.insertExercise(ExerciseEntity(workoutId = 1, exerciseName = "Abdominal Crunch"))
                    workoutDao.insertExercise(ExerciseEntity(workoutId = 1, exerciseName = "Step-Up onto Chair"))
                    workoutDao.insertExercise(ExerciseEntity(workoutId = 1, exerciseName = "Squat"))
                    workoutDao.insertExercise(ExerciseEntity(workoutId = 1, exerciseName = "Tricep Dip On Chair"))
                    workoutDao.insertExercise(ExerciseEntity(workoutId = 1, exerciseName = "Plank"))
                    workoutDao.insertExercise(ExerciseEntity(workoutId = 1, exerciseName = "High Knees Running In Place"))
                    workoutDao.insertExercise(ExerciseEntity(workoutId = 1, exerciseName = "Lunges"))
                    workoutDao.insertExercise(ExerciseEntity(workoutId = 1, exerciseName = "Push up and Rotation"))
                    workoutDao.insertExercise(ExerciseEntity(workoutId = 1, exerciseName = "SidePlank"))
                }
            }
        }
    }

    private fun setUpListOfDataIntoRecyclerView(exerciseList: ArrayList<ExerciseEntity>, dao: WorkoutDao){
        val itemsAdapter = ItemsAdapter(exerciseList)
        val layoutManager = LinearLayoutManager(this)
        binding?.rvItemsList?.layoutManager = layoutManager
        binding?.rvItemsList?.adapter = itemsAdapter
    }
}