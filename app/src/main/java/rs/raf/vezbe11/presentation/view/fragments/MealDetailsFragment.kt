package rs.raf.vezbe11.presentation.view.fragments

import android.icu.util.Measure
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.data.models.Food
import rs.raf.vezbe11.data.models.Nutrition
import rs.raf.vezbe11.databinding.FragmentFilterBinding
import rs.raf.vezbe11.databinding.FragmentMealdetailsBinding
import rs.raf.vezbe11.presentation.contract.FoodContract
import rs.raf.vezbe11.presentation.contract.NutritionContract
import rs.raf.vezbe11.presentation.view.states.FoodByIdState
import rs.raf.vezbe11.presentation.view.states.NutritionState
import rs.raf.vezbe11.presentation.viewmodel.FoodViewModel
import rs.raf.vezbe11.presentation.viewmodel.NutritionViewModel
import java.util.*
import kotlin.math.roundToInt

class MealDetailsFragment : Fragment(R.layout.fragment_mealdetails){

    private val foodViewModel: FoodContract.ViewModel by sharedViewModel<FoodViewModel>()
    private val nutritionModel: NutritionContract.ViewModel by sharedViewModel<NutritionViewModel>()
    private var _binding: FragmentMealdetailsBinding? = null
    private var calories: List<Nutrition>? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealdetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    private fun init(){
        initListeners()
        initObservers()
    }
    private fun initObservers(){
        foodViewModel.foodByIdState.observe(viewLifecycleOwner, {
            renderStateFoodById(it)
        })
        nutritionModel.nutritionState.observe(viewLifecycleOwner, {
            renderNutrition(it)
        })

    }

