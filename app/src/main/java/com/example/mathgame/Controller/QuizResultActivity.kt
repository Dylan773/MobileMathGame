package com.example.mathgame.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.example.mathgame.R
import com.example.mathgame.model.MathDataBase
import com.example.mathgame.model.Student
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


class QuizResultActivity : AppCompatActivity() {
    private val db = MathDataBase(this)
    private val studentName = StudentDetailActivity.studentName
    private val finalScore = QuizScreenActivity.playerScore

    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)

        val filteredName =
            studentName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

        val displayUserScore = "${filteredName}, you scored: ${finalScore}/14"
        findViewById<TextView>(R.id.textViewPlayerOverview).text = displayUserScore

        // Initialising the pie chart
        val pie = AnyChart.pie()
        pie.fill("aquastyle")

        // Data to be displayed on the pie chart
        val data: MutableList<DataEntry> = ArrayList()
        data.add(ValueDataEntry("Correct", finalScore))
        data.add(ValueDataEntry("Incorrect", 14 - finalScore)) // change to variable

        pie.data(data)

        // Connecting the pie chart to the view
        val anyChartView = findViewById<AnyChartView>(R.id.any_chart_view)
        anyChartView.setChart(pie)
    }

    /**
     * On click, returns the user to the applications main menu activity.
     */
    fun buttonMainMenu(view: View) {
        val date = LocalDate.now().toString()

        // Add the current student's session data to the database
        val student = Student(-1, studentName, finalScore, date)
        val result = db.addStudentRecord(student)
        when (result) {
            1 -> {
                Toast.makeText(this, "Submitting record", Toast.LENGTH_SHORT).show()

                // Creates a 1.5s delay between screen transition
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    val intent = Intent(this, StartScreenActivity::class.java)
                    startActivity(intent)
                }, 1500)
                
//                val intent = Intent(this, StartScreenActivity::class.java)
//                startActivity(intent)
            }
            -1 -> Toast.makeText(this, "Error submitting record", Toast.LENGTH_SHORT).show()
        }
    }
}