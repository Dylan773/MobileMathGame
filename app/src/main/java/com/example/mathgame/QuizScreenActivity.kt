package com.example.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.mathgame.model.Answer
import com.example.mathgame.model.Question
import com.example.mathgame.model.QuestionPool

class QuizScreenActivity : AppCompatActivity() {

    lateinit var questionPool: QuestionPool
    lateinit var questionList: ArrayList<Question>
    lateinit var answerList: ArrayList<Answer>

    var index: Int = 0
    val questionLimit: Int = 14 //TODO - change this dynamically


    //move
    lateinit var gameQuestions: ArrayList<Question>
    lateinit var gameAnswers: ArrayList<Answer>
    var currentQuestionNo: Int = 0
    var currentQuestionID: Int = 0
    //lateinit var questionIDText: ArrayList<QuestionID>


    //var radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

    // TODO - create an array list that obtains all the relevant answers and print each one in a text view to see if theres any duplicates
    //TODO set progress bar value
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_screen)

        questionPool = QuestionPool(this)
        gameQuestions = questionPool.setGameQuestions()
        //gameAnswers = questionPool.getQuestionAnswers()
        answerList = questionPool.getAnswerList() // TODO

        //TODO
        currentQuestionID = gameQuestions.get(index).questionTypeID
        currentQuestionNo =
            gameQuestions.get(index).questionNumber // The questions number in the database

        // Topic TextView
        findViewById<TextView>(R.id.textViewCurrentTopic).text =
            "Topic: " + questionPool.getQuestionTopicDesc(currentQuestionID)

        // Question TextView
        findViewById<TextView>(R.id.textViewQuestionText).text =
            gameQuestions.get(index).questionText

        // Progress Bar
        findViewById<ProgressBar>(R.id.progressBar).max = gameQuestions.size
        findViewById<ProgressBar>(R.id.progressBar).setProgress(index + 1, true)

        // RadioButtons
//        findViewById<RadioButton>(R.id.option1).text = "1"
//        findViewById<RadioButton>(R.id.option2).text = "2"
//        findViewById<RadioButton>(R.id.option3).text = "3"
//        findViewById<RadioButton>(R.id.option4).text = "4"
//        findViewById<RadioButton>(R.id.option5).text = "5"
        //populateOptionsTst()
        populateRadioButtons()

    }


    fun populateRadioButtons() {
        val filteredAnswers = ArrayList<Answer>()
        for (ans in answerList)
            if (ans.questionNumberID == gameQuestions.get(index).questionNumber)
                filteredAnswers.add(ans)
        filteredAnswers.shuffle() // Randomises all filtered answers

        val answersMap = mapOf(
            "one" to filteredAnswers[0].answerText, "two" to filteredAnswers[1].answerText,
            "three" to filteredAnswers[2].answerText, "four" to filteredAnswers[3].answerText,
            "five" to filteredAnswers[4].answerText, "six" to filteredAnswers[5].answerText,
            "seven" to filteredAnswers[6].answerText, "eight" to filteredAnswers[7].answerText
        )

        val correctAnswer: Answer = filteredAnswers.filter { it.isCorrect == 1 }[0]
        filteredAnswers.remove(correctAnswer) // Remove the correct answer from the remaining pool

        val setAnswerOptions = ArrayList<Answer>()
        setAnswerOptions.add(correctAnswer) // Add the correct answer to the selection pool
        setAnswerOptions.add(filteredAnswers[0]) // Add 4 randomised answers (in-correct) to the options pool
        setAnswerOptions.add(filteredAnswers[1])
        setAnswerOptions.add(filteredAnswers[2])
        setAnswerOptions.add(filteredAnswers[3])

        // Shuffle the options so the correct answer is not statically displayed each question
        setAnswerOptions.shuffle()

        // Clear all radio buttons before assignment, and then assign each button an answer
        findViewById<RadioGroup>(R.id.radioGroup).clearCheck()
        findViewById<RadioButton>(R.id.option1).text = setAnswerOptions[0].answerText
        findViewById<RadioButton>(R.id.option2).text = setAnswerOptions[1].answerText
        findViewById<RadioButton>(R.id.option3).text = setAnswerOptions[2].answerText
        findViewById<RadioButton>(R.id.option4).text = setAnswerOptions[3].answerText
        findViewById<RadioButton>(R.id.option5).text = setAnswerOptions[4].answerText
    }

    /**
     *
     */
    fun buttonSubmit(view: View) {
        if (findViewById<RadioGroup>(R.id.radioGroup).checkedRadioButtonId != -1 && index + 1 != gameQuestions.size) {
            index++
            currentQuestionID = gameQuestions.get(index).questionTypeID

            // Topic TextView
            findViewById<TextView>(R.id.textViewCurrentTopic).text =
                "Topic: " + questionPool.getQuestionTopicDesc(currentQuestionID)


            // TODO - could probably add this in the populte method to reduce repetition/ setting the question each time
            // Question TextView
            findViewById<TextView>(R.id.textViewQuestionText).text =
                gameQuestions.get(index).questionText

            // Progress Bar
            findViewById<ProgressBar>(R.id.progressBar).setProgress(index + 1, true)

            populateRadioButtons()

        } else {
//            val intent = Intent(this, StartScreenActivity::class.java)
//            startActivity(intent) // change to result screen
            Toast.makeText(this, "Please select an option", Toast.LENGTH_LONG).show()
            // upon final question, use putExtra to transfer user name???
        }
    }

//    fun checkAnswer() {
//        if (findViewById<RadioGroup>(R.id.radioGroup).checkedRadioButtonId.)
//    }
}


//// Extract the correct answer
//val correctAnswer = allAnswers.filter { it.isCorrect == 1 }[0]
//allAnswers.remove(correctAnswer) // Remove from list?????
//allAnswers.shuffle() // Randomise the answers
