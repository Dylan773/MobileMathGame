package com.example.mathgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.mathgame.model.Answer
import com.example.mathgame.model.Question
import com.example.mathgame.model.QuestionPool

class QuizScreenActivity : AppCompatActivity() {

    lateinit var questionPool: QuestionPool
    lateinit var questionList: ArrayList<Question>
    lateinit var answerList: ArrayList<Answer>

    var questionIndex: Int = 0
    val questionLimit: Int = 14 //TODO - change this dynamically

    //move
    lateinit var gameQuestions: ArrayList<Question>
    lateinit var currentQuestion: String
    var currentQuestionID: Int = 0
    //lateinit var questionIDText: ArrayList<QuestionID>



    //TODO set progress bar value
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_screen)

        questionPool = QuestionPool(this)
        gameQuestions = questionPool.setGameQuestions()

        //TODO
        currentQuestionID = gameQuestions.get(questionIndex).questionTypeID
        //currentQuestion = gameQuestions.get(questionIndex).questionText

        // Topic TextView
        findViewById<TextView>(R.id.textViewCurrentTopic).text = "Topic: " + questionPool.getQuestionTopicDesc(currentQuestionID)

        // Question TextView
        findViewById<TextView>(R.id.textViewQuestionText).text = gameQuestions.get(questionIndex).questionText


        // Progress Bar
        findViewById<ProgressBar>(R.id.progressBar).max = 14
        findViewById<ProgressBar>(R.id.progressBar).setProgress(1, true)



//        questionList.shuffle() // Shuffles all question in this list
//        findViewById<TextView>(R.id.textViewQuestionText).text = questionList.get(48).questionText

    }


    fun idk() {

    }

    /**
     *
     */
    fun buttonSubmit(view: View) {
        questionIndex++
        currentQuestionID = gameQuestions.get(questionIndex).questionTypeID

        // Topic TextView
        findViewById<TextView>(R.id.textViewCurrentTopic).text = "Topic: " + questionPool.getQuestionTopicDesc(currentQuestionID)

        // Question TextView
        findViewById<TextView>(R.id.textViewQuestionText).text = gameQuestions.get(questionIndex).questionText

        // Progress Bar
        findViewById<ProgressBar>(R.id.progressBar).setProgress(questionIndex + 1, true)

    }
}


//        questionList = questionPool.getQuestionList() // Get all questions
//        answerList = questionPool.getAnswerList() // Get all answers