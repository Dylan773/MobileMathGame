package com.example.mathgame.controller

import android.content.Intent
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


class AdminQuestionActivity : AppCompatActivity() {
    private lateinit var questionPool: QuestionPool
    private lateinit var answerList: ArrayList<Answer>
    private lateinit var gameQuestions: ArrayList<Question>
    private val db = MathDataBase(this)

    // Question Identifiers
    private lateinit var questionText: String
    private var questionTopic: Int = 0

    // Answer Identifiers
    private lateinit var correctOption: String
    private lateinit var optionTwo: String
    private lateinit var optionThree: String
    private lateinit var optionFour: String
    private lateinit var optionFive: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_question)

        // Database Initialisation
        questionPool = QuestionPool(this)
        gameQuestions = questionPool.setGameQuestions()
        answerList = questionPool.getAnswerList()

//        // Question Identifiers
//        questionText = findViewById<EditText>(R.id.editTextQuestionText).text.toString()
//        questionTopic = findViewById<EditText>(R.id.editTextQuestionTopic).text.toString().toInt()
//
//        // Answer Identifiers
//        correctOption = findViewById<EditText>(R.id.editTextCorrectAnswer).text.toString()
//        optionTwo = findViewById<EditText>(R.id.editTexOption2).text.toString()
//        optionThree = findViewById<EditText>(R.id.editTextOption3).text.toString()
//        optionFour = findViewById<EditText>(R.id.editTextOption4).text.toString()
//        optionFive = findViewById<EditText>(R.id.editTextOption5).text.toString()
    }

    // TODO - validation, no items can be null + RESET TEXT VIEWS
    fun buttonAdd(view: View) {
        val setNewQuestionNo = db.getAllQuestions().size + 1
            //questionPool.getQuestionList().size + 1

        // Question Identifiers
        questionText = findViewById<EditText>(R.id.editTextQuestionText).text.toString()
        questionTopic = findViewById<EditText>(R.id.editTextQuestionTopic).text.toString().toInt()

        // Answer Identifiers
        correctOption = findViewById<EditText>(R.id.editTextCorrectAnswer).text.toString()
        optionTwo = findViewById<EditText>(R.id.editTexOption2).text.toString()
        optionThree = findViewById<EditText>(R.id.editTextOption3).text.toString()
        optionFour = findViewById<EditText>(R.id.editTextOption4).text.toString()
        optionFive = findViewById<EditText>(R.id.editTextOption5).text.toString()

        try {
//            if (viewIsEmpty())
//                throw NullPointerException("Please fill all fields")

//            if (questionTopic < 1 || questionTopic > 7)
//                throw NumberFormatException("Please enter a topic number between 1 and 7")

            if (questionTopic.toInt() < 1 || questionTopic.toInt() > 7)
                throw InvalidNumberException("Please enter a topic number between 1 and 7")

            // Create the question
            val question = Question(-1, questionTopic.toInt(), setNewQuestionNo, questionText)

            // Create the five options for the question
            val correctOptionAns = Answer(-1, setNewQuestionNo, correctOption, 1)
            val optionAns2 = Answer(-1, setNewQuestionNo, optionTwo, 0)
            val optionAns3 = Answer(-1, setNewQuestionNo, optionThree, 0)
            val optionAns4 = Answer(-1, setNewQuestionNo, optionFour, 0)
            val optionAns5 = Answer(-1, setNewQuestionNo, optionFive, 0)

            val questionResult = db.addQuestion(question)
            val answerResult = db.addAnswerOptions(
                correctOptionAns, optionAns2, optionAns3,
                optionAns4, optionAns5
            )

            // Adding each question and answer to the database
            if (questionResult == 1 && answerResult == 1)
                Toast.makeText(this, "Question added successfully", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Error adding question", Toast.LENGTH_SHORT).show()



        } catch (exception: NullPointerException) {
            Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
        } catch (exception: NumberFormatException) {
            Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
        } catch (exception: InvalidNumberException) {
            Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
        }
    }

//    fun buttonAdd(view: View) {
//        Toast.makeText(this, db.getAllQuestions().size.toString(), Toast.LENGTH_LONG).show()
//        //questionPool.getQuestionList().lastIndex
//    }

    private fun viewIsEmpty(): Boolean {
        var b = false

        // Question
        if (questionText.isEmpty()) b = true
        if (questionTopic.toString().isEmpty()) b = true

        // Answers
        if (correctOption.isEmpty()) b = true
        if (optionTwo.isEmpty()) b = true
        if (optionThree.isEmpty()) b = true
        if (optionFour.isEmpty()) b = true
        if (optionFive.isEmpty()) b = true
        
        return b
    }
}