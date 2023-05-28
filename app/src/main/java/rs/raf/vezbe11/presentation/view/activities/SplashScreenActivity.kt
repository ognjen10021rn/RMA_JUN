package rs.raf.vezbe11.presentation.view.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.window.SplashScreen
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent
import rs.raf.vezbe11.databinding.ActivitySplashscreenBinding
import timber.log.Timber


class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashscreenBinding
    private val sharedPreferences: SharedPreferences by inject()

    private val splashScreenDelay: Long = 3000 // Delay duration in milliseconds
    private val handler = Handler() //koristi se za sleep(Thread.sleep nece raditi
                                    //kako treba.Sleepujemo da bismo simulirali ucitavanje)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadSplashScreen()
    }

    private fun loadSplashScreen() {
        handler.postDelayed({
            val loggedIn: Boolean = sharedPreferences.getBoolean("login", false)
            if (loggedIn) {
                // Start MainActivity if the user is successfully logged in
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // Start LoginActivity if the user is not logged in
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, splashScreenDelay)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}