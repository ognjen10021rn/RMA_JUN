package rs.raf.vezbe11.data.repositories

import rs.raf.vezbe11.data.datasources.local.FoodDao
import rs.raf.vezbe11.data.datasources.remote.FoodService
import rs.raf.vezbe11.data.models.Resource
import io.reactivex.Observable
import rs.raf.vezbe11.data.models.Category
import rs.raf.vezbe11.data.models.CategoryEntity
import rs.raf.vezbe11.data.models.FoodByParameterEntity
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
    override fun getFoodByCategory(name: String): Observable<Resource<Unit>> {
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



}
