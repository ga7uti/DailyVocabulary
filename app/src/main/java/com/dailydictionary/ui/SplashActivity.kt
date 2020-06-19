package com.dailydictionary.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.service.autofill.UserData
import androidx.appcompat.app.AppCompatActivity
import com.dailydictionary.R


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed(Runnable {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500)
    }
}