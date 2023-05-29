package rs.raf.vezbe11.data.repositories

import rs.raf.vezbe11.data.datasources.local.FoodDao
import rs.raf.vezbe11.data.datasources.remote.FoodService
import rs.raf.vezbe11.data.models.Resource
import io.reactivex.Observable
import rs.raf.vezbe11.data.models.CategoryEntity
import timber.log.Timber

class FoodRepositoryImplementation(
    private val localDataSource: FoodDao,
    private val remoteDataSource: FoodService,

    ) : FoodRepository {
    override fun fetchAllCategories(): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAll()
            .doOnNext {
                val entities = it.map {
                    CategoryEntity(it.idCategory, it.strCategory, it.strCategoryThumb, it.strCategoryDescription)
                }

                localDataSource.deleteAndInsertAllCategories(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun getAllCategories(): Observable<List<CategoryEntity>> {
        TODO("Not yet implemented")
    }


}
