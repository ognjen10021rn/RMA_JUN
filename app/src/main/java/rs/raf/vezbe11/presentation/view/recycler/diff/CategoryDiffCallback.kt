package rs.raf.vezbe11.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.vezbe11.data.models.CategoryEntity

class CategoryDiffCallback : DiffUtil.ItemCallback<CategoryEntity>(){

    override fun areItemsTheSame(oldItem: CategoryEntity, newItem: CategoryEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CategoryEntity, newItem: CategoryEntity): Boolean {
        return oldItem.name == newItem.name
    }
}