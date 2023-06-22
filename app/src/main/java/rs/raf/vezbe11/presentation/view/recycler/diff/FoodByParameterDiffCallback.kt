package rs.raf.vezbe11.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.vezbe11.data.models.FoodByParameter

class FoodByParameterDiffCallback: DiffUtil.ItemCallback<FoodByParameter>(){

    override fun areItemsTheSame(oldItem: FoodByParameter, newItem: FoodByParameter): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FoodByParameter, newItem: FoodByParameter): Boolean {
        return oldItem.strMeal == newItem.strMeal
    }
}
