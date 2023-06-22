package rs.raf.vezbe11.data.repositories

import androidx.core.graphics.createBitmap
import rs.raf.vezbe11.data.datasources.local.FoodDao
import rs.raf.vezbe11.data.datasources.remote.FoodService
import io.reactivex.Observable
import rs.raf.vezbe11.data.models.*
import rs.raf.vezbe11.data.models.entities.AreaEntity
import rs.raf.vezbe11.data.models.entities.CategoryEntity
import rs.raf.vezbe11.data.models.entities.FoodByParameterEntity
import rs.raf.vezbe11.data.models.entities.IngredientEntity
import timber.log.Timber


//val idCategory:String,
//val strCategory:String,
//val strCategoryThumb:String,
//val strCategoryDescription:String,

class FoodRepositoryImplementation(
    private val localDataSource: FoodDao,
    private val remoteDataSource: FoodService,
    //val jsonArray: List<CategoriesResponse>

    ) : FoodRepository {
    override fun fetchAllCategories(): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAll()
            .doOnNext {
                Timber.e("\n\n\nUpis u bazu!!!\n\n\n")
                val test = it.categories
                val entities = test.map {
                    CategoryEntity(it.idCategory, it.strCategory,it.strCategoryThumb, it.strCategoryDescription)
                }

                localDataSource.deleteAndInsertAllCategories(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }
    override fun fetchFoodByCategory(name: String): Observable<Resource<Unit>> {
        return remoteDataSource
            .getFoodsByCategory(name)
            .doOnNext {

                val mealList = it.meals
                val entities = mealList.map {
                    FoodByParameterEntity(it.idMeal,it.strMeal,it.strMealThumb)
                }

                localDataSource.deleteAndInsertFoodsByParameter(entities)
            }
            .map {
                Resource.Success(Unit)
            }

    }
    override fun fetchFoodById(id: String): Observable<Resource<Unit>> {
        return remoteDataSource
            .getFoodById(id)
            .doOnNext{
                val meal = it.meals.get(0)
                Food(meal.idMeal,meal.strMeal,meal.strCategory,meal.strArea,
                    meal.strInstructions,meal.strMealThumb,meal.strTags,meal.strYoutube,
                    meal.strIngredient1,meal.strIngredient2,meal.strIngredient3,meal.strIngredient4,meal.strIngredient5,
                    meal.strMeasure1,meal.strMeasure2,meal.strMeasure3,meal.strMeasure4,meal.strMeasure5)

            }
            .map {
                Resource.Success(Unit)

            }
    }
    override fun fetchAllIngredients(): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAllIngredients("list")
            .doOnNext {

                val mealList = it.meals
                val entities = mealList.map {
                    IngredientEntity(it.idIngredient, it.strDescription, it.strIngredient, it.strType)
                }
                localDataSource.deleteAndInsertIngredients(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }
    override fun fetchFoodByArea(area: String): Observable<Resource<Unit>> {
        return remoteDataSource
            .getFoodsByArea(area)
            .doOnNext {

                val mealList = it.meals
                val entities = mealList.map {
                    FoodByParameterEntity(it.idMeal,it.strMeal,it.strMealThumb)
                }

                localDataSource.deleteAndInsertFoodsByParameter(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }
    override fun fetchAllAreas(): Observable<Resource<Unit>> {
        var i: Long = 0
        return remoteDataSource
            .getAllAreas("list")
            .doOnNext {
                val mealList = it.meals
                val entities = mealList.map {
                    AreaEntity(i, it.strArea)
                }
                i++
                println(entities)
                localDataSource.deleteAndInsertAreas(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun getAllCategories(): Observable<List<Category>> {
        return localDataSource
            .getAll()
            .map {
                it.map {
                    Category(it.id, it.name, it.imagePath, it.description)
                }
            }
    }

    override fun getCategoriesByName(name: String): Observable<List<Category>> {
        TODO("Not yet implemented")
    }


    override fun getFoodsByParameter(limit: Int, offset: Int): Observable<List<FoodByParameter>> {
        return localDataSource
            .getFoodsByParameter(limit, offset)
            .map {
                it.map {
                    FoodByParameter(it.id, it.strMeal, it.strThumb)
                }
            }
    }





    override fun getAllAreas(): Observable<List<Area>> {
        return localDataSource
            .getAllAreas()
            .map {
                it.map {
                    Area(it.strArea)
                }
            }
    }





    override fun getAllIngredients(): Observable<List<Ingredient>> {
        return localDataSource
            .getAllIngredients()
            .map {
                it.map {
                    Ingredient(it.idIngredient, it.strDescription, it.strIngredient, it.strType)
                }
            }
    }


}
