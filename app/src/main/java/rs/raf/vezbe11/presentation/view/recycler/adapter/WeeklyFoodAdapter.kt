package rs.raf.vezbe11.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.vezbe11.data.models.Category
import rs.raf.vezbe11.data.models.SavedFood
import rs.raf.vezbe11.databinding.LayoutItemCategoryBinding
import rs.raf.vezbe11.databinding.LayoutItemWeeklyplanBinding
import rs.raf.vezbe11.presentation.view.recycler.diff.CategoryDiffCallback
import rs.raf.vezbe11.presentation.view.recycler.diff.WeeklyFoodDiffCallback
import rs.raf.vezbe11.presentation.view.recycler.viewholder.CategoryViewHolder
import rs.raf.vezbe11.presentation.view.recycler.viewholder.WeeklyFoodViewHolder
import timber.log.Timber

class WeeklyFoodAdapter(
    weeklyFoodDiffCallback: WeeklyFoodDiffCallback,
    //private val onItemClicked: (SavedFood) -> Unit, //kada se klikne na celu karticu
    //private val onButtonClicked: (SavedFood) -> Unit, //kada se klikne da dugme desno(otvara se detaljan prikaz)

): ListAdapter<SavedFood, WeeklyFoodViewHolder>(weeklyFoodDiffCallback){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyFoodViewHolder {


        val itemBinding = LayoutItemWeeklyplanBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return WeeklyFoodViewHolder(itemBinding)


    }
    override fun onBindViewHolder(holder: WeeklyFoodViewHolder, position: Int) {
        holder.bind(getItem(position))
    }




}