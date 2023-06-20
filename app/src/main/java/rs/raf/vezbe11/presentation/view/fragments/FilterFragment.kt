package rs.raf.vezbe11.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.map
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.compat.SharedViewModelCompat.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.data.models.Area
import rs.raf.vezbe11.data.models.Category
import rs.raf.vezbe11.data.models.CategoryEntity
import rs.raf.vezbe11.data.models.Ingredient
import rs.raf.vezbe11.databinding.FragmentFilterBinding
import rs.raf.vezbe11.presentation.contract.FoodContract
import rs.raf.vezbe11.presentation.view.recycler.adapter.FoodByParameterAdapter
import rs.raf.vezbe11.presentation.view.states.FoodState
import rs.raf.vezbe11.presentation.viewmodel.FoodViewModel
import timber.log.Timber

class FilterFragment : Fragment(R.layout.fragment_filter) {

    private val foodViewModel: FoodContract.ViewModel by sharedViewModel<FoodViewModel>()
    private var _binding: FragmentFilterBinding? = null
    private var categories : List<Category>?=null
    private var areas: List<Area>?=null
    private var ingredients: List<Ingredient>?=null
    private lateinit var foodByParameterAdapter: FoodByParameterAdapter

    private val binding get() = _binding!!
    //TODO treba ti adapter za listu hrane
    //private lateinit var foodAdapter: FoodAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }
    private fun init(){
        initRecycler()
        initObservers()
        initListeners()
    }

    private fun initRecycler(){
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        foodByParameterAdapter = FoodByParameterAdapter()
        binding.recyclerView.adapter = foodByParameterAdapter
    }
    private fun initObservers(){
        foodViewModel.foodState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
    }
    private fun initListeners() {
        binding.spinnerParameter.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?,view: View?,position: Int,id: Long
                ) {

                    if(position==1){
                        showViewObjects()
                        binding.selectTextView.setText("Select category")
                        addCategoriesToDropdownMenu()


                    }
                    else if(position==2){
                        showViewObjects()
                        binding.selectTextView.setText("Select location")
                        addAreasToDropdownMenu()
                    }
                    else if(position==3){
                        showViewObjects()
                        binding.selectTextView.setText("Select Ingredient")
                        addIngredientsToDropdownMenu()
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

        //dropdown gde se bira kategorija(lokacija ili sastojak)
        binding.dropdownItemsSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?,view: View?,position: Int,id: Long
                ) {

                    if(position!=0){//sve samo da nije --choose--
                        //Fill recycler
                    //ZBOG PAGINACIJE TREBA DA SE UBACI 10 PO 10,napravio sam poziv u dao

                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //TODO
                }
            }

        binding.buttonNext.setOnClickListener {
            binding.paginationPageTextView.text=(binding.paginationPageTextView.text.toString().toInt()+1).toString()
            val pageNumber=binding.paginationPageTextView.text.toString().toInt()
            val limit=pageNumber*10
            val offset=10
            //proslediti bazi select * from foodByParemeter orderBy id limit ? offset ?
            //napravio sam poziv u foodDAO
            //ovi smrdljivi dugmici se ne vide..



        }
        binding.buttonPrevious.setOnClickListener {
            if(binding.paginationPageTextView.text.toString().toInt()>1){
                binding.paginationPageTextView.text=(binding.paginationPageTextView.text.toString().toInt()-1).toString()
                val pageNumber=binding.paginationPageTextView.text.toString().toInt()
                val limit=pageNumber*10
                val offset=10

            }
        }
    }
    private fun addIngredientsToDropdownMenu(){

    }
    private fun renderState(state: FoodState) {
        when (state) {
            is FoodState.Success -> {
                showLoadingState(false)
                categories = state.categories
                //TODO KAKO DA DOHVATIM AREAS I INGREDIENTS (u state-u ima samo categories)??
                //da se napravi neki drugi state?
            }
            is FoodState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is FoodState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is FoodState.Loading -> {
                showLoadingState(true)
            }
        }
    }
    private fun addAreasToDropdownMenu() {
        val areasNames = mutableListOf<String>()

        for (area in areas!!) {
            areasNames.add(area.strArea)
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, areasNames!!)
        binding.dropdownItemsSpinner.adapter = adapter
    }

    private fun addCategoriesToDropdownMenu() {

        val categoriesNames = mutableListOf<String>()

        for (category in categories!!) {
            categoriesNames.add(category.name)
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoriesNames!!)
        binding.dropdownItemsSpinner.adapter = adapter



    }
        private fun showViewObjects(){
            Toast.makeText(context, "PRIKAZUJEM VIEW-OVE", Toast.LENGTH_SHORT).show()
        binding.selectTextView.visibility = View.VISIBLE
        binding.dropdownItemsSpinner.visibility = View.VISIBLE
        binding.filterByTagsTextView.visibility = View.VISIBLE
        binding.editTextFilterByName.visibility = View.VISIBLE
        binding.filterByNameTextView.visibility = View.VISIBLE
        binding.editTextFilterByTags.visibility = View.VISIBLE
        binding.sortByABCCheckBox.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.VISIBLE
        binding.buttonNext.visibility = View.VISIBLE
        binding.buttonPrevious.visibility = View.VISIBLE
        binding.paginationPageTextView.visibility = View.VISIBLE

    }

    private fun showLoadingState(loading: Boolean) {
       binding.loadingPb.isVisible = loading
    }
}