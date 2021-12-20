package com.example.mathgame.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mathgame.R

/**
 * This applications starting screen.
 *
 * @author Dylan Brand
 */
class StartScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)
    }

    /**
     * Upon click, transitions the user (student) to the Student Detail Activity, prompting the user
     * to input their name before quiz start.
     */
    fun startGameStudent(view: View) {
        val intent = Intent(this, StudentDetailActivity::class.java)
        startActivity(intent)
    }

    /**
     * Upon click, transitions the user (teacher) to the Admin Login Activity, prompting the user to
     * input their login details, allowing them to add new game questions.
     */
    fun startGameTeacher(view: View) {
        val intent = Intent(this, AdminLoginActivity::class.java)
        startActivity(intent)
    }
}