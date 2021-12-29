package com.example.mathgame.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.mathgame.R
import com.example.mathgame.model.Admin
import com.example.mathgame.model.MathDataBase

/**
 * Admin Login Activity that prompts the user/admin to input their username and password.
 * The username and password are then compared for equality against all admin records stored in the
 * Admin table in the database.
 *
 * If the records match an existing user, the user is transitioned to the Admin Question Activity,
 * allowing the user to add new quiz questions. Otherwise, the user will be prompted with a
 * message identifying the input details have not matched a record.
 *
 * @author Dylan Brand
 */
class AdminLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)
    }

    /**
     * Obtains the input username and password from the user, comparing these values to existing
     * records in the Admin table, an action is then performed depending on the comparison outcome.
     */
    fun validateCredentials(view: View) {
        val userName = findViewById<EditText>(R.id.editTextUsername).text.toString()
        val userPassword = findViewById<EditText>(R.id.editTextPassword).text.toString()

        if (userName.isEmpty() || userPassword.isEmpty())
            Toast.makeText(this, "Please insert Username and Password", Toast.LENGTH_SHORT).show()
        else {
            val db = MathDataBase(this)

            val result = db.getAdmin(Admin(-1, "", userName, userPassword))
            when (result) {
                1 -> {
                    Toast.makeText(this, "Validating User", Toast.LENGTH_SHORT).show()
                    // Creates a 1.5s delay between screen transition
                    val handler = Handler(Looper.getMainLooper())
                    handler.postDelayed({
                        val intent = Intent(this, AdminQuestionActivity::class.java)
                        startActivity(intent)
                    }, 1500)
                }
                -1 -> Toast.makeText(this, "Details don't match a user", Toast.LENGTH_SHORT).show()
                -2 -> Toast.makeText(this, "Database error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}