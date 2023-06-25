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
import rs.raf.vezbe11.presentation.view.states.FoodByIdState
import rs.raf.vezbe11.presentation.view.states.SelectedCategoryState
import rs.raf.vezbe11.presentation.viewmodel.FoodViewModel
import java.util.Calendar
import java.util.UUID

class MealDetailsSaveFragment :Fragment(R.layout.fragment_categorydetails) {

    private val foodViewModel: FoodContract.ViewModel by sharedViewModel<FoodViewModel>()
    private var _binding: FragmentMealdetailsaveBinding? = null
    private val binding get() = _binding!!
    private var food: Food? = null


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
        food = foodViewModel.getCurrentFood()
        foodViewModel.getFoodWithId(food!!.id.toString())
    }
    private fun initObservers(){
        binding.saveButton.setOnClickListener {
            val mealType = binding.obrokSpinner.selectedItem.toString() //dorucak...
            val prepareDate1 = binding.datePicker.dayOfMonth
            val prepareDate2 = binding.datePicker.month
            val prepareDate3 = binding.datePicker.year
            val cal = Calendar.getInstance()
            cal.set(prepareDate3, prepareDate2, prepareDate1)
            val time = cal.time
            if (food != null){
                val entity = SavedFood(System.currentTimeMillis(), food!!.name, food!!.strInstructions, food!!.strCategory,
                    time.time, mealType, food!!.strMealThumb, food!!.strYoutube, food!!.strIngredient1,
                    food!!.strIngredient2, food!!.strIngredient3, food!!.strIngredient4, food!!.strIngredient5,
                    food!!.strMeasure1, food!!.strMeasure2, food!!.strMeasure3, food!!.strMeasure4, food!!.strMeasure5)

                foodViewModel.insertSavedFood(entity)

                val transaction= parentFragment?.childFragmentManager?.beginTransaction()
                transaction?.replace(R.id.outerFcvFilterFragment, SavedFoodFragment())
                transaction?.addToBackStack(null)
                transaction?.commit()
            }


//            val name = binding.nameEt.text.toString()
//            val category = binding.obrokSpinner.selectedItem.toString()
//            val ingredients = binding.ingredientsEt.text.toString()
//            val instructions = binding.instructionsEt.text.toString()
//            val image = binding.imageEt.text.toString()
//            val meal = SelectedCategoryState(name, category, ingredients, instructions, image)
//            foodViewModel.saveMeal(meal)



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

