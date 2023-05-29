package rs.raf.vezbe11.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import rs.raf.vezbe11.data.models.FoodEntity

@Dao
abstract class FoodDao {

    //U bazi se cuva hrana samo onu koju korisnik izabere u svoj meni

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(entity: FoodEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(entities: List<FoodEntity>): Completable

    @Query("SELECT * FROM foods")
    abstract fun getAll(): List<FoodEntity>

    @Query ("DELETE FROM foods")
    abstract fun deleteAll()

    @Query("SELECT * FROM foods WHERE name LIKE :name || '%'")
    abstract fun getByName(name: String): List<FoodEntity>



}