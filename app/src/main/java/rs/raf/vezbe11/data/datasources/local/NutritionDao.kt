package rs.raf.vezbe11.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.vezbe11.data.models.entities.CategoryEntity
import rs.raf.vezbe11.data.models.entities.FoodEntity

@Dao
abstract class NutritionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(entity: FoodEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(entities: List<FoodEntity>): Completable

    @Query("SELECT * FROM foods")
    abstract fun getAll(): Observable<List<FoodEntity>>

    @Query("DELETE FROM foods")
    abstract fun deleteAll()

    @Query("SELECT * FROM foods WHERE name LIKE :name || '%'")
    abstract fun getByName(name: String): List<FoodEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllCategories(entities: List<CategoryEntity>): Completable


    @Transaction
    open fun deleteAndInsertAllCategories(entities: List<CategoryEntity>) {
        deleteAll()
        insertAllCategories(entities).blockingAwait()
    }
}