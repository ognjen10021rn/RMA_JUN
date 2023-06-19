package rs.raf.vezbe11.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.vezbe11.data.models.Category
import rs.raf.vezbe11.data.models.CategoryEntity
import rs.raf.vezbe11.databinding.LayoutItemCategoryBinding
import rs.raf.vezbe11.presentation.view.recycler.diff.CategoryDiffCallback
import rs.raf.vezbe11.presentation.view.recycler.viewholder.CategoryViewHolder

class CategoryAdapter : ListAdapter<Category, CategoryViewHolder>(CategoryDiffCallback()){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemBinding = LayoutItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(itemBinding)
    }
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }




}