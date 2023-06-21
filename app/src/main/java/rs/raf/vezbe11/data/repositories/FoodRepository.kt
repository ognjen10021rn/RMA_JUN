package rs.raf.vezbe11.data.repositories

import io.reactivex.Observable
import rs.raf.vezbe11.data.models.*

interface FoodRepository {

    fun fetchAllCategories(): Observable<Resource<Unit>>
    fun getAllCategories(): Observable<List<Category>>
    fun getCategoriesByName(name: String): Observable<List<Category>>
    fun fetchFoodByCategory(name:String): Observable<Resource<Unit>>
    fun fetchFoodByArea(area: String): Observable<Resource<Unit>>
    fun getFoodsByParameter(limit: Int, offset: Int): Observable<List<FoodByParameter>>

    fun fetchAllAreas(): Observable<Resource<Unit>>
    fun getAllAreas(): Observable<List<Area>>

    fun fetchAllIngredients(): Observable<Resource<Unit>>
    fun getAllIngredients(): Observable<List<Ingredient>>


}