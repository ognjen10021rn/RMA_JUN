package rs.raf.vezbe11.data.models.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "nutrition")
class NutritionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val calories: Double,
    val serving_size_g: Double,
    val fat_total_g: Double,
    val fat_saturated_g: Double,
    val protein_g: Double,
    val sodium_mg: Double,
    val potassium_mg: Double,
    val cholesterol_mg: Double,
    val carbohydrates_total_g: Double,
    val fiber_g: Double,
    val sugar_g: Double


)
