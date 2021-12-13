package com.example.mathgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class QuizResultActivity : AppCompatActivity() {
     //lateinit var studentName: String
     private var studentName = StudentDetailActivity.studentName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)


        val message = intent.getStringExtra("PLAYER_SCORE")

        findViewById<TextView>(R.id.textViewPlayerOverview).text =
            "${studentName}'s score: ${message}/14"
    }
}