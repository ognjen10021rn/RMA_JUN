package rs.raf.vezbe11.data.models.responses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class FoodByParameterResponse(
    val idMeal:Long,
    val strMeal:String,
    val strMealThumb:String, //thumbnail

)

