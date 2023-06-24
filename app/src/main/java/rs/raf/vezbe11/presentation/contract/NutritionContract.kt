package rs.raf.vezbe11.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.vezbe11.presentation.view.states.FoodState
import rs.raf.vezbe11.presentation.view.states.NutritionState

interface NutritionContract {
    interface ViewModel {
        val nutritionState: LiveData<NutritionState>

        fun fetchAllNutritionByQuery(query: String)
        fun getAllNutrition()

    }
}