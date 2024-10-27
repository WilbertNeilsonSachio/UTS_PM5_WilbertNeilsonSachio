package com.example.uts_pm5_wilbertneilsonsachio

import android.content.Intent
import android.os.Bundle
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
    private lateinit var questions: List<QuizQuestion>  // List of questions for the selected difficulty

    private var score: Int = 0      // Track the user’s score
    private var questionIndex: Int = 0  // Track the current question number

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the difficulty level from the intent
        val difficulty = intent.getStringExtra("difficulty") ?: "easy"

        // Load the appropriate questions based on difficulty
        questions = when (difficulty) {
            "medium" -> mediumQuestions
            "hard" -> hardQuestions
            "expert" -> expertQuestions
            else -> easyQuestions  // Default to easy
        }

        // Load the first question
        loadQuestion()

        // Set click listeners for the answer buttons
        binding.option1.setOnClickListener { checkAnswer(binding.option1.text.toString()) }
        binding.option2.setOnClickListener { checkAnswer(binding.option2.text.toString()) }
        binding.option3.setOnClickListener { checkAnswer(binding.option3.text.toString()) }
        binding.option4.setOnClickListener { checkAnswer(binding.option4.text.toString()) }
    }

    // Function to load the current question
    private fun loadQuestion() {
        val currentQuestion = questions[questionIndex]

        binding.flagImage.setImageResource(currentQuestion.imageResId)

        val shuffledOptions = currentQuestion.options.shuffled()
        binding.option1.text = shuffledOptions[0]
        binding.option2.text = shuffledOptions[1]
        binding.option3.text = shuffledOptions[2]
        binding.option4.text = shuffledOptions[3]
    }

    private fun checkAnswer(selectedAnswer: String) {
        val correctAnswer = questions[questionIndex].correctAnswer


        if (selectedAnswer == correctAnswer) {
            score++
        }

        questionIndex++
        if (questionIndex < questions.size) {
            loadQuestion()
        } else {
            showResult()
        }
    }

    private fun showResult() {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("score", score)
            putExtra("totalQuestions", questions.size)
        }
        startActivity(intent)
        finish()
    }

    // Question sets
    private val easyQuestions = listOf(
        QuizQuestion(R.drawable.flag_usa, "USA", listOf("USA", "Canada", "UK", "Germany")),
        QuizQuestion(R.drawable.flag_france, "France", listOf("France", "Italy", "Spain", "Portugal")),
//        QuizQuestion(R.drawable.flag_spain, "Spain", listOf("UK", "Canada", "Spain", "Portugal")),
//        QuizQuestion(R.drawable.flag_japan, "Japan", listOf("Japan", "South Korea", "China", "Bangladesh")),
//        QuizQuestion(R.drawable.flag_germany, "Japan", listOf("Belgium", "Argentina", "Australia", "Germany"))
    )

    private val mediumQuestions = listOf(
        QuizQuestion(R.drawable.flag_singapore, "Singapore", listOf("Singapore", "Monaco", "Poland", "Indonesia")),
        QuizQuestion(R.drawable.flag_indonesia, "Indonesia", listOf("Indonesia", "Malaysia", "Thailand", "Vietnam")),
//        QuizQuestion(R.drawable.flag_newZealand, "New Zealand", listOf("New Zealand", "UK", "Australia", "USA")),
//        QuizQuestion(R.drawable.flag_sriLanka, "Sri Lanka", listOf("Sri Lanka", "Maldives", "India", "Bhutan")),
//        QuizQuestion(R.drawable.flag_panama, "Panama", listOf("Panama", "Belize", "Jamaica", "Cuba"))
    )

    private val hardQuestions = listOf(
        QuizQuestion(R.drawable.flag_guatemala, "Guatemala", listOf("Nicaragua", "Guatemala", "El Salvador", "Honduras")),
        QuizQuestion(R.drawable.flag_southafrica, "South Africa", listOf("South Africa", "Kenya", "Nigeria", "Ghana")),
//        QuizQuestion(R.drawable.flag_finland, "Finland", listOf("Finland", "Norway", "Sweden", "Iceland")),
//        QuizQuestion(R.drawable.flag_kuwait, "Kuwait", listOf("Kuwait", "Jordan", "Sudan", "Iraq")),
//        QuizQuestion(R.drawable.flag_papuaNewGuinea, "Papua New Guinea", listOf("Papua New Guinea", "Solomon Islands", "Guinea", "Cape Verde"))
    )

    private val expertQuestions = listOf(
        QuizQuestion(R.drawable.flag_trinidadandtobago, "Trinidad and Tobago", listOf("Trinidad And Tobago", "Tanzania", "St. Kitts and Nevis", "Bermuda")),
        QuizQuestion(R.drawable.flag_vanuatu, "Vanuatu", listOf("Vanuatu", "Fiji", "Tonga", "Samoa")),
//        QuizQuestion(R.drawable.flag_gabon, "Gabon", listOf("Gabon", "Guinea", "Cameroon", "The Gambia")),
//        QuizQuestion(R.drawable.flag_belarus, "Belarus", listOf("Belarus", "Ukraine", "Tajikistan", "Bulgaria")),
//        QuizQuestion(R.drawable.flag_seychelles, "Seychelles", listOf("Seychelles", "Comoros", "Mauritius", "Mauritania"))
    )
}