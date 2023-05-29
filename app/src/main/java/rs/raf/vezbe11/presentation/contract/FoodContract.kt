package rs.raf.vezbe11.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.vezbe11.data.models.CategoryEntity


interface FoodContract {
    interface ViewModel {
        //atributi
        val categories: LiveData<List<CategoryEntity>>

        //metode
        fun fetchAllCategories();
        fun getAllCategories(): LiveData<List<CategoryEntity>>;



    }
}