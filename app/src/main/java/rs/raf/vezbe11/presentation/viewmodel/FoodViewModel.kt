package rs.raf.vezbe11.presentation.viewmodel

import androidx.lifecycle.ViewModel
import rs.raf.vezbe11.data.repositories.FoodRepository
import rs.raf.vezbe11.presentation.contract.FoodContract


class FoodViewModel(
    private val repository: FoodRepository
) : ViewModel(), FoodContract.ViewModel{

    //navesti metode u contractu,pa overajdovati


}