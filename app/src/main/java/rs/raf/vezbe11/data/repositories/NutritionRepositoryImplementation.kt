package rs.raf.vezbe11.data.repositories

import rs.raf.vezbe11.data.datasources.local.NutritionDao
import rs.raf.vezbe11.data.datasources.remote.NutritionService

class NutritionRepositoryImplementation(
    private val localDataSource: NutritionDao,
    private val remoteDataSource: NutritionService
): NutritionRepository, FoodRepository {
    //overajdovane metode

}