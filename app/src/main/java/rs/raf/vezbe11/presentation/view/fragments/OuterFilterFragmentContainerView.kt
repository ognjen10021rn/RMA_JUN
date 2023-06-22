package rs.raf.vezbe11.presentation.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import rs.raf.vezbe11.R

class OuterFilterFragmentContainerView:Fragment(R.layout.fragment_outerfilterfragmentcontainer) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInnerFragment()
    }

    override fun onResume() {
        super.onResume()
        childFragmentManager
            .beginTransaction()
            .replace(R.id.outerFcvFilterFragment, FilterFragment())
            .commit()
    }
    private fun initInnerFragment() {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.outerFcvFilterFragment, FilterFragment())
            .commit()
    }



}