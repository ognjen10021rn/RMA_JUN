package rs.raf.vezbe11.presentation.view.fragments

import android.icu.util.Measure
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.data.models.Food
import rs.raf.vezbe11.data.models.Nutrition
import rs.raf.vezbe11.data.models.SavedFood
import rs.raf.vezbe11.databinding.FragmentChoosefoodBinding
import rs.raf.vezbe11.databinding.FragmentFilterBinding
import rs.raf.vezbe11.databinding.FragmentMealdetailsBinding
import rs.raf.vezbe11.presentation.contract.FoodContract
import rs.raf.vezbe11.presentation.contract.NutritionContract
import rs.raf.vezbe11.presentation.view.recycler.adapter.FoodAdapter
import rs.raf.vezbe11.presentation.view.recycler.adapter.FoodByParameterAdapter
import rs.raf.vezbe11.presentation.view.recycler.adapter.SavedFoodAdapter
import rs.raf.vezbe11.presentation.view.recycler.diff.FoodByParameterDiffCallback
import rs.raf.vezbe11.presentation.view.recycler.diff.FoodDiffCallback
import rs.raf.vezbe11.presentation.view.recycler.diff.SavedFoodDiffCallback
import rs.raf.vezbe11.presentation.view.states.FoodByIdState
import rs.raf.vezbe11.presentation.view.states.FoodState2
import rs.raf.vezbe11.presentation.view.states.NutritionState
import rs.raf.vezbe11.presentation.view.states.SavedFoodState
import rs.raf.vezbe11.presentation.viewmodel.FoodViewModel
import rs.raf.vezbe11.presentation.viewmodel.NutritionViewModel
import java.io.File
import java.util.*
import kotlin.math.roundToInt

class ChooseFood : Fragment(R.layout.fragment_choosefood){

    private val foodViewModel: FoodContract.ViewModel by sharedViewModel<FoodViewModel>()
    //private val nutritionModel: NutritionContract.ViewModel by sharedViewModel<NutritionViewModel>()
    private var _binding: FragmentChoosefoodBinding? = null
    private var calories: List<Nutrition>? = null
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var savedFoodAdapter: SavedFoodAdapter
    private val binding get() = _binding!!
    private var currentMeal: Food? = null
    private var savedFood: List<SavedFood>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChoosefoodBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    private fun init(){
        initObservers()
        //initRecycler2()
        //initRecycler1()
        initListeners()
        //initRecycler1()
    }
    private fun initListeners(){
        binding.myFood.setOnClickListener{
            foodViewModel.getAllSavedFood()
        }
        binding.apiFood.setOnClickListener{
            foodViewModel.fetchAllFoods()
            foodViewModel.getAllFoods()
        }
    }
    private fun initRecycler1(){
        binding.recyClerView11.layoutManager = LinearLayoutManager(context)
        foodAdapter = FoodAdapter(FoodDiffCallback())
        binding.recyClerView11.adapter = foodAdapter
    }
    private fun initRecycler2(){
        binding.recyClerView11.layoutManager = LinearLayoutManager(context)
        savedFoodAdapter = SavedFoodAdapter(SavedFoodDiffCallback(),{

//            val transaction= parentFragment?.childFragmentManager?.beginTransaction()
//            transaction?.replace(R.id.outerFcvFilterFragment,MealDetailsFragment())
//            transaction?.addToBackStack(null)
//            transaction?.commit()

        },{

        })
        binding.recyClerView11.adapter = savedFoodAdapter
    }

    private fun initObservers(){

        foodViewModel.foodState2.observe(viewLifecycleOwner, {
            renderStateFoodById(it)
        })
        foodViewModel.savedFoodState.observe(viewLifecycleOwner, {
            renderSavedFood(it)
        })

    }

    private fun renderStateFoodById(state: FoodState2){
        when (state) {
            is FoodState2.Success -> {
                showLoadingState(false)

                ///println("USAOOOOOO????")
                initRecycler1()
//                println(state.foods)
                foodAdapter.submitList(state.foods)
                //foodByParameterAdapter.submitList(state.foods)
            }
            is FoodState2.Error -> {
                showLoadingState(false)
                //Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is FoodState2.Loading -> {
                showLoadingState(true)
            }
        }
    }
    private fun renderSavedFood(state: SavedFoodState){
        when (state) {
            is SavedFoodState.Success -> {
                showLoadingState(false)
                initRecycler2()
                savedFoodAdapter.submitList(state.savedFoods)
            }
            is SavedFoodState.Error -> {
                showLoadingState(false)
                //Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is SavedFoodState.Loading -> {
                showLoadingState(true)
            }
        }
    }




    private fun showLoadingState(loading: Boolean) {
        binding.progressBar3.isVisible = loading
        binding.recyClerView11.isVisible = !loading
    }











}