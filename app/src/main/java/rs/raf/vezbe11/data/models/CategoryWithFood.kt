package rs.raf.vezbe11.data.models

import androidx.room.Embedded
import androidx.room.Relation

//One-To-Many, Category prema svim njegovim foodovima
//ovako je relacija izdvojena na vezbama(vezbe 10)
class CategoryWithFood(
    @Embedded
    val category: CategoryEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val food: List<FoodEntity>?


)

