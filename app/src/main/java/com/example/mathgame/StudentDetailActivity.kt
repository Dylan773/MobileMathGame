package com.example.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class StudentDetailActivity : AppCompatActivity() {
    var studentName: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail)
    }

    //TODO - change the description (timer) if not implemented
    fun startGame(view: View) {
        studentName = findViewById<EditText>(R.id.editTextStudentName).toString()
        //val intent = Intent(this, GameScreenActivity::class.java)
        val intent = Intent(this, QuizScreenActivity::class.java)
        startActivity(intent)
//        if (studentName.text.isEmpty())
//            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show()
//        else
//        studentName = findViewById<EditText>(R.id.editTextStudentName).toString()
//        startActivity(intent)
        //save name


    }






    //    val firstName = findViewById<EditText>(R.id.editTextPersonFName)
//    val lastName = findViewById<EditText>(R.id.editTextPersonLName)
//    val salary = findViewById<EditText>(R.id.editTextSalary)
//    val textNoOfEmployee = findViewById<TextView>(R.id.textViewNoOfEmployee)
//
//    try {
//        val myEmployee = Employee(firstName.text.toString(), lastName.text.toString(), salary.text.toString().toFloat()) // create new employee object
//        myEmployeeList.addEmployee(myEmployee) // add employee to employee list
//        textNoOfEmployee.text = "No Of Employee: " + myEmployeeList.getNoOfEmployee()
//
//        firstName.text.clear()
//        lastName.text.clear()
//        salary.text.clear()
//    }
//    catch(e: NumberFormatException) {
//        val toast = Toast.makeText(applicationContext, "Salary not accepted", Toast.LENGTH_LONG)
//        toast.show()
//    }
//    catch(e: IllegalArgumentException) {
//        val toast = Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG)
//        toast.show()
//    }
}