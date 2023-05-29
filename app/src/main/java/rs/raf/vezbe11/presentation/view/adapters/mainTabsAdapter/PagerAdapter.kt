package rs.raf.vezbe11.presentation.view.adapters.mainTabsAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rs.raf.vezbe11.presentation.view.fragments.CategoryListFragment

class PagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private const val ITEM_COUNT = 3
        const val FRAGMENT_1 = 0
        const val FRAGMENT_2 = 1
        const val FRAGMENT_3 = 2
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            FRAGMENT_1 -> CategoryListFragment() //prikaz kategorije jela
            FRAGMENT_2 -> CategoryListFragment()
            else -> CategoryListFragment()
        }
    }

    override fun getCount() = ITEM_COUNT

    override fun getPageTitle(position: Int): CharSequence =
        when(position) {
            FRAGMENT_1 -> "1"
            FRAGMENT_2 -> "2"
            else -> "3"
        }


}