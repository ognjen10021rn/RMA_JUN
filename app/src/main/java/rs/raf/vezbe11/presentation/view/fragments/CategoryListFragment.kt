package rs.raf.vezbe11.presentation.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import rs.raf.vezbe11.databinding.FragmentCategorylistBinding
import rs.raf.vezbe11.presentation.view.recycler.adapter.CategoryAdapter

class CategoryListFragment :Fragment(){

    private lateinit var binding: FragmentCategorylistBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCategorylistBinding.inflate(layoutInflater)
        init()
    }
    private fun init() {
        initRecycler()
    }

    private fun initRecycler(){
        binding.recyclerView.adapter = CategoryAdapter()



    }

}