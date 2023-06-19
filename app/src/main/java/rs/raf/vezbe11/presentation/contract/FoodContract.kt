package rs.raf.vezbe11.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.vezbe11.data.models.CategoryEntity
import rs.raf.vezbe11.presentation.view.states.AddFoodState
import rs.raf.vezbe11.presentation.view.states.FoodState


interface FoodContract {
    interface ViewModel {
        //atributi
        val foodState: LiveData<FoodState>
        val addDone: LiveData<AddFoodState>
        val categories: LiveData<List<CategoryEntity>>

        //metode
        fun fetchAllCategories();
        fun getAllCategories();
        fun getCategoriesByName(name: String);



    }
}