package com.example.uts_pm5_wilbertneilsonsachio

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uts_pm5_wilbertneilsonsachio.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val score = intent.getIntExtra("score", 0)
        val totalQuestions = intent.getIntExtra("totalQuestions", 0)
        val passed = intent.getBooleanExtra("passed", false)

        displayResultMessage(passed, score, totalQuestions)
        playResultSound(passed)


        binding.btnShare.setOnClickListener {
            shareResult(score, totalQuestions)
        }

        binding.btnPlayAgain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun playResultSound(passed: Boolean) {
        val soundRes = if (passed) R.raw.passed else R.raw.failed
        mediaPlayer = MediaPlayer.create(this, soundRes)
        mediaPlayer?.start()
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun shareResult(score: Int, totalQuestions: Int) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "I scored $score out of $totalQuestions in the Flag Quiz!")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, null))
    }

    private fun displayResultMessage(passed: Boolean, score: Int, totalQuestions: Int) {
        val message = if (passed) {
            getString(R.string.passed_message, score, totalQuestions)
        } else {
            getString(R.string.failed_message, score, totalQuestions)
        }
        binding.tvScore.text = message
    }
}