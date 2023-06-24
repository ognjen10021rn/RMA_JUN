package rs.raf.vezbe11.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rs.raf.vezbe11.data.models.Resource
import rs.raf.vezbe11.data.repositories.NutritionRepository
import rs.raf.vezbe11.presentation.contract.NutritionContract
import rs.raf.vezbe11.presentation.view.states.FoodByIdState
import rs.raf.vezbe11.presentation.view.states.FoodState
import rs.raf.vezbe11.presentation.view.states.NutritionState

class NutritionViewModel(
    private val repository: NutritionRepository,
): ViewModel(), NutritionContract.ViewModel{
    private val subscriptions = CompositeDisposable()

    override val nutritionState: MutableLiveData<NutritionState> = MutableLiveData()

    override fun fetchAllNutritionByQuery(query: String) {
        val subscription = repository
            .fetchAllNutrition(query)
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> nutritionState.value = NutritionState.Loading
                        is Resource.Success -> nutritionState.value = NutritionState.DataFetched
                        is Resource.Error -> nutritionState.value = NutritionState.Error("Error happened while fetching data from Nutrition: $query")
                    }
                },
                {
                    nutritionState.value = NutritionState.Error("Error happened while fetching data from Nutrition: $query")

                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllNutrition() {
        val subscription = repository
            .getAllNutrition()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    nutritionState.value = NutritionState.Success(it)
                },
                {
                    nutritionState.value = NutritionState.Error("Error happened while getting data from Nutrition!")

                }
            )
        subscriptions.add(subscription)
    }


}