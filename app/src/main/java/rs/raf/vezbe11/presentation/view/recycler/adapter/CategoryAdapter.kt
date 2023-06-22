package rs.raf.vezbe11.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.vezbe11.data.models.Category
import rs.raf.vezbe11.databinding.LayoutItemCategoryBinding
import rs.raf.vezbe11.presentation.view.recycler.diff.CategoryDiffCallback
import rs.raf.vezbe11.presentation.view.recycler.viewholder.CategoryViewHolder
import timber.log.Timber

class CategoryAdapter(
    categoryDiffCallback: CategoryDiffCallback,
    private val onItemClicked: (Category) -> Unit, //kada se klikne na celu karticu
    private val onButtonClicked: (Category) -> Unit, //kada se klikne da dugme desno(otvara se detaljan prikaz)

): ListAdapter<Category, CategoryViewHolder>(categoryDiffCallback){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {


        val itemBinding = LayoutItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CategoryViewHolder(itemBinding,{
                Timber.e("Usao u adapter click na car d")
            if(it>=0){
            val category = getItem(it)
            onItemClicked.invoke(category)

            }

        },{

            if(it>=0){
                Timber.e("Usao u adapter click na button")

            val category = getItem(it)
            onButtonClicked.invoke(category)
            }
        })





    }
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position), onButtonClicked)
    }




}