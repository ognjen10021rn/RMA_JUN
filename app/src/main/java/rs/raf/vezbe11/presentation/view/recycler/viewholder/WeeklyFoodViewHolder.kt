package rs.raf.vezbe11.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import rs.raf.vezbe11.data.models.Category
import rs.raf.vezbe11.data.models.SavedFood
import rs.raf.vezbe11.databinding.LayoutItemCategoryBinding
import rs.raf.vezbe11.databinding.LayoutItemWeeklyplanBinding


class WeeklyFoodViewHolder (
    private val itemBinding: LayoutItemWeeklyplanBinding,
    //onItemClicked: (Int) -> Unit,
    //onButtonClicked: (Int) -> Unit

) : RecyclerView.ViewHolder(itemBinding.root){

    fun bind(food: SavedFood){


//        itemBinding.buttonConfigure.setOnClickListener{
//            onButtonClicked.invoke(category)
//        }

        itemBinding.categoryName.text = food.name
//        Glide.with(itemBinding.root)
//            .load(category.imagePath)
//            .into(itemBinding.categoryPicture);
    }

}