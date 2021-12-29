package com.example.mathgame.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.mathgame.InvalidNumberException
import com.example.mathgame.R
import com.example.mathgame.model.Answer
import com.example.mathgame.model.MathDataBase
import com.example.mathgame.model.Question
import com.example.mathgame.model.QuestionPool
import java.lang.NullPointerException
import java.lang.NumberFormatException

/**
 * Admin Question Activity that allows an authorised admin to input new questions and answers to
 * the database.
 *
 * @author Dylan Brand
 */
class AdminQuestionActivity : AppCompatActivity() {
    private lateinit var questionPool: QuestionPool
    private lateinit var answerList: ArrayList<Answer>
    private lateinit var gameQuestions: ArrayList<Question>
    private val db = MathDataBase(this)

    // Question Identifiers
    private var questionText: String = ""
    private var questionTopicText: String = ""

    // Answer Identifiers
    private var correctOption: String = ""
    private var optionTwo: String = ""
    private var optionThree: String = ""
    private var optionFour: String = ""
    private var optionFive: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_question)

        // Database Initialisation
        questionPool = QuestionPool(this)
        gameQuestions = questionPool.setGameQuestions()
        answerList = questionPool.getAnswerList()
    }

    /**
     * Upon activation of the submit button, all EditText's are checked for user input
     * (String values). If one or more EditText's are null or out of bounds (QuestionTopicID),
     * an exception is thrown and caught, informing the user via a prompt of the required action.
     *
     * If all user input is within bounds and is of an accepted data type, each question and
     * answers will be added to their respective tables within the database. Then all EditText's
     * will be reset to null for new user input.
     */
    fun buttonAdd(view: View) {
        val setNewQuestionNo = db.getAllQuestions().size + 1

        try {
            // Question Identifiers
            questionText = findViewById<EditText>(R.id.editTextQuestionText).text.toString()
            questionTopicText = findViewById<EditText>(R.id.editTextQuestionTopic).text.toString()

            // Answer Identifiers
            correctOption = findViewById<EditText>(R.id.editTextCorrectAnswer).text.toString()
            optionTwo = findViewById<EditText>(R.id.editTexOption2).text.toString()
            optionThree = findViewById<EditText>(R.id.editTextOption3).text.toString()
            optionFour = findViewById<EditText>(R.id.editTextOption4).text.toString()
            optionFive = findViewById<EditText>(R.id.editTextOption5).text.toString()

            // Catches invalid number input
            if (questionTopicText.toInt() !in 1..7)
                throw InvalidNumberException("Please enter a valid TopicID")

            // Catches empty input for topicID
            if (questionTopicText.isEmpty())
                throw NullPointerException("TopicID cannot be empty")

            val editTexts = arrayOf(
                questionText, correctOption, optionTwo,
                optionThree, optionFour, optionFive
            )

            // Iterates through the above array, throwing a NPE if any EditText's are empty
            for (s in editTexts.indices) {
                if (editTexts[s].isEmpty())
                    throw NullPointerException("Complete all fields")
            }

            // Create the question if validation is complete
            val question = Question(-1, questionTopicText.toInt(), setNewQuestionNo, questionText)

            // Create the five answers for the question
            val correctOptionAns = Answer(-1, setNewQuestionNo, correctOption, 1)
            val optionAns2 = Answer(-1, setNewQuestionNo, optionTwo, 0)
            val optionAns3 = Answer(-1, setNewQuestionNo, optionThree, 0)
            val optionAns4 = Answer(-1, setNewQuestionNo, optionFour, 0)
            val optionAns5 = Answer(-1, setNewQuestionNo, optionFive, 0)

            // Add each record to the corresponding tables in the database
            val questionResult = db.addQuestion(question)
            val answerResult = db.addAnswerOptions(
                correctOptionAns, optionAns2, optionAns3,
                optionAns4, optionAns5
            )

            // If the addition was successful, clear all EditText's for new input
            if (questionResult == 1 && answerResult == 1) {
                Toast.makeText(this, "Adding Question", Toast.LENGTH_LONG).show()

                // Creates a 1.5s delay whilst adding record, and resets this screens UI
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    resetUI()
                }, 1500)
            } else
                Toast.makeText(this, "Error adding question", Toast.LENGTH_SHORT).show()

        } catch (exception: NullPointerException) {
            Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
        } catch (exception: NumberFormatException) {
            Toast.makeText(this, "Invalid TopicID value", Toast.LENGTH_SHORT).show()
        } catch (exception: InvalidNumberException) {
            Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Resets all EditText's on this activity to a default (empty) state.
     */
    private fun resetUI() {
        // Clears all question EditText's upon question addition
        findViewById<EditText>(R.id.editTextQuestionText).setText("")
        findViewById<EditText>(R.id.editTextQuestionTopic).setText("")

        // Clears all answer EditText's upon answer addition
        findViewById<EditText>(R.id.editTextCorrectAnswer).setText("")
        findViewById<EditText>(R.id.editTexOption2).setText("")
        findViewById<EditText>(R.id.editTextOption3).setText("")
        findViewById<EditText>(R.id.editTextOption4).setText("")
        findViewById<EditText>(R.id.editTextOption5).setText("")
    }
}