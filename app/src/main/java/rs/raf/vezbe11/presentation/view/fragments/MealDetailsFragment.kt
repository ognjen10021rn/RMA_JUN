package rs.raf.vezbe11.presentation.view.fragments

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
import rs.raf.vezbe11.databinding.FragmentFilterBinding
import rs.raf.vezbe11.databinding.FragmentMealdetailsBinding
import rs.raf.vezbe11.presentation.contract.FoodContract
import rs.raf.vezbe11.presentation.view.states.FoodByIdState
import rs.raf.vezbe11.presentation.viewmodel.FoodViewModel

class MealDetailsFragment : Fragment(R.layout.fragment_mealdetails){

    private val foodViewModel: FoodContract.ViewModel by sharedViewModel<FoodViewModel>()
    private var _binding: FragmentMealdetailsBinding? = null
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

    }
    private fun initListeners(){
        binding.buttonSaveMeal.setOnClickListener {
           // foodViewModel.saveMeal()
        }
    }
    private fun renderStateFoodById(state:FoodByIdState){

        //TODO ne ulazi mi u success
        when(state){
            is FoodByIdState.Success -> {
                showLoadingState(false)
                val meal = state.selectedMeal
                binding.mealNameTextView.text = meal.strMeal
                binding.categoryTextView.text = meal.strCategory
                binding.areaTextView.text = meal.strArea
                binding.instructionTextView.text = meal.strInstructions

                val ingredientsText="${meal.strIngredient1} (${meal.strMeasure1})," +
                        "${meal.strIngredient2} (${meal.strMeasure2})," +
                        "${meal.strIngredient3} (${meal.strMeasure3})," +
                        "${meal.strIngredient4} (${meal.strMeasure4})," +
                        "${meal.strIngredient5} ( ${meal.strMeasure5}),"

                binding.ingredientsTextView.text =ingredientsText
                binding.linkTextView.text = meal.strYoutube

                Glide.with(this) //TODO da li je ovo dobro?
                    .load(meal.strMealThumb)
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
    private fun showLoadingState(loading: Boolean) {
        binding.progressBar.isVisible = loading
    }











}