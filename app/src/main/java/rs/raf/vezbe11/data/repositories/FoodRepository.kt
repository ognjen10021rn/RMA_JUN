package rs.raf.vezbe11.data.repositories

import io.reactivex.Observable
import rs.raf.vezbe11.data.models.*

interface FoodRepository {

    //FETCHES
    fun fetchAllCategories(): Observable<Resource<Unit>>
    fun fetchFoodByCategory(name:String): Observable<Resource<Unit>>
    fun fetchFoodByArea(area: String): Observable<Resource<Unit>>
    fun fetchAllAreas(): Observable<Resource<Unit>>
    fun fetchFoodById(id: String): Observable<Resource<Unit>>

    //GETS
    fun fetchAllIngredients(): Observable<Resource<Unit>>
    fun getAllCategories(): Observable<List<Category>>
    fun getCategoriesByName(name: String): Observable<List<Category>>
    fun getFoodsByParameter(limit: Int, offset: Int): Observable<List<FoodByParameter>>
    fun getAllAreas(): Observable<List<Area>>
    fun getAllIngredients(): Observable<List<Ingredient>>


}