package rs.raf.vezbe11.presentation.view.states

import rs.raf.vezbe11.data.models.Area
import rs.raf.vezbe11.data.models.Nutrition

sealed class NutritionState {
    object Loading: NutritionState()
    object DataFetched: NutritionState()
    data class Success(val nutrition: List<Nutrition>): NutritionState()
    data class Error(val message: String): NutritionState()
}