package com.example.mediclient

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.mediclient.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        loadSplashScreen()
    }

    private fun loadSplashScreen() {
        lifecycleScope.launch {
            delay(1500L)
            moveToSign()
            finish()
        }
    }

    private fun moveToSign() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}