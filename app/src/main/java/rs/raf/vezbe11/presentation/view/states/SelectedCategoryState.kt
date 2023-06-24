package rs.raf.vezbe11.presentation.view.states

import rs.raf.vezbe11.data.models.Area
import rs.raf.vezbe11.data.models.Category

sealed class SelectedCategoryState{
    data class Success(val category: Category): SelectedCategoryState()
    data class Error(val message: String): SelectedCategoryState()
}
