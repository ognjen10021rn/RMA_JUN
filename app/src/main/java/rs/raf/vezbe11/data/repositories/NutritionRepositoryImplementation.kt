package rs.raf.vezbe11.data.repositories

import io.reactivex.Observable
import rs.raf.vezbe11.data.datasources.local.NutritionDao
import rs.raf.vezbe11.data.datasources.remote.NutritionService
import rs.raf.vezbe11.data.models.CategoryEntity
import rs.raf.vezbe11.data.models.Resource

class NutritionRepositoryImplementation(
    private val localDataSource: NutritionDao,
    private val remoteDataSource: NutritionService
): NutritionRepository, FoodRepository { //TODO ispraviti
    //overajdovane metode
    override fun fetchAllCategories(): Observable<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override fun getAllCategories(): Observable<List<CategoryEntity>> {
        TODO("Not yet implemented")
    }

}