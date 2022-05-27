package com.example.darren.myworkout

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.darren.myworkout.databinding.ActivitySettingBinding
import com.example.darren.myworkout.databinding.DialogUpdateBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SettingActivity : AppCompatActivity() {
    private var binding: ActivitySettingBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val dao = (application as WorkoutApp).db.workoutDao()

        setDefaultWorkout(dao)

        setUpListOfDataIntoRecyclerView( dao)

        showCurrentOnOffDuration(dao)

        binding?.buttonAdd?.setOnClickListener {
            addExercise(dao)
        }

        binding?.buttonSet?.setOnClickListener {
            setDuration(dao)
        }

    }

    private fun setDuration(dao: WorkoutDao) {
        val inputRestDuration = binding?.editRestDuration?.text.toString()
        val inputExerciseDuration = binding?.editExerciseDuration?.text.toString()

        if(inputRestDuration.isNotEmpty() && inputExerciseDuration.isNotEmpty()){
            lifecycleScope.launch{
                dao.updateWorkoutRestDuration(1,inputRestDuration.toLong())
                dao.updateWorkoutExerciseDuration(1,inputExerciseDuration.toLong())

                binding?.editRestDuration?.setText(inputRestDuration)
                binding?.editExerciseDuration?.setText(inputExerciseDuration)

                Toast.makeText(this@SettingActivity, "Updated", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun showCurrentOnOffDuration(workoutDao: WorkoutDao) {
        lifecycleScope.launch {
            val restDuration = workoutDao.getWorkoutRestDuration(1)
            val exerciseDuration = workoutDao.getWorkoutExerciseDuration(1)

            binding?.editRestDuration?.setText(restDuration.toInt().toString())
            binding?.editExerciseDuration?.setText(exerciseDuration.toInt().toString())
        }
    }


    private fun addExercise(workoutDao: WorkoutDao){
        val addExerciseName = binding?.editExerciseName?.text.toString()

        if (addExerciseName.isNotEmpty()){
            lifecycleScope.launch {
                workoutDao.insertExercise(ExerciseEntity(workoutId = 1, exerciseName = addExerciseName))
                binding?.editExerciseName?.text?.clear()

                Toast.makeText(this@SettingActivity,"New Exercise is added.", Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this, "Please enter an exercise name.", Toast.LENGTH_LONG).show()
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

    private fun setUpListOfDataIntoRecyclerView(dao: WorkoutDao){
        lifecycleScope.launch {
            dao.getExercise(1).collect {
                val list = ArrayList(it)
                val itemsAdapter = ItemsAdapter(list,
                    {
                            updateId->
                        updateExerciseDialog(updateId, dao)

                    },
                    {
                            deleteId ->
                        deleteExerciseDialog(deleteId, dao)
                    }
                )
                val layoutManager = LinearLayoutManager(this@SettingActivity)
                binding?.rvItemsList?.layoutManager = layoutManager
                binding?.rvItemsList?.adapter = itemsAdapter
            }
        }
    }


    private fun deleteExerciseDialog(deleteId: Int, workoutDao: WorkoutDao) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Exercise")
        builder.setIcon(android.R.drawable.ic_dialog_alert)


        builder.setPositiveButton("YES"){dialogInterface, _ ->
            lifecycleScope.launch {
                workoutDao.deleteExerciseById(deleteId)
            }
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("NO"){dialogInterface, _ ->
            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun updateExerciseDialog(updateId: Int, workoutDao: WorkoutDao){
        val updateDialog = Dialog(this, R.style.Theme_Dialog)
        updateDialog.setCancelable(false)
        val updateDialogBinding = DialogUpdateBinding.inflate(layoutInflater)
        updateDialog.setContentView(updateDialogBinding.root)

        lifecycleScope.launch {
            val oldExerciseName = workoutDao.getExerciseNameById(updateId)
            updateDialogBinding.etUpdateName.setText(oldExerciseName)
        }



        updateDialogBinding.tvUpdate.setOnClickListener {
            val name = updateDialogBinding.etUpdateName.text.toString()
            if(name.isNotEmpty() ){
                lifecycleScope.launch{
                    workoutDao.updateExerciseName(updateId, name)
                    Toast.makeText(applicationContext, "Exercise Updated", Toast.LENGTH_SHORT).show()
                    updateDialog.dismiss()
                }
            } else{
                Toast.makeText(applicationContext, "Exercise name can not be blank", Toast.LENGTH_LONG).show()
            }
        }

        updateDialogBinding.tvCancel.setOnClickListener{
            updateDialog.dismiss()
        }
        updateDialog.show()
    }
}