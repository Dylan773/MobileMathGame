package com.example.mathgame.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.mathgame.R
import java.lang.NullPointerException

/**
 * Activity that prompts the user to input their first name for this game sessions. Whilst
 * highlighting the key components of the application and how to play the quiz.
 *
 * @author Dylan Brand
 */
class StudentDetailActivity : AppCompatActivity() {
    companion object { var studentName = "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail)
    }

    /**
     * Checks if the user has input a name, validating that the name is not null.
     * Once a valid name has been input, the user is transitioned to the Quiz Screen Activity,
     * starting the quiz session.
     */
    fun startGame(view: View) {
        try {
            studentName = findViewById<EditText>(R.id.editTextStudentName).text.toString()

            // Student name null check
            if (studentName.isEmpty())
                throw NullPointerException("Name not entered")

            // If name is not null, then start the next activity
            val intent = Intent(this, QuizScreenActivity::class.java)
            startActivity(intent)

        } catch (exception: NullPointerException) {
            Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
        }
    }
}