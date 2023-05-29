package rs.raf.vezbe11.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import rs.raf.vezbe11.data.models.CategoryEntity
import rs.raf.vezbe11.databinding.LayoutItemCategoryBinding

class CategoryViewHolder (private val itemBinding: LayoutItemCategoryBinding) : RecyclerView.ViewHolder(itemBinding.root){

        fun bind(category: CategoryEntity){
            itemBinding.categoryName.text = category.name
        }
}