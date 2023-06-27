package rs.raf.vezbe11.presentation.view.states

import rs.raf.vezbe11.data.models.Food

sealed class FoodByIdState {

     object Loading: FoodByIdState()
     object DataFetched: FoodByIdState()
     data class Success(val selectedMeal: Food): FoodByIdState()
     data class Success2(val foods: List<Food>): FoodByIdState()
     data class Error(val message: String): FoodByIdState()



}