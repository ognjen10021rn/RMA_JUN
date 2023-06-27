package rs.raf.vezbe11.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rs.raf.vezbe11.data.models.Food
import rs.raf.vezbe11.data.models.FoodByParameter
import rs.raf.vezbe11.databinding.LayoutItemFoodbyparameterBinding


class FoodViewHolder(

    private val itemBinding: LayoutItemFoodbyparameterBinding,
    //onItemClicked: (Int) -> Unit


) : RecyclerView.ViewHolder(itemBinding.root){


    init {

    }


    fun bind(food: Food){
        itemBinding.foodName.text = food.name
        Glide.with(itemBinding.root)
            .load(food.strMealThumb)
            .into(itemBinding.foodPicture);

//        itemBinding.buttonConfigure.setOnClickListener{
////            var mPosition:Int = adapterPosition
////            println("\n\nID:\n\n")
////            println(food.id)
//
//        }
    }

}