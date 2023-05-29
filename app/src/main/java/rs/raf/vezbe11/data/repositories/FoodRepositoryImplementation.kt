package rs.raf.vezbe11.data.repositories

import rs.raf.vezbe11.data.datasources.local.FoodDao
import rs.raf.vezbe11.data.datasources.remote.FoodService

class FoodRepositoryImplementation(
    private val localDataSource: FoodDao,
    private val remoteDataSource: FoodService,

    ) : FoodRepository {
}