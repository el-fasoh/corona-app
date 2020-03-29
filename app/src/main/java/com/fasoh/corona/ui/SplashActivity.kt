package com.fasoh.corona.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.fasoh.corona.MainActivity
import com.fasoh.corona.R
import com.fasoh.corona.extentions.show
import com.fasoh.corona.repositories.SettingsRepository
import com.heetch.countrypicker.CountryPickerCallbacks
import com.heetch.countrypicker.CountryPickerDialog
import kotlinx.android.synthetic.main.activity_splash.*
import org.koin.android.ext.android.inject


class SplashActivity : AppCompatActivity() {

    private val settingsRepository: SettingsRepository by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        val startAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_out)
        imageView.show()
        imageView.startAnimation(startAnimation)

        Handler().postDelayed({
            if (settingsRepository.defaultCountry.isNullOrEmpty()) {
                val countryPicker =
                    CountryPickerDialog(this,
                        CountryPickerCallbacks { country, flagResId ->
                            settingsRepository.defaultCountry = country.isoCode
                            settingsRepository.defaultDial =country.dialingCode
                            startMain()
                        })
                countryPicker.setTitle("Select default country")
                countryPicker.show()
            }else{
               startMain()
            }
        }, 4000)


    }

    private fun startMain(){
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}
