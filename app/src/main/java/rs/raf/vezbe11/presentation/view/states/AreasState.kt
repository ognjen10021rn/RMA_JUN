package rs.raf.vezbe11.presentation.view.states

import rs.raf.vezbe11.data.models.Area

sealed class AreasState {
    object Loading: AreasState()
    object DataFetched: AreasState()
    data class Success(val areas: List<Area>): AreasState()
    data class Error(val message: String): AreasState()
}