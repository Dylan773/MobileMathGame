package com.example.mathgame

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
//        if (studentName.text.isEmpty())
//            Toast.makeText(this, )
        // button validation (no empty name) - findByViewId(textView)
    }
}