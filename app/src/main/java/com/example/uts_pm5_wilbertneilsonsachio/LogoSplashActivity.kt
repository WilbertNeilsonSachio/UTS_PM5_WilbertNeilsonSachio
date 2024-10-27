package com.example.uts_pm5_wilbertneilsonsachio

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uts_pm5_wilbertneilsonsachio.databinding.ActivityLogoSplashBinding

class LogoSplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogoSplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Use the system splash screen for Android 12+ devices
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            installSplashScreen()
        }

        super.onCreate(savedInstanceState)
        binding = ActivityLogoSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Apply fade-in animation to the logo
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.logoImage.startAnimation(fadeIn)

        // Set click listener on the entire layout to proceed immediately
        binding.root.setOnClickListener {
            // Navigate directly to the MainActivity
            startActivity(Intent(this@LogoSplashActivity, MainActivity::class.java))
            finish()
        }
    }
}