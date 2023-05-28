package rs.raf.vezbe11.presentation.view.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent
import rs.raf.vezbe11.databinding.ActivityLoginBinding
import rs.raf.vezbe11.databinding.ActivitySplashscreenBinding
import timber.log.Timber

class LoginActivity() : AppCompatActivity(),KoinComponent {

    private lateinit var binding: ActivityLoginBinding
    private val sharedPreferences: SharedPreferences by inject()//bice dohvacen preko depen.inj.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.e("LoginActivity.onCreate")
        init()
    }
    private fun init(){
        initView();
        initListeners();
    }

    private fun initView(){
        binding.warningPassword.visibility = android.view.View.INVISIBLE
        binding.warningUsername.visibility = android.view.View.INVISIBLE

    }

    private fun initListeners(){
        binding.submitButton.setOnClickListener {
            val username = binding.usernameInput.text.toString()
            val password = binding.passwordInput.text.toString()
            //Hardkodovan user,nama se u proslom projektu trazilo da imamo usere u odvojenom fajlu
            //ovde se to ne trazi
            if(validateInputs(username,password)){
                if(username == "admin" && password == "admin"){
                    sharedPreferences.edit().putBoolean("login", true).apply() //TODO:na logoutu staviti false
                    sharedPreferences.edit().putString("username", username).apply()
                    sharedPreferences.edit().putString("password", password).apply()

                    Toast.makeText(this, "Uspesno ste se ulogovali", Toast.LENGTH_SHORT).show()

                    //pokrecemo mainActivity ako se korisnik uspesno ulogovao
                    Intent(this, MainActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }

                }
            }
        }
    }
    //trazi se u zadatku samo da password bude veci od 4 karaktera
    private fun validateInputs(username: String, password: String): Boolean{


        if(username.isEmpty()){
            binding.warningUsername.visibility = android.view.View.VISIBLE
            return false
        }
        if(password.isEmpty()){
            binding.warningPassword.visibility = android.view.View.VISIBLE
            return false
        }

       if(password.length<4){
              binding.warningPassword.visibility = android.view.View.VISIBLE
           return false
       }

    return true;
    }
}