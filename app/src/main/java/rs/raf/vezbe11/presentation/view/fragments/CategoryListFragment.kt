package rs.raf.vezbe11.presentation.view.fragments

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.compat.ScopeCompat.viewModel
import rs.raf.vezbe11.databinding.FragmentCategorylistBinding
import rs.raf.vezbe11.presentation.contract.FoodContract
import rs.raf.vezbe11.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.vezbe11.presentation.view.recycler.diff.CategoryDiffCallback
import rs.raf.vezbe11.presentation.viewmodel.FoodViewModel
import timber.log.Timber
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
class CategoryListFragment : Fragment(){

    private lateinit var binding: FragmentCategorylistBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private val foodViewModel: FoodContract.ViewModel by sharedViewModel<FoodViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.e("onCreate CategoryListFragment")
        super.onCreate(savedInstanceState)
        binding = FragmentCategorylistBinding.inflate(layoutInflater)
        init()

    }
    private fun init() {
        initRecycler()
    }

    private fun initRecycler(){
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        categoryAdapter = CategoryAdapter(CategoryDiffCallback(),{
            //sta se desi kada kliknemo na kategoriju
            Toast.makeText(context, "Clicked: ${it.name}", Toast.LENGTH_SHORT).show()

        })
        binding.recyclerView.adapter = categoryAdapter
        categoryAdapter.submitList(foodViewModel.getAllCategories().value)
        Timber.e("lista "+foodViewModel.getAllCategories().value.toString())
        //TODO lista je null

    }

}