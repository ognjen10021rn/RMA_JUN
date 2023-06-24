package rs.raf.vezbe11.data.repositories

import io.reactivex.Observable
import rs.raf.vezbe11.data.models.Food
import rs.raf.vezbe11.data.models.Nutrition
import rs.raf.vezbe11.data.models.Resource

interface NutritionRepository {
    fun fetchAllNutrition(query: String): Observable<Resource<Unit>>
    fun getAllNutrition(): Observable<List<Nutrition>>
}