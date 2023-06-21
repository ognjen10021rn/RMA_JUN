package rs.raf.vezbe11.presentation.view.states

import rs.raf.vezbe11.data.models.Area
import rs.raf.vezbe11.data.models.Category
import rs.raf.vezbe11.data.models.FoodByParameter
import rs.raf.vezbe11.data.models.FoodByParameterEntity

sealed class AreasState {
    object Loading: AreasState()
    object DataFetched: AreasState()
    data class Success(val areas: List<Area>): AreasState()
    data class Error(val message: String): AreasState()
}