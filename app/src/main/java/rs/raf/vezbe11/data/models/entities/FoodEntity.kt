package rs.raf.vezbe11.data.models.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


//JOS NE ZNAM KAKO IZGLEDA API sa kojeg getujemo inff,NAPRAVIO SAM VISE ENTITETA ZA SVAKI SLUCAJ,MOZDA CE NAM
//TREBATI SAMO FOOD i nista vise

@Entity(tableName = "foods")
class FoodEntity(

    @PrimaryKey(autoGenerate = true)
//    val id: Long,
//    val name: String,
//    val description: String,
//    val calories: Long,
//    val categoryId: Int,
//
//    val recipe: String,
//    val imagePath: String, //url iz apija
//    val recipeVideo:String,//iz apija dobijamo link ka videu,moramo da pogledamo sta sve api ima.
//    val dateToPrepare : Date,
//    val mealType: String, //Dorucak,rucak,vecera,to se isto trazi
//    val country: String, //Iz koje zemlje je jelo,to se isto trazi
//
//    val ingredientsMap: Map<String,Long> //mapa sastojaka i njihovih kolicina,ne treba nam IngredientEntity

    val id: Long,
    val name: String,
    val strCategory: String,
    val strArea: String,
    val strInstructions: String,
    val strMealThumb: String,
    val strTags: String,
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


)
