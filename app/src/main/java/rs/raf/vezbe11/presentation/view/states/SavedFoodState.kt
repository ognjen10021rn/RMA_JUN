package rs.raf.vezbe11.presentation.view.states

import rs.raf.vezbe11.data.models.Area
import rs.raf.vezbe11.data.models.SavedFood

sealed class SavedFoodState {
    object Loading: SavedFoodState()
    object DataFetched: SavedFoodState()
    data class Success(val savedFoods: List<SavedFood>): SavedFoodState()
    data class Success2(val savedFood: SavedFood): SavedFoodState()
    data class Error(val message: String): SavedFoodState()
}