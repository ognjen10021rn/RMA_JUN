package rs.raf.vezbe11.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
class IngredientEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val quantity: String, //Stavio sam string da bude,ako neko napise 1/2 solje tipa
    val calories: Long, //treba da na osnovu kalorije sastojka,sracunamo kalorije hrane.

)

