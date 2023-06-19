package rs.raf.vezbe11.data.repositories

import io.reactivex.Observable
import rs.raf.vezbe11.data.datasources.local.NutritionDao
import rs.raf.vezbe11.data.datasources.remote.NutritionService
import rs.raf.vezbe11.data.models.Category
import rs.raf.vezbe11.data.models.CategoryEntity
import rs.raf.vezbe11.data.models.Food
import rs.raf.vezbe11.data.models.Resource

class NutritionRepositoryImplementation(
    private val localDataSource: NutritionDao,
    private val remoteDataSource: NutritionService
): NutritionRepository{ //TODO ispraviti

    override fun fetchAllNutrition(): Observable<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override fun getAllNutrition(): Observable<List<Food>> {
        TODO("Not yet implemented")
    }


}