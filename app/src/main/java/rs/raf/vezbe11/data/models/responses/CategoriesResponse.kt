package rs.raf.vezbe11.data.models.responses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CategoriesResponse (
    //imena su bas kao i u apiju
    val idCategory:Long,
    val strCategory:String,
    val strCategoryThumb:String,
    val strCategoryDescription:String,


)