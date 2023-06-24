package rs.raf.vezbe11.data.repositories

import io.reactivex.Observable
import rs.raf.vezbe11.data.datasources.local.NutritionDao
import rs.raf.vezbe11.data.datasources.remote.NutritionService
import rs.raf.vezbe11.data.models.Food
import rs.raf.vezbe11.data.models.Nutrition
import rs.raf.vezbe11.data.models.Resource
import rs.raf.vezbe11.data.models.entities.FoodByParameterEntity
import rs.raf.vezbe11.data.models.entities.NutritionEntity

class NutritionRepositoryImplementation(
    private val localDataSource: NutritionDao,
    private val remoteDataSource: NutritionService
): NutritionRepository{

    override fun fetchAllNutrition(query: String): Observable<Resource<Unit>> {
        var i: Long = 0
        return remoteDataSource
            .getNutritionByQuery(query)
            .doOnNext {

                val entities = it.map {
                    NutritionEntity(i, it.name, it.calories, it.serving_size_g, it.fat_total_g,
                        it.fat_saturated_g, it.protein_g, it.sodium_mg, it.potassium_mg, it.cholesterol_mg,
                        it.carbohydrates_total_g, it.fiber_g, it.sugar_g)
                }
                i++
                localDataSource.deleteAndInsertAllNutrition(entities)
            }
            .map {
                Resource.Success(Unit)
            }

    }

    override fun getAllNutrition(): Observable<List<Nutrition>> {
        return localDataSource
            .getAllNutrition()
            .map {
                it.map {
                    Nutrition(it.id, it.name, it.calories, it.serving_size_g, it.fat_total_g,
                        it.fat_saturated_g, it.protein_g, it.sodium_mg, it.potassium_mg, it.cholesterol_mg,
                        it.carbohydrates_total_g, it.fiber_g, it.sugar_g)
                }
            }
    }


}