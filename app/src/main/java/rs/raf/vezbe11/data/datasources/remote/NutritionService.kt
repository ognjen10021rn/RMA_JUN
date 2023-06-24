package rs.raf.vezbe11.data.datasources.remote

import androidx.annotation.AttrRes
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
import rs.raf.vezbe11.data.models.responses.CategoryResponse
import rs.raf.vezbe11.data.models.responses.MealResponse
import rs.raf.vezbe11.data.models.responses.NutritionResponse

interface NutritionService {
    @Headers("X-Api-Key: hl7rfNamX+1mmeoEqaYdMw==HkBR7bgJ22p7mVIP")
    @GET("nutrition")
    fun getNutritionByQuery(@Query("query") str: String): Observable<List<NutritionResponse>>
    //https://api-ninjas.com/api/nutrition?query=str
}