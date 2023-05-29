package rs.raf.vezbe11.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rs.raf.vezbe11.data.models.CategoryEntity
import rs.raf.vezbe11.data.repositories.FoodRepository
import rs.raf.vezbe11.presentation.contract.FoodContract
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber


class FoodViewModel(
    private val repository: FoodRepository
) : ViewModel(), FoodContract.ViewModel{

    private val subscriptions=CompositeDisposable() //ovo je mutable live data
    override var categories: MutableLiveData<List<CategoryEntity>> = MutableLiveData() //ovo je mutable live data
    //navesti metode u contractu,pa overajdovati

    init {
        fetchAllCategories()
        //fetchom smo upisali u bazu,sad treba da getujem
        categories=repository.getAllCategories() //ovo je observable,ne moze ovako
    }
    override fun fetchAllCategories() {
        repository.fetchAllCategories() //smestice u categories listu

    }

    override fun getAllCategories(): LiveData<List<CategoryEntity>> {
        return categories
    }

}