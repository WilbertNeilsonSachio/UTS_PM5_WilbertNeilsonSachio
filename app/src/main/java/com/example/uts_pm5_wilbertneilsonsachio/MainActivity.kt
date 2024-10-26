package com.example.uts_pm5_wilbertneilsonsachio

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uts_pm5_wilbertneilsonsachio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEasy.setOnClickListener {
            startQuiz("easy")
        }
        binding.btnMedium.setOnClickListener {
            startQuiz("medium")
        }
        binding.btnHard.setOnClickListener {
            startQuiz("hard")
        }
        binding.btnExpert.setOnClickListener {
            startQuiz("expert")
        }
    }

    private fun startQuiz(difficulty: String) {
        val intent = Intent(this, QuizActivity::class.java)
        intent.putExtra("difficulty", difficulty)
        startActivity(intent)
    }
}