    private fun renderNutrition(state: NutritionState){
        when(state){
            is NutritionState.Success -> {
                showLoadingState(false)
                calories = state.nutrition
                var finalCalories : Double = 0.0

                for(cal in calories!!){
                    finalCalories += cal.calories
                }
                val textCalories = "Total calories: $finalCalories"
                binding.caloriesTextView.text = textCalories
            }
            is NutritionState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is NutritionState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fetched Nutrition!", Toast.LENGTH_SHORT).show()
            }
            is NutritionState.Loading -> {
                showLoadingState(true)
            }
        }

    }
    private fun initListeners(){
        binding.buttonSaveMeal.setOnClickListener {
           // foodViewModel.saveMeal()
            val transaction= parentFragment?.childFragmentManager?.beginTransaction()
            transaction?.replace(R.id.outerFcvFilterFragment, MealDetailsSaveFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
    }
    private fun renderStateFoodById(state:FoodByIdState){

        //TODO ne ulazi mi u success
        when(state){
            is FoodByIdState.Success -> {
                showLoadingState(false)
                val meal = state.selectedMeal
                binding.mealNameTextView.text = state.selectedMeal.name
                binding.categoryTextView.text = state.selectedMeal.strCategory
                binding.areaTextView.text = state.selectedMeal.strArea
                binding.instructionTextView.text = state.selectedMeal.strInstructions

//                val measure1 = meal.strMeasure1?.let { checkMeasure(it) }
//                val measure2 = meal.strMeasure2?.let { checkMeasure(it) }
//                val measure3 = meal.strMeasure3?.let { checkMeasure(it) }
//                val measure4 = meal.strMeasure4?.let { checkMeasure(it) }
//                val measure5 = meal.strMeasure5?.let { checkMeasure(it) }

                val nutritionCalories = "${meal.strMeasure1} ${meal.strIngredient1}, ${meal.strMeasure2} ${meal.strIngredient2}, " +
                        "${meal.strMeasure3} ${meal.strIngredient3}, ${meal.strMeasure4} ${meal.strIngredient4}, " +
                        "${meal.strMeasure5} ${meal.strIngredient5}"
                nutritionModel.fetchAllNutritionByQuery(nutritionCalories)
                nutritionModel.getAllNutrition()



                val ingredientsText="${state.selectedMeal.strIngredient1} (${state.selectedMeal.strMeasure1})," +
                        "${state.selectedMeal.strIngredient2} (${state.selectedMeal.strMeasure2})," +
                        "${state.selectedMeal.strIngredient3} (${state.selectedMeal.strMeasure3})," +
                        "${state.selectedMeal.strIngredient4} (${state.selectedMeal.strMeasure4})," +
                        "${state.selectedMeal.strIngredient5} ( ${state.selectedMeal.strMeasure5}),"

                binding.ingredientsTextView.text = ingredientsText
                binding.linkTextView.text = state.selectedMeal.strYoutube

                Glide.with(this) //TODO da li je ovo dobro?
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

    private fun checkMeasure(str: String): String{
        if(str!!.contains("grams"))
            return str
        if(str!!.contains("cup")){
            var measure: Float = 0.0f
            val res : Float
            if(str.contains(" ")){
                val split = str.split(" ",",",";", "")
                if(split[0].contains("/")){

                    val split1 = split[0].split("/")[0].toFloat()
                    val split2 = split[0].split("/")[1].toFloat()
                    measure = split1/split2
                }
                else{
                    measure = split[0].toFloat()
                }
                res = measure * 201.6f

                return "${res.roundToInt()} grams"
            }
        }
        if(str!!.toLowerCase(Locale.ROOT).contains("tbsp") || str!!.toLowerCase(Locale.ROOT).contains("table spoon") || str!!.toLowerCase(
                Locale.ROOT).contains("tablespoon")){
            if (str.contains(" ")){
                val split = str.split(" ")
                val res : Float
                var measure: Float = 0.0f
                if(split[0].contains("/")){
                    val split1 = split[0].split("/")[0].toFloat()
                    val split2 = split[0].split("/")[1].toFloat()
                    measure = split1/split2
                }
                else{
                    measure = split[0].toFloat()

                }
                res = measure * 12.7817f
                return "${res.roundToInt()} grams"
            }else{
//               if(str.contains("tbsp")){
////                    val newVal = str.replace("tbsp", "").toFloat()
////                    val test = newVal * 12.7817f
////                    return "$test grams"
////                }
////                if(str.contains("tablespoon")){
////                    val newVal = str.replace("tablespoon", "").toFloat()
////                    val test = newVal * 12.7817f
////                    return "$test grams"
              return "13 grams"
            }
        }
        if(str!!.toLowerCase(Locale.ROOT).contains("tsp") || str!!.toLowerCase(Locale.ROOT).contains("tea spoon") || str!!.toLowerCase(
                Locale.ROOT).contains("teaspoon")){
            if(str.contains(" ")){

                val split = str.split(" ",",",";")
                val res : Float
                var measure: Float = 0.0f
                if(split[0].contains("/")){
                    val split1 = split[0].split("/")[0].toFloat()
                    val split2 = split[0].split("/")[1].toFloat()
                    measure = split1/split2
                }else{
                    measure = split[0].toFloat()

                }
                res = measure * 4.26057f

                return "${res.roundToInt()} grams"
            }
            return "4 grams"
        }
        if(str!!.toLowerCase(Locale.ROOT).contains("oz") || str!!.toLowerCase(Locale.ROOT).contains("ounce") ){
            if (str.contains(" ")){
                val split = str.split(" ",",",";")
                val res : Float
                var measure: Float = 0.0f
                if(split[0].contains("/")){
                    val split1 = split[0].split("/")[0].toFloat()
                    val split2 = split[0].split("/")[1].toFloat()
                    measure = split1/split2
                }else{
                    measure = split[0].toFloat()

                }
                res = measure * 28.34952f

                return "${res.roundToInt()} grams"
            }
        }
        if(str!!.toLowerCase(Locale.ROOT).contains("pint") || str!!.toLowerCase(Locale.ROOT).contains(" pt") ){
            if(str.contains(" ")){
                val split = str.split(" ",",",";")
                val res : Float
                var measure: Float = 0.0f
                if(split[0].contains("/")){
                    val split1 = split[0].split("/")[0].toFloat()
                    val split2 = split[0].split("/")[1].toFloat()
                    measure = split1/split2
                }else{
                    measure = split[0].toFloat()

                }
                res = measure * 473.17648f

                return "${res.roundToInt()} grams"
            }
        }
        if(str!!.toLowerCase(Locale.ROOT).contains("quart") || str!!.toLowerCase(Locale.ROOT).contains(" qt") ){
            if(str.contains(" ")){
                val split = str.split(" ",",",";")
                val res : Float
                var measure: Float = 0.0f
                if(split[0].contains("/")){
                    val split1 = split[0].split("/")[0].toFloat()
                    val split2 = split[0].split("/")[1].toFloat()
                    measure = split1/split2
                }else{
                    measure = split[0].toFloat()

                }
                res = measure * 806.4f

                return "${res.roundToInt()} grams"
            }
        }
        if(str!!.toLowerCase(Locale.ROOT).contains("gallon") || str!!.toLowerCase(Locale.ROOT).contains(" gal") ){
            if(str.contains(" ")){

                val split = str.split(" ",",",";")
                val res : Float
                var measure: Float = 0.0f
                if(split[0].contains("/")){
                    val split1 = split[0].split("/")[0].toFloat()
                    val split2 = split[0].split("/")[1].toFloat()
                    measure = split1/split2
                }else{
                    measure = split[0].toFloat()

                }
                res = measure * 3225.6f

                return "${res.roundToInt()} grams"
            }
        }
        if(str!!.toLowerCase(Locale.ROOT).contains("pound") || str!!.toLowerCase(Locale.ROOT).contains(" lb") ){
            if(str.contains(" ")){

                val split = str.split(" ",",",";")
                val res : Float
                var measure: Float = 0.0f
                if(split[0].contains("/")){
                    val split1 = split[0].split("/")[0].toFloat()
                    val split2 = split[0].split("/")[1].toFloat()
                    measure = split1/split2
                }else{
                    measure = split[0].toFloat()

                }
                res = measure * 453.59238f

                return "${res.roundToInt()} grams"
            }
        }
        if((str!!.toLowerCase(Locale.ROOT).contains("liter") || str!!.toLowerCase(Locale.ROOT).contains(" l")) && !str!!.contains("large")){
            if(str.contains(" ")){
                val split = str.split(" ",",",";")
                val res : Float
                var measure: Float = 0.0f
                if(split[0].contains("/")){
                    val split1 = split[0].split("/")[0].toFloat()
                    val split2 = split[0].split("/")[1].toFloat()
                    measure = split1/split2
                }else{
                    measure = split[0].toFloat()

                }
                res = measure * 1000f

                return "${res.roundToInt()} grams"
            }
        }
        if(str!!.toLowerCase(Locale.ROOT).contains("deciliter") || str!!.toLowerCase(Locale.ROOT).contains(" dl") ){
            if(str.contains(" ")){

                val split = str.split(" ",",",";")
                val res : Float
                var measure: Float = 0.0f
                if(split[0].contains("/")){
                    val split1 = split[0].split("/")[0].toFloat()
                    val split2 = split[0].split("/")[1].toFloat()
                    measure = split1/split2
                }else{
                    measure = split[0].toFloat()

                }
                res = measure * 100f

                return "${res.roundToInt()} grams"
            }
        }
        if(str!!.toLowerCase(Locale.ROOT).contains("milliliter") || str!!.toLowerCase(Locale.ROOT).contains(" ml") ){
            if(str.contains(" ")){
                val split = str.split(" ",",",";")
                val res : Float
                var measure: Float = 0.0f
                if(split[0].contains("/")){
                    val split1 = split[0].split("/")[0].toFloat()
                    val split2 = split[0].split("/")[1].toFloat()
                    measure = split1/split2
                }else{
                    measure = split[0].toFloat()

                }
                res = measure * 1f

                return "${res.roundToInt()} grams"
            }
        }
        return str

    }
    private fun showLoadingState(loading: Boolean) {
        binding.progressBar.isVisible = loading
    }











}