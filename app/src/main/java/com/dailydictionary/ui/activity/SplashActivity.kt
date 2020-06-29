package com.dailydictionary.ui.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.dailydictionary.R
import com.dailydictionary.pref.UserPref


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            checkTheme()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500)
    }

    private fun checkTheme() {
        when (UserPref(this).darkMode) {
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            }
            else -> {
                UserPref(this).darkMode = isUsingNightModeResources()
            }
        }
    }

    private fun isUsingNightModeResources(): Int {
        return when (resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> 1
            Configuration.UI_MODE_NIGHT_NO -> 0
            Configuration.UI_MODE_NIGHT_UNDEFINED -> 0
            else -> 0
        }
    }
}