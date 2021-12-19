package com.example.mathgame.Controller

import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.mathgame.R
import com.example.mathgame.model.Admin
import com.example.mathgame.model.MathDataBase
import java.time.LocalDate
import java.util.*

class AdminLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)
    }

    // TODO - change database username to lowercase and re-upload
    fun buttonLogin(view: View) {

    }

    fun validateCredentials(view: View) {
        // if correct, login. otherwoise display toast message

        val date = LocalDate.now()

        val userName = findViewById<EditText>(R.id.editTextUsername).text.toString()
        val userPassword = findViewById<EditText>(R.id.editTextPassword).text.toString()

        if (userName.isEmpty() || userPassword.isEmpty())
            Toast.makeText(this, "Please insert Username and Password", Toast.LENGTH_SHORT).show()
        else {
            val db = MathDataBase(this)
            val result = db.getAdmin(Admin(-1, "", userName, userPassword))

//            // TODO - when state
//            if (result == -1)
//                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
//            else if (result == -2)
//                Toast.makeText(this, "Database error", Toast.LENGTH_SHORT).show()
//            else
//                Toast.makeText(this, "Succesfull", Toast.LENGTH_SHORT).show()
//            // start new activity here... question screen

            when (result) {
                1 -> {
                    val intent = Intent(this, AdminQuestionActivity::class.java)
                    startActivity(intent)
                }
                -1 -> Toast.makeText(this, "Details don't match a user", Toast.LENGTH_SHORT).show()
                -2 -> Toast.makeText(this, "Database error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}