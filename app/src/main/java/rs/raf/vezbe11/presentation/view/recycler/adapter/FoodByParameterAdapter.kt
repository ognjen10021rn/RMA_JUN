package rs.raf.vezbe11.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.vezbe11.data.models.FoodByParameterEntity
import rs.raf.vezbe11.databinding.LayoutItemFoodbyparameterBinding
import rs.raf.vezbe11.presentation.view.recycler.diff.FoodByParameterDiffCallback
import rs.raf.vezbe11.presentation.view.recycler.viewholder.FoodByParameterViewHolder

class FoodByParameterAdapter : ListAdapter<FoodByParameterEntity, FoodByParameterViewHolder>(FoodByParameterDiffCallback()){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodByParameterViewHolder {
        val itemBinding = LayoutItemFoodbyparameterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodByParameterViewHolder(itemBinding)
    }
    override fun onBindViewHolder(holder: FoodByParameterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }




}