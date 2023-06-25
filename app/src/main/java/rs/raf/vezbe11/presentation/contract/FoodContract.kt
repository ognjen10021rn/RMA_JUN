package rs.raf.vezbe11.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.vezbe11.data.models.Category
import rs.raf.vezbe11.data.models.Food
import rs.raf.vezbe11.data.models.SavedFood
import rs.raf.vezbe11.data.models.entities.AreaEntity
import rs.raf.vezbe11.data.models.entities.CategoryEntity
import rs.raf.vezbe11.data.models.entities.FoodByParameterEntity
import rs.raf.vezbe11.data.models.entities.FoodEntity
import rs.raf.vezbe11.presentation.view.states.*


interface FoodContract {
    interface ViewModel {
        //atributi
        val foodState: LiveData<FoodState>
        val foodByParamaterState: LiveData<FoodByParamaterState>
        val areaState: LiveData<AreasState>
        val addDone: LiveData<AddFoodState>
        val foodByIdState: LiveData<FoodByIdState>
        val selectedCategoryState: LiveData<SelectedCategoryState>
        val savedFoodState: LiveData<SavedFoodState>


        val categories: LiveData<List<CategoryEntity>>
        val meals: LiveData<List<FoodByParameterEntity>>
        val areas: LiveData<List<AreaEntity>>
        val selectedFood : LiveData<FoodEntity>
        val selectedCategory : LiveData<Category>


        //metode
        fun fetchAllCategories();
        fun fetchAllAreas();
        fun fetchMealsByArea(area: String);
        fun fetchMealsByCategory(category: String);

        fun getCurrentSavedFood(): SavedFood;
        fun fetchFoodWithId(id:String);
        fun getFoodWithId(id:String);
        fun getAllCategories();
        fun getCategoriesByName(name: String);

        fun getAllSavedFood();
        fun insertSavedFood(food: SavedFood);
        fun deleteSavedFoodById(id: String);
        fun getSavedFoodById(id: String);
        fun updateSavedFood(food: SavedFood)
        fun getAllMealsByParamater(limit: Int, offset: Int);
        fun getAllAreas();

        fun getSelectedCategory(category: String);
        fun getCurrentFood(): Food


    }
}