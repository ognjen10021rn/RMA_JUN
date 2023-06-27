package rs.raf.vezbe11.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.vezbe11.data.models.Food
import rs.raf.vezbe11.data.models.FoodByParameter

class FoodDiffCallback: DiffUtil.ItemCallback<Food>(){

    override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
        return oldItem.name == newItem.name
    }
}
