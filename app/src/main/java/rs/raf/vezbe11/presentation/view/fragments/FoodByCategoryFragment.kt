package rs.raf.vezbe11.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.databinding.FragmentFilterBinding
import rs.raf.vezbe11.databinding.FragmentFoodbycategorylistBinding
import rs.raf.vezbe11.presentation.contract.FoodContract
import rs.raf.vezbe11.presentation.view.recycler.adapter.FoodByParameterAdapter
import rs.raf.vezbe11.presentation.view.recycler.diff.FoodByParameterDiffCallback
import rs.raf.vezbe11.presentation.viewmodel.FoodViewModel

class FoodByCategoryFragment :Fragment(R.layout.fragment_foodbycategorylist){

    private val foodViewModel: FoodContract.ViewModel by sharedViewModel<FoodViewModel>()
    private var _binding: FragmentFoodbycategorylistBinding? = null
    private val binding get() = _binding!!
    private lateinit var foodByParameterAdapter: FoodByParameterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodbycategorylistBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        initRecycler()
        initObservers()

    }
    private fun initRecycler(){
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        foodByParameterAdapter = FoodByParameterAdapter(FoodByParameterDiffCallback()){
            foodViewModel.fetchFoodWithId(it.id.toString())
            val transaction= parentFragment?.childFragmentManager?.beginTransaction()
            transaction?.replace(R.id.outerFcvFilterFragment,MealDetailsFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
        binding.recyclerView.adapter = foodByParameterAdapter

    }
    private fun initObservers(){
        //TODO ubaciti hranu po kategoriji
//        foodViewModel.foodByCategory.observe(viewLifecycleOwner, {
//            foodByParameterAdapter.submitList(it)
//        })
    }
}