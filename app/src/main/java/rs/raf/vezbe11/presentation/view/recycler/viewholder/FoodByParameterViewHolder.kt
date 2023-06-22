package rs.raf.vezbe11.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rs.raf.vezbe11.data.models.FoodByParameter
import rs.raf.vezbe11.databinding.LayoutItemFoodbyparameterBinding


class FoodByParameterViewHolder(

    private val itemBinding: LayoutItemFoodbyparameterBinding,
    onItemClicked: (Int) -> Unit


) : RecyclerView.ViewHolder(itemBinding.root){


    init {
        itemBinding.root.setOnClickListener {
            onItemClicked.invoke(adapterPosition)
        }
    }


    fun bind(food: FoodByParameter){
        itemBinding.foodName.text = food.strMeal
        Glide.with(itemBinding.root)
            .load(food.strThumb)
            .into(itemBinding.foodPicture);

        itemBinding.buttonConfigure.setOnClickListener{
            var mPosition:Int = adapterPosition
            println("\n\nID:\n\n")
            println(food.id)

        }
    }

}