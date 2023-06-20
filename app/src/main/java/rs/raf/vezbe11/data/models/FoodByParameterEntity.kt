package rs.raf.vezbe11.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "foodsByParameter")
class FoodByParameterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val strMeal:String,
    val strThumb:String,


) {
}