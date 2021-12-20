package com.example.mathgame.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


    // TODO - validation, no items can be null + RESET TEXT VIEWS
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
                throw NumberFormatException("Please enter a valid TopicID") // numberforat

            // Catches empty input
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

            // Create the question
            val question = Question(-1, questionTopicText.toInt(), setNewQuestionNo, questionText)

            // Create the five options for the question
            val correctOptionAns = Answer(-1, setNewQuestionNo, correctOption, 1)
            val optionAns2 = Answer(-1, setNewQuestionNo, optionTwo, 0)
            val optionAns3 = Answer(-1, setNewQuestionNo, optionThree, 0)
            val optionAns4 = Answer(-1, setNewQuestionNo, optionFour, 0)
            val optionAns5 = Answer(-1, setNewQuestionNo, optionFive, 0)

            // Add the each record to their database
            val questionResult = db.addQuestion(question)
            val answerResult = db.addAnswerOptions(
                correctOptionAns, optionAns2, optionAns3,
                optionAns4, optionAns5
            )

            // Adding each question and answer to the database
            if (questionResult == 1 && answerResult == 1) {
                Toast.makeText(this, "Question added successfully", Toast.LENGTH_LONG).show()

                //TODO - clear fields
                findViewById<EditText>(R.id.editTextQuestionText).setText("")
                findViewById<EditText>(R.id.editTextQuestionTopic).setText("")

                // Answer Identifiers
                findViewById<EditText>(R.id.editTextCorrectAnswer).setText("")
                findViewById<EditText>(R.id.editTexOption2).setText("")
                findViewById<EditText>(R.id.editTextOption3).setText("")
                findViewById<EditText>(R.id.editTextOption4).setText("")
                findViewById<EditText>(R.id.editTextOption5).setText("")

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

}

//    try {
//        StudentDetailActivity.studentName = findViewById<EditText>(R.id.editTextStudentName).text.toString()
//
//        // Student name null check
//        if (StudentDetailActivity.studentName.isEmpty())
//            throw NullPointerException("Name not entered")
//
//        // If name is not null, then start the next activity
//        val intent = Intent(this, QuizScreenActivity::class.java)
//        startActivity(intent)
//
//    } catch (exception: NullPointerException) {
//        Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
//    }dfg      f

