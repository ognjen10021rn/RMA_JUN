package rs.raf.vezbe11.presentation.view.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.window.SplashScreen
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent
import rs.raf.vezbe11.databinding.ActivitySplashscreenBinding
import timber.log.Timber


class SplashScreenActivity : AppCompatActivity(),KoinComponent{
    private lateinit var binding: ActivitySplashscreenBinding
    private val sharedPreferences: SharedPreferences by inject()//bice dohvacen preko depen.inj.
    //Morao sam da prebacim ovde,zato sto pocetni aktiviti aplikacija mora da ima prazan konstruktor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadSplashScreen()
    }
    private fun loadSplashScreen() {

        val loggedIn: Boolean = sharedPreferences.getBoolean("login", false)
        Thread.sleep(2000);//cisto da splashscreen traje malo
        if (loggedIn) {
            //pokrecemo mainActivity ako se korisnik uspesno ulogovao
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        } else {
            //pokrecemo loginActivity ako se korisnik nije ulogovao
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }

    }


}