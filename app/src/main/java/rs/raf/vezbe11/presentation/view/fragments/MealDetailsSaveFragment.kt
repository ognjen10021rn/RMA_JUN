package rs.raf.vezbe11.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.data.models.Category
import rs.raf.vezbe11.data.models.Food
import rs.raf.vezbe11.data.models.SavedFood
import rs.raf.vezbe11.data.models.entities.SavedFoodEntity
import rs.raf.vezbe11.databinding.FragmentCategorydetailsBinding
import rs.raf.vezbe11.databinding.FragmentFilterBinding
import rs.raf.vezbe11.databinding.FragmentMealdetailsaveBinding
import rs.raf.vezbe11.presentation.contract.FoodContract
import rs.raf.vezbe11.presentation.contract.NutritionContract
import rs.raf.vezbe11.presentation.view.states.FoodByIdState
import rs.raf.vezbe11.presentation.view.states.NutritionState
import rs.raf.vezbe11.presentation.view.states.SelectedCategoryState
import rs.raf.vezbe11.presentation.viewmodel.FoodViewModel
import rs.raf.vezbe11.presentation.viewmodel.NutritionViewModel
import java.util.Calendar
import java.util.UUID

class MealDetailsSaveFragment :Fragment(R.layout.fragment_categorydetails) {

    private val foodViewModel: FoodContract.ViewModel by sharedViewModel<FoodViewModel>()
    private val nutritionModel: NutritionContract.ViewModel by sharedViewModel<NutritionViewModel>()
    private var _binding: FragmentMealdetailsaveBinding? = null
    private val binding get() = _binding!!
    private var food: Food? = null
    private var finalCalories: Double = 0.0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealdetailsaveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initObservers()

    }

    private fun init(){
        foodViewModel.foodByIdState.observe(viewLifecycleOwner, Observer{
            renderStateFoodById(it)
        })
        nutritionModel.nutritionState.observe(viewLifecycleOwner, Observer{
            renderNutrition(it)
        })
        food = foodViewModel.getCurrentFood()
        foodViewModel.getFoodWithId(food!!.id.toString())
    }
    private fun initObservers(){
        binding.saveButton.setOnClickListener {
            val mealType = binding.obrokSpinner.selectedItem.toString() //dorucak...

                val entity = SavedFood(System.currentTimeMillis(), food!!.name, food!!.strInstructions, food!!.strCategory,
                    binding.datePicker.dayOfMonth, binding.datePicker.month, binding.datePicker.year, finalCalories,mealType, food!!.strMealThumb, food!!.strYoutube, food!!.strIngredient1,
                    food!!.strIngredient2, food!!.strIngredient3, food!!.strIngredient4, food!!.strIngredient5,
                    food!!.strMeasure1, food!!.strMeasure2, food!!.strMeasure3, food!!.strMeasure4, food!!.strMeasure5, null)
                    // food nema custom image path

                foodViewModel.insertSavedFood(entity)

                finalCalories = 0.0
                val transaction= parentFragment?.childFragmentManager?.beginTransaction()
                transaction?.replace(R.id.outerFcvFilterFragment, FilterFragment())
                transaction?.addToBackStack(null)
                transaction?.commit()


//            val name = binding.nameEt.text.toString()
//            val category = binding.obrokSpinner.selectedItem.toString()
//            val ingredients = binding.ingredientsEt.text.toString()
//            val instructions = binding.instructionsEt.text.toString()
//            val image = binding.imageEt.text.toString()
//            val meal = SelectedCategoryState(name, category, ingredients, instructions, image)
//            foodViewModel.saveMeal(meal)



        }

    }
    private fun renderNutrition(state: NutritionState){
        when(state){
            is NutritionState.Success -> {
                showLoadingState(false)
                //var finalCalories : Double = 0.0

                for(cal in state.nutrition){
                    finalCalories += cal.calories
                }

            }
            is NutritionState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is NutritionState.DataFetched -> {
                showLoadingState(false)
                //Toast.makeText(context, "Fetched Nutrition!", Toast.LENGTH_SHORT).show()
            }
            is NutritionState.Loading -> {
                showLoadingState(true)
            }
        }

    }
    private fun renderStateFoodById(state: FoodByIdState){
        when(state){
            is FoodByIdState.Success -> {
                food = state.selectedMeal
                showLoadingState(false)

                binding.textViewName.text = state.selectedMeal.name
                Glide.with(this)
                    .load(state.selectedMeal.strMealThumb)
                    .into(binding.imageView);
                val nutritionCalories = "${food!!.strMeasure1} ${food!!.strIngredient1}, ${food!!.strMeasure2} ${food!!.strIngredient2}, " +
                        "${food!!.strMeasure3} ${food!!.strIngredient3}, ${food!!.strMeasure4} ${food!!.strIngredient4}, " +
                        "${food!!.strMeasure5} ${food!!.strIngredient5}"
                nutritionModel.fetchAllNutritionByQuery(nutritionCalories)
                nutritionModel.getAllNutrition()
            }
            is FoodByIdState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is FoodByIdState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fetched!", Toast.LENGTH_SHORT).show()
            }
            is FoodByIdState.Loading -> {
                showLoadingState(true)
            }
        }
    }
    private fun showLoadingState(state: Boolean){
        binding.progBar.isVisible = state
        binding.textViewName.isVisible = !state
        binding.imageView.isVisible = !state

    }



}

