package rs.raf.vezbe11.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.vezbe11.data.models.entities.CategoryEntity
import rs.raf.vezbe11.data.models.entities.FoodEntity
import rs.raf.vezbe11.data.models.entities.NutritionEntity

@Dao
abstract class NutritionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(entity: FoodEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(entities: List<NutritionEntity>): Completable

    @Query("SELECT * FROM nutrition")
    abstract fun getAllNutrition(): Observable<List<NutritionEntity>>

    @Query("DELETE FROM nutrition")
    abstract fun deleteAll()

    @Query("SELECT * FROM nutrition WHERE name LIKE :name || '%'")
    abstract fun getByName(name: String): List<NutritionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllNutrition(entities: List<NutritionEntity>): Completable


    @Transaction
    open fun deleteAndInsertAllNutrition(entities: List<NutritionEntity>) {
        deleteAll()
        insertAllNutrition(entities).blockingAwait()
    }
}