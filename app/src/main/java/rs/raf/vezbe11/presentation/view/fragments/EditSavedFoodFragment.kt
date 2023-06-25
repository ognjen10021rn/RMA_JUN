package rs.raf.vezbe11.presentation.view.fragments

import android.os.Bundle
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
import rs.raf.vezbe11.data.models.SavedFood
import rs.raf.vezbe11.databinding.FragmentCategorylistBinding
import rs.raf.vezbe11.databinding.FragmentEditsavedfoodBinding
import rs.raf.vezbe11.presentation.contract.FoodContract
import rs.raf.vezbe11.presentation.view.states.FoodState
import rs.raf.vezbe11.presentation.view.states.SavedFoodState
import rs.raf.vezbe11.presentation.viewmodel.FoodViewModel
import java.text.DateFormat
import java.util.Calendar
import java.util.Date
import kotlin.time.days

class EditSavedFoodFragment : Fragment(R.layout.fragment_editsavedfood) {

    private val foodViewModel: FoodContract.ViewModel by sharedViewModel<FoodViewModel>()
    private var _binding: FragmentEditsavedfoodBinding? = null

    private val binding get() = _binding!!
    private var categories : List<Category>?=null
    private var food: SavedFood? = null


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
        initView()
        initListeners()
    }
    private fun initObservers(){
        foodViewModel.foodState.observe(viewLifecycleOwner, Observer {
            renderStateFoods(it)
        })
        foodViewModel.savedFoodState.observe(viewLifecycleOwner, Observer {
            renderStateSavedFoods(it)
        })
        food = foodViewModel.getCurrentSavedFood()
        foodViewModel.getSavedFoodById(food!!.id.toString())
        foodViewModel.getAllCategories()
    }
    private fun initView(){

        //TODO dodati u viewmodelu selectedSavedFood,zato sto savedFood i Food nisu istu,savedFood ima date
        //binding.nameEditText.setText(foodViewModel.getSelectedSavedFood().name)



//        Glide.with(binding.imageView.context)
//            .load(foodViewModel.getSelectedSavedFood().image)
//            .into(binding.foodImage)

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
    private fun renderStateSavedFoods(state: SavedFoodState){
        when (state) {
            is SavedFoodState.Success2 -> {
                showLoadingState(false)
                food = state.savedFood
                binding.nameEditText.setText(food!!.name)
                binding.editTextInstructions.setText(food!!.strInstructions)
                val date = Date(food!!.date)
//                var dejt = date.fromTimestamp(food!!.date)
//                val day = dejt.day
//                val month = dejt.month
//                val year = dejt.yeard
                //binding.datePicker.updateDate(date.year, date.month, date.day)


                // Todo Jebes mi sve ne znam kako da setujem date kako treba, uvek mi uzme godinu 1900 i jos neke gluposti
                //
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