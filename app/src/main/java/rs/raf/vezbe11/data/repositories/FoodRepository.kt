package rs.raf.vezbe11.data.repositories

import io.reactivex.Observable
import rs.raf.vezbe11.data.models.Category
import rs.raf.vezbe11.data.models.CategoryEntity
import rs.raf.vezbe11.data.models.Resource

interface FoodRepository {

    fun fetchAllCategories(): Observable<Resource<Unit>>
    fun getAllCategories(): Observable<List<Category>>
    fun getCategoriesByName(name: String): Observable<List<Category>>


}