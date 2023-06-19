package rs.raf.vezbe11.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.vezbe11.data.models.Food
import rs.raf.vezbe11.data.models.FoodEntity
import rs.raf.vezbe11.data.models.Resource

interface NutritionRepository {
    fun fetchAllNutrition(): Observable<Resource<Unit>>
    fun getAllNutrition(): Observable<List<Food>>
}