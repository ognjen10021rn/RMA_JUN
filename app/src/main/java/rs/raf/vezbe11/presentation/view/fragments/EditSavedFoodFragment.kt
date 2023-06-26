package rs.raf.vezbe11.presentation.view.fragments

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.data.database.converters.DateConverter
import rs.raf.vezbe11.data.models.Category
import rs.raf.vezbe11.data.models.Nutrition
import rs.raf.vezbe11.data.models.SavedFood
import rs.raf.vezbe11.databinding.FragmentCategorylistBinding
import rs.raf.vezbe11.databinding.FragmentEditsavedfoodBinding
import rs.raf.vezbe11.presentation.contract.FoodContract
import rs.raf.vezbe11.presentation.contract.NutritionContract
import rs.raf.vezbe11.presentation.view.states.FoodState
import rs.raf.vezbe11.presentation.view.states.NutritionState
import rs.raf.vezbe11.presentation.view.states.SavedFoodState
import rs.raf.vezbe11.presentation.viewmodel.FoodViewModel
import rs.raf.vezbe11.presentation.viewmodel.NutritionViewModel
import java.text.DateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import kotlin.time.days

class EditSavedFoodFragment : Fragment(R.layout.fragment_editsavedfood) {

    private val foodViewModel: FoodContract.ViewModel by sharedViewModel<FoodViewModel>()
    private val nutritionViewModel: NutritionContract.ViewModel by sharedViewModel<NutritionViewModel>()
    private var _binding: FragmentEditsavedfoodBinding? = null

    private val binding get() = _binding!!
    private var categories : List<Category>?=null
    private var food: SavedFood? = null
    private var calories: List<Nutrition>? = null
    private var listCalories: List<Nutrition>? = null
    private val permissions = arrayOf(Manifest.permission.CAMERA)
    private val PERMISSION_ACCEPTED=1


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditsavedfoodBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    private fun init(){
        initObservers()
        initListeners()
        initPermissions()
    }

    private fun initPermissions(){
        binding.imageView.setOnClickListener(View.OnClickListener {

            if (PERMISSION_ACCEPTED == 1) {
                catchFoodPhoto()

            } else {
                requestPermissions(permissions, PERMISSION_ACCEPTED)
                if (PERMISSION_ACCEPTED == 1) {
                    catchFoodPhoto()

                }
            }
        })



    }
    private fun catchFoodPhoto(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 1)


    }

    private fun initObservers(){
        foodViewModel.foodState.observe(viewLifecycleOwner, Observer {
            renderStateFoods(it)
        })
        foodViewModel.savedFoodState.observe(viewLifecycleOwner, Observer {
            renderStateSavedFoods(it)
        })
        nutritionViewModel.nutritionState.observe(viewLifecycleOwner, Observer {
            renderNutrition(it)
        })

        food = foodViewModel.getCurrentSavedFood()
        foodViewModel.getSavedFoodById(food!!.id.toString())
        foodViewModel.getAllCategories()
    }
    private fun initListeners(){
        binding.buttonSave.setOnClickListener {

                food!!.name = binding.nameEditText.text.toString()
                food!!.strInstructions = binding.editTextInstructions.text.toString()
                //food!!.strMealThumb = binding.imageView.????
                food!!.strMealType = binding.dropdownMealType.selectedItem.toString()
                food!!.strCategory = binding.categoryDropdown.selectedItem.toString()
                foodViewModel.updateSavedFood(food!!)

        }

    }

    private fun addCategoriesToDropdownMenu() {

        val categoriesNames = mutableListOf<String>()

        for (category in categories!!) {
            categoriesNames.add(category.name)
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoriesNames!!)
        binding.categoryDropdown.adapter = adapter


    }
    private fun renderStateFoods(state: FoodState) {
        when (state) {
            is FoodState.Success -> {
                showLoadingState(false)
                categories = state.categories
                val queryString = "100g ${food!!.strIngredient1}, 100g ${food!!.strIngredient2}, " +
                        "100g ${food!!.strIngredient3}, 100g ${food!!.strIngredient4}, " +
                        "100g ${food!!.strIngredient5}"
                nutritionViewModel.fetchAllNutritionByQuery(queryString)
                nutritionViewModel.getAllNutrition()
                addCategoriesToDropdownMenu()
            }
            is FoodState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is FoodState.DataFetched -> {
                showLoadingState(false)

            }
            is FoodState.Loading -> {
                showLoadingState(true)
            }
        }
    }
    //private var isFetched: Boolean = false
    private fun renderNutrition(state: NutritionState){
        when (state) {
            is NutritionState.Success -> {
                showLoadingState(false)
                    binding.calorieCountTv.setText(food!!.calories.toString())


                    listCalories = state.nutrition
                    var stringBuilder: String? = "Calories per 100g:\n"
                    for(cal in listCalories!!){
                        stringBuilder+= "Ingridient: ${cal.name}, Calories: ${cal.calories}\n"
                    }
                    binding.allCalories.setText(stringBuilder)
            }
            is NutritionState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is NutritionState.Loading -> {
                showLoadingState(true)
            }
        }
    }
    private fun renderStateSavedFoods(state: SavedFoodState){
        when (state) {
            is SavedFoodState.Success2 -> {
                showLoadingState(false)
                food = state.savedFood
                binding.nameEditText.setText(food!!.name)
                binding.editTextInstructions.setText(food!!.strInstructions)
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_MONTH, food!!.dayOfMonth)
                binding.datePicker.updateDate(food!!.year, food!!.month, food!!.dayOfMonth)
                //nutritionViewModel.fetchAllNutritionByQuery(queryString)

                Glide.with(this)
                    .load(food!!.strMealThumb)
                    .into(binding.imageView)
            }
            is SavedFoodState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is SavedFoodState.Loading -> {
                showLoadingState(true)
            }
        }
    }


    private fun showLoadingState(loading: Boolean) {
        binding.progressBar2.isVisible = loading
    }






}