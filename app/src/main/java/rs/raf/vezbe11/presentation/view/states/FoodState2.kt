package rs.raf.vezbe11.presentation.view.states

import rs.raf.vezbe11.data.models.Area
import rs.raf.vezbe11.data.models.Category
import rs.raf.vezbe11.data.models.Food
import rs.raf.vezbe11.data.models.Ingredient

sealed class FoodState2{
    object Loading: FoodState2()
    object DataFetched: FoodState2()
    data class Success(val foods: List<Food>): FoodState2()
    data class Error(val message: String): FoodState2()
}