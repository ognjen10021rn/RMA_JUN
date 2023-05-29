package rs.raf.vezbe11.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


//Dorucak,rucak,vecera.To pise u projektu
@Entity(tableName = "categories")
class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val imagePath:String, //imamo sliku za svaku kategoriju,pretpostavljam da cuvamo path u bazu ne znam
    val description: String,
)




