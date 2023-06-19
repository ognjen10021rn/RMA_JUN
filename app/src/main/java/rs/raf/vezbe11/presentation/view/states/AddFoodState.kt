package rs.raf.vezbe11.presentation.view.states

sealed class AddFoodState{
    object Success: AddFoodState()
    data class Error(val message: String): AddFoodState()
}
