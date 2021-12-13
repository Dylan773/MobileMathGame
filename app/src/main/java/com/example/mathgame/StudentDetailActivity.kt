package com.example.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import java.lang.NullPointerException

/**
 *
 */
class StudentDetailActivity : AppCompatActivity() {
    var studentName: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail)
    }

    //TODO - change the description (timer) if not implemented
    /**
     *
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
            val toast = Toast.makeText(this, exception.message, Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}

