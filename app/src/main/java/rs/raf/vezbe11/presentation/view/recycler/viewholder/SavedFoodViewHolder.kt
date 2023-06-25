package rs.raf.vezbe11.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rs.raf.vezbe11.data.models.SavedFood
import rs.raf.vezbe11.databinding.LayoutItemSavedfoodBinding

class SavedFoodViewHolder(
    private val itemBinding: LayoutItemSavedfoodBinding,
    onItemClicked: (Int) -> Unit,
    onButtonClicked: (Int) -> Unit

) : RecyclerView.ViewHolder(itemBinding.root) {

    init {
        itemBinding.root.setOnClickListener {
            onItemClicked.invoke(adapterPosition)
        }
        itemBinding.buttonDelete.setOnClickListener {
            onButtonClicked.invoke(adapterPosition)
        }

    }

    fun bind(savedFood:SavedFood, onButtonClicked: (SavedFood) -> Unit){
        itemBinding.buttonDelete.setOnClickListener{
            onButtonClicked.invoke(savedFood)
        }

        itemBinding.foodName.text = savedFood.name
        Glide.with(itemBinding.root)
            .load(savedFood.strMealThumb)
            .into(itemBinding.foodPicture);

    }


}

