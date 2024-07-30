package com.example.fitnessquizapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class QuizFragment : Fragment() {

    private var questionIndex = 0
    private val answers = mutableListOf<String>()

    private val questions = listOf(
        "How many days a week should you exercise for general fitness?",
        "What is a good source of protein for muscle building?",
        "Which type of exercise is best for cardiovascular health?",
        "How long should you warm up before a workout?",
        "What is the recommended duration of a moderate-intensity workout session?",
        "How often should you include strength training in your routine?",
        "What is an example of a high-intensity interval training exercise?",
        "How much water should you drink before, during, and after exercise?",
        "What is the benefit of stretching before exercise?",
        "What is a common mistake people make when trying to lose weight?"
    )

    private val options = listOf(
        listOf("1 day", "3 days", "5 days", "7 days"),
        listOf("Chicken", "Vegetables", "Fruit", "Bread"),
        listOf("Running", "Yoga", "Pilates", "Weightlifting"),
        listOf("5 minutes", "10 minutes", "15 minutes", "20 minutes"),
        listOf("20 minutes", "30 minutes", "45 minutes", "1 hour"),
        listOf("Once a week", "Twice a week", "Three times a week", "Every day"),
        listOf("Burpees", "Walking", "Swimming", "Cycling"),
        listOf("500ml", "1 litre", "1.5 litres", "2 litres"),
        listOf("Improve flexibility", "Burn calories", "Build muscle", "Increase endurance"),
        listOf("Not exercising enough", "Eating too little", "Over-exercising", "Skipping meals")
    )

    private val correctAnswers = listOf(
        "5 days",
        "Chicken",
        "Running",
        "10 minutes",
        "30 minutes",
        "Three times a week",
        "Burpees",
        "2 litres",
        "Improve flexibility",
        "Over-exercising"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_quiz, container, false)

        val questionText: TextView = root.findViewById(R.id.question_text)
        val radioGroup: RadioGroup = root.findViewById(R.id.radio_group)
        val submitButton: Button = root.findViewById(R.id.submit_button)

        fun updateQuestion() {
            val question = questions[questionIndex]
            val optionsList = options[questionIndex]
            questionText.text = question
            radioGroup.removeAllViews()
            for (option in optionsList) {
                val radioButton = RadioButton(requireContext())
                radioButton.text = option
                radioGroup.addView(radioButton)
            }
        }

        updateQuestion()

        submitButton.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            if (selectedId != -1) {
                val selectedRadioButton: RadioButton = root.findViewById(selectedId)
                answers.add(selectedRadioButton.text.toString())
                questionIndex++
                if (questionIndex < questions.size) {
                    updateQuestion()
                } else {
                    saveResults()
                    showResults()
                }
            }
        }

        return root
    }

    private fun saveResults() {
        val sharedPreferences = requireContext().getSharedPreferences("quiz_history", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("last_result", answers.joinToString("\n"))
        editor.apply()
    }

    private fun countCorrectAnswers(): Int {
        var correctCount = 0
        for (i in answers.indices) {
            if (answers[i] == correctAnswers[i]) {
                correctCount++
            }
        }
        return correctCount
    }

    private fun showResults() {
        val correctCount = countCorrectAnswers()
        val resultsBuilder = StringBuilder()
        for (i in questions.indices) {
            resultsBuilder.append("Question ${i + 1}: ${questions[i]}\n")
            resultsBuilder.append("Your answer: ${answers.getOrNull(i) ?: "No answer"}\n")
            resultsBuilder.append("Correct answer: ${correctAnswers[i]}\n\n")
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Quiz Results")
            .setMessage("You answered ${answers.size} questions. Correct answers: $correctCount\n\n${resultsBuilder.toString()}")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }
}
