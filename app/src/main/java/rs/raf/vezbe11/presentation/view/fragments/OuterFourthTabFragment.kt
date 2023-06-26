package rs.raf.vezbe11.presentation.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import rs.raf.vezbe11.R

class OuterFourthTabFragment : Fragment(R.layout.fragment_outerfourthtab) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInnerFragment()
    }

    override fun onResume() {
        super.onResume()
        childFragmentManager
            .beginTransaction()
            .replace(R.id.outerFcvFourthTabFragment, StatisticFragment()) //TODO promeniti
            .commit()
    }
    private fun initInnerFragment() {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.outerFcvFourthTabFragment, StatisticFragment())
            .commit()
    }


}