package rs.raf.vezbe11.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.databinding.FragmentSavedmealslistBinding
import rs.raf.vezbe11.presentation.contract.FoodContract
import rs.raf.vezbe11.presentation.view.recycler.adapter.SavedFoodAdapter
import rs.raf.vezbe11.presentation.view.recycler.diff.SavedFoodDiffCallback
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
            //TODO setovati selektovanu hranu "setSelectedSavedFood"
            val transaction=parentFragment?.childFragmentManager?.beginTransaction()
            transaction?.replace(R.id.outerFcvThirdTabFragment,EditSavedFoodFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()




        },
        {
            //onButtonClicked
            //TODO obrisati obrok iz liste sacuvanih obroka




        })





    }
    private fun initObservers(){
        //TODO dodati listu sacuvane hrane u viewmodelu

    }


}