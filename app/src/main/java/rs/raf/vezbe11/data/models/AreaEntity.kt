package rs.raf.vezbe11.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "areas")
class AreaEntity(
    @PrimaryKey(autoGenerate = true)
    val idArea: Long,
    val strArea: String
)
