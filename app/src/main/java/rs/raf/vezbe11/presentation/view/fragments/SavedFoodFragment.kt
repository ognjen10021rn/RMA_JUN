package rs.raf.vezbe11.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.databinding.FragmentSavedmealslistBinding
import rs.raf.vezbe11.presentation.contract.FoodContract
import rs.raf.vezbe11.presentation.view.recycler.adapter.SavedFoodAdapter
import rs.raf.vezbe11.presentation.view.recycler.diff.SavedFoodDiffCallback
import rs.raf.vezbe11.presentation.view.states.NutritionState
import rs.raf.vezbe11.presentation.view.states.SavedFoodState
import rs.raf.vezbe11.presentation.viewmodel.FoodViewModel

class SavedFoodFragment : Fragment(R.layout.fragment_savedmealslist) {

    private val foodViewModel: FoodContract.ViewModel by sharedViewModel<FoodViewModel>()
    private var _binding: FragmentSavedmealslistBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var savedFoodAdapter: SavedFoodAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedmealslistBinding.inflate(inflater, container, false)
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
      binding.savedMealsRecyclerView.layoutManager = LinearLayoutManager(context)
        savedFoodAdapter = SavedFoodAdapter(SavedFoodDiffCallback(),{
            //onItemClicked
            foodViewModel.getSavedFoodById(it.id.toString())
            //TODO setovati selektovanu hranu "setSelectedSavedFood"
            val transaction=parentFragment?.childFragmentManager?.beginTransaction()
            transaction?.replace(R.id.outerFcvThirdTabFragment, EditSavedFoodFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()

        },
        {
            //onButtonClicked
            foodViewModel.deleteSavedFoodById(it.id.toString())
            //foodViewModel.getAllSavedFood()

        })


        binding.savedMealsRecyclerView.adapter = savedFoodAdapter


    }
    private fun initObservers(){
        foodViewModel.savedFoodState.observe(viewLifecycleOwner, {
            renderFood(it)
        })

        foodViewModel.getAllSavedFood()
    }

    private fun renderFood(state: SavedFoodState){
        when(state){
            is SavedFoodState.Success -> {
                showLoadingState(false)
                savedFoodAdapter.submitList(state.savedFoods)
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

    private fun showLoadingState(boolean: Boolean){
        binding.savedMealsRecyclerView.isVisible = !boolean
        binding.progBar2.isVisible = boolean
    }


}