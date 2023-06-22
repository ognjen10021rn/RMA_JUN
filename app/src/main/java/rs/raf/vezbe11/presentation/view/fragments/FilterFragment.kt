package rs.raf.vezbe11.presentation.view.fragments


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.data.models.Area
import rs.raf.vezbe11.data.models.Category
import rs.raf.vezbe11.data.models.FoodByParameter
import rs.raf.vezbe11.data.models.Ingredient
import rs.raf.vezbe11.databinding.FragmentFilterBinding
import rs.raf.vezbe11.presentation.contract.FoodContract
import rs.raf.vezbe11.presentation.view.recycler.adapter.FoodByParameterAdapter
import rs.raf.vezbe11.presentation.view.recycler.diff.FoodByParameterDiffCallback
import rs.raf.vezbe11.presentation.view.states.AreasState
import rs.raf.vezbe11.presentation.view.states.FoodByParamaterState
import rs.raf.vezbe11.presentation.view.states.FoodState
import rs.raf.vezbe11.presentation.viewmodel.FoodViewModel
import timber.log.Timber


class FilterFragment : Fragment(R.layout.fragment_filter) {

    private val foodViewModel: FoodContract.ViewModel by sharedViewModel<FoodViewModel>()
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private var categories : List<Category>?=null
    private var areas: List<Area>?=null
    private var ingredients: List<Ingredient>?=null
    private lateinit var foodByParameterAdapter: FoodByParameterAdapter
    private var currentMealList:List<FoodByParameter>?=null


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
        initListObserver()
    }

    private fun initRecycler(){
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        foodByParameterAdapter = FoodByParameterAdapter(FoodByParameterDiffCallback()){
            //Prebaciti se na fragment za prikaz detalja o hrani

            foodViewModel.fetchFoodWithId(it.id.toString())
            //ne pitaj me kako sam ovo uradio,sat vremena se mucim sa ovim(samo ga je nalepio preko vec postojeceg fragmenta)
            //i oba su bila vidljiva tako jedan preko drugog
            //Inace mora postojati spoljasni fragment container,ako zelimo da radimo replace-ovanje,i to sam postavio
            //da bude tab1,i samo u njega da replacujemo ostale fragmente
            val transaction= parentFragment?.childFragmentManager?.beginTransaction()
            transaction?.replace(R.id.outerFcvFilterFragment,MealDetailsFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()

        }
        binding.recyclerView.adapter = foodByParameterAdapter
    }
    private fun initListObserver(){
        foodViewModel.foodByParamaterState.observe(viewLifecycleOwner, Observer {

            renderList(it)
        })
    }
    private fun initObservers(){
        foodViewModel.foodState.observe(viewLifecycleOwner, Observer {

            renderStateFoods(it)
        })
        foodViewModel.areaState.observe(viewLifecycleOwner, Observer {

            renderAreaState(it)
        })
        foodViewModel.getAllAreas()
        foodViewModel.fetchAllAreas()
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

//                    if(position!=0){//sve samo da nije --choose--
//                        println(binding.dropdownItemsSpinner.selectedItem)
//                    }
                    if(binding.spinnerParameter.selectedItem.equals("Location")){
                        foodViewModel.fetchMealsByArea(binding.dropdownItemsSpinner.selectedItem.toString())
                    }
                    if(binding.spinnerParameter.selectedItem.equals("Category")){
                        foodViewModel.fetchMealsByCategory(binding.dropdownItemsSpinner.selectedItem.toString())
                    }
                    foodViewModel.getAllMealsByParamater(10, 0)

                    println(binding.spinnerParameter.selectedItem)
                    println(binding.dropdownItemsSpinner.selectedItem)
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //TODO
                }
            }

        binding.buttonNext.setOnClickListener {
            binding.paginationPageTextView.text=(binding.paginationPageTextView.text.toString().toInt()+1).toString()
            val pageNumber=binding.paginationPageTextView.text.toString().toInt()
            println(pageNumber)
            val limit=pageNumber*10
            val offset=10
            foodViewModel.getAllMealsByParamater(limit, offset)


        }
        binding.buttonPrevious.setOnClickListener {
            if(binding.paginationPageTextView.text.toString().toInt()>1){
                binding.paginationPageTextView.text=(binding.paginationPageTextView.text.toString().toInt()-1).toString()
                val pageNumber=binding.paginationPageTextView.text.toString().toInt()
                val limit=pageNumber/10
                val offset=10
                foodViewModel.getAllMealsByParamater(limit, offset)

            }
        }
        binding.editTextFilterByName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().length>=1){

                    val containsString=binding.editTextFilterByName.text.toString()
                    Timber.e(containsString)
                    val tmpList= currentMealList?.filter { it.strMeal.toLowerCase().contains(containsString) }
                    foodByParameterAdapter.submitList(tmpList)
                    Timber.e(tmpList.toString())
                }
                else{
                    //foodViewModel.getAllMealsByParamater(10, 0)
                    foodByParameterAdapter.submitList(currentMealList)
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int,count: Int,after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int,before: Int,count: Int) {
            }
        })
        binding.sortByABCCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                val tmpList= currentMealList?.sortedBy { it.strMeal }
                foodByParameterAdapter.submitList(tmpList)
            }
            else{
                foodByParameterAdapter.submitList(currentMealList)
            }
        }
    }
    private fun addIngredientsToDropdownMenu(){

    }

    private fun renderList(state: FoodByParamaterState){
        when (state) {
            is FoodByParamaterState.Success -> {
                showLoadingState(false)
                println(state.meals)
                foodByParameterAdapter.submitList(state.meals)
                currentMealList=state.meals

            }
            is FoodByParamaterState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is FoodByParamaterState.DataFetched -> {
                showLoadingState(false)

            }
            is FoodByParamaterState.Loading -> {
                showLoadingState(true)
            }
        }
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
    private fun renderAreaState(state: AreasState) {
        when (state) {
            is AreasState.Success -> {
                showLoadingState(false)
                areas = state.areas
            }
            is AreasState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is AreasState.DataFetched -> {
                showLoadingState(false)

            }
            is AreasState.Loading -> {
                showLoadingState(true)
            }
        }
    }
    private fun renderStateFoodsByParameter(state: FoodByParamaterState) {
        when (state) {
            is FoodByParamaterState.Success -> {
                showLoadingState(false)
            }
            is FoodByParamaterState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is FoodByParamaterState.DataFetched -> {
                showLoadingState(false)

            }
            is FoodByParamaterState.Loading -> {
                showLoadingState(true)
            }
        }
    }
    private fun addAreasToDropdownMenu() {
        val areasNames = mutableListOf<String>()
        //println(areas)
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