package rs.raf.vezbe11.data.models

class SavedFood(
    val id: Long,
    var name: String,
    var strInstructions: String,
    var strCategory: String,
    var date:Long,
    var strMealType: String,
    var strMealThumb: String,
    val strYoutube: String,
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strMeasure1: String?,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?,
) {
}