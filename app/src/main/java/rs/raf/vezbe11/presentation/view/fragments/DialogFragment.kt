package rs.raf.vezbe11.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.databinding.FragmentCategorylistBinding
import rs.raf.vezbe11.databinding.FragmentFooddialogBinding
import rs.raf.vezbe11.presentation.contract.FoodContract
import rs.raf.vezbe11.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.vezbe11.presentation.viewmodel.FoodViewModel

class DialogFragment : Fragment(R.layout.fragment_fooddialog) {

    private var _binding: FragmentFooddialogBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var categoryAdapter: CategoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFooddialogBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    private fun init(){

    }
}