package rs.raf.vezbe11.presentation.view.recycler.viewholder

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rs.raf.vezbe11.data.models.SavedFood
import rs.raf.vezbe11.databinding.LayoutItemSavedfoodBinding
import java.io.File

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
        if(savedFood.customImagePath != null){
//            Glide.with(itemBinding.root)
//                .load(savedFood.customImagePath)
//                .into(itemBinding.foodPicture);
            val f = File(savedFood.customImagePath!!)
            val uri : Uri = Uri.fromFile(f)
            itemBinding.foodPicture.setImageURI(uri)
        }else{
            Glide.with(itemBinding.root)
                .load(savedFood.strMealThumb)
                .into(itemBinding.foodPicture);
        }

    }


}

