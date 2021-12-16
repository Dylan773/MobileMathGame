package com.example.mathgame.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mathgame.R

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
        //val intent = Intent(this, QuizResultActivity::class.java)
        val intent = Intent(this, AdminLoginActivity::class.java)
        startActivity(intent)
    }
}