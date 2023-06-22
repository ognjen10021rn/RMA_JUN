package rs.raf.vezbe11.data.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
class IngredientEntity(
    @PrimaryKey(autoGenerate = true)
    val idIngredient: Long,
    val strDescription: String,
    val strIngredient: String, //Stavio sam string da bude,ako neko napise 1/2 solje tipa
    val strType: String, //treba da na osnovu kalorije sastojka,sracunamo kalorije hrane.

)

