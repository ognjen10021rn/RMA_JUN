package rs.raf.vezbe11.presentation.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import rs.raf.vezbe11.R
import rs.raf.vezbe11.databinding.ActivityMainBinding
import rs.raf.vezbe11.presentation.view.adapters.mainTabsAdapter.PagerAdapter
import timber.log.Timber

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        Timber.e("MainActivity")
        setContentView(binding.root)
        init()
    }
    private fun init() {
        initViewPager();
        initNavigation()

    }

    private fun initNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_1 ->  {
                    binding.viewPager.setCurrentItem(PagerAdapter.FRAGMENT_1, false)
                }
                R.id.navigation_2 ->  {
                    binding.viewPager.setCurrentItem(PagerAdapter.FRAGMENT_2, false)
                }
                R.id.navigation_3 ->  {
                    binding.viewPager.setCurrentItem(PagerAdapter.FRAGMENT_3, false)
                }
            }
            true
        }
    }
    private fun initViewPager(){
        binding.viewPager.adapter = PagerAdapter(supportFragmentManager)
    }

















}