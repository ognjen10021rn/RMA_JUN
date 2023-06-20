package rs.raf.vezbe11.presentation.view.states

import rs.raf.vezbe11.data.models.Area
import rs.raf.vezbe11.data.models.Category
import rs.raf.vezbe11.data.models.Food
import rs.raf.vezbe11.data.models.Ingredient

sealed class FoodState{
    object Loading: FoodState()
    object DataFetched: FoodState()
    data class Success(val categories: List<Category>): FoodState()
    data class Error(val message: String): FoodState()
}