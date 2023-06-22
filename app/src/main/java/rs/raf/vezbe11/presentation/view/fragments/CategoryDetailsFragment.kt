package rs.raf.vezbe11.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.databinding.FragmentCategorydetailsBinding
import rs.raf.vezbe11.databinding.FragmentFilterBinding
import rs.raf.vezbe11.presentation.contract.FoodContract
import rs.raf.vezbe11.presentation.viewmodel.FoodViewModel

class CategoryDetailsFragment :Fragment(R.layout.fragment_categorydetails) {

    private val foodViewModel: FoodContract.ViewModel by sharedViewModel<FoodViewModel>()
    private var _binding: FragmentCategorydetailsBinding? = null
    private val binding get() = _binding!!


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

        foodViewModel.selectedCategory.observe(viewLifecycleOwner, {
           binding.textViewDescription.text = it.description
            binding.textViewName.text = it.name

            Glide.with(binding.imageView.context)
                .load(it.imagePath)
                .into(binding.imageView)
        })


    }
}