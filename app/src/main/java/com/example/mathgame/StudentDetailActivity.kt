package com.example.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class StudentDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail)
    }

    fun startGame(view: View) {
        val studentName = findViewById<EditText>(R.id.editTextStudentName)
        //val intent = Intent(this, GameScreenActivity::class.java)
        val intent = Intent(this, QuizScreenActivity::class.java)
        startActivity(intent)
//        if (studentName.text.isEmpty())
//            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show()
//        else
//        startActivity(intent)
        //save name
    }
}