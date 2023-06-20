package rs.raf.vezbe11.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import rs.raf.vezbe11.data.models.CategoryEntity
import rs.raf.vezbe11.data.repositories.FoodRepository
import rs.raf.vezbe11.presentation.contract.FoodContract
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.vezbe11.data.models.Category
import rs.raf.vezbe11.data.models.Resource
import rs.raf.vezbe11.presentation.view.states.AddFoodState
import rs.raf.vezbe11.presentation.view.states.FoodState
import timber.log.Timber
import java.util.concurrent.TimeUnit


class FoodViewModel(
    private val foodRepository: FoodRepository
) : ViewModel(), FoodContract.ViewModel{

    private val subscriptions = CompositeDisposable() //ovo je mutable live data
    override val foodState: MutableLiveData<FoodState> = MutableLiveData()
    override val addDone: LiveData<AddFoodState> = MutableLiveData()   // Ognjen: Ovo ce biti za insert ako bude potrebno
    override var categories: MutableLiveData<List<CategoryEntity>> = MutableLiveData() //ovo je mutable live data
    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    //navesti metode u contractu,pa overajdovati

    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                foodRepository
                    .getCategoriesByName(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                        Timber.e(it)
                    }
            }
            .subscribe(
                {
                    foodState.value = FoodState.Success(it)
                },
                {
                    foodState.value = FoodState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }
    override fun fetchAllCategories() {

        //foodRepository.fetchAllCategories() //smestice u categories listu
        val subscription = foodRepository
            .fetchAllCategories()
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> foodState.value = FoodState.Loading
                        is Resource.Success -> foodState.value = FoodState.DataFetched
                        is Resource.Error -> foodState.value = FoodState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    foodState.value = FoodState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllCategories() { // iz lokalne baze vraca listu kategorija
        val subscription = foodRepository
            .getAllCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    foodState.value = FoodState.Success(it)
                },
                {
                    foodState.value = FoodState.Error("Error happened while fetching data from db")
                    //Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getCategoriesByName(name: String) {
        publishSubject.onNext(name)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}