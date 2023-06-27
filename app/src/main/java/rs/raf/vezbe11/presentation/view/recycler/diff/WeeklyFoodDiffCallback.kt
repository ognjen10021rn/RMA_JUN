package rs.raf.vezbe11.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.vezbe11.data.models.Category
import rs.raf.vezbe11.data.models.SavedFood

class WeeklyFoodDiffCallback : DiffUtil.ItemCallback<SavedFood>(){

    override fun areItemsTheSame(oldItem: SavedFood, newItem: SavedFood): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SavedFood, newItem: SavedFood): Boolean {
        return oldItem.name == newItem.name
    }
}