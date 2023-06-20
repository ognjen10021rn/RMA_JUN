package rs.raf.vezbe11.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.vezbe11.data.models.CategoryEntity
import rs.raf.vezbe11.data.models.FoodByParameterEntity
import rs.raf.vezbe11.data.models.FoodEntity

@Dao
abstract class FoodDao {

    //U bazi se cuva hrana samo onu koju korisnik izabere u svoj meni

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(entity: FoodEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(entities: List<FoodEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllFoodsByCategory(entities: List<FoodByParameterEntity>): Completable



    @Query("SELECT * FROM categories")
    abstract fun getAll(): Observable<List<CategoryEntity>>

    @Query ("DELETE FROM foods")
    abstract fun deleteAll()

    @Query ("DELETE FROM foodsByParameter")
    abstract fun deleteAllFoodsByCategory()


    @Query("SELECT * FROM foods WHERE name LIKE :name || '%'")
    abstract fun getByName(name: String): List<FoodEntity>

    @Query("SELECT * FROM foodsByParameter ORDER BY id LIMIT :limit OFFSET :offset")
    abstract fun getFoodsByParameter(limit: Int, offset: Int): Observable<List<FoodByParameterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllCategories(entities: List<CategoryEntity>): Completable


    @Transaction
    open fun deleteAndInsertAllCategories(entities: List<CategoryEntity>) {
        deleteAll()
        insertAllCategories(entities).blockingAwait() // po defaultu je async, ali mi zelimo da bude sync
    }

    @Transaction
    open fun deleteAndInsertFoodsByParameter(entities: List<FoodByParameterEntity>) {
        deleteAllFoodsByCategory()
//        insertAll(entities).blockingAwait() // po defaultu je async, ali mi zelimo da bude sync
        insertAllFoodsByCategory(entities).blockingAwait()
    }




}