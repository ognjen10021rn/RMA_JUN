package rs.raf.vezbe11.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.data.models.Category
import rs.raf.vezbe11.databinding.FragmentCategorylistBinding
import rs.raf.vezbe11.databinding.FragmentEditsavedfoodBinding
import rs.raf.vezbe11.presentation.contract.FoodContract
import rs.raf.vezbe11.presentation.view.states.FoodState
import rs.raf.vezbe11.presentation.viewmodel.FoodViewModel

class EditSavedFoodFragment : Fragment(R.layout.fragment_editsavedfood) {

    private val foodViewModel: FoodContract.ViewModel by sharedViewModel<FoodViewModel>()
    private var _binding: FragmentEditsavedfoodBinding? = null

    private val binding get() = _binding!!
    private var categories : List<Category>?=null


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
        initView()
        initListeners()
    }
    private fun initView(){

        addCategoriesToDropdownMenu()
        //TODO dodati u viewmodelu selectedSavedFood,zato sto savedFood i Food nisu istu,savedFood ima date
        //binding.nameEditText.setText(foodViewModel.getSelectedSavedFood().name)



//        Glide.with(binding.imageView.context)
//            .load(foodViewModel.getSelectedSavedFood().image)
//            .into(binding.foodImage)

    }
    private fun initListeners(){
        binding.buttonSave.setOnClickListener {
            //TODO
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

    private fun showLoadingState(loading: Boolean) {
        binding.progressBar2.isVisible = loading
    }





}