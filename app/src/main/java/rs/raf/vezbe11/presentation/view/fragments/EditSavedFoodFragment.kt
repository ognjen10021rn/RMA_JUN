package rs.raf.vezbe11.presentation.view.fragments

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.ext.getOrCreateScope
import rs.raf.vezbe11.R
import rs.raf.vezbe11.data.models.Category
import rs.raf.vezbe11.data.models.Nutrition
import rs.raf.vezbe11.data.models.SavedFood
import rs.raf.vezbe11.data.models.entities.SavedFoodEntity
import rs.raf.vezbe11.databinding.FragmentEditsavedfoodBinding
import rs.raf.vezbe11.presentation.contract.FoodContract
import rs.raf.vezbe11.presentation.contract.NutritionContract
import rs.raf.vezbe11.presentation.view.states.FoodState
import rs.raf.vezbe11.presentation.view.states.NutritionState
import rs.raf.vezbe11.presentation.view.states.SavedFoodState
import rs.raf.vezbe11.presentation.viewmodel.FoodViewModel
import rs.raf.vezbe11.presentation.viewmodel.NutritionViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class EditSavedFoodFragment : Fragment(R.layout.fragment_editsavedfood) {

    private val foodViewModel: FoodContract.ViewModel by sharedViewModel<FoodViewModel>()
    private val nutritionViewModel: NutritionContract.ViewModel by sharedViewModel<NutritionViewModel>()
    private var _binding: FragmentEditsavedfoodBinding? = null

    private val binding get() = _binding!!
    private var categories : List<Category>?=null
    private var food: SavedFood? = null
    private var calories: List<Nutrition>? = null
    private var listCalories: List<Nutrition>? = null
    private val permissions = arrayOf(Manifest.permission.CAMERA)

    companion object{
    private const val CAMERA_PERMISSION_REQUEST_CODE=1
    private const val CAMERA_REQUEST_CODE=2

    }



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
        initObservers()
        initListeners()
        initPermissions()
    }

    private fun initPermissions() {



        binding.imageView.setOnClickListener {
            if (checkCameraPermission()) {
                Toast.makeText(requireContext(), "Permission already granted", Toast.LENGTH_SHORT).show()
                //catchFoodPhoto()
                catchFoodPhoto2()

            } else {
                requestCameraPermission() // ako prihvati pokrenuce se catchFoodPhotyo
                if(checkCameraPermission()){
                    catchFoodPhoto2()
                }
            }
        }
    }
    private fun catchFoodPhoto2(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_PERMISSION_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) // Use external storage directory
            val imageFileName = "IMG_$timeStamp.jpg"
            val photoFile = createImageFile(storageDir!!, imageFileName)

            val images:Bitmap=data?.extras?.get("data") as Bitmap
            binding.imageView.setImageBitmap(images)


            if (photoFile != null) {
                val imageUri = FileProvider.getUriForFile(
                    requireContext(),
                    requireContext().packageName + ".fileprovider",
                    photoFile
                )
                binding.imageView.setImageDrawable(null)

                val savedEntity = SavedFood(food!!.id,
                    food!!.name,
                    food!!.strInstructions,
                    food!!.strCategory,
                    food!!.dayOfMonth,
                    food!!.month,
                    food!!.year,
                    food!!.calories,
                    food!!.strMealType,
                    food!!.strMealThumb,
                    food!!.strYoutube,
                    food!!.strIngredient1,
                    food!!.strIngredient2,
                    food!!.strIngredient3,
                    food!!.strIngredient4,
                    food!!.strIngredient5,
                    food!!.strMeasure1,
                    food!!.strMeasure2,
                    food!!.strMeasure3,
                    food!!.strMeasure4,
                    food!!.strMeasure5,
                    imageUri.toString(),
                 )
                foodViewModel.updateSavedFood(savedEntity)


                Toast.makeText(requireContext(), imageUri.toString(), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Failed to save the photo", Toast.LENGTH_SHORT).show()
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkCameraPermission(): Boolean {
        val cameraPermission = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.CAMERA
        )
        return cameraPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(), permissions, CAMERA_PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                catchFoodPhoto2()
            }
        }
    }


    private fun createImageFile(storageDir: File, imageFileName: String): File {
        val folder = File(storageDir, "capturedImages")
        if (!folder.exists()) {
            folder.mkdirs()
        }

        val imageFile = File(folder, imageFileName)
        val imageFilePath = imageFile.absolutePath

        // Save the image file if needed
        // ...

        if(!imageFile.exists()){
            imageFile.createNewFile()
        }
        return imageFile
    }

    private fun initObservers(){
        foodViewModel.foodState.observe(viewLifecycleOwner, Observer {
            renderStateFoods(it)
        })
        foodViewModel.savedFoodState.observe(viewLifecycleOwner, Observer {
            renderStateSavedFoods(it)
        })
        nutritionViewModel.nutritionState.observe(viewLifecycleOwner, Observer {
            renderNutrition(it)
        })

        food = foodViewModel.getCurrentSavedFood()
        foodViewModel.getSavedFoodById(food!!.id.toString())
        foodViewModel.getAllCategories()
    }
    private fun initListeners(){
        binding.buttonSave.setOnClickListener {

                food!!.name = binding.nameEditText.text.toString()
                food!!.strInstructions = binding.editTextInstructions.text.toString()
                //food!!.strMealThumb = binding.imageView.????
                food!!.strMealType = binding.dropdownMealType.selectedItem.toString()
                food!!.strCategory = binding.categoryDropdown.selectedItem.toString()
                foodViewModel.updateSavedFood(food!!)

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
                val queryString = "100g ${food!!.strIngredient1}, 100g ${food!!.strIngredient2}, " +
                        "100g ${food!!.strIngredient3}, 100g ${food!!.strIngredient4}, " +
                        "100g ${food!!.strIngredient5}"
                nutritionViewModel.fetchAllNutritionByQuery(queryString)
                nutritionViewModel.getAllNutrition()
                addCategoriesToDropdownMenu()
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
    //private var isFetched: Boolean = false
    private fun renderNutrition(state: NutritionState){
        when (state) {
            is NutritionState.Success -> {
                showLoadingState(false)
                    binding.calorieCountTv.setText(food!!.calories.toString())


                    listCalories = state.nutrition
                    var stringBuilder: String? = "Calories per 100g:\n"
                    for(cal in listCalories!!){
                        stringBuilder+= "Ingridient: ${cal.name}, Calories: ${cal.calories}\n"
                    }
                    binding.allCalories.setText(stringBuilder)
            }
            is NutritionState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is NutritionState.Loading -> {
                showLoadingState(true)
            }
        }
    }
    private fun renderStateSavedFoods(state: SavedFoodState){
        when (state) {
            is SavedFoodState.Success2 -> {
                showLoadingState(false)
                food = state.savedFood
                binding.nameEditText.setText(food!!.name)
                binding.editTextInstructions.setText(food!!.strInstructions)
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_MONTH, food!!.dayOfMonth)
                binding.datePicker.updateDate(food!!.year, food!!.month, food!!.dayOfMonth)
                //nutritionViewModel.fetchAllNutritionByQuery(queryString)

                Toast.makeText(context, "Radim render", Toast.LENGTH_SHORT).show()
                Glide.with(this)
                    .load(food!!.strMealThumb)
                    .into(binding.imageView)
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


    private fun showLoadingState(loading: Boolean) {
        binding.progressBar2.isVisible = loading
    }






}