package com.example.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView
import com.example.mathgame.model.Answer
import com.example.mathgame.model.Question
import com.example.mathgame.model.QuestionPool
import kotlin.properties.Delegates

class QuizScreenActivity : AppCompatActivity() {

    lateinit var questionPool: QuestionPool
    lateinit var questionList: ArrayList<Question>
    lateinit var answerList: ArrayList<Answer>

    var questionIndex: Int = 0
    val questionLimit: Int = 14 //TODO - change this dynamically


    //move
    lateinit var gameQuestions: ArrayList<Question>
    lateinit var gameAnswers: ArrayList<Answer>
    var currentQuestionNo: Int = 0
    var currentQuestionID: Int = 0
    //lateinit var questionIDText: ArrayList<QuestionID>


    //var radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

    //TODO set progress bar value
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_screen)

        questionPool = QuestionPool(this)
        gameQuestions = questionPool.setGameQuestions()
        //gameAnswers = questionPool.getQuestionAnswers()
        answerList = questionPool.getAnswerList() // TODO

        //TODO
        currentQuestionID = gameQuestions.get(questionIndex).questionTypeID
        currentQuestionNo = gameQuestions.get(questionIndex).questionNumber

        // Topic TextView
        findViewById<TextView>(R.id.textViewCurrentTopic).text =
            "Topic: " + questionPool.getQuestionTopicDesc(currentQuestionID)

        // Question TextView
        findViewById<TextView>(R.id.textViewQuestionText).text =
            gameQuestions.get(questionIndex).questionText

        // Progress Bar
        findViewById<ProgressBar>(R.id.progressBar).max = gameQuestions.size
        findViewById<ProgressBar>(R.id.progressBar).setProgress(questionIndex + 1, true)

        // RadioButtons
        findViewById<RadioButton>(R.id.option1).text = "1"
        findViewById<RadioButton>(R.id.option2).text = "2"
        findViewById<RadioButton>(R.id.option3).text = "3"
        findViewById<RadioButton>(R.id.option4).text = "4"
        findViewById<RadioButton>(R.id.option5).text = "5"
        //populateOptionsTst()

    }

    //TODO - add the correct answer and 4 other answers to the list
    /**
     *
     */
    fun populateOptionsTst() {
        val allAnswers = ArrayList<Answer>()

        for (a in answerList)
            if (a.questionNumberID == gameQuestions.get(questionIndex).questionNumber)
                allAnswers.add(a)

        // Extract the correct answer
        val correctAnswer = allAnswers.filter { it.isCorrect == 1 }[0]
        allAnswers.remove(correctAnswer) // Remove from list?????
        allAnswers.shuffle() // Randomise the answers

        val questionAnswers = ArrayList<Answer>()
        questionAnswers.add(correctAnswer) // Add the correct answer to the list

        // Add the remaining (randomised) answers to the list until the list contains 5 elements.
        for (a in allAnswers)
            do {
                questionAnswers.add(a)
            } while (questionAnswers.size < 6)

        questionAnswers.shuffle()

        // Populate the RadioButton's
        findViewById<RadioButton>(R.id.option1).text = questionAnswers[0].answerText
        findViewById<RadioButton>(R.id.option2).text = questionAnswers[1].answerText
        findViewById<RadioButton>(R.id.option3).text = questionAnswers[2].answerText
        findViewById<RadioButton>(R.id.option4).text = questionAnswers[3].answerText
        findViewById<RadioButton>(R.id.option5).text = questionAnswers[4].answerText
    }


    /**
     *
     */
    fun buttonSubmit(view: View) {
        if (questionIndex + 1 != gameQuestions.size) {
            questionIndex++
            currentQuestionID = gameQuestions.get(questionIndex).questionTypeID

            // Topic TextView
            findViewById<TextView>(R.id.textViewCurrentTopic).text =
                "Topic: " + questionPool.getQuestionTopicDesc(currentQuestionID)

            // Question TextView
            findViewById<TextView>(R.id.textViewQuestionText).text =
                gameQuestions.get(questionIndex).questionText

            // Progress Bar
            findViewById<ProgressBar>(R.id.progressBar).setProgress(questionIndex + 1, true)

            // Radio Buttons
            findViewById<RadioButton>(R.id.option1).text = ""//questionAnswers()[0].answerText
            findViewById<RadioButton>(R.id.option2).text = ""//questionAnswers[1].answerText
            findViewById<RadioButton>(R.id.option3).text = ""//questionAnswers[2].answerText
            findViewById<RadioButton>(R.id.option4).text = ""//questionAnswers[3].answerText
            findViewById<RadioButton>(R.id.option5).text = ""//questionAnswers[4].answerText

        } else {
            val intent = Intent(this, StartScreenActivity::class.java)
            startActivity(intent) // change to result screen
        }
    }


}
