package com.example.uts_pm5_wilbertneilsonsachio

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uts_pm5_wilbertneilsonsachio.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the score and total number of questions from the Intent
        val score = intent.getIntExtra("score", 0)
        val totalQuestions = intent.getIntExtra("totalQuestions", 0)

        // Display the result in the TextView
        binding.tvScore.text = getString(R.string.result_text, score, totalQuestions)

        // Set click listener for the Share button
        binding.btnShare.setOnClickListener {
            shareResult(score, totalQuestions)
        }

        // Set click listener for the Play Again button (optional)
        binding.btnPlayAgain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish() // Close the ResultActivity
        }
    }

    // Function to share the quiz result via other apps
    private fun shareResult(score: Int, totalQuestions: Int) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "I scored $score out of $totalQuestions in the Flag Quiz!")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, null))
    }
}