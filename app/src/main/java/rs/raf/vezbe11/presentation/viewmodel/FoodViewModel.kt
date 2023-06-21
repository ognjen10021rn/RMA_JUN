package rs.raf.vezbe11.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import rs.raf.vezbe11.data.repositories.FoodRepository
import rs.raf.vezbe11.presentation.contract.FoodContract
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.vezbe11.data.models.*
import rs.raf.vezbe11.presentation.view.states.AddFoodState
import rs.raf.vezbe11.presentation.view.states.AreasState
import rs.raf.vezbe11.presentation.view.states.FoodByParamaterState
import rs.raf.vezbe11.presentation.view.states.FoodState
import timber.log.Timber
import java.util.concurrent.TimeUnit


class FoodViewModel(
    private val foodRepository: FoodRepository,
) : ViewModel(), FoodContract.ViewModel{
    //Subscriptions
    private val subscriptions = CompositeDisposable() //ovo je mutable live data
    //States
    override val foodState: MutableLiveData<FoodState> = MutableLiveData()
    override val foodByParamaterState: MutableLiveData<FoodByParamaterState> = MutableLiveData()
    override val addDone: MutableLiveData<AddFoodState> = MutableLiveData()   // Ognjen: Ovo ce biti za insert ako bude potrebno
    override val areaState: MutableLiveData<AreasState> = MutableLiveData()

    //Liste
    override var categories: MutableLiveData<List<CategoryEntity>> = MutableLiveData()
    override val meals: MutableLiveData<List<FoodByParameterEntity>> = MutableLiveData()
    override var areas: MutableLiveData<List<AreaEntity>> = MutableLiveData()

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

    override fun fetchMealsByArea(area: String) {
        val subscription = foodRepository
            .fetchFoodByArea(area)
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> foodByParamaterState.value = FoodByParamaterState.Loading
                        is Resource.Success -> foodByParamaterState.value = FoodByParamaterState.DataFetched
                        is Resource.Error -> foodState.value = FoodState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    foodByParamaterState.value = FoodByParamaterState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchMealsByCategory(category: String) {
        val subscription = foodRepository
            .fetchFoodByCategory(category)
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> foodByParamaterState.value = FoodByParamaterState.Loading
                        is Resource.Success -> foodByParamaterState.value = FoodByParamaterState.DataFetched
                        is Resource.Error -> foodState.value = FoodState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    foodByParamaterState.value = FoodByParamaterState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllMealsByParamater(limit: Int, offset: Int) {
        val subscription = foodRepository
            .getFoodsByParameter(limit, offset)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    foodByParamaterState.value = FoodByParamaterState.Success(it)
                },
                {
                    foodByParamaterState.value = FoodByParamaterState.Error("Error while fetching meals with paramater: Limit $limit and Offset $offset")
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllAreas() {
        val subscription = foodRepository
            .getAllAreas()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    areaState.value = AreasState.Success(it)
                },
                {
                    areaState.value = AreasState.Error("Error while fetching areas")
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllAreas() {
        val subscription = foodRepository
            .fetchAllAreas()
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> areaState.value = AreasState.Loading
                        is Resource.Success -> areaState.value = AreasState.DataFetched
                        is Resource.Error -> areaState.value = AreasState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    areaState.value = AreasState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}