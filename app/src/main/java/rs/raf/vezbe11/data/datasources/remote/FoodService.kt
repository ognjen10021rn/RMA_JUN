package rs.raf.vezbe11.data.datasources.remote

import retrofit2.http.GET
import io.reactivex.Observable
import retrofit2.http.Query
import rs.raf.vezbe11.data.models.responses.CategoriesResponse
import rs.raf.vezbe11.data.models.responses.FoodByCategoryResponse
import rs.raf.vezbe11.data.models.responses.FoodResponse

interface FoodService {

    @GET("categories.php")
    fun getAll(): Observable<List<CategoriesResponse>>

    @GET("filter.php")
    fun getFoodsByCategory(@Query("c") category: String): Observable<List<FoodByCategoryResponse>>
    //www.themealdb.com/api/json/v1/1/filter.php?c=Seafood

    @GET("lookup.php")
    fun getFoodById(@Query("i") id: String): Observable<List<FoodResponse>>
    //API VRACA LISTU,SA JEDNIM OBROKOM
    //www.themealdb.com/api/json/v1/1/lookup.php?i=52772

    @GET("search.php")
    fun getFoodByName(@Query("s") name: String): Observable<List<FoodResponse>>
    //www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata


}