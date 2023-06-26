package rs.raf.vezbe11.presentation.view.adapters.mainTabsAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rs.raf.vezbe11.databinding.FragmentOuterfilterfragmentcontainerBinding
import rs.raf.vezbe11.presentation.view.fragments.CategoryListFragment
import rs.raf.vezbe11.presentation.view.fragments.OuterCategoryListFragmentContainer
import rs.raf.vezbe11.presentation.view.fragments.OuterFifthTabFragment
import rs.raf.vezbe11.presentation.view.fragments.OuterFilterFragmentContainerView
import rs.raf.vezbe11.presentation.view.fragments.OuterFourthTabFragment
import rs.raf.vezbe11.presentation.view.fragments.OuterThirdTabFragment


class PagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private const val ITEM_COUNT = 5
        const val FRAGMENT_1 = 0
        const val FRAGMENT_2 = 1
        const val FRAGMENT_3 = 2
        const val FRAGMENT_4 = 3
        const val FRAGMENT_5 = 4

    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            FRAGMENT_1 -> OuterCategoryListFragmentContainer() //prikaz kategorije jela

            FRAGMENT_2 -> OuterFilterFragmentContainerView()
            FRAGMENT_3 -> OuterThirdTabFragment()
            FRAGMENT_4 -> OuterFourthTabFragment()
            else -> OuterFifthTabFragment()

        }
    }

    override fun getCount() = ITEM_COUNT

    override fun getPageTitle(position: Int): CharSequence =
        when(position) {
            FRAGMENT_1 -> "1"
            FRAGMENT_2 -> "2"
            FRAGMENT_3 -> "3"
            FRAGMENT_4 -> "4"
            else -> "5"

        }


}