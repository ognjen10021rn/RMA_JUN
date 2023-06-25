package rs.raf.vezbe11.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.vezbe11.data.models.SavedFood
import rs.raf.vezbe11.databinding.LayoutItemSavedfoodBinding
import rs.raf.vezbe11.presentation.view.recycler.diff.SavedFoodDiffCallback
import rs.raf.vezbe11.presentation.view.recycler.viewholder.SavedFoodViewHolder

class SavedFoodAdapter(
    SavedFoodDiffCallback: SavedFoodDiffCallback,
    private val onItemClicked: (SavedFood) -> Unit,
    private val onButtonClicked: (SavedFood) -> Unit

    ): ListAdapter<SavedFood, SavedFoodViewHolder>(SavedFoodDiffCallback){



        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedFoodViewHolder {
            val itemBinding = LayoutItemSavedfoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return SavedFoodViewHolder(itemBinding, {
                val savedFood = getItem(it)
                onItemClicked.invoke(savedFood)
            },{
                val savedFood = getItem(it)
                onButtonClicked.invoke(savedFood)
            })
        }

        override fun onBindViewHolder(holder: SavedFoodViewHolder, position: Int) {
            holder.bind(getItem(position), onButtonClicked)
        }
}