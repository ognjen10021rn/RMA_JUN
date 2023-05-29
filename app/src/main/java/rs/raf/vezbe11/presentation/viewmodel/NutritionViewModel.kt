package rs.raf.vezbe11.presentation.viewmodel

import androidx.lifecycle.ViewModel
import rs.raf.vezbe11.data.repositories.NutritionRepository
import rs.raf.vezbe11.presentation.contract.NutritionContract

class NutritionViewModel(
    private val repository: NutritionRepository
): ViewModel(), NutritionContract.ViewModel{

    //navesti metode u contractu,pa overajdovati


}