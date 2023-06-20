package rs.raf.vezbe11.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.vezbe11.data.models.FoodByParameterEntity

class FoodByParameterDiffCallback: DiffUtil.ItemCallback<FoodByParameterEntity>(){

    override fun areItemsTheSame(oldItem: FoodByParameterEntity, newItem: FoodByParameterEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FoodByParameterEntity, newItem: FoodByParameterEntity): Boolean {
        return oldItem.strMeal == newItem.strMeal
    }
}
