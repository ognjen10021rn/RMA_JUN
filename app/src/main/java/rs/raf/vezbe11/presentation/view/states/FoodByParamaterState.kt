package rs.raf.vezbe11.presentation.view.states

import rs.raf.vezbe11.data.models.Category
import rs.raf.vezbe11.data.models.FoodByParameter
import rs.raf.vezbe11.data.models.FoodByParameterEntity

sealed class FoodByParamaterState {
    object Loading: FoodByParamaterState()
    object DataFetched: FoodByParamaterState()
    data class Success(val meals: List<FoodByParameter>): FoodByParamaterState()
    data class Error(val message: String): FoodByParamaterState()
}