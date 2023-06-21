package rs.raf.vezbe11.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.vezbe11.data.models.AreaEntity
import rs.raf.vezbe11.data.models.CategoryEntity
import rs.raf.vezbe11.data.models.FoodByParameterEntity
import rs.raf.vezbe11.presentation.view.states.AddFoodState
import rs.raf.vezbe11.presentation.view.states.AreasState
import rs.raf.vezbe11.presentation.view.states.FoodByParamaterState
import rs.raf.vezbe11.presentation.view.states.FoodState


interface FoodContract {
    interface ViewModel {
        //atributi
        val foodState: LiveData<FoodState>
        val foodByParamaterState: LiveData<FoodByParamaterState>
        val areaState: LiveData<AreasState>
        val addDone: LiveData<AddFoodState>


        val categories: LiveData<List<CategoryEntity>>
        val meals: LiveData<List<FoodByParameterEntity>>
        val areas: LiveData<List<AreaEntity>>


        //metode
        fun fetchAllCategories();
        fun getAllCategories();
        fun getCategoriesByName(name: String);

        fun fetchMealsByArea(area: String);
        fun fetchMealsByCategory(category: String);


        fun getAllMealsByParamater(limit: Int, offset: Int);
        fun getAllAreas();
        fun fetchAllAreas();



    }
}