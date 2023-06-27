package rs.raf.vezbe11.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.data.models.Category
import rs.raf.vezbe11.databinding.FragmentCategorydetailsBinding
import rs.raf.vezbe11.databinding.FragmentFilterBinding
import rs.raf.vezbe11.presentation.contract.FoodContract
import rs.raf.vezbe11.presentation.view.states.SelectedCategoryState
import rs.raf.vezbe11.presentation.viewmodel.FoodViewModel

class CategoryDetailsFragment :Fragment(R.layout.fragment_categorydetails) {

    private val foodViewModel: FoodContract.ViewModel by sharedViewModel<FoodViewModel>()
    private var _binding: FragmentCategorydetailsBinding? = null
    private val binding get() = _binding!!
    private var category: Category? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategorydetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
    }
    private fun initObservers(){

        foodViewModel.selectedCategoryState.observe(viewLifecycleOwner, Observer{
//            println("CATEGORY ENTITY ${it.name}")
//            binding.textViewDescription.text = it.description
//            binding.textViewName.text = it.name
//
//            Glide.with(binding.imageView.context)
//                .load(it.imagePath)
//                .into(binding.imageView)

            initListener(it)

        })



    }
    fun initListener(state: SelectedCategoryState){
        when(state){
            is SelectedCategoryState.Success ->{
                category = state.category

                binding.textViewDescription.text = state.category.description
                binding.textViewName.text = state.category.name
                Glide.with(binding.imageView.context)
                .load(state.category.imagePath)
                .into(binding.imageView)
            }
        }
    }
}