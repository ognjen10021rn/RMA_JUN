package rs.raf.vezbe11.data.datasources.remote

import retrofit2.http.GET
import io.reactivex.Observable
import retrofit2.http.Query
import rs.raf.vezbe11.data.models.Ingredient
import rs.raf.vezbe11.data.models.responses.*

interface FoodService {

    @GET("categories.php")
    fun getAll(): Observable<CategoryResponse>

    @GET("filter.php")
    fun getFoodsByCategory(@Query("c") category: String): Observable<MealResponse>
    //www.themealdb.com/api/json/v1/1/filter.php?c=Seafood

    @GET("filter.php")
    fun getFoodsByArea(@Query("a") area: String): Observable<MealResponse>
    //www.themealdb.com/api/json/v1/1/filter.php?a=Canadian

    @GET("filter.php")
    fun getFoodsByIngredient(@Query("i") ingredient: String): Observable<MealResponse>
    //www.themealdb.com/api/json/v1/1/filter.php?i=chicken_breast


    @GET("lookup.php")
    fun getFoodById(@Query("i") id: String): Observable<List<FoodResponse>>
    //API VRACA LISTU,SA JEDNIM OBROKOM
    //www.themealdb.com/api/json/v1/1/lookup.php?i=52772

    @GET("search.php")
    fun getFoodByName(@Query("s") name: String): Observable<List<FoodResponse>>
    //www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata
    @GET("list.php")
    fun getAllAreas(@Query("a") area: String): Observable<AreasResponse>
    @GET("list.php")
    fun getAllIngredients(@Query("i") ingredient: String): Observable<IngredientsResponse>
    //https://www.themealdb.com/api/json/v1/1/list.php?a=list



}