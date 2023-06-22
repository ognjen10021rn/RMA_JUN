package rs.raf.vezbe11.data.models.responses

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
class FoodListResponse(
    val meals: List<FoodResponse>
) {
}