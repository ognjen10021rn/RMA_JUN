package rs.raf.vezbe11.data.models.responses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IngredientResponse(
    val idIngredient: Long,
    val strIngredient: String,
    val strDescription: String,
    val strType: String,
)
