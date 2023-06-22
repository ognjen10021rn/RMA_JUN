package rs.raf.vezbe11.presentation.view.recycler.viewholder

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.koin.android.ext.android.get
import rs.raf.vezbe11.R
import rs.raf.vezbe11.data.models.Category
import rs.raf.vezbe11.databinding.LayoutItemCategoryBinding
import rs.raf.vezbe11.presentation.view.activities.MainActivity
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class CategoryViewHolder (
    private val itemBinding: LayoutItemCategoryBinding,
    onItemClicked: (Int) -> Unit,
    onButtonClicked: (Int) -> Unit

) : RecyclerView.ViewHolder(itemBinding.root){


    init {
        itemBinding.root.setOnClickListener {
            onItemClicked.invoke(adapterPosition)
        }
        itemBinding.buttonConfigure.setOnClickListener{
            onButtonClicked.invoke(adapterPosition)


        }

    }

    fun bind(category: Category, onButtonClicked: (Category) -> Unit){



        itemBinding.buttonConfigure.setOnClickListener{
            onButtonClicked.invoke(category)
        }

        itemBinding.categoryName.text = category.name
        Glide.with(itemBinding.root)
            .load(category.imagePath)
            .into(itemBinding.categoryPicture);
    }

}