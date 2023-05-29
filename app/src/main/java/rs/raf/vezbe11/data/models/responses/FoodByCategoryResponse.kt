package rs.raf.vezbe11.data.models.responses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class FoodByCategoryResponse(
    val strMeal:String,
    val strMealThumb:String, //thumbnail
    val idMeal:String

)

