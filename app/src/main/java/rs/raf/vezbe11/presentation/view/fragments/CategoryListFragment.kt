package rs.raf.vezbe11.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.compat.ScopeCompat.viewModel
import rs.raf.vezbe11.databinding.FragmentCategorylistBinding
import rs.raf.vezbe11.presentation.contract.FoodContract
import rs.raf.vezbe11.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.vezbe11.presentation.view.recycler.diff.CategoryDiffCallback
import rs.raf.vezbe11.presentation.viewmodel.FoodViewModel
import timber.log.Timber
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.databinding.FragmentFooddialogBinding
import rs.raf.vezbe11.presentation.view.states.FoodState

class CategoryListFragment : Fragment(R.layout.fragment_categorylist){

    private val foodViewModel: FoodContract.ViewModel by sharedViewModel<FoodViewModel>()
    private var _binding: FragmentCategorylistBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var categoryAdapter: CategoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategorylistBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initRecycler()
        initObservers()
    }

    private fun initRecycler(){
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        categoryAdapter = CategoryAdapter(CategoryDiffCallback(),{
        //onItemClicked

            //TODO isto kao i u drugoj lambdi,setovati u viewmodelu selektovanu kategoriju

            val transaction=parentFragment?.childFragmentManager?.beginTransaction()
            transaction?.replace(R.id.outerFcvCategoriesList,FoodByCategoryFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()


        },{
            //onButtonClicked

            foodViewModel.getSelectedCategory(it.name)
            //TODO u viewmodelu getovati kategoriju po imenu(izvuci odavde,radi) i
            //onda u categoryDetails observovati selektovanu kategoriju i popuniti

            val transaction=parentFragment?.childFragmentManager?.beginTransaction()
            transaction?.replace(R.id.outerFcvCategoriesList,CategoryDetailsFragment())
            transaction?.addToBackStack(null)

            transaction?.commit()
        })



        binding.recyclerView.adapter = categoryAdapter

    }
    private fun initObservers(){

        foodViewModel.foodState.observe(viewLifecycleOwner, Observer {
            //Timber.e(it.toString())
            renderState(it)
        })

        foodViewModel.getAllCategories()
        foodViewModel.fetchAllCategories()
    }
    private fun initListeners() { // Ovo je za promenu texta view modela, ne treba nam ja msm

        binding.inputEt.doAfterTextChanged {
            val filter = it.toString()
            foodViewModel.getCategoriesByName(filter)
        }
    }
    private fun renderState(state: FoodState) {
        when (state) {
            is FoodState.Success -> {
                showLoadingState(false)
                categoryAdapter.submitList(state.categories)
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
        binding.inputEt.isVisible = !loading
        binding.recyclerView.isVisible = !loading
        binding.loadingPb.isVisible = loading
    }


}