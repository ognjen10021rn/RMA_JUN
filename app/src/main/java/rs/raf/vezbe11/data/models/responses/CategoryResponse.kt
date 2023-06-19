package rs.raf.vezbe11.data.models.responses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CategoryResponse (
        val categories: List<CategoriesResponse>
    )
