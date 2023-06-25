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
import rs.raf.vezbe11.data.models.entities.AreaEntity
import rs.raf.vezbe11.data.models.entities.CategoryEntity
import rs.raf.vezbe11.data.models.entities.FoodByParameterEntity
import rs.raf.vezbe11.data.models.entities.FoodEntity
import rs.raf.vezbe11.presentation.view.states.*
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
    override val foodByIdState: MutableLiveData<FoodByIdState> = MutableLiveData()
    override val selectedCategoryState: MutableLiveData<SelectedCategoryState> = MutableLiveData()
    override val savedFoodState: MutableLiveData<SavedFoodState> = MutableLiveData()

    private var currentFood: Food? = null
    private var savedFood: SavedFood? = null
    //Liste
    override var categories: MutableLiveData<List<CategoryEntity>> = MutableLiveData()
    override val meals: MutableLiveData<List<FoodByParameterEntity>> = MutableLiveData()
    override var areas: MutableLiveData<List<AreaEntity>> = MutableLiveData()
    override var selectedFood: MutableLiveData<FoodEntity> = MutableLiveData()
    override val selectedCategory: LiveData<Category> = MutableLiveData()


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

                    }
            }
            .subscribe(
                {
                    foodState.value = FoodState.Success(it)
                },
                {
                    foodState.value = FoodState.Error("Error happened while fetching data from db")

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

                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchFoodWithId(id: String) {
        val subscription = foodRepository
            .fetchFoodById(id)
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> foodByIdState.value = FoodByIdState.Loading
                        is Resource.Success -> foodByIdState.value = FoodByIdState.DataFetched
                        is Resource.Error -> foodByIdState.value = FoodByIdState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    foodByIdState.value = FoodByIdState.Error("Error happened while fetching data from the server")

                }
            )
        subscriptions.add(subscription)
    }

    override fun getFoodWithId(id: String) {
        val subscription = foodRepository
            .getFoodById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    foodByIdState.value = FoodByIdState.Success(it)
                    currentFood = it

                },
                {
                    foodByIdState.value = FoodByIdState.Error("Error happened while getting ${id}")

                }
            )
        subscriptions.add(subscription)
    }
    override fun getCurrentFood(): Food{
        return currentFood!!
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

                }
            )
        subscriptions.add(subscription)
    }

    override fun getCategoriesByName(name: String) {
        publishSubject.onNext(name)
    }

    override fun getAllSavedFood() {
        val subscription = foodRepository
            .getAllSavedFood()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    savedFoodState.value = SavedFoodState.Success(it)
                },
                {
                    savedFoodState.value = SavedFoodState.Error("Error happened while getting saved Food")

                }
            )
        subscriptions.add(subscription)
    }

    override fun insertSavedFood(food: SavedFood) {
        val subscription = foodRepository
            .insertSavedFood(food)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        subscriptions.add(subscription)
    }

    override fun deleteSavedFoodById(id: String) {
        val subscription = foodRepository
            .deleteSavedFoodById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        subscriptions.add(subscription)
    }

    override fun getSavedFoodById(id: String) {
        val subscription = foodRepository
            .getSavedFoodById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    savedFoodState.value = SavedFoodState.Success2(it)
                    savedFood = it
                },
                {
                    savedFoodState.value = SavedFoodState.Error("Error happened while gettingFood")
                }
            )
        subscriptions.add(subscription)
    }

    override fun getCurrentSavedFood(): SavedFood{
        return savedFood!!
    }

    override fun updateSavedFood(food: SavedFood) {
        val subscription = foodRepository
            .updateSavedFood(food)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
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

    override fun getSelectedCategory(category: String) {
        val subscription = foodRepository
            .getCategoryByName(category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    selectedCategoryState.value = SelectedCategoryState.Success(it)
                },
                {
                    selectedCategoryState.value = SelectedCategoryState.Error("Error while fetching category")
                }
            )
        subscriptions.add(subscription)
    }


    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}