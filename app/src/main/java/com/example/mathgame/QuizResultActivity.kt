package com.example.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class QuizResultActivity : AppCompatActivity() {
     private var studentName = StudentDetailActivity.studentName

    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)

        val playerScore = intent.getStringExtra("PLAYER_SCORE")
        val displayUserScore = "${studentName}'s score: ${playerScore}/14"

        findViewById<TextView>(R.id.textViewPlayerOverview).text = displayUserScore
            //"${studentName}'s score: ${message}/14"
    }

    /**
     * On click, returns the user to the applications main menu activity.
     */
    fun buttonMainMenu(view: View) {
        val intent = Intent(this, StartScreenActivity::class.java)
        startActivity(intent)
    }

}