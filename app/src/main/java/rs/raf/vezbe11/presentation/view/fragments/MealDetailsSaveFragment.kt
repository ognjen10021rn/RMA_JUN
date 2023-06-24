package rs.raf.vezbe11.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.data.models.Category
import rs.raf.vezbe11.databinding.FragmentCategorydetailsBinding
import rs.raf.vezbe11.databinding.FragmentFilterBinding
import rs.raf.vezbe11.databinding.FragmentMealdetailsaveBinding
import rs.raf.vezbe11.presentation.contract.FoodContract
import rs.raf.vezbe11.presentation.view.states.SelectedCategoryState
import rs.raf.vezbe11.presentation.viewmodel.FoodViewModel

class MealDetailsSaveFragment :Fragment(R.layout.fragment_categorydetails) {

    private val foodViewModel: FoodContract.ViewModel by sharedViewModel<FoodViewModel>()
    private var _binding: FragmentMealdetailsaveBinding? = null
    private val binding get() = _binding!!


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

        initObservers()
    }
    private fun initObservers(){
        binding.obrokSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {

                    //
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

    }



}

