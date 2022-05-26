package com.example.darren.myworkout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.darren.myworkout.databinding.ActivitySettingBinding
import com.example.darren.myworkout.databinding.ItemsRowBinding


class ItemsAdapter(private val items: ArrayList<ExerciseEntity>): RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemsRowBinding): RecyclerView.ViewHolder(binding.root){
        val llMain = binding.llMain
        val tvEmail = binding.tvExerciseName
        val tvExerciseNumber = binding.tvExerciseNumber
        val ivEdit = binding.ivEdit
        val ivDelete = binding.ivDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = items[position]

        holder.tvEmail.text = item.exerciseName
        holder.tvExerciseNumber.text = (position+1).toString()

        if (position % 2 == 0){
            holder.llMain.setBackgroundColor(ContextCompat.getColor(context, R.color.lightGrey))
        } else{
            holder.llMain.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }
}