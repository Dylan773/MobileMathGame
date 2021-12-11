package com.example.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.view.View
import android.widget.EditText
import com.example.mathgame.model.Answer
import com.example.mathgame.model.Question
import com.example.mathgame.model.QuestionPool

class StartScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)
    }

    /**
     *
     */
    fun startGameStudent(view: View) {
        val intent = Intent(this, StudentDetailActivity::class.java)
        startActivity(intent)
    }

    /**
     *
     */
    fun startGameTeacher(view: View) {

    }

//    fun sendMessage(view: View) {
//        val editText = findViewById<EditText>(R.id.editTextTextPersonName)
//        val message = editText.text.toString()
//        val intent = Intent(this, DisplayMessageActivity::class.java).apply {
//            putExtra(AlarmClock.EXTRA_MESSAGE, message)
//        }
//        startActivity(intent)
//    }
}