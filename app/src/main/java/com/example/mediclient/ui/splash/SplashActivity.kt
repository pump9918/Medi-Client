package com.example.mediclient.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.mediclient.R
import com.example.mediclient.databinding.ActivitySplashBinding
import com.example.mediclient.ui.chatbot.ChatbotActivity
import com.example.mediclient.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
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
        startActivity(Intent(this, ChatbotActivity::class.java))
        finish()
    }
}
