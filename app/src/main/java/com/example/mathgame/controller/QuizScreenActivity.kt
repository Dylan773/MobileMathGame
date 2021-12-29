package com.example.mathgame.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.mathgame.R
import com.example.mathgame.model.Answer
import com.example.mathgame.model.Question
import com.example.mathgame.model.QuestionPool

/**
 * This applications quiz screen, prompting the user with a question and five randomised answers.
 * Obtaining all questions and answers from an SQLite database.
 *
 * @author Dylan Brand
 */
class QuizScreenActivity : AppCompatActivity() {
    // Database specific variables
    private lateinit var questionPool: QuestionPool
    private lateinit var answerList: ArrayList<Answer>
    private lateinit var gameQuestions: ArrayList<Question>
    private lateinit var correctAnswer: Answer

    // Game screen specific variables, all with a default value of 0
    private var index: Int = 0
    private var currentQuestionNo: Int = 0
    private var currentQuestionID: Int = 0

    companion object { var playerScore = 0 }

    /**
     * Upon activity creation, the question pool is initialised with data obtained from the
     * database and populates the view, displaying the first question to the user.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_screen)

        // Database Initialisation
        questionPool = QuestionPool(this)
        gameQuestions = questionPool.setGameQuestions()
        answerList = questionPool.getAnswerList()

        // Question Identifiers
        currentQuestionID = gameQuestions.get(index).questionTypeID
        currentQuestionNo = gameQuestions.get(index).questionNumber

        /* Forces the score to zero upon activity start/restart,
        useful if the user tries to go back using non app controls or emulator crashes */
        playerScore = 0

        /* Display Initial Question */
        findViewById<TextView>(R.id.textViewCurrentTopic).text =
            "Topic: ${questionPool.getQuestionTopicDesc(currentQuestionID)}"

        findViewById<TextView>(R.id.textViewQuestionText).text =
            gameQuestions.get(index).questionText

        // ProgressBar Initialisation
        findViewById<ProgressBar>(R.id.progressBar).max = gameQuestions.size
        findViewById<ProgressBar>(R.id.progressBar).setProgress(index + 1, true)

        val questionCount = "${index + 1}/${gameQuestions.size}"
        findViewById<TextView>(R.id.textViewQuestionCount).text = questionCount

        populateRadioButtons()
    }

    /**
     * Upon click of the submit button, if the user has not selected an option from the radio group
     * a message is displayed to the user prompting them to select one of the five options.
     *
     * If the user has selected an option, the selected option's string value is then compared to
     * the current question's correct answer string value, once the answer is checked the next
     * question is displayed to the user. Finally, if the last is currently displayed, the user
     * will be transitioned to the Quiz Result Activity.
     */
    fun buttonSubmit(view: View) {
        if (findViewById<RadioGroup>(R.id.radioGroup).checkedRadioButtonId != -1) { // if an option has been selected
            if (index + 1 != gameQuestions.size) {
                index++
                currentQuestionID = gameQuestions.get(index).questionTypeID

                // Checks if the selected option is correct before the next question is displayed
                checkScore()

                /* Setting the view for the next question */
                findViewById<TextView>(R.id.textViewCurrentTopic).text =
                    "Topic: ${questionPool.getQuestionTopicDesc(currentQuestionID)}"

                findViewById<TextView>(R.id.textViewQuestionText).text =
                    gameQuestions.get(index).questionText

                findViewById<ProgressBar>(R.id.progressBar).progress = index + 1

                val questionCount = "${index + 1}/${gameQuestions.size}"
                findViewById<TextView>(R.id.textViewQuestionCount).text = questionCount

                populateRadioButtons()

            } else { // If the last question has been submitted
                checkScore()

                val intent = Intent(this, QuizResultActivity::class.java)
                startActivity(intent) // Companion object used to obtain player score
            }
        } else // If the user has not selected a RadioButton
            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
    }

    /**
     * Iterates through all stored answers', extracting each answer where the answer's
     * questionNumberID matches the current question's number ID, adding each answer to a filtered
     * ArrayList.
     *
     * The correct answer is then extracted and removed from the filtered list, and added to a new
     * options ArrayList, whereas the remaining filtered answers are shuffled and added to the
     * new options list until the list has five elements (answers).
     *
     * Finally, the game options ArrayList is shuffled to randomise the order of answers,
     * each RadioButton on the quiz screen is then populated respectively using the corresponding
     * index of the ArrayList.
     */
    private fun populateRadioButtons() {
        val filteredAnswers = ArrayList<Answer>()

        // Iterates through all application answers, extracting answers only applicable to the current question
        for (ans in answerList)
            if (ans.questionNumberID == gameQuestions[index].questionNumber)
                filteredAnswers.add(ans)
        filteredAnswers.shuffle() // Randomises all filtered answers

        // Extract the correct answer, and remove that answer from the filtered answer pool
        correctAnswer = filteredAnswers.filter { it.isCorrect == 1 }[0]
        filteredAnswers.remove(correctAnswer)

        // Create an ArrayList of randomised answers to display to the user
        val setAnswerOptions = ArrayList<Answer>()
        setAnswerOptions.add(correctAnswer) // Add the correct answer to the list
        setAnswerOptions.add(filteredAnswers[0]) // Then add 4 randomised answers to the list
        setAnswerOptions.add(filteredAnswers[1])
        setAnswerOptions.add(filteredAnswers[2])
        setAnswerOptions.add(filteredAnswers[3])

        // Shuffle the options so the correct answer is not statically displayed each question
        setAnswerOptions.shuffle()

        // Clear all radio buttons before assignment, and then assign each button an answer/text
        findViewById<RadioGroup>(R.id.radioGroup).clearCheck()
        findViewById<RadioButton>(R.id.option1).text = setAnswerOptions[0].answerText
        findViewById<RadioButton>(R.id.option2).text = setAnswerOptions[1].answerText
        findViewById<RadioButton>(R.id.option3).text = setAnswerOptions[2].answerText
        findViewById<RadioButton>(R.id.option4).text = setAnswerOptions[3].answerText
        findViewById<RadioButton>(R.id.option5).text = setAnswerOptions[4].answerText
    }

    //        val answersMap = mapOf(
//            "one" to filteredAnswers[0].answerText, "two" to filteredAnswers[1].answerText,
//            "three" to filteredAnswers[2].answerText, "four" to filteredAnswers[3].answerText,
//            "five" to filteredAnswers[4].answerText, "six" to filteredAnswers[5].answerText,
//            "seven" to filteredAnswers[6].answerText, "eight" to filteredAnswers[7].answerText
//        )
    /**
     * Obtains the text values of the currently selected RadioButton and the current question's answer,
     * comparing both values for equality.
     *
     * If both String values are equal, the player score is incremented by 1, otherwise the
     * player's score remains unchanged.
     */
    private fun checkScore() {
        val selectedOptionID = findViewById<RadioGroup>(R.id.radioGroup).checkedRadioButtonId
        val selectedOptionText = findViewById<RadioButton>(selectedOptionID).text

        if (selectedOptionText.equals(correctAnswer.answerText))
            playerScore++
    }
}