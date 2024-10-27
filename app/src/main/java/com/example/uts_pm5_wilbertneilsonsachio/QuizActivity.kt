package com.example.uts_pm5_wilbertneilsonsachio

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uts_pm5_wilbertneilsonsachio.databinding.ActivityQuizBinding

data class QuizQuestion(
    val imageResId: Int,
    val correctAnswer: String,
    val options: List<String>
)

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private lateinit var questions: List<QuizQuestion>

    private var score: Int = 0
    private var questionIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val difficulty = intent.getStringExtra("difficulty") ?: "easy"

        questions = when (difficulty) {
            "medium" -> mediumQuestions.shuffled()
            "hard" -> hardQuestions.shuffled()
            "expert" -> expertQuestions.shuffled()
            else -> easyQuestions.shuffled()
        }

        binding.progressBar.max = questions.size

        loadQuestion()

        binding.option1.setOnClickListener { checkAnswer(binding.option1.text.toString()) }
        binding.option2.setOnClickListener { checkAnswer(binding.option2.text.toString()) }
        binding.option3.setOnClickListener { checkAnswer(binding.option3.text.toString()) }
        binding.option4.setOnClickListener { checkAnswer(binding.option4.text.toString()) }
    }

    private fun loadQuestion() {
        setButtonsEnabled(true)

        val currentQuestion = questions[questionIndex]

        binding.flagImage.setImageResource(currentQuestion.imageResId)

        val shuffledOptions = currentQuestion.options.shuffled()
        binding.option1.text = shuffledOptions[0]
        binding.option2.text = shuffledOptions[1]
        binding.option3.text = shuffledOptions[2]
        binding.option4.text = shuffledOptions[3]

        binding.questionCounter.text = "${questionIndex + 1}/${questions.size}"

        binding.progressBar.progress = questionIndex + 1
    }

    private fun playSound(isCorrect: Boolean) {
        val soundRes = if (isCorrect) R.raw.correct else R.raw.wrong
        val mediaPlayer = MediaPlayer.create(this, soundRes)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { it.release() }
    }

    private fun checkAnswer(selectedAnswer: String) {

        val correctAnswer = questions[questionIndex].correctAnswer

        val selectedButton = when (selectedAnswer) {
            binding.option1.text.toString() -> binding.option1
            binding.option2.text.toString() -> binding.option2
            binding.option3.text.toString() -> binding.option3
            binding.option4.text.toString() -> binding.option4
            else -> null
        }

        if (selectedAnswer == correctAnswer) {
            score++
            playSound(true)
            val popUp = AnimationUtils.loadAnimation(this, R.anim.popup)
            selectedButton?.startAnimation(popUp)
        } else {
            playSound(false)
            val shake = AnimationUtils.loadAnimation(this, R.anim.shake)
            selectedButton?.startAnimation(shake)
        }

        setButtonsEnabled(false)

        selectedButton?.postDelayed({
            questionIndex++
            if (questionIndex < questions.size) {
                loadQuestion()
            } else {
                showResult()
            }
        }, 1000)
    }

    private fun setButtonsEnabled(enabled: Boolean) {
        binding.option1.isEnabled = enabled
        binding.option2.isEnabled = enabled
        binding.option3.isEnabled = enabled
        binding.option4.isEnabled = enabled
    }

    private fun showResult() {
        val passed = score >= (questions.size * 0.7)

        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("score", score)
            putExtra("totalQuestions", questions.size)
            putExtra("passed", passed)
        }
        startActivity(intent)
        finish()
    }

    private val easyQuestions = listOf(
        QuizQuestion(R.drawable.flag_usa, "USA", listOf("USA", "Canada", "UK", "Germany")),
        QuizQuestion(R.drawable.flag_france, "France", listOf("France", "Italy", "Spain", "Portugal")),
        QuizQuestion(R.drawable.flag_spain, "Spain", listOf("UK", "Canada", "Spain", "Portugal")),
        QuizQuestion(R.drawable.flag_japan, "Japan", listOf("Japan", "South Korea", "China", "Bangladesh")),
        QuizQuestion(R.drawable.flag_germany, "Germany", listOf("Belgium", "Argentina", "Australia", "Germany")),
        QuizQuestion(R.drawable.flag_china, "China", listOf("China", "Japan", "South Korea", "Vietnam")),
        QuizQuestion(R.drawable.flag_switzerland, "Switzerland", listOf("Switzerland", "Denmark", "Austria", "Norway")),
        QuizQuestion(R.drawable.flag_russia, "Russia", listOf("Russia", "Ukraine", "Kazakhstan", "Belarus")),
        QuizQuestion(R.drawable.flag_saudiarabia, "Saudi Arabia", listOf("Saudi Arabia", "Kuwait", "Iraq", "Qatar")),
        QuizQuestion(R.drawable.flag_ukraine, "Ukraine", listOf("Ukraine", "Russia", "Belarus", "Georgia"))
    )

    private val mediumQuestions = listOf(
        QuizQuestion(R.drawable.flag_singapore, "Singapore", listOf("Singapore", "Monaco", "Poland", "Indonesia")),
        QuizQuestion(R.drawable.flag_indonesia, "Indonesia", listOf("Indonesia", "Malaysia", "Thailand", "Vietnam")),
        QuizQuestion(R.drawable.flag_newzealand, "New Zealand", listOf("New Zealand", "UK", "Australia", "USA")),
        QuizQuestion(R.drawable.flag_srilanka, "Sri Lanka", listOf("Sri Lanka", "Maldives", "India", "Bhutan")),
        QuizQuestion(R.drawable.flag_panama, "Panama", listOf("Panama", "Belize", "Jamaica", "Cuba")),
        QuizQuestion(R.drawable.flag_nigeria, "Nigeria", listOf("Nigeria", "Ghana", "Cameroon", "Kenya")),
        QuizQuestion(R.drawable.flag_croatia, "Croatia", listOf("Croatia", "Serbia", "Slovenia", "Montenegro")),
        QuizQuestion(R.drawable.flag_qatar, "Qatar", listOf("Qatar", "Bahrain", "Oman", "UAE")),
        QuizQuestion(R.drawable.flag_pakistan, "Pakistan", listOf("Pakistan", "Bangladesh", "Afghanistan", "India")),
        QuizQuestion(R.drawable.flag_chile, "Chile", listOf("Chile", "Peru", "Argentina", "Bolivia"))
    )

    private val hardQuestions = listOf(
        QuizQuestion(R.drawable.flag_guatemala, "Guatemala", listOf("Nicaragua", "Guatemala", "El Salvador", "Honduras")),
        QuizQuestion(R.drawable.flag_southafrica, "South Africa", listOf("South Africa", "Kenya", "Nigeria", "Ghana")),
        QuizQuestion(R.drawable.flag_finland, "Finland", listOf("Finland", "Norway", "Sweden", "Iceland")),
        QuizQuestion(R.drawable.flag_kuwait, "Kuwait", listOf("Kuwait", "Jordan", "Sudan", "Iraq")),
        QuizQuestion(R.drawable.flag_papuanewguinea, "Papua New Guinea", listOf("Papua New Guinea", "Solomon Islands", "Guinea", "Cape Verde")),
        QuizQuestion(R.drawable.flag_armenia, "Armenia", listOf("Armenia", "Azerbaijan", "Georgia", "Turkey")),
        QuizQuestion(R.drawable.flag_ethiopia, "Ethiopia", listOf("Ethiopia", "Somalia", "Sudan", "Djibouti")),
        QuizQuestion(R.drawable.flag_paraguay, "Paraguay", listOf("Paraguay", "Uruguay", "Argentina", "Chile")),
        QuizQuestion(R.drawable.flag_haiti, "Haiti", listOf("Haiti", "Dominican Republic", "Cuba", "Jamaica")),
        QuizQuestion(R.drawable.flag_maldives, "Maldives", listOf("Maldives", "Seychelles", "Mauritius", "Sri Lanka"))
    )

    private val expertQuestions = listOf(
        QuizQuestion(R.drawable.flag_trinidadandtobago, "Trinidad And Tobago", listOf("Trinidad And Tobago", "Tanzania", "St. Kitts and Nevis", "Bermuda")),
        QuizQuestion(R.drawable.flag_vanuatu, "Vanuatu", listOf("Vanuatu", "Fiji", "Tonga", "Samoa")),
        QuizQuestion(R.drawable.flag_gabon, "Gabon", listOf("Gabon", "Guinea", "Cameroon", "The Gambia")),
        QuizQuestion(R.drawable.flag_belarus, "Belarus", listOf("Belarus", "Ukraine", "Tajikistan", "Bulgaria")),
        QuizQuestion(R.drawable.flag_seychelles, "Seychelles", listOf("Seychelles", "Comoros", "Mauritius", "Mauritania")),
        QuizQuestion(R.drawable.flag_namibia, "Namibia", listOf("Namibia", "Botswana", "Angola", "Zimbabwe")),
        QuizQuestion(R.drawable.flag_rwanda, "Rwanda", listOf("Rwanda", "Burundi", "Uganda", "Congo")),
        QuizQuestion(R.drawable.flag_suriname, "Suriname", listOf("Suriname", "Guyana", "French Guiana", "Belize")),
        QuizQuestion(R.drawable.flag_guyana, "Guyana", listOf("Guyana", "Suriname", "Jamaica", "Haiti")),
        QuizQuestion(R.drawable.flag_sierraleone, "Sierra Leone", listOf("Gabon", "The Gambia", "Sierra Leone", "Equatorial Guinea"))
    )
}