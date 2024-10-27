package com.example.uts_pm5_wilbertneilsonsachio

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.uts_pm5_wilbertneilsonsachio.databinding.ActivityWarningSplashBinding

class WarningSplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWarningSplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            installSplashScreen()
        }

        super.onCreate(savedInstanceState)
        binding = ActivityWarningSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.warningText.startAnimation(fadeIn)

        val blink = AnimationUtils.loadAnimation(this, R.anim.blink)
        binding.pressToContinueText.startAnimation(blink)

        binding.root.setOnClickListener {
            startActivity(Intent(this@WarningSplashActivity, LogoSplashActivity::class.java))
            finish()
        }
    }
}