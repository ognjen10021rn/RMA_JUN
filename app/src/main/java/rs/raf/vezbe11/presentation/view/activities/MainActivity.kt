package rs.raf.vezbe11.presentation.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import rs.raf.vezbe11.databinding.ActivityMainBinding
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

    }

}