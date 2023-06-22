package rs.raf.vezbe11.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.vezbe11.data.models.Food
import rs.raf.vezbe11.data.models.entities.AreaEntity
import rs.raf.vezbe11.data.models.entities.CategoryEntity
import rs.raf.vezbe11.data.models.entities.FoodByParameterEntity
import rs.raf.vezbe11.data.models.entities.FoodEntity
import rs.raf.vezbe11.presentation.view.states.AddFoodState
import rs.raf.vezbe11.presentation.view.states.AreasState
import rs.raf.vezbe11.presentation.view.states.FoodByIdState
import rs.raf.vezbe11.presentation.view.states.FoodByParamaterState
import rs.raf.vezbe11.presentation.view.states.FoodState


interface FoodContract {
    interface ViewModel {
        //atributi
        val foodState: LiveData<FoodState>
        val foodByParamaterState: LiveData<FoodByParamaterState>
        val areaState: LiveData<AreasState>
        val addDone: LiveData<AddFoodState>
        val foodByIdState: LiveData<FoodByIdState>


        val categories: LiveData<List<CategoryEntity>>
        val meals: LiveData<List<FoodByParameterEntity>>
        val areas: LiveData<List<AreaEntity>>
        val selectedFood : LiveData<FoodEntity>
        val selectedCategory : LiveData<CategoryEntity>


        //metode
        fun fetchAllCategories();
        fun fetchAllAreas();
        fun fetchMealsByArea(area: String);
        fun fetchMealsByCategory(category: String);

        fun fetchFoodWithId(id:String);
        fun getAllCategories();
        fun getCategoriesByName(name: String);


        fun getAllMealsByParamater(limit: Int, offset: Int);
        fun getAllAreas();




    }
